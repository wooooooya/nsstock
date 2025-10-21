package com.example.stocks.entity.user;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserFavoriteId implements Serializable {
    private String userInfo;
    private String shortCode;
}