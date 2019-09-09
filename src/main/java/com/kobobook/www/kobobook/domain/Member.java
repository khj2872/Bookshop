package com.kobobook.www.kobobook.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@Builder
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Integer id;

    @Column(nullable = false)
    private String userEmail;

    @Column(nullable = false)
    private String username;

    private String password;

    private String oauthId;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    private String phone;

    @Embedded
    private Address address;

    private LocalDateTime regDate;

    private long point;

    public boolean matchPassword(String password) {
        return this.getPassword().equals(password);
    }

    public boolean isAdmin() {
        return this.role.equals(Role.ROLE_ADMIN);
    }

}
