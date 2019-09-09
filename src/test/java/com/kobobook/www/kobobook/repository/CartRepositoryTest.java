package com.kobobook.www.kobobook.repository;

import com.kobobook.www.kobobook.domain.Cart;
import com.kobobook.www.kobobook.domain.Item;
import com.kobobook.www.kobobook.domain.Member;
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
public class CartRepositoryTest {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void testFindByMemberAndItem() {
        //given
        Member member = Member.builder()
                .username("kang")
                .userEmail("test@email.com")
                .build();
        memberRepository.save(member);

        Item item = Item.builder()
                .name("대규모 서비스를 지탱하는 기술")
                .build();
        itemRepository.save(item);

        Cart cart = Cart.builder()
                .member(member)
                .item(item)
                .build();
        cartRepository.save(cart);

        //when
        Cart found = cartRepository.findByMemberAndItem(member, item);

        //then
        assertThat(found.getMember().getUsername()).isEqualTo("kang");
        assertThat(found.getMember().getUserEmail()).isEqualTo("test@email.com");
        assertThat(found.getItem().getName()).isEqualTo("대규모 서비스를 지탱하는 기술");

    }

    @Test
    public void testFindByMember() {
        //given
        Member member = Member.builder()
                .username("kang")
                .userEmail("test@email.com")
                .build();
        memberRepository.save(member);

        Item item = Item.builder()
                .name("자료구조")
                .build();
        itemRepository.save(item);

        Cart cart = Cart.builder()
                .member(member)
                .item(item)
                .build();
        cartRepository.save(cart);

        //when
        List<Cart> cartList = cartRepository.findByMember(member);

        //then
        assertThat(cartList.size()).isEqualTo(1);
        assertThat(cartList.get(0).getMember().getUsername()).isEqualTo("kang");
        assertThat(cartList.get(0).getMember().getUserEmail()).isEqualTo("test@email.com");
        assertThat(cartList.get(0).getItem().getName()).isEqualTo("자료구조");

    }

}
