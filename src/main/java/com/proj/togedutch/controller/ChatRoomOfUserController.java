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
    @ApiOperation(value = "채팅유저확인 post")
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

//    @PutMapping("/{chatRoom_id}/user_id/in")



//    // 채팅방에 유저 추가
//    @PostMapping("/{chatRoom_id}/user/{user_id}")
//    public BaseResponse<ChatRoomUser> inviteUserController (@PathVariable("chatRoom_id") int chatRoomIdx, @PathVariable("user_id") int userId) throws Exception{
//        try{
//            ChatRoomOfUser chatRoomUser = chatRoomOfUserService.inviteUser(chatRoomIdx,userId);
//            return new BaseResponse<>(chatRoomUser);
//        } catch (BaseException e){
//            return new BaseResponse<>(e.getStatus());
//        }
//    }
//
//    // 채팅방에 존재하는 있는 유저모두 출력
//    @GetMapping("/{chatRoom_id}/users")
//    public BaseResponse<List<ChatRoomUser>> getChatRoomUsers(@PathVariable("chatRoom_id") int chatRoomIdx) throws Exception{
//        try {
//            List<ChatRoomUser> chatRoomUsers = chatRoomService.getChatRoomUsers(chatRoomIdx);
//            return new BaseResponse<>(chatRoomUsers);
//        } catch (BaseException e){
//            return new BaseResponse<>(e.getStatus());
//        }
//    }
//
//    // 현재 채팅방안에서 채팅을 보는중
//    @PutMapping("/{chatRoom_id}/user/{user_id}/in")
//    public BaseResponse<ChatRoomUser> inChatRoomUser(@PathVariable("chatRoom_id") int chatRoomIdx, @PathVariable("user_id") int userId){
//        try{
//            ChatRoomUser chatRoomUser = chatRoomService.inChatRoomUser(chatRoomIdx,userId);
//            return new BaseResponse<>(chatRoomUser);
//        } catch (BaseException e) {
//            return new BaseResponse<>(e.getStatus());
//        }
//    }
//
//    // 현재 채팅방을 보지 않는 중
//    @PutMapping("/{chatRoom_id}/user/{user_id}/out")
//    public BaseResponse<ChatRoomUser> outChatRoomUser(@PathVariable("chatRoom_id") int chatRoomIdx, @PathVariable("user_id") int userId){
//        try{
//            ChatRoomUser chatRoomUser = chatRoomService.outChatRoomUser(chatRoomIdx,userId);
//            return new BaseResponse<>(chatRoomUser);
//        } catch (BaseException e) {
//            return new BaseResponse<>(e.getStatus());
//        }
//    }
//
//    // 채팅방 나가기
//    @DeleteMapping("/{chatRoom_id}/user/{user_id}/leave")
//    public BaseResponse<Integer> leaveChatRoomUser (@PathVariable("chatRoom_id") int chatRoomIdx, @PathVariable("user_id") int userId) throws Exception{
//        try{
//            int chatRoomUser = chatRoomService.leaveChatRoomUser(chatRoomIdx,userId);
//            return new BaseResponse<>(chatRoomUser);
//        } catch (BaseException e){
//            return new BaseResponse<>(e.getStatus());
//        }
//    }
//
//    // 채팅방에 존재하는 한명의 유저 정보
//    @GetMapping("/{chatRoom_id}/user/{user_id}")
//    public BaseResponse<ChatRoomUser> getChatRoomUser(@PathVariable("chatRoom_id") int chatRoomIdx, @PathVariable("user_id") int userId) throws Exception{
//        try {
//            ChatRoomUser chatRoomUser = chatRoomService.getChatRoomUser(chatRoomIdx,userId);
//            return new BaseResponse<>(chatRoomUser);
//        } catch (BaseException e){
//            return new BaseResponse<>(e.getStatus());
//        }
//    }


}
