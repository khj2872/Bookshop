package com.kobobook.www.kobobook.repository;

import com.kobobook.www.kobobook.domain.*;
import com.kobobook.www.kobobook.dto.OrderInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void testFindOrderWithMemberAndDelivery() {
        //given
        Member member = Member.builder()
                .username("kang")
                .userEmail("test@email.com")
                .build();
        memberRepository.save(member);

        Item item = Item.builder()
                .name("책")
                .build();
        itemRepository.save(item);

        Cart cart = Cart.builder()
                .member(member)
                .item(item)
                .build();
        Cart savedCart = cartRepository.save(cart);

        OrderInfo orderInfo = OrderInfo.builder()
                .address(Address.builder()
                        .address("익산")
                        .receiver("혁")
                        .zipcode("12345")
                        .build()
                )
                .orderListId(new Integer[]{savedCart.getId()})
                .usingPoint(0)
                .build();

        Delivery delivery = Delivery.builder()
                .orderInfo(orderInfo)
                .status(DeliveryStatus.READY)
                .build();

        Order order = Order.builder()
                .member(member)
                .delivery(delivery)
                .status(OrderStatus.ORDER)
                .build();
        Order savedOrder = orderRepository.save(order);

        //when
        Order found = orderRepository.findOrderWithMemberAndDelivery(savedOrder.getId());

        //then
        assertThat(found.getMember().getUsername()).isEqualTo("kang");
        assertThat(found.getDelivery().getAddress().getAddress()).isEqualTo("익산");
    }

}
