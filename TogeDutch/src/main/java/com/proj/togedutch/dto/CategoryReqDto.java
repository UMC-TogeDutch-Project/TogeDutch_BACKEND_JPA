package com.proj.togedutch.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryReqDto {
    @ApiModelProperty(example = "카테고리1")
    private String category1;
    @ApiModelProperty(example = "카테고리2")
    private String category2;
    @ApiModelProperty(example = "카테고리3")
    private String category3;
    @ApiModelProperty(example = "카테고리4")
    private String category4;
    @ApiModelProperty(example = "카테고리5")
    private String category5;
    @ApiModelProperty(example = "카테고리6")
    private String category6;
    @ApiModelProperty(example = "검색자의 현재 위도")
    private Double latitude;
    @ApiModelProperty(example = "검색자의 현재 경도")
    private Double longitude;
}
