package com.kobobook.www.kobobook.service;

import com.kobobook.www.kobobook.domain.Category;
import com.kobobook.www.kobobook.domain.Item;
import com.kobobook.www.kobobook.domain.Review;
import com.kobobook.www.kobobook.dto.ItemDTO;
import com.kobobook.www.kobobook.repository.CategoryRepository;
import com.kobobook.www.kobobook.repository.ItemRepository;
import com.kobobook.www.kobobook.repository.ReviewRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ItemServiceTest {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void testReadItemsByCategory() {
        //given
        Category category = Category.builder()
                .name("인문")
                .build();
        Category savedCategory = categoryRepository.save(category);

        Item item = Item.builder()
                .category(category)
                .name("알고리즘")
                .build();
        itemRepository.save(item);

        //when
        Page<ItemDTO.ItemSimple> itemPage = itemService.readItemsByCategory(savedCategory.getId());

        //then
        assertThat(itemPage.getTotalElements()).isEqualTo(1);
        assertThat(itemPage.getContent().get(0).getName()).isEqualTo("알고리즘");

    }

    @Test
    public void testReadItemDetail() {
        //given
        Category category = Category.builder()
                .name("IT")
                .build();
        categoryRepository.save(category);

        Item item = Item.builder()
                .category(category)
                .name("운영체제")
                .build();
        Item savedItem = itemRepository.save(item);

        //when
        ItemDTO.ItemWithCategory found = itemService.readItemDetail(savedItem.getId());

        //then
        assertThat(found.getCategory().getName()).isEqualTo("IT");
        assertThat(found.getName()).isEqualTo("운영체제");
    }

}
