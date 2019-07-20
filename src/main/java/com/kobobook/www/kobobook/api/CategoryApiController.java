package com.kobobook.www.kobobook.api;

import com.kobobook.www.kobobook.domain.Category;
import com.kobobook.www.kobobook.dto.CategoryDTO;
import com.kobobook.www.kobobook.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/categories")
@AllArgsConstructor
public class CategoryApiController {

    private CategoryService categoryService;

    /*
    * 카테고리 리스트
    * */
    @GetMapping("")
    public ResponseEntity<List<CategoryDTO>> readCategories() {
        return new ResponseEntity<>(categoryService.readCategoryList(), HttpStatus.OK);
    }

}
