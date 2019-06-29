package com.kobobook.www.kobobook.repository;

import com.kobobook.www.kobobook.domain.Item;
import com.kobobook.www.kobobook.domain.Member;
import com.kobobook.www.kobobook.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByItem(Item item);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.item = :item GROUP BY r.item")
    float findAvgRatingByItem(Item item);

    Review findByMember(Member member);
}
