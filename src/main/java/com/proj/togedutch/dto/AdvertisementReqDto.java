package com.proj.togedutch.dto;

import com.proj.togedutch.domain.Advertisement;
import lombok.*;

import javax.persistence.Column;
import java.sql.Timestamp;

@NoArgsConstructor
@Data
public class AdvertisementReqDto {
    private String store;
    private String information;
    private String mainMenu;
    private int deliveryTips;
    private String request;
    private int userIdx;
    private String image;
    private String tid;
    private Double latitude;
    private Double longitude;

    @Builder
    public AdvertisementReqDto(String store, String information, String mainMenu, int deliveryTips, String request, int userIdx, String image, String tid, Double latitude, Double longitude) {
        this.store = store;
        this.information = information;
        this.mainMenu = mainMenu;
        this.deliveryTips = deliveryTips;
        this.request = request;
        this.userIdx = userIdx;
        this.image = image;
        this.tid = tid;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    public Advertisement toEntity() {
        return Advertisement.builder()
                .store(store)
                .information(information)
                .mainMenu(mainMenu)
                .deliveryTips(deliveryTips)
                .request(request)
                .userIdx(userIdx)
                .image(image)
                .tid(tid)
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }
}
