package com.assignment.login.user.domain;

import com.assignment.login.user.util.CryptUtil;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "activated")
    private Boolean activated;

    @Builder
    public User(Long id, String email, String nickname, String password, String name, String phone, Boolean activated) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.activated = activated;
    }

    public void changePassword(final String password) {
        this.password = CryptUtil.encode(password);
    }
}
