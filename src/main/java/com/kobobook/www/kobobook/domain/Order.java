package com.kobobook.www.kobobook.domain;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "ORDERS")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID")
    private Integer id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private long usingPoint;

    private long savingPoint;

    public static Order createOrder(Member member, Delivery delivery, long usingPoint, long savingPoint, List<OrderItem> orderItems) {
        Order order = Order.builder()
                .member(member)
                .delivery(delivery)
                .usingPoint(usingPoint)
                .savingPoint(savingPoint)
                .status(OrderStatus.ORDER)
                .orderDate(LocalDateTime.now())
                .build();
        delivery.setOrder(order);

//        Arrays.stream(orderItems)
//                .forEach(order::addOrderItem);

        orderItems.forEach(order::addOrderItem);
        return order;
    }

    //==비즈니스 로직==//
    /** 주문 취소 */
    public void cancel() {
        if (this.delivery.getStatus() == DeliveryStatus.COMP || this.delivery.getStatus() == DeliveryStatus.DELIVERY) {
            throw new RuntimeException("이미 배송중이거나 완료된 상품은 취소가 불가능합니다.");
        }

        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    /** 배송 완료*/
    public void complete() {
        this.getDelivery().setStatus(DeliveryStatus.COMP);
        // 적립 예정 포인트 적립
        long point = this.getMember().getPoint() + this.getSavingPoint();
        this.getMember().setPoint(point);
    }

    //==조회 로직==//
    /** 전체 주문 가격 조회 */
    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }

    //==연관관계 메서드==//
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

}
