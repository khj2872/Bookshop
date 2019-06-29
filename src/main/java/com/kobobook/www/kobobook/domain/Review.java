package com.kobobook.www.kobobook.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class Review {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private Float rating;

    @Temporal(TemporalType.TIMESTAMP)
    private Date regDate;

    private String content;

    public static Review createReview(Member member, Item item, float rating, String content) {
        Review review = new Review();
        review.setMember(member);
        review.setItem(item);
        item.getReviews().add(review);
        review.setRating(rating);
        review.setContent(content);
        review.setRegDate(new Date());

        return review;
    }

}
