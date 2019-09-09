//package com.kobobook.www.kobobook.service;
//
//import com.kobobook.www.kobobook.domain.*;
//import com.kobobook.www.kobobook.dto.OrderInfo;
//import com.kobobook.www.kobobook.repository.CartRepository;
//import com.kobobook.www.kobobook.repository.ItemRepository;
//import com.kobobook.www.kobobook.repository.MemberRepository;
//import com.kobobook.www.kobobook.repository.OrderRepository;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@Transactional
//public class OrderServiceTest {
//
//    @Autowired
//    private MemberRepository memberRepository;
//
//    @Autowired
//    private OrderService orderService;
//
//    @Autowired
//    private CartRepository cartRepository;
//
//    @Autowired
//    private ItemRepository itemRepository;
//
//    @Autowired
//    private OrderRepository orderRepository;
//
//    @Test
//    public void testCreateOrder() {
//        //given
//        Member member = Member.builder()
//                .username("kang")
//                .userEmail("test@email.com")
//                .build();
//        Member savedMember = memberRepository.save(member);
//
//        Item item = Item.builder()
//                .name("책")
//                .price(1000)
//                .stock(2)
//                .build();
//        itemRepository.save(item);
//
//        Cart cart = Cart.builder()
//                .member(savedMember)
//                .item(item)
//                .count(1)
//                .build();
//        Cart savedCart = cartRepository.save(cart);
//
//        OrderInfo orderInfo = OrderInfo.builder()
//                .address(Address.builder()
//                        .address("익산")
//                        .receiver("혁")
//                        .zipcode("12345")
//                        .build()
//                )
//                .orderListId(new Integer[]{savedCart.getId()})
//                .usingPoint(0)
//                .build();
//
//        //when
//        Integer orderId = orderService.createOrder(savedMember.getId(), orderInfo);
//        Order found = orderRepository.findById(orderId).orElse(null);
//
//        //then
//        assertThat(found.getMember().getUsername()).isEqualTo("kang");
//        assertThat(found.getOrderItems().get(0).getItem().getName()).isEqualTo("책");
//
//    }

//    @Test
//    public void testCancelOrder() {
//        //given
//        Member member = Member.builder()
//                .username("kang")
//                .userEmail("test@email.com")
//                .build();
//        Member savedMember = memberRepository.save(member);
//
//        Item item = Item.builder()
//                .name("책")
//                .price(1000)
//                .stock(2)
//                .build();
//        itemRepository.save(item);
//
//        Cart cart = Cart.builder()
//                .member(savedMember)
//                .item(item)
//                .count(1)
//                .build();
//        Cart savedCart = cartRepository.save(cart);
//
//        OrderInfo orderInfo = OrderInfo.builder()
//                .address(Address.builder()
//                        .address("익산")
//                        .receiver("혁")
//                        .zipcode("12345")
//                        .build()
//                )
//                .orderListId(new Integer[]{savedCart.getId()})
//                .usingPoint(0)
//                .build();
//
//        //when
//        orderService.cancelOrder(order);
//        //then
//    }

//}
