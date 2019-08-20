package com.kobobook.www.kobobook.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
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
        Review review = new Review();
        review.setMember(member);

        review.setItem(item);
        item.getReviews().add(review);

        review.setRating(rating);
        review.setContent(content);
        review.setRegDate(LocalDateTime.now());

        return review;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", member=" + member +
                ", rating=" + rating +
                ", regDate=" + regDate +
                ", content='" + content + '\'' +
                '}';
    }
}
