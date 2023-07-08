package com.proj.togedutch.dto;

import com.proj.togedutch.domain.Advertisement;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisementResDto {
    @ApiModelProperty(example = "광고 생성시 부여된 id")
    private int adIdx;
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
    @ApiModelProperty(example = "최초 작성 시간")
    private Timestamp createdAt;
    @ApiModelProperty(example = "마지막으로 수정한 시간")
    private Timestamp updatedAt;
    @ApiModelProperty(example = "광고 상태")
    private String status;
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
