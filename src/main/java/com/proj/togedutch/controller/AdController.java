package com.proj.togedutch.controller;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.config.BaseResponse;
import com.proj.togedutch.config.BaseResponseStatus;
import com.proj.togedutch.dto.AdvertisementReqDto;
import com.proj.togedutch.dto.AdvertisementResDto;
import com.proj.togedutch.dto.KakaoReadyResDto;
import com.proj.togedutch.service.AWSS3Service;
import com.proj.togedutch.service.AdService;
import com.proj.togedutch.service.KakaoPayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/ad")
@Api(tags = {"광고 API"})
public class AdController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final KakaoPayService kakaoPayService;
    private final KakaoPayController kakaoPayController;
    private final AdService adService;
    private final AWSS3Service awsS3Service;

    @Value("${cloud.aws.url}")
    private String url;

    // 광고 생성
    @PostMapping("/{userIdx}")
    @ApiOperation(value = "광고 생성", notes = "카카오페이 결제창을 띄워주고 결제가 완료되면 새로운 광고를 생성합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userIdx", value = "광고 생성자의 user_id"),
            @ApiImplicitParam(name = "ad", value = "광고 생성시 작성한 내용"),
            @ApiImplicitParam(name = "file", value = "광고 생성시 첨부한 이미지")
    })
    public BaseResponse<?> createAd(@PathVariable("userIdx") int userIdx, @RequestPart AdvertisementReqDto ad,
                                                @RequestPart(value = "file" , required = false) MultipartFile file) throws IOException{
        if (ad.getStore() == null) {
            return new BaseResponse<>(BaseResponseStatus.POST_AD_EMPTY_STORE);
        }
        if (ad.getInformation() == null) {
            return new BaseResponse<>(BaseResponseStatus.POST_AD_EMPTY_INFORMATION);
        }
        if (ad.getMainMenu() == null) {
            return new BaseResponse<>(BaseResponseStatus.POST_AD_EMPTY_MAINMENU);
        }
        if (Integer.valueOf(ad.getDeliveryTips()) == null) {
            return new BaseResponse<>(BaseResponseStatus.POST_AD_EMPTY_TIP);
        }
        if (ad.getLatitude() == null) {
            return new BaseResponse<>(BaseResponseStatus.POST_AD_EMPTY_LOCATION);
        }
        if (ad.getLongitude() == null) {
            return new BaseResponse<>(BaseResponseStatus.POST_AD_EMPTY_LOCATION);
        }

        String fileUrl = null;
        if(file!=null && !file.isEmpty())
            fileUrl = url + awsS3Service.uploadAdFile(file);

        // 카카오 결제 준비 - 결제 요청 service 실행
        KakaoReadyResDto kakaoReadyRes = kakaoPayController.kakaoPayReady(ad, userIdx, fileUrl);
        return new BaseResponse<>(kakaoReadyRes);
    }
    //개인의 광고 전체 조회
    @GetMapping("")
    @ApiOperation(value = "유저의 광고 전체 조회", notes = "유저가 신청했던 광고를 조회합니다.")
    @ApiImplicitParam(name = "userIdx", value = "광고 생성자의 user_id")
    public BaseResponse<List<AdvertisementResDto>> getAllAds(@RequestParam int userIdx) throws BaseException {
        try {
            List<AdvertisementResDto> getAds = adService.getAdsByUserId(userIdx);
            return new BaseResponse<>(getAds);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    //랜덤으로 반경 1km 광고 10개 조회
    @GetMapping("/random")
    @ApiOperation(value = "랜덤 광고 조회", notes = "유저 위치에서 반경 1km 안에 있는 광고를 10개 조회합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "latitude", value = "유저의 현재 위도"),
            @ApiImplicitParam(name = "longitude", value = "유저의 현재 경도")
    })
    public BaseResponse<List<AdvertisementResDto>> getRandomAd(@RequestParam double latitude, @RequestParam double longitude) throws BaseException {
        try {
            List<AdvertisementResDto> getRandomAds = adService.getRandomAds(latitude, longitude);
            return new BaseResponse<>(getRandomAds);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    //광고 상세 조회
    @GetMapping("{adIdx}")
    @ApiOperation(value = "광고 상세 조회", notes = "해당 광고의 상세 정보를 조회합니다.")
    @ApiImplicitParam(name = "adIdx", value = "광고 생성시 부여된 id")
    public BaseResponse<AdvertisementResDto> getAdById(@PathVariable("adIdx") int adIdx) throws BaseException {
        try {
            AdvertisementResDto getAd = adService.getAdById(adIdx);
            return new BaseResponse<>(getAd);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
}

