package com.proj.togedutch.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostReqDto {
    @ApiModelProperty(example = "제목")
    private String title;
    @ApiModelProperty(example = "첨부한 URL")
    private String url;
    @ApiModelProperty(example = "배달팁")
    private int delivery_tips;
    @ApiModelProperty(example = "최소 주문 금액")
    private int minimum;
    @ApiModelProperty(example = "주문 시간")
    private String order_time;
    @ApiModelProperty(example = "모집 인원")
    private int num_of_recruits;
    @ApiModelProperty(example = "모집된 인원")
    private int recruited_num;
    @ApiModelProperty(example = "공고 상태")
    private String status;
    @ApiModelProperty(example = "위도")
    private Double latitude;
    @ApiModelProperty(example = "경도")
    private Double longitude;
    @ApiModelProperty(example = "카테고리")
    private String category;

}
