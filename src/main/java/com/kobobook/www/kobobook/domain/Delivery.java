package com.kobobook.www.kobobook.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kobobook.www.kobobook.dto.OrderInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Delivery {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DELIVERY_ID")
    private Integer id;

    @Embedded
    private Address address;

    @JsonIgnore
    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Order order;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    public Delivery(OrderInfo orderInfo, DeliveryStatus status) {
        this.address = orderInfo.getAddress();
        this.status = status;
    }

    @Override
    public String toString() {
        return "Delivery{" +
                "id=" + id +
                ", address=" + address +
                ", status=" + status +
                '}';
    }
}