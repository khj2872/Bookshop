package com.kobobook.www.kobobook.service;

import com.kobobook.www.kobobook.domain.Cart;
import com.kobobook.www.kobobook.domain.Item;
import com.kobobook.www.kobobook.domain.Member;
import com.kobobook.www.kobobook.repository.CartRepository;
import com.kobobook.www.kobobook.repository.ItemRepository;
import com.kobobook.www.kobobook.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ItemRepository itemRepository;

    /*
    * 장바구니 항목 추가
    * */
    @Transactional
    public Integer createCart(Integer memberId, Integer itemId, Long count) {
        System.out.println("itemId : " + itemId + "count : " + count);
        Member member = memberRepository.findById(memberId).orElse(null);
        Item item = itemRepository.findById(itemId).orElse(null);

        Cart dupChkCart = cartRepository.findByMemberAndItem(member, item);
        if(dupChkCart != null) {
            dupChkCart.setCount(count);
            Cart cart = cartRepository.save(dupChkCart);
            return cart.getId();
        } else {
            Cart cart = cartRepository.save(Cart.createCart(member, item, count, item.getSavingRate()));
            return cart.getId();
        }
    }

    /*
    * 장바구니 리스트 출력
    * */
    @Transactional
    public List<Cart> readCartList(Integer memberId) {
        Member member = memberRepository.findById(memberId).orElse(null);
        List<Cart> carts = cartRepository.findByMember(member);
        for(Cart cart : carts) {
            cart.setPrice(itemRepository.findById(cart.getItem().getId()).orElse(null).getPrice());
        }
        return carts;
    }

    /*
    * 장바구니 항목 삭제
    * */
    @Transactional
    public List<Cart> deleteCart(Cart cart) {
        cartRepository.delete(cart);
        return readCartList(cart.getMember().getId());
    }

    /*
    * 장바구니 항목 수량 수정
    * */
    @Transactional
    public List<Cart> updateCart(Cart cart, long count) {
        cart.setCount(count);
        cartRepository.save(cart);
        return readCartList(cart.getMember().getId());
    }
}
