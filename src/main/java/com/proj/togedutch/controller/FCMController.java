package com.proj.togedutch.controller;

import com.proj.togedutch.dto.FCMReqDto;
import com.proj.togedutch.service.FCMService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class FCMController {
    private final FCMService fcmService;

    @PostMapping("/api/fcm")
    public ResponseEntity pushMessage(@RequestBody FCMReqDto requestDTO) throws IOException {
        // FCMReqDto > targetToken = 기기에서 얻어온 registration token값
        // registration token : FCM에서 발급 받은 토큰 + 서버에 전달하여 클라이언트 앱에서 메시지 알림을 전송할 때 사용
        fcmService.sendMessageTo(
                requestDTO.getTargetToken(),
                requestDTO.getTitle(),
                requestDTO.getBody());
        return ResponseEntity.ok().build();
    }
}
