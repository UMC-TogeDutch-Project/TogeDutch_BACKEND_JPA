package com.proj.togedutch.service;


import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.domain.ChatLocation;
import com.proj.togedutch.domain.ChatMessage;
import com.proj.togedutch.domain.ChatPhoto;
import com.proj.togedutch.dto.ChatLocationDto;
import com.proj.togedutch.dto.ChatMessageDto;
import com.proj.togedutch.dto.ChatPhotoDto;
import com.proj.togedutch.dto.UserResDto;
import com.proj.togedutch.repository.ChatLocationRepository;
import com.proj.togedutch.repository.ChatMessageRepository;
import com.proj.togedutch.repository.ChatPhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.proj.togedutch.config.BaseResponseStatus.DATABASE_ERROR;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatMessageRepository chatMessageRepository;
    private final ChatPhotoRepository chatPhotoRepository;
    private final ChatLocationRepository chatLocationRepository;

    // 채팅 메세지 전체 조회
    public List<ChatMessageDto> getChatMessages (int chatRoomIdx) throws BaseException {
        List<ChatMessage> chatMessages = chatMessageRepository.findByChatRoomId(chatRoomIdx);
        Collections.sort(chatMessages, (message1, message2) -> message2.getCreateAt().compareTo(message1.getCreateAt()));
        return chatMessages.stream()
                .map(m->new ChatMessageDto(m))
                .collect(Collectors.toList());
    }


    // 채팅 이미지 생성
    public ChatPhotoDto createChatPhoto(int chatRoomIdx, int userIdx, String file) throws BaseException {
        // Dto to Entity
        ChatPhoto newChatPhoto = ChatPhoto.builder()
                .chatRoomIdx(chatRoomIdx)
                .userIdx(userIdx)
                .image(file)
                .build();

        int chatPhotoIdx = chatPhotoRepository.save(newChatPhoto).getChatPhotoIdx();
        return getChatPhoto(chatRoomIdx, chatPhotoIdx);
    }

    // 채팅 이미지 조회
    public ChatPhotoDto getChatPhoto(int chatRoomIdx, int chatPhotoIdx) throws BaseException {
        ChatPhoto getChatPhoto = chatPhotoRepository.findByChatPhotoIdxAndChatRoomIdx(chatPhotoIdx, chatRoomIdx);
        return new ChatPhotoDto(getChatPhoto);
    }

    // 채팅방에서 이미지 전체 조회
    public List<ChatPhotoDto> getChatPhotos(int chatRoomIdx) throws BaseException {
        List<ChatPhoto> getChatPhotos = chatPhotoRepository.findByChatRoomIdx(chatRoomIdx);
        return getChatPhotos.stream()
                .map(m->new ChatPhotoDto(m))
                .collect(Collectors.toList());
    }

    // 위치 설정
    public ChatLocationDto createChatLocation(int chatRoomIdx, int userIdx, BigDecimal latitude, BigDecimal longitude) throws BaseException {
        ChatLocation newChatLocation = ChatLocation.builder()
                .chatRoomIdx(chatRoomIdx)
                .userIdx(userIdx)
                .latitude(latitude)
                .longitude(longitude)
                .build();

        ChatLocation getChatLocation = chatLocationRepository.save(newChatLocation);
        return new ChatLocationDto(getChatLocation);
    }

    // 위치 조회
    public ChatLocationDto getChatLocationById(int chatRoomIdx, int chatLocationIdx) throws BaseException {
        ChatLocation getChatLocation = chatLocationRepository.findByChatRoomIdxAndChatLocationIdx(chatRoomIdx, chatLocationIdx);
        return new ChatLocationDto(getChatLocation);
    }

    // 위치 수정
    public ChatLocationDto putChatLocation(int chatRoomIdx, int chatLocationIdx, BigDecimal latitude, BigDecimal longitude) throws BaseException {
        ChatLocation getChatLocation = chatLocationRepository.findById(chatLocationIdx)
                .orElseThrow(IllegalArgumentException::new);
        getChatLocation.updateChatLocation(chatRoomIdx, latitude, longitude);
        ChatLocation newChatLocation = chatLocationRepository.save(getChatLocation);
        return new ChatLocationDto(newChatLocation);
    }
}
