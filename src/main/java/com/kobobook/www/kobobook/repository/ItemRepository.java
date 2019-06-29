package com.kobobook.www.kobobook.repository;

import com.kobobook.www.kobobook.domain.Category;
import com.kobobook.www.kobobook.domain.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {

    Page<Item> findAll(Pageable pageable);

    Page<Item> findByCategory(Category category, Pageable pageable);
}
