package com.kobobook.www.kobobook.dto;

import com.kobobook.www.kobobook.domain.Address;
import com.kobobook.www.kobobook.domain.Role;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {

    private Integer id;

    private String userEmail;

    private String username;

    private Role role;

    private String phone;

    private Address address;

    private Date regDate;

    private long point;

}
