package com.kobobook.www.kobobook.repository.custom;

import com.kobobook.www.kobobook.domain.Order;
import com.kobobook.www.kobobook.domain.OrderSearch;

import java.util.List;

public interface CustomOrderRepository {

    List<Order> searchList(Integer memberId, OrderSearch orderSearch);

    Order searchOrderDetail(Integer orderId);

}
