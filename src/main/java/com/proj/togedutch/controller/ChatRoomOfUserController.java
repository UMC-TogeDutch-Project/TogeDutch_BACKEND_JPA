package com.proj.togedutch.controller;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.config.BaseResponse;
import com.proj.togedutch.domain.ChatRoomOfUser;
import com.proj.togedutch.dto.ChatRoomDto;
import com.proj.togedutch.dto.ChatRoomOfUserResDto;
import com.proj.togedutch.service.ChatRoomOfUserService;
import com.proj.togedutch.service.ChatRoomService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/jpa/chatRoom")
@Api(tags = {"채팅 유저 API"})
public class ChatRoomOfUserController {

    private final ChatRoomOfUserService chatRoomOfUserService;

    // 채팅방에 유저 입장 - post
    @PostMapping("/{chatRoom_id}/user/{user_id}")
    @ApiOperation(value = "채팅유저확인 post",notes = "채팅방에 유저가 초대 됩니다.")
    public BaseResponse<ChatRoomOfUserResDto> createChatRoomOfUser(@PathVariable("chatRoom_id") int chatRoom_id, @PathVariable("user_id") int user_id){
        try{
            ChatRoomOfUserResDto chatRoomOfUserResDto = new ChatRoomOfUserResDto();
            int chatRoomIdx = chatRoomOfUserService.inviteUser(chatRoomOfUserResDto,chatRoom_id,user_id);
            ChatRoomOfUserResDto chatRoomOfUser = chatRoomOfUserService.getChatRoomOfUser(chatRoomIdx);
            return new BaseResponse<>(chatRoomOfUser);
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    // 채팅방에 있는 유저모두 출력
    @GetMapping("/{chatRoom_id}/users")
    public BaseResponse<List<ChatRoomOfUserResDto>> getChatRoomOfUser(@PathVariable("chatRoom_id") int chatRoom_id){
        return new BaseResponse<>(chatRoomOfUserService.getChatRoomOfUsers(chatRoom_id));
    }

    // 현재 채팅방안에서 채팅을 보는 상태
    @PutMapping("/{chatRoom_id}/user/{user_id}/in")
    public BaseResponse<ChatRoomOfUserResDto> inChatRoomUser(@PathVariable("chatRoom_id") int chatRoomIdx, @PathVariable("user_id") int userId){
        try {
            ChatRoomOfUserResDto chatRoomUser = chatRoomOfUserService.inChatRoomUser(chatRoomIdx,userId);
            return new BaseResponse<>(chatRoomUser);
        }catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    //현재 채팅방에 없는 상태
    @PutMapping("/{chatRoom_id}/user/{user_id}/out")
    public BaseResponse<ChatRoomOfUserResDto> outChatRoomUser(@PathVariable("chatRoom_id") int chatRoomIdx, @PathVariable("user_id") int userId){
        try {
            ChatRoomOfUserResDto chatRoomUser = chatRoomOfUserService.outChatRoomUser(chatRoomIdx,userId);
            return new BaseResponse<>(chatRoomUser);
        }catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    // 채팅방에서 나가기
    @DeleteMapping("/{chatRoom_id}/user/{user_id}/leave")
    public BaseResponse<Integer> leaveChatRoomUser(@PathVariable("chatRoom_id") int chatRoomIdx, @PathVariable("user_id") int userId){
        try{
            int chatRoomUser = chatRoomOfUserService.leaveChatRoomUser(chatRoomIdx,userId);
            return new BaseResponse<>(chatRoomUser);
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    // 채팅방 읽지않은 메세지 수 출력
    @GetMapping("/{chatRoom_id}/user/{user_id}/unread")
    public BaseResponse<Integer> unreadChatRoomUser(@PathVariable("chatRoom_id") int chatRoomIdx, @PathVariable("user_id") int userIdx){
        try{
            int unreadCnt = chatRoomOfUserService.unreadChatRoomUser(chatRoomIdx,userIdx);
            return new BaseResponse<>(unreadCnt);
        }catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }
}
