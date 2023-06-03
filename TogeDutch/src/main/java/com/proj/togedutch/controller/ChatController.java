package com.proj.togedutch.controller;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.config.BaseResponse;
import com.proj.togedutch.dto.ChatLocationDto;
import com.proj.togedutch.dto.ChatPhotoDto;
import com.proj.togedutch.service.AWSS3Service;
import com.proj.togedutch.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chatRoom/{chatRoom_id}")
public class ChatController {
    private final ChatService chatService;
    private final AWSS3Service awsS3Service;
    @Value("${cloud.aws.url}")
    private String url;

    // 채팅이미지 생성
    @PostMapping("/chatPhoto")
    public BaseResponse<ChatPhotoDto> PostChatPhoto(@PathVariable("chatRoom_id") int chatRoomId, @RequestParam int user, @RequestPart MultipartFile file) throws IOException {
        try {
            String fileUrl = null;
            fileUrl = url + awsS3Service.uploadChatFile(file);
            ChatPhotoDto chatPhoto = chatService.createChatPhoto(chatRoomId,user,fileUrl);
            return new BaseResponse<>(chatPhoto);
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    //채팅 이미지 가져오기
    @GetMapping("/chatPhoto/{chatPhoto_id}")
    public BaseResponse<ChatPhotoDto> GetChatPhoto(@PathVariable("chatRoom_id") int chatRoomId,@PathVariable("chatPhoto_id") int chatPhotoId) {
        try {
            ChatPhotoDto getChatPhoto = chatService.getChatPhoto(chatRoomId,chatPhotoId);
            return new BaseResponse<>(getChatPhoto);
        } catch(BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    // 채팅 이미지 내역 전체조회
    @GetMapping("/chatPhotos")
    public BaseResponse<List<ChatPhotoDto>> GetChatPhotos(@PathVariable("chatRoom_id") int chatRoomId) {
        try{
            List<ChatPhotoDto> getChatPhotos = chatService.getChatPhotos(chatRoomId);
            return  new BaseResponse<>(getChatPhotos);
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    // 위도경도 위치 저장
    @PostMapping("/chatLocation")
    public BaseResponse<ChatLocationDto> postChatLocation(@PathVariable("chatRoom_id")int chatRoom_id, @RequestParam int user,
                                                       @RequestParam BigDecimal latitude, @RequestParam BigDecimal longitude) {
        try {
            ChatLocationDto chatLocation = chatService.createChatLocation(chatRoom_id, user, latitude, longitude);
            return new BaseResponse<>(chatLocation);
        }catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    @GetMapping("/chatLocation/{chatLocation_id}")
    public BaseResponse<ChatLocationDto> getChatLocation(@PathVariable("chatRoom_id")int chatRoom_id,
                                                         @PathVariable("chatLocation_id")int chatLocationIdx) {
        try {
            ChatLocationDto chatLocation = chatService.getChatLocationById(chatRoom_id, chatLocationIdx);
            return new BaseResponse<>(chatLocation);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
    // 위치 정보 수정
    @PutMapping("/chatLocation/{chatLocation_id}")
    public BaseResponse<ChatLocationDto> putChatLocation(@PathVariable("chatRoom_id")int chatRoom_id, @PathVariable("chatLocation_id")int chatLocationIdx,
                                                         @RequestParam BigDecimal latitude, @RequestParam BigDecimal longitude) {
        try {
            ChatLocationDto chatLocation = chatService.putChatLocation(chatRoom_id, chatLocationIdx, latitude, longitude);
            return new BaseResponse<>(chatLocation);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

}
