package com.kobobook.www.kobobook.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Column(nullable = false)
    private String role;

    private String phone;

    @Embedded
    private Address address;

    @Temporal(TemporalType.TIMESTAMP)
    private Date regDate;

    private long point;

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    public boolean matchPassword(String password) {
        return this.getPassword().equals(password);
    }
}
