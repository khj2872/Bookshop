package com.kobobook.www.kobobook.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private double rating;

    private LocalDateTime regDate;

    private String content;

    public static Review createReview(Member member, Item item, double rating, String content) {
        Review review = Review.builder()
                .member(member)
                .item(item)
                .rating(rating)
                .content(content)
                .regDate(LocalDateTime.now())
                .build();
        item.getReviews().add(review);
        return review;
    }

}
