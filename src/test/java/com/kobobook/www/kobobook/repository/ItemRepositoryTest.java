package com.kobobook.www.kobobook.repository;

import com.kobobook.www.kobobook.domain.Category;
import com.kobobook.www.kobobook.domain.Item;
import com.kobobook.www.kobobook.domain.Member;
import com.kobobook.www.kobobook.domain.Review;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void testFindByCategoryId() {
        //given
        Category category = Category.builder()
                .name("IT")
                .build();
        Category savedCategory = categoryRepository.save(category);

        Item item1 = Item.builder()
                .category(category)
                .name("파이썬")
                .build();
        itemRepository.save(item1);

        Item item2 = Item.builder()
                .category(category)
                .name("HTML")
                .build();
        itemRepository.save(item2);

        //when
        Page<Item> itemPage = itemRepository.findByCategoryId(savedCategory.getId(), PageRequest.of(0, 2));

        //then
        assertThat(itemPage.getTotalElements()).isEqualTo(2);
        assertThat(itemPage.getContent().get(0).getName()).isEqualTo("파이썬");
        assertThat(itemPage.getTotalPages()).isEqualTo(1);

    }

    @Test
    public void testFindItemWithCategoryAndReviews() {
        //given
        Member member = Member.builder()
                .username("kang")
                .userEmail("test@email.com")
                .build();
        memberRepository.save(member);

        Category category = Category.builder()
                .name("IT")
                .build();
        categoryRepository.save(category);

        Item item = Item.builder()
                .category(category)
                .name("Vue.js")
                .build();
        Item savedItem = itemRepository.save(item);

        //when
        Item found = itemRepository.findItemWithCategory(savedItem.getId());

        //then
        assertThat(found.getCategory().getName()).isEqualTo("IT");
        assertThat(found.getName()).isEqualTo("Vue.js");
    }

}
