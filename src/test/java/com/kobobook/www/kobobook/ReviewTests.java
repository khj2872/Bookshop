package com.kobobook.www.kobobook;

import com.kobobook.www.kobobook.domain.*;
import com.kobobook.www.kobobook.dto.ReviewDTO;
import com.kobobook.www.kobobook.repository.ItemRepository;
import com.kobobook.www.kobobook.repository.MemberRepository;
import com.kobobook.www.kobobook.service.ReviewService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ReviewTests {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void createTest() throws Exception {
//        // given
//        Member member = new Member();
//        member.setUsername("테스트사용자");
//        member.setRegDate(new Date());
//        member.setRole(Role.ROLE_USER);
//        member.setUserEmail("test@mail.com");
//        Member newMember = memberRepository.save(member);
//
//        Category category = new Category();
//        category.setName("테스트카테고리");
//
//        Item item = new Item();
//        item.setName("testItem");
//        item.setPrice(2000);
//        item.setRegDate(new Date());
//        item.setCategory(category);
//        Item newItem = itemRepository.save(item);
//        System.out.println("newItem.getId() : " + newItem.getId());
//
//        Review review = new Review();
//        review.setContent("안녕하세요. 리뷰테스트입니다.");
//        review.setItem(newItem);
//        review.setRating(4.0);
//
//        // when
//        List<ReviewDTO> reviewList = reviewService.createReview(newMember.getId(), review);
//        // then
//        assertThat(reviewList).hasSize(1);
//        assertThat(newItem.getAvgRating()).isEqualTo(review.getRating());
//
    }

}
