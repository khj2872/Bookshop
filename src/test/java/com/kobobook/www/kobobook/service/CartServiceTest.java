package com.kobobook.www.kobobook.service;

import com.kobobook.www.kobobook.domain.Cart;
import com.kobobook.www.kobobook.domain.Item;
import com.kobobook.www.kobobook.domain.Member;
import com.kobobook.www.kobobook.dto.CartDTO;
import com.kobobook.www.kobobook.repository.CartRepository;
import com.kobobook.www.kobobook.repository.ItemRepository;
import com.kobobook.www.kobobook.repository.MemberRepository;
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
public class CartServiceTest {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void testCreateCart() {
        //given
        Member member = Member.builder()
                .username("kang")
                .userEmail("test@email.com")
                .build();
        Member savedMember = memberRepository.save(member);

        Item item = Item.builder()
                .name("HTTP 완벽 가이드")
                .build();
        Item savedItem = itemRepository.save(item);

        int count = 2;

        //when
        cartService.createCart(savedMember.getId(), savedItem.getId(), count);
        Cart cart = cartRepository.findByMemberAndItem(member, item);

        //then
        assertThat(cart).isNotNull();
        assertThat(cart.getMember().getUsername()).isEqualTo("kang");
        assertThat(cart.getItem().getName()).isEqualTo("HTTP 완벽 가이드");
        assertThat(cart.getCount()).isEqualTo(count);
    }

    @Test
    public void testReadCartList() {
        //given
        Member member = Member.builder()
                .username("kang")
                .userEmail("test@email.com")
                .build();
        Member savedMember = memberRepository.save(member);

        Item item = Item.builder()
                .name("HTTP 완벽 가이드")
                .build();
        itemRepository.save(item);

        int count = 3;

        Cart cart = Cart.builder()
                .member(member)
                .item(item)
                .count(count)
                .build();
        cartRepository.save(cart);

        //when
        List<CartDTO> cartList = cartService.readCartList(savedMember.getId());

        //then
        assertThat(cartList.size()).isEqualTo(1);
        assertThat(cartList.get(0).getMember().getUsername()).isEqualTo("kang");
        assertThat(cartList.get(0).getItem().getName()).isEqualTo("HTTP 완벽 가이드");
        assertThat(cartList.get(0).getCount()).isEqualTo(count);

    }

    @Test
    public void testDeleteCart() {
        //given
        Member member = Member.builder()
                .username("kang")
                .userEmail("test@email.com")
                .build();
        memberRepository.save(member);

        Item item = Item.builder()
                .name("HTTP 완벽 가이드")
                .build();
        itemRepository.save(item);

        int count = 3;

        Cart cart = Cart.builder()
                .member(member)
                .item(item)
                .count(count)
                .build();
        Cart savedCart = cartRepository.save(cart);

        //when
        cartService.deleteCart(savedCart);
        Cart found = cartRepository.findByMemberAndItem(member, item);

        //then
        assertThat(found).isNull();
    }

    @Test
    public void testUpdateCart() {
        //given
        Member member = Member.builder()
                .username("kang")
                .userEmail("test@email.com")
                .build();
        memberRepository.save(member);

        Item item = Item.builder()
                .name("HTTP 완벽 가이드")
                .build();
        itemRepository.save(item);

        int count = 3;

        Cart cart = Cart.builder()
                .member(member)
                .item(item)
                .count(count)
                .build();
        Cart savedCart = cartRepository.save(cart);

        //when
        cartService.updateCart(savedCart, 2);
        Cart found = cartRepository.findByMemberAndItem(member, item);

        //then
        assertThat(found.getCount()).isEqualTo(2);

    }

    @Test
    public void testReadOrderList() {
        //given
        Member member = Member.builder()
                .username("kang")
                .userEmail("test@email.com")
                .build();
        memberRepository.save(member);

        Item item1 = Item.builder()
                .name("HTTP 완벽 가이드")
                .build();
        itemRepository.save(item1);

        Item item2 = Item.builder()
                .name("알고리즘")
                .build();
        itemRepository.save(item2);

        int count1 = 3;
        Cart cart1 = Cart.builder()
                .member(member)
                .item(item1)
                .count(count1)
                .build();
        Cart savedCart1 = cartRepository.save(cart1);

        int count2 = 3;
        Cart cart2 = Cart.builder()
                .member(member)
                .item(item2)
                .count(count2)
                .build();
        Cart savedCart2 = cartRepository.save(cart2);

        Integer[] cartIdList = {savedCart1.getId(), savedCart2.getId()};

        //when
        List<CartDTO> cartList = cartService.readOrderList(cartIdList);

        //then
        assertThat(cartList.size()).isEqualTo(2);

    }

}
