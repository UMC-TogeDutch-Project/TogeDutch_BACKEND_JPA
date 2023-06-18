package com.proj.togedutch.dto;

import lombok.Data;

@Data
public class FCMReqDto {
    private String targetToken;
    private String title;
    private String body;
}
