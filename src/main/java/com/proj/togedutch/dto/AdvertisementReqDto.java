package com.proj.togedutch.dto;

import com.proj.togedutch.domain.Advertisement;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Column;
import java.sql.Timestamp;

@NoArgsConstructor
@Data
public class AdvertisementReqDto {
    @ApiModelProperty(example = "가게 이름")
    private String store;
    @ApiModelProperty(example = "가게 정보 링크")
    private String information;
    @ApiModelProperty(example = "가게의 메인메뉴")
    private String mainMenu;
    @ApiModelProperty(example = "배달팁")
    private int deliveryTips;
    @ApiModelProperty(example = "가게의 요청사항")
    private String request;
    @ApiModelProperty(example = "광고 생성자의 id")
    private int userIdx;
    @ApiModelProperty(example = "첨부한 이미지 URL")
    private String image;
    @ApiModelProperty(example = "결제고유번호")
    private String tid;
    @ApiModelProperty(example = "위도")
    private Double latitude;
    @ApiModelProperty(example = "경도")
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