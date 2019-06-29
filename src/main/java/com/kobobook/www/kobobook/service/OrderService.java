package com.kobobook.www.kobobook.service;

import com.kobobook.www.kobobook.domain.*;
import com.kobobook.www.kobobook.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    /*
    * 단일상품 주문
    * */
    @Transactional
    public Integer order(Integer memberId, Integer itemId, int count) {
        Member member = memberRepository.findById(memberId).orElse(null);
        Item item = itemRepository.findById(itemId).orElse(null);

        Delivery delivery = new Delivery(member.getAddress(), DeliveryStatus.READY);

        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        Order order = Order.createOrder(member, delivery, orderItem);

        member.setPoint(member.getPoint() + item.getPrice() * count * item.getSavingRate() / 100);

        memberRepository.save(member);
        orderRepository.save(order);

        return order.getId();
    }

    /*
    * 장바구니 상품 주문
    * */
    @Transactional
    public Integer cartOrder(Integer memberId, Integer[] cartId) {
        Member member = memberRepository.findById(memberId).orElse(null);

        OrderItem[] orderItems = new OrderItem[cartId.length];

        long point = 0;

        for(int i=0; i<cartId.length; i++) {
            Cart cart = cartRepository.findById(cartId[i]).orElse(null);
            Item item = itemRepository.findById(cart.getItem().getId()).orElse(null);
            orderItems[i] = OrderItem.createOrderItem(item, item.getPrice(), cart.getCount());
            point += item.getPrice() * cart.getCount() * item.getSavingRate() / 100;
        }

        Delivery delivery = new Delivery(member.getAddress(), DeliveryStatus.READY);

        Order order = Order.createOrder(member, delivery, orderItems);

        member.setPoint(member.getPoint() + point);

        memberRepository.save(member);
        orderRepository.save(order);

        return order.getId();
    }

    /*
    * 배송 처리
    * */
    @Transactional
    public void startOrder(Integer orderItemId) {
        OrderItem orderItem = orderItemRepository.findById(orderItemId).orElse(null);
        orderItem.getOrder().getDelivery().setStatus(DeliveryStatus.COMP);
        orderItemRepository.save(orderItem);
    }

    /*
    * 주문 취소
    * */
    @Transactional
    public void cancelOrder(Integer orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        order.cancel();
    }

    /*
    *
    * */
    @Transactional
    public List<Cart> readOrderList(Integer[] cartIdList) {
        List<Cart> orderList = new ArrayList<>();
        for(Integer id : cartIdList) {

            Cart cart = cartRepository.findById(id).orElse(null);
            Item item = itemRepository.findById(cart.getItem().getId()).orElse(null);
            cart.setPrice(item.getPrice());
            orderList.add(cart);
        }
        return orderList;
    }

    /*
    * 주문 완료 목록 조회
    * */
    @Transactional
    public List<Order> readCompleteOrderList(Integer memberId) {
        Member member = memberRepository.findById(memberId).orElse(null);
        return orderRepository.findByMember(member);
    }

}
