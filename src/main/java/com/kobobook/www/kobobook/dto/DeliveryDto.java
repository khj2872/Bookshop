package com.kobobook.www.kobobook.dto;

import com.kobobook.www.kobobook.domain.Address;
import com.kobobook.www.kobobook.domain.DeliveryStatus;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryDto {

    private Integer id;

    private Address address;

    private DeliveryStatus status;

}
