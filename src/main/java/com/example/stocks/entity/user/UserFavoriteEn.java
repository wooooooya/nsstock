package com.example.stocks.entity.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_favorites")
@IdClass(UserFavoriteId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserFavoriteEn {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uuid")
    private UserInfoEn userInfo;

    @Id
    @Column(name = "short_code", columnDefinition = "CHAR(8)")
    private String shortCode;
}