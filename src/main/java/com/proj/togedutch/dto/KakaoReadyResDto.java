package com.proj.togedutch.dto;

import lombok.Data;

import java.util.Date;

@Data
public class KakaoReadyResDto {
    private String tid; // 결제 고유번호, 20자
    private String next_redirect_app_url; // 요청한 클라이언트(Client)가 모바일 앱일 경우카카오톡 결제 페이지 Redirect URL
    private String next_redirect_pc_url; // pc 웹 결제페이지 url
    private String next_redirect_mobile_url; //모바일 웹 결제페이지 url
    private String partner_order_id;
    private Date created_at;
}
