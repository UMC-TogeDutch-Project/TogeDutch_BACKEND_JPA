package com.proj.togedutch.controller;

import com.proj.togedutch.domain.ChatLocation;
import com.proj.togedutch.domain.ChatMeetTime;
import com.proj.togedutch.domain.ChatMessage;
import com.proj.togedutch.domain.ChatPhoto;
import com.proj.togedutch.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/jpa/chat")
public class ChatMessageController {
    private final ChatMessageService chatMessageService;

    // 채팅 메시지
    // websocket "/pub/chat/message"로 들어오는 메시징을 처리한다.
    // 입장 message.type = ENTER , 퇴장 message.type = QUIT
    @MessageMapping("/message")
    public void message(ChatMessage message){
        chatMessageService.sendChatMessaage(message);
    }

    //채팅 이미지 전송
    @MessageMapping("/image")
    public void image(ChatPhoto chatPhoto){
        chatMessageService.sendChatImgFile(chatPhoto);
    }

    // 채팅 위치 전송
    @MessageMapping("/location")
    public void location(ChatLocation location){
        chatMessageService.sendChatLocation(location);
    }

    //채팅 만남시간 전송
    @MessageMapping("/meetTime")
    public void meetTime(ChatMeetTime meetTime){
        chatMessageService.sendChatMeetTime(meetTime);
    }


}
