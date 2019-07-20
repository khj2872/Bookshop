package com.kobobook.www.kobobook.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.junit.Test;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
@ToString
public class Address {

    private String receiver;

    private String telPhone;

    private String address;

    private String zipcode;

}
