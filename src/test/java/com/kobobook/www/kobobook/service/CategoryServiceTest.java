package com.kobobook.www.kobobook.service;

import com.kobobook.www.kobobook.domain.Category;
import com.kobobook.www.kobobook.dto.CategoryDTO;
import com.kobobook.www.kobobook.repository.CategoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testReadCategoryList() {
        //given
        Category category = Category.builder()
                .name("과학")
                .build();
        categoryRepository.save(category);

        //when
        List<CategoryDTO> categoryList = categoryService.readCategoryList();

        //then
        assertThat(categoryList.size()).isEqualTo(1);
        assertThat(categoryList.get(0).getName()).isEqualTo("과학");
    }

}
