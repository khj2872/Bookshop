package com.kobobook.www.kobobook.repository;

import com.kobobook.www.kobobook.domain.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DeliveryRepository extends JpaRepository<Delivery, Integer> {
//    @Query("SELECT d FROM Delivery d WHERE d.order.id = :orderId")
//    Delivery findByOrderId(Integer orderId);
}
