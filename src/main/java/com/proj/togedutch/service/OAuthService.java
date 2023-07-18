package com.proj.togedutch.service;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.domain.User;
import com.proj.togedutch.dto.KakaoLoginDto;
import com.proj.togedutch.dto.UserResDto;
import com.proj.togedutch.repository.UserRepository;
import com.proj.togedutch.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OAuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public UserResDto kakaoLogin(KakaoLoginDto kakaoLoginDto) throws BaseException {
        String email = kakaoLoginDto.getEmail();
        User getUser = userRepository.findUserByEmail(email);

        // 앱 설치 후 최초 카카오 로그인 시도 : 회원가입 진행
        if(getUser == null) {
            // 카카오로 회원가입하는 경우 -> 이메일로 비밀번호 찾기 막아두기 (현아언니 수정 끝나면 !!)
            User newUser = new User(1, email, kakaoLoginDto.getLatitude(), kakaoLoginDto.getLongitude());
            getUser = userRepository.save(newUser);
        }

        String jwt = jwtService.createJwt(getUser.getUserIdx());
        UserResDto userResDto = new UserResDto(getUser.getUserIdx(), 1, email, getUser.getLatitude(), getUser.getLongitude());
        userResDto.setJwt(jwt);
        return userResDto;
    }
}
