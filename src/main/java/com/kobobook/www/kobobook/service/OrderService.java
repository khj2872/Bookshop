package com.kobobook.www.kobobook.service;

import com.kobobook.www.kobobook.domain.*;
import com.kobobook.www.kobobook.dto.OrderDTO;
import com.kobobook.www.kobobook.dto.OrderInfo;
import com.kobobook.www.kobobook.repository.CartRepository;
import com.kobobook.www.kobobook.repository.MemberRepository;
import com.kobobook.www.kobobook.repository.OrderRepository;
import com.kobobook.www.kobobook.repository.custom.OrderRepositoryImpl;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService {

    private MemberRepository memberRepository;

    private OrderRepository orderRepository;

    private CartRepository cartRepository;

    private OrderRepositoryImpl orderRepositoryImpl;

    private ModelMapper modelMapper;

    /*
    * 상품 주문
    * */
    @Transactional
    public Integer createOrder(Integer memberId, OrderInfo orderInfo) {
        int orderListSize = orderInfo.getOrderListId().length;
        Member member = memberRepository.findById(memberId).orElse(null);
        OrderItem[] orderItems = new OrderItem[orderListSize];

        long savingPoint = 0;
        for(int i=0; i<orderListSize; i++) {
            Cart cart = cartRepository.findById(orderInfo.getOrderListId()[i]).orElse(null);
            orderItems[i] = OrderItem.createOrderItem(cart.getItem(), cart.getItem().getPrice(), cart.getCount());
            savingPoint += cart.getItem().getPrice() * cart.getCount() * cart.getItem().getSavingRate() / 100;
            cartRepository.delete(cart);
        }

        Delivery delivery = new Delivery(orderInfo, DeliveryStatus.READY);
        Order order = Order.createOrder(member, delivery, orderInfo.getUsingPoint(), savingPoint, orderItems);
        member.setPoint(member.getPoint() - orderInfo.getUsingPoint());
        Order newOrder = orderRepository.save(order);

        return newOrder.getId();
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
    * 배송완료 확인
    * */
    @Transactional
    public void completeOrder(Integer orderId) {
        Order order = orderRepository.findOrderWithMemberAndDelivery(orderId);
        order.complete();
    }

    /*
    * 주문 완료 목록 조회
    * */
    @Transactional
    public List<OrderDTO> readCompleteOrderList(Integer memberId, OrderSearch orderSearch) {
        return orderRepositoryImpl.searchList(memberId, orderSearch).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /*
    * 주문 상세내역 조회
    * */
    @Transactional
    public OrderDTO readOrderDetail(Integer orderId) {
        return convertToDto(orderRepositoryImpl.searchOrderDetail(orderId));
    }

    private OrderDTO convertToDto(Order order) {
        OrderDTO orderDto = modelMapper.map(order, OrderDTO.class);
        orderDto.setTotalPrice(order.getTotalPrice());
        return orderDto;
    }

}
