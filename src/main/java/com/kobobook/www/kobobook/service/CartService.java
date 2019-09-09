package com.kobobook.www.kobobook.service;

import com.kobobook.www.kobobook.domain.Cart;
import com.kobobook.www.kobobook.domain.Item;
import com.kobobook.www.kobobook.domain.Member;
import com.kobobook.www.kobobook.dto.CartDTO;
import com.kobobook.www.kobobook.repository.CartRepository;
import com.kobobook.www.kobobook.repository.ItemRepository;
import com.kobobook.www.kobobook.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CartService {

    private CartRepository cartRepository;

    private MemberRepository memberRepository;

    private ItemRepository itemRepository;

    private ModelMapper modelMapper;

    /*
    * 장바구니 항목 추가
    * */
    @Transactional
    public void createCart(Integer memberId, Integer itemId, int count) {
        Member member = memberRepository.findById(memberId).orElse(null);
        Item item = itemRepository.findById(itemId).orElse(null);

        Cart dupChkCart = cartRepository.findByMemberAndItem(member, item);
        if(dupChkCart != null) { // 장바구니에 이미 담겨있을 경우
            dupChkCart.setCount(count);
        } else { // 새로운 상품일 경우
            cartRepository.save(Cart.createCart(member, item, count, item.getSavingRate()));
        }
    }

    /*
    * 장바구니 리스트 출력
    * */
    @Transactional
    public List<CartDTO> readCartList(Integer memberId) {
        Member member = memberRepository.findById(memberId).orElse(null);
        List<Cart> carts = cartRepository.findByMember(member);
        carts.forEach(cart -> cart.setPrice(cart.getItem().getPrice()));
        return convertToDto(carts);
    }

    /*
    * 장바구니 항목 삭제
    * */
    @Transactional
    public List<CartDTO> deleteCart(Cart cart) {
        cartRepository.delete(cart);
        return readCartList(cart.getMember().getId());
    }

    /*
    * 장바구니 항목 수량 수정
    * */
    @Transactional
    public List<CartDTO> updateCart(Cart cart, int count) {
        cart.setCount(count);
        cartRepository.save(cart);
        return readCartList(cart.getMember().getId());
    }

    /*
     *
     * */
    @Transactional
    public List<CartDTO> readOrderList(Integer[] cartIdList) {
        List<Cart> orderList = new ArrayList<>();
        Arrays.stream(cartIdList)
                .forEach(i -> {
                    Cart cart = cartRepository.findById(i).orElse(null);
                    Item item = itemRepository.findById(cart.getItem().getId()).orElse(null);
                    cart.setPrice(item.getPrice());
                    orderList.add(cart);
                });

        return convertToDto(orderList);
    }

    private List<CartDTO> convertToDto(List<Cart> cartList) {
        return cartList.stream()
                .map(c -> modelMapper.map(c, CartDTO.class))
                .collect(Collectors.toList());
    }

}
