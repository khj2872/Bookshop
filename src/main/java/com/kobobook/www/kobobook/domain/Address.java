package com.kobobook.www.kobobook.domain;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

    private String receiver;

    private String telPhone;

    private String address;

    private String zipcode;

}
