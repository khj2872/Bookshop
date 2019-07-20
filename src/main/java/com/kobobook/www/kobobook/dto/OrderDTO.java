package com.kobobook.www.kobobook.dto;

import com.kobobook.www.kobobook.domain.OrderStatus;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private Integer id;

    private Date orderDate;

    private OrderStatus status;

    private List<OrderItemDTO> orderItems;

    private DeliveryDto delivery;

    private long usingPoint;

    private long savingPoint;

    private long totalPrice;

}