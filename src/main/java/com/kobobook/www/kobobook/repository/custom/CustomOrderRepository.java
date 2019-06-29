package com.kobobook.www.kobobook.repository.custom;

import com.kobobook.www.kobobook.domain.Order;
import com.kobobook.www.kobobook.domain.OrderSearch;
import com.kobobook.www.kobobook.domain.OrderStatus;

import java.util.List;

public interface CustomOrderRepository {

    List<Order> search(OrderSearch orderSearch);

    List<Order> findByStatus(OrderStatus orderStatus);

}
