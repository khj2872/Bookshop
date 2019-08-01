package com.kobobook.www.kobobook.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
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

    @Temporal(TemporalType.TIMESTAMP)
    private Date regDate;

    private long point;

//    @JsonIgnore
//    @OneToMany(mappedBy = "member")
//    private List<Order> orders = new ArrayList<>();

    public boolean matchPassword(String password) {
        return this.getPassword().equals(password);
    }

    public boolean isAdmin() {
        return this.role.equals(Role.ROLE_ADMIN);
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", userEmail='" + userEmail + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", oauthId='" + oauthId + '\'' +
                ", role='" + role + '\'' +
                ", phone='" + phone + '\'' +
                ", address=" + address +
                ", regDate=" + regDate +
                ", point=" + point +
                '}';
    }
}
