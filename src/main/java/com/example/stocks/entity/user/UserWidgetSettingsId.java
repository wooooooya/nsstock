package com.example.stocks.entity.user;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@NoArgsConstructor
@EqualsAndHashCode
public class UserWidgetSettingsId implements Serializable {
    private String userInfo;
    private Integer position;
}