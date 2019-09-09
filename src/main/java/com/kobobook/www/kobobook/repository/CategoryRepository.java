package com.kobobook.www.kobobook.repository;

import com.kobobook.www.kobobook.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
