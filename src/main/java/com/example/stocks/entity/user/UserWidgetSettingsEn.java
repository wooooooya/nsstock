package com.example.stocks.entity.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_widget_settings")
@IdClass(UserWidgetSettingsId.class)
@Getter
@Setter
@NoArgsConstructor
public class UserWidgetSettingsEn {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uuid")
    private UserInfoEn userInfo;

    @Id
    @Column(name = "position", nullable = false)
    private Integer position;

    @Column(name = "widget_id", nullable = false)
    private String widgetId;

    public UserWidgetSettingsEn(UserInfoEn userInfo, String widgetId, Integer position) {
        this.userInfo = userInfo;
        this.widgetId = widgetId;
        this.position = position;
    }
}