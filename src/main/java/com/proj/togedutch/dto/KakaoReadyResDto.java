package com.proj.togedutch.dto;

import lombok.Data;

import java.util.Date;

@Data
public class KakaoReadyResDto {
    private String tid; // 결제 고유번호
    private String next_redirect_app_url;
    private String next_redirect_pc_url; // 결제페이지 url
    private String next_redirect_mobile_url;
    private String partner_order_id;
    private Date created_at;
}
