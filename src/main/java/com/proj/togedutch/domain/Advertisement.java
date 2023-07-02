package com.proj.togedutch.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@DynamicInsert
@Entity
@Table(name="Advertisement")
public class Advertisement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ad_id")
    private int adIdx;
    private String store;
    private String information;
    @Column(name="main_menu")
    private String mainMenu;
    @Column(name="delivery_tips")
    private int deliveryTips;
    private String request;
    @Column(name="created_at")
    @CreationTimestamp
    private Timestamp createdAt;
    @Column(name="updated_at")
    @UpdateTimestamp
    private Timestamp updatedAt;
    @Column
    private String status;
    @Column(name="User_user_id")
    private int userIdx;
    private String image;
    private String tid;
    private Double latitude;
    private Double longitude;

    @Builder
    public Advertisement(int adIdx, String store, String information, String mainMenu, int deliveryTips, String request, Timestamp createdAt, Timestamp updatedAt, String status, int userIdx, String image, String tid, Double latitude, Double longitude) {
        this.adIdx = adIdx;
        this.store = store;
        this.information = information;
        this.mainMenu = mainMenu;
        this.deliveryTips = deliveryTips;
        this.request = request;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
        this.userIdx = userIdx;
        this.image = image;
        this.tid = tid;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
