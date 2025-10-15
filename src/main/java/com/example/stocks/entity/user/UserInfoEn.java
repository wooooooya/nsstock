package com.example.stocks.entity.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_info")
@Getter
@Setter
@NoArgsConstructor
public class UserInfoEn {

    @Id
    @Column(name = "uuid", length = 36)
    private String uuid;

    @Column(name = "email", nullable = false) // email 길이는 DB와 동일하게 255
    private String email;

    @Column(name = "login_id", nullable = false, length = 255, unique = true)
    private String loginId;

    @Column(name = "pw", nullable = false)
    private String pw;

    @OneToMany(mappedBy = "userInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserWidgetSettingsEn> userWidgetSettings = new ArrayList<>();

    @OneToMany(mappedBy = "userInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserFavoriteEn> favorites = new ArrayList<>();
}