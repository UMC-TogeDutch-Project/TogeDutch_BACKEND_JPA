package com.proj.togedutch.dto;

import com.proj.togedutch.domain.Advertisement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisementResDto {
    private int adIdx;
    private String store;
    private String information;
    private String mainMenu;
    private int deliveryTips;
    private String request;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String status;
    private int userIdx;
    private String image;
    private String tid;
    private Double latitude;
    private Double longitude;

    public AdvertisementResDto(Advertisement entity) {
        this.adIdx = entity.getAdIdx();
        this.store = entity.getStore();
        this.information = entity.getInformation();
        this.mainMenu = entity.getMainMenu();
        this.deliveryTips = entity.getDeliveryTips();
        this.request = entity.getRequest();
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
        this.status = entity.getStatus();
        this.userIdx = entity.getUserIdx();
        this.image = entity.getImage();
        this.tid = entity.getTid();
        this.latitude = entity.getLatitude();
        this.longitude = entity.getLongitude();
    }
}
