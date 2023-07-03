package com.proj.togedutch.service;

import com.proj.togedutch.domain.*;
import com.proj.togedutch.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    private final ChatRoomOfUserRepository chatRoomOfUserRepository;

    private final ChatPhotoRepository chatPhotoRepository;

    private final ChatLocationRepository chatLocationRepository;

    private final ChatMeetTimeRepository chatMeetTimeRepository;

    @Autowired
    private SimpMessageSendingOperations simpMessagingTemplate;

    //채팅에서 메세지 전송
    @Transactional
    public void sendChatMessaage(ChatMessage message){
        String roomIdName = Integer.toString(message.getChatRoomId());
        if (ChatMessage.MessageType.ENTER.equals(message.getType())){
            message.setContent(message.getWriter()+"님이 방에 입장했습니다.");
            } else if (ChatMessage.MessageType.QUIT.equals(message.getType())) {
            message.setContent(message.getWriter() + "님이 방에서 나갔습니다.");
        }
        ChatMessage chatMessage = ChatMessage.builder()
                .chat_id(message.getChatId())
                .chatRoom_chatRoom_id(message.getChatRoomId())
                .user_user_id(message.getUserId())
                .created_at(message.getCreateAt())
                .content(message.getContent())
                .name(message.getWriter()) //이름 유저에서 가져와야함
                .build();
        chatRoomOfUserRepository.incrementIsReadCount(message.getChatRoomId());
        chatMessageRepository.save(chatMessage);
        simpMessagingTemplate.convertAndSend("/sub/chat/room/"+roomIdName,message);
    }

    // 채팅에서 이미지 전송
    @Transactional
    public void sendChatImgFile(ChatPhoto photo){
        String roomIdName = Integer.toString(photo.getChatRoomIdx());
        simpMessagingTemplate.convertAndSend("/sub/chat/room/" + roomIdName, photo); //photo 전송
        ChatPhoto chatPhoto = ChatPhoto.builder()
                .chatRoomIdx(photo.getChatRoomIdx())
                .userIdx(photo.getUserIdx())
                .image(photo.getImage())
                .build();
        chatPhotoRepository.save(chatPhoto);
    }

    // 채팅에서 위치 전송
    @Transactional
    public void sendChatLocation(ChatLocation location){
        String roomIdName = Integer.toString(location.getChatRoomIdx());
        simpMessagingTemplate.convertAndSend("/sub/chat/room/" + roomIdName, location); //photo 전송
        ChatLocation chatLocation = ChatLocation.builder()
                .chatRoomIdx(location.getChatRoomIdx())
                .userIdx(location.getUserIdx())
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .build();
        chatLocationRepository.save(chatLocation);
    }

     //채팅에서 만남시간 전송
    @Transactional
    public void sendChatMeetTime(ChatMeetTime meetTime){
        String roomIdName = Integer.toString(meetTime.getChatRoomIdx());
        simpMessagingTemplate.convertAndSend("/sub/chat/room/" + roomIdName, meetTime); //photo 전송
        ChatMeetTime chatMeetTime = ChatMeetTime.builder()
                .chatRoomIdx(meetTime.getChatRoomIdx())
                .userIdx(meetTime.getUserIdx())
                .meetTime(meetTime.getMeetTime())
                .build();
        chatMeetTimeRepository.save(chatMeetTime);
    }
}
