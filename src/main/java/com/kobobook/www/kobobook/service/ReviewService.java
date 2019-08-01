package com.kobobook.www.kobobook.service;

import com.kobobook.www.kobobook.domain.Item;
import com.kobobook.www.kobobook.domain.Member;
import com.kobobook.www.kobobook.domain.Review;
import com.kobobook.www.kobobook.dto.ReviewDTO;
import com.kobobook.www.kobobook.elasticsearch.EsItem;
import com.kobobook.www.kobobook.exception.UnauthorizedException;
import com.kobobook.www.kobobook.repository.EsItemRepository;
import com.kobobook.www.kobobook.repository.ItemRepository;
import com.kobobook.www.kobobook.repository.MemberRepository;
import com.kobobook.www.kobobook.repository.ReviewRepository;
import lombok.AllArgsConstructor;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.GetQuery;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.prefixQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

@Service
@AllArgsConstructor
public class ReviewService {

    private ReviewRepository reviewRepository;

    private ItemRepository itemRepository;

    private MemberRepository memberRepository;

    private EsItemRepository esItemRepository;

    private ModelMapper modelMapper;

    /*
    * 상품 리뷰 작성
    * */
    @Transactional
    public Map<String, Object> createReview(Integer memberId, Review review) {
        Member member = memberRepository.findById(memberId).orElse(null);
        Item item = itemRepository.findById(review.getItem().getId()).orElse(null);

        Review newReview = Review.createReview(member, item, review.getRating(), review.getContent());
        reviewRepository.save(newReview);

        Double avgRating = reviewRepository.findAvgRatingByItem(item);

        Map<String, Object> map = new HashMap<>();
        map.put("avgRating", updateAvgRating(item, avgRating));
        map.put("reviewList", readReviewList(item.getId()));

        return map;
    }

    /*
    * 리뷰 삭제
    * */
    @Transactional
    public Map<String, Object> deleteReview(Integer memberId, Integer reviewId) throws UnauthorizedException {
        Review review = reviewRepository.findReviewWithItem(reviewId);

        if(authCheck(memberId, review)) {
            reviewRepository.delete(review);

            Double avgRating = reviewRepository.findAvgRatingByItem(review.getItem());
            Map<String, Object> map = new HashMap<>();
            if (avgRating != null) {
                Item item = itemRepository.findById(review.getItem().getId()).orElse(null);

                map.put("avgRating", updateAvgRating(item, avgRating));
                map.put("reviewList", readReviewList(item.getId()));
            }
            return map;

        } else {
            throw new UnauthorizedException("삭제 권한이 없는 사용자입니다.");
        }
    }

    private double updateAvgRating(Item item, Double avgRating) {
        double roundedRating = Double.parseDouble(String.format("%.1f", avgRating));
        item.setAvgRating(roundedRating);

        EsItem esItem = esItemRepository.findById(item.getId()).orElse(null);
        esItem.setAvgrating(roundedRating);

        return roundedRating;
    }

    /*
    * 리뷰 수정
    * */
    @Transactional
    public List<ReviewDTO> updateReview(Integer memberId, Review review, String content) throws UnauthorizedException {
        if(authCheck(memberId, review)) {
            review.setContent(content);
            Review newReview = reviewRepository.save(review);
            return readReviewList(newReview.getItem().getId());
        } else {
            throw new UnauthorizedException("수정 권한이 없는 사용자입니다.");
        }
    }

    /*
     * 해당 아이템 리뷰 리스트 출력
     * */
    public List<ReviewDTO> readReviewList(Integer itemId) {
        return convertToDto(reviewRepository.findByItem(itemId));
    }

    /*
    * 삭제 시 권한체크
    * */
    private Boolean authCheck(Integer memberId, Review review) {
        return memberId == review.getMember().getId();
    }

    private List<ReviewDTO> convertToDto(List<Review> reviewList) {
        return reviewList.stream()
                .map(r -> modelMapper.map(r, ReviewDTO.class))
                .collect(Collectors.toList());
    }

}
