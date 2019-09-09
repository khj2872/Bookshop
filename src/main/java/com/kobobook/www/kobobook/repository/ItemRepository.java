package com.kobobook.www.kobobook.repository;

import com.kobobook.www.kobobook.domain.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ItemRepository extends JpaRepository<Item, Integer> {

    @Query("SELECT i " +
            "FROM Item i " +
            "WHERE i.category.id = :categoryId")
    Page<Item> findByCategoryId(Integer categoryId, Pageable pageable);

    @Query("SELECT i " +
            "FROM Item i JOIN FETCH i.category " +
            "WHERE i.id = :itemId ")
    Item findItemWithCategory(Integer itemId);

}
