package com.kobobook.www.kobobook.service;

import com.kobobook.www.kobobook.domain.Item;
import com.kobobook.www.kobobook.domain.Member;
import com.kobobook.www.kobobook.domain.Review;
import com.kobobook.www.kobobook.repository.ItemRepository;
import com.kobobook.www.kobobook.repository.MemberRepository;
import com.kobobook.www.kobobook.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private MemberRepository memberRepository;

    /*
    * 상품 리뷰 작성
    * */
    @Transactional
    public List<Review> createReview(Integer memberId, Integer itemId, String content, float rating) {
        Member member = memberRepository.findById(memberId).orElse(null);
        Item item = itemRepository.findById(itemId).orElse(null);

        Review review = Review.createReview(member, item, rating, content);

        reviewRepository.save(review);

        item.setAvgRating(reviewRepository.findAvgRatingByItem(item));

        return readReviewList(itemId);
    }

    /*
    * 해당 아이템 리뷰 리스트 출력
    * */
    private List<Review> readReviewList(Integer itemId) {
        Item item = itemRepository.findById(itemId).orElse(null);
        return reviewRepository.findByItem(item);
    }

    /*
    * 리뷰 삭제
    * */
    @Transactional
    public List<Review> deleteReview(Integer reviewId) {
        Review review = reviewRepository.findById(reviewId).orElse(null);
        reviewRepository.delete(review);
        return readReviewList(review.getItem().getId());
    }

    /*
    * 리뷰 수정
    * */
    @Transactional
    public List<Review> updateReview(Integer reviewId, String content) {
        Review review = reviewRepository.findById(reviewId).orElse(null);
        review.setContent(content);
        reviewRepository.save(review);
        return readReviewList(review.getItem().getId());
    }

    /*
    * 권한체크
    * */
    @Transactional
    public Boolean authCheck(Integer memberId, Integer reviewId) {
        Member member = memberRepository.findById(memberId).orElse(null);
        Review review = reviewRepository.findById(reviewId).orElse(null);

        if(member.getId() == review.getMember().getId()) return true;
        else return false;
    }

}
