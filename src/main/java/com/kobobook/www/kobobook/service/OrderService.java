package com.kobobook.www.kobobook.service;

import com.kobobook.www.kobobook.domain.*;
import com.kobobook.www.kobobook.dto.OrderInfo;
import com.kobobook.www.kobobook.dto.OrderDTO;
import com.kobobook.www.kobobook.repository.*;
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

    private OrderItemRepository orderItemRepository;

    private OrderRepositoryImpl orderRepositoryImpl;

    private ModelMapper modelMapper;

    /*
    * 다중 상품 주문
    * */
    @Transactional
    public Integer cartOrder(Integer memberId, OrderInfo orderInfo) {
        Member member = memberRepository.findById(memberId).orElse(null);

        OrderItem[] orderItems = new OrderItem[orderInfo.getOrderListId().length];

        long savingPoint = 0;

        for(int i = 0; i< orderInfo.getOrderListId().length; i++) {
            Cart cart = cartRepository.findById(orderInfo.getOrderListId()[i]).orElse(null);
            orderItems[i] = OrderItem.createOrderItem(cart.getItem(), cart.getItem().getPrice(), cart.getCount());
            savingPoint += cart.getItem().getPrice() * cart.getCount() * cart.getItem().getSavingRate() / 100;
            cartRepository.delete(cart);
        }

        Delivery delivery = new Delivery(orderInfo, DeliveryStatus.READY);

        Order order = Order.createOrder(member, delivery, orderInfo.getUsingPoint(), savingPoint, orderItems);

        member.setPoint(member.getPoint() - orderInfo.getUsingPoint());

        Order newOrder = orderRepository.save(order);

        System.out.println("newOrder.getId() : " + newOrder.getId());

        return newOrder.getId();
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
    public void cancelOrder(Integer orderId) throws RuntimeException{
        Order order = orderRepository.findById(orderId).orElse(null);
        order.cancel();
    }


    /*
    * 주문 완료 목록 조회
    * */
    @Transactional
    public List<OrderDTO> readCompleteOrderList(Integer memberId, OrderSearch orderSearch) {
        return orderRepositoryImpl.searchList(memberId, orderSearch).stream()
                .map(o -> convertToDto(o))
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
