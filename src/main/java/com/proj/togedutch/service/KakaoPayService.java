package com.proj.togedutch.service;

import com.proj.togedutch.dto.KakaoApproveResDto;
import com.proj.togedutch.dto.KakaoCancelResDto;
import com.proj.togedutch.dto.KakaoReadyResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@RequiredArgsConstructor
@Service
public class KakaoPayService {
    private static final String HOST = "https://kapi.kakao.com";
    private KakaoReadyResDto kakaoReadyRes;
    private KakaoApproveResDto kakaoApproveRes;
    private KakaoCancelResDto kakaoCancelRes;
    /**
     * 카카오 요구 헤더값
     */
    private HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();

        String auth = "KakaoAK " + "c9b25c5ace47f4ccdbc524e87891f97e"; // 카카오 어드민키
        httpHeaders.add("Authorization", auth);
        httpHeaders.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");

        return httpHeaders;
    }
    public KakaoReadyResDto payReady() {

        // 서버로 요청할 Body
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("cid", "TC0ONETIME");
        params.add("partner_order_id", "1001"); // 가맹점 주문번호, 최대 100자
        params.add("partner_user_id", "togeDutch"); // 가맹점 회원 id, 최대 100자
        params.add("item_name", "가치더치 광고 결제"); // 상품명
        params.add("quantity", "1"); // 상품 수량
        params.add("total_amount", "5000"); // 상품 금액
        params.add("tax_free_amount", "0"); // 상품 비과세 금액
        params.add("approval_url", "http://localhost:8080/payment/success"); // 결제 승인 redirect url
        params.add("cancel_url", "http://localhost:8080/payment/cancel"); // 결제 취소 redirect url
        params.add("fail_url", "http://localhost:8080/payment/fail"); // 결제 실패 redirect url

        log.info("파트너 주문 아이디:"+ params.get("partner_order_id"));
        HttpEntity<MultiValueMap<String, String>> requestBody = new HttpEntity<MultiValueMap<String, String>>(params, this.getHeaders());
        RestTemplate restTemplate = new RestTemplate();
        try {
            kakaoReadyRes = restTemplate.postForObject(new URI(HOST + "/v1/payment/ready"), requestBody, KakaoReadyResDto.class);

            log.info("결제준비 응답객체:" + kakaoReadyRes);
            return kakaoReadyRes;
        } catch (RestClientException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public KakaoApproveResDto payApprove(String pg_token) {

        // 서버로 요청할 Body
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("cid", "TC0ONETIME");
        params.add("tid", kakaoReadyRes.getTid());
        params.add("partner_order_id", "1001");
        params.add("partner_user_id", "togeDutch");
        params.add("pg_token", pg_token);

        log.info("파트너 주문 아이디:"+ params.get("partner_order_id"));
        HttpEntity<MultiValueMap<String, String>> requestBody = new HttpEntity<MultiValueMap<String, String>>(params, this.getHeaders());
        RestTemplate restTemplate = new RestTemplate();
        try {
            kakaoApproveRes = restTemplate.postForObject(new URI(HOST + "/v1/payment/approve"), requestBody, KakaoApproveResDto.class);

            log.info("결제승인 응답객체:" + kakaoApproveRes);
            return kakaoApproveRes;
        } catch (RestClientException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public KakaoCancelResDto payCancel(String tid){
        // 서버로 요청할 Body
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("cid", "TC0ONETIME");
        params.add("tid", tid);
        params.add("cancel_amount", "5000"); // 환불금액
        params.add("cancel_tax_free_amount", "0"); //환불 비과세 금액

        HttpEntity<MultiValueMap<String, String>> requestBody = new HttpEntity<MultiValueMap<String, String>>(params, this.getHeaders());
        RestTemplate restTemplate = new RestTemplate();

        try {
            kakaoCancelRes = restTemplate.postForObject(new URI(HOST + "/v1/payment/cancel"), requestBody, KakaoCancelResDto.class);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        log.info("결제취소 응답객체:" + kakaoCancelRes);
        return kakaoCancelRes;
    }
}
