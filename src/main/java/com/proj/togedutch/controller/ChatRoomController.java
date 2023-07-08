package com.proj.togedutch.controller;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.config.BaseResponse;
import com.proj.togedutch.domain.ChatRoom;
import com.proj.togedutch.dto.ChatRoomDto;
import com.proj.togedutch.service.ChatRoomService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/jpa/chatRoom")
@Api(tags = {"채팅방 API"})
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    // 채팅방 생성
    @PostMapping("")
    @ApiOperation(value = "채팅방 생성", notes = "채팅방을 생성합니다.")
    public BaseResponse<ChatRoomDto> createChatRoom(){
        int insertIdx = chatRoomService.createChatRoom(new ChatRoomDto());
        ChatRoomDto chatRoomDto = chatRoomService.getChatRoomById(insertIdx);
        return new BaseResponse<>(chatRoomDto);
    }

    // 모든 채팅방 리스트 조회
    @GetMapping("")
    @ApiOperation(value = "채팅방 전체 조회", notes = "모든 채팅방을 조회합니다.")
    public BaseResponse<List<ChatRoomDto>> getAllChatRooms() throws BaseException {
        List<ChatRoomDto> getChatRoomsRes = chatRoomService.getAllChatRooms();
        return new BaseResponse<>(getChatRoomsRes);
    }

    //채팅방 하나 조회
    @GetMapping("/{chatRoom_id}")
    @ApiOperation(value = "특정 채팅방 조회", notes = "채팅방 id로 특정 채팅방을 조회합니다.")
    @ApiImplicitParam(name = "chatRoom_id", value = "채팅방 생성시 발급되는 id")
    public BaseResponse<ChatRoomDto> getChatRoomById(@PathVariable("chatRoom_id") int chatRoomIdx){
        ChatRoomDto chatRoomDto = chatRoomService.getChatRoomById(chatRoomIdx);
        return new BaseResponse<>(chatRoomDto);
    }

    // 채팅방 삭제
    @DeleteMapping("/{chatRoom_id}")
    @ApiOperation(value = "특정 채팅방 삭제", notes = "채팅방 id로 특정 채팅방을 삭제합니다.")
    @ApiImplicitParam(name = "chatRoom_id", value = "채팅방 생성시 발급되는 id")
    public BaseResponse<Integer> deleteChatRoom (@PathVariable("chatRoom_id") int chatRoomIdx) {
        chatRoomService.deleteChatRoom(chatRoomIdx);
        return new BaseResponse<>(1);
    }
}
