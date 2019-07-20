package com.kobobook.www.kobobook.service;

import com.kobobook.www.kobobook.domain.Item;
import com.kobobook.www.kobobook.domain.Member;
import com.kobobook.www.kobobook.domain.Review;
import com.kobobook.www.kobobook.dto.ReviewDTO;
import com.kobobook.www.kobobook.exception.UnauthorizedException;
import com.kobobook.www.kobobook.repository.ItemRepository;
import com.kobobook.www.kobobook.repository.MemberRepository;
import com.kobobook.www.kobobook.repository.ReviewRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReviewService {

    private ReviewRepository reviewRepository;

    private ItemRepository itemRepository;

    private MemberRepository memberRepository;

    private ModelMapper modelMapper;

    /*
    * 상품 리뷰 작성
    * */
    @Transactional
    public List<ReviewDTO> createReview(Integer memberId, Review review) {
        Member member = memberRepository.findById(memberId).orElse(null);
        Item item = itemRepository.findById(review.getItem().getId()).orElse(null);

        Review newReview = Review.createReview(member, item, review.getRating(), review.getContent());

        reviewRepository.save(newReview);

        item.setAvgRating(reviewRepository.findAvgRatingByItem(item));

        return convertToDto(reviewRepository.findByItem(item.getId()));
    }


    /*
    * 리뷰 삭제
    * */
    @Transactional
    public List<ReviewDTO> deleteReview(Integer memberId, Review review) throws UnauthorizedException {
        if(authCheck(memberId, review)) {
            reviewRepository.delete(review);
            return readReviewList(review.getItem().getId());
        } else {
            throw new UnauthorizedException("삭제 권한이 없는 사용자입니다.");
        }
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
    private List<ReviewDTO> readReviewList(Integer itemId) {
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
