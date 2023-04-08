package com.proj.togedutch.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryReqDto {
    private String category1;
    private String category2;
    private String category3;
    private String category4;
    private String category5;
    private String category6;
    private Double latitude;
    private Double longitude;
}
