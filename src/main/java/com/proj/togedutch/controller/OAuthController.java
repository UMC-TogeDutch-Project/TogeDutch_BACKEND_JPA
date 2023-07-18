package com.proj.togedutch.controller;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.config.BaseResponse;
import com.proj.togedutch.dto.KakaoLoginDto;
import com.proj.togedutch.dto.UserResDto;
import com.proj.togedutch.service.OAuthService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class OAuthController {
    private final OAuthService oAuthService;

    @PostMapping("/kakao")
    public BaseResponse<UserResDto> kakaoLogin(@RequestBody KakaoLoginDto kakoLoginDto) throws Exception {
        try{
            UserResDto userResDto = oAuthService.kakaoLogin(kakoLoginDto);
            return new BaseResponse<>(userResDto);
        } catch(BaseException e) {
            e.printStackTrace();
            return new BaseResponse<>(e.getStatus());
        }
    }
}
