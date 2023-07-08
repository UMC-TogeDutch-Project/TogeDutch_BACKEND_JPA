package com.proj.togedutch.dto;

import lombok.Data;

@Data
public class CardInfo {
    private String purchase_corp, purchase_corp_code; // 매입 카드사 한글명, 코드
    private String issuer_corp, issuer_corp_code; // 카드 발급사 한글명, 코드
    private String bin, card_type; // 카드 BIN, 타입
    private String install_month, approved_id, card_mid; // 할부 개월 수, 카드사 승인번호, 카드사 가맹점 번호
    private String interest_free_install; // 무이자할부 여부(Y/N)
    private String card_item_code; // 카드 상품 코드
}
