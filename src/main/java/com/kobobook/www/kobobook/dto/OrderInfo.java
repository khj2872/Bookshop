package com.kobobook.www.kobobook.dto;

import com.kobobook.www.kobobook.domain.Address;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class OrderInfo {

    private Address address;

    private Integer[] orderListId;

    private long usingPoint;

}
