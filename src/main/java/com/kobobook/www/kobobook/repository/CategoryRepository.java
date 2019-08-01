package com.kobobook.www.kobobook.repository;

import com.kobobook.www.kobobook.domain.Category;
import com.kobobook.www.kobobook.dto.CategoryDTO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query("SELECT new com.kobobook.www.kobobook.dto.CategoryDTO(c.id, c.name) FROM Category c")
    List<CategoryDTO> findCategoriesAll();

}
