package com.kobobook.www.kobobook.controller;

import com.kobobook.www.kobobook.domain.Category;
import com.kobobook.www.kobobook.repository.CategoryRepository;
import com.kobobook.www.kobobook.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/category")
public class CategoryRestController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping("/main")
    public Category createCategory(Category category) {
        System.out.println(category);
        return categoryRepository.save(category);
    }

}
