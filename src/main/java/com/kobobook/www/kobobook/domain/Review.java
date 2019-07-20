package com.kobobook.www.kobobook.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
public class Review {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private double rating;

    @Temporal(TemporalType.TIMESTAMP)
    private Date regDate;

    private String content;

    public static Review createReview(Member member, Item item, double rating, String content) {
        Review review = new Review();
        review.setMember(member);

        review.setItem(item);
        item.getReviews().add(review);

        review.setRating(rating);
        review.setContent(content);
        review.setRegDate(new Date());

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
