package com.proj.togedutch.controller;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.config.BaseResponse;
import com.proj.togedutch.config.BaseResponseStatus;
import com.proj.togedutch.dto.*;
import com.proj.togedutch.service.AdService;
import com.proj.togedutch.service.KakaoPayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/payment")
@Api(tags = {"카카오페이 API"})
public class KakaoPayController {
    private final KakaoPayService kakaoPayService;
    private final AdService adService;
    private AdvertisementReqDto ad;
    private int userIdx;
    private String fileUrl;

    @GetMapping("")
    public void kakaoPayGet(){
    }

    @GetMapping("/ready")
    @ApiOperation(value = "카카오페이 결제 준비",
            notes = "카카오페이 결제를 준비하는 단계로 정보를 카카오페이 서버에 전달하고 결제 고유 번호인 TID를 받습니다." +
                    "직접 호출하지 않고 광고 생성 api를 호출하면 자동으로 호출됩니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ad", value = "광고 생성시 작성한 내용"),
            @ApiImplicitParam(name = "userIdx", value = "광고 생성자의 user_id"),
            @ApiImplicitParam(name = "file", value = "광고 생성시 첨부한 이미지")
    })
    public KakaoReadyResDto kakaoPayReady(AdvertisementReqDto ad, int userIdx, String fileUrl) {
        //log.info("주문가격:"+totalAmount);
        this.ad = ad;
        this.userIdx = userIdx;
        this.fileUrl = fileUrl;
        KakaoReadyResDto kakaoReadyRes = kakaoPayService.payReady();
        // 카카오 결제 준비 - 결제 요청 service 실행
        return kakaoReadyRes;
    }
    @GetMapping("/success")
    @ApiOperation(value = "카카오페이 결제 성공", notes = "결제가 성공했을 때 자동으로 호출되는 api입니다.")
    @ApiImplicitParam(name = "pg_token", value = "결제승인 요청을 인증하는 토큰. 결제 대기 화면이 approval_url에 붙여 리다이렉트해줍니다.")
    public BaseResponse<AdvertisementResDto> kakaoPaySuccess(@RequestParam("pg_token") String pg_token, Model model){
        log.info("kakaoPaySuccess get............................................");
        log.info("kakaoPaySuccess pg_token : " + pg_token);
        KakaoApproveResDto kakaoApproveRes = kakaoPayService.payApprove(pg_token);
        try{
            AdvertisementResDto newAd = adService.createAd(ad, userIdx, fileUrl, kakaoApproveRes.getTid());
            AdvertisementResDto getAd = adService.getAdById(newAd.getAdIdx());
            return new BaseResponse<>(getAd); // 광고 신청 현황 페이지로 redirect
        }catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
    @GetMapping("/cancel")
    @ApiOperation(value = "카카오페이 결제 진행 중 취소", notes = "결제 진행 중 취소했을 때 취소화면을 리다이렉트합니다.")
    public void kakaoPayCancel() throws BaseException {
        throw new BaseException(BaseResponseStatus.KAKAO_PAY_CANCLE); // 결제 취소화면 redirect
    }
    @GetMapping("/fail")
    @ApiOperation(value = "카카오페이 결제 진행 중 실패", notes = "결제가 실패했을 때 실패화면을 리다이렉트합니다.")
    public void kakaoPayFail() throws BaseException {
        throw new BaseException(BaseResponseStatus.KAKAO_PAY_FAIL); // 결제 실패화면 redirect
    }

    @PostMapping("/refund")
    @ApiOperation(value = "카카오페이 결제 환불", notes = "결제했던 것을 환불합니다..")
    @ApiImplicitParam(name = "tid", value = "결제할 때 발급되는 결제고유번호")
    public BaseResponse<?> kakaoPayRefund(@RequestParam("tid") String tid){
        KakaoCancelResDto kakaoCancelRes = kakaoPayService.payCancel(tid);
        try {
            adService.deleteAd(tid);
            return new BaseResponse<>(kakaoCancelRes);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}
