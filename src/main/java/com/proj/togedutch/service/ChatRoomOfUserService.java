package com.proj.togedutch.service;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.config.BaseResponseStatus;
import com.proj.togedutch.domain.ChatRoomOfUser;
import com.proj.togedutch.dto.ChatRoomOfUserResDto;
import com.proj.togedutch.repository.ChatRoomOfUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatRoomOfUserService {

    private final ChatRoomOfUserRepository chatRoomOfUserRepository;

    public int inviteUser(ChatRoomOfUserResDto chatRoomOfUserResDto,int chatRoom_id,int user_id) throws BaseException {
        chatRoomOfUserResDto.setChatRoom_id(chatRoom_id);
        chatRoomOfUserResDto.setUser_id(user_id);
        return (chatRoomOfUserRepository.save(chatRoomOfUserResDto.toEntity()).getChatRoomIdx());

//        try {
//            chatRoomOfUserResDto.setChatRoom_id(chatRoom_id);
//            chatRoomOfUserResDto.setUser_id(user_id);
//            return (chatRoomOfUserRepository.save(chatRoomOfUserResDto.toEntity()).getChatRoomIdx());
//        } catch (DataIntegrityViolationException e) {
//            if (e.getCause() instanceof SQLException) {
//                SQLException sqlException = (SQLException) e.getCause();
//                if (sqlException.getErrorCode() == 1062) {
//                    throw new BaseException(BaseResponseStatus.DUPLICATE_KEY_ORROR);
//                } else if (sqlException.getErrorCode() == 1452) {
//                    throw new BaseException(BaseResponseStatus.FOREIGN_KEY_ORROR);
//                } else throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
//            } else throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
//        } catch (Exception e) {
//            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
//        }
    }

    // 현재 채팅방에 있는 유저
    public ChatRoomOfUserResDto getChatRoomOfUser(int chatRoomIdx){
        ChatRoomOfUser chatRoomOfUser = chatRoomOfUserRepository.findById(chatRoomIdx)
                .orElseThrow(()-> new IllegalArgumentException("해당 채팅방이 없습니다. ID = "+chatRoomIdx ));
        return new ChatRoomOfUserResDto(chatRoomOfUser);
    }

    // 현재 채팅방에 있는 모든 유저
    public List<ChatRoomOfUserResDto> getChatRoomOfUsers(int chatRoomIdx){
        List<ChatRoomOfUser> chatRoomOfUsers = chatRoomOfUserRepository.findAll();
        return chatRoomOfUsers.stream()
                .map(m-> new ChatRoomOfUserResDto(m.getChatRoomIdx(), m.getUserIdx(),m.getStatus(),m.getIs_read(),m.getUpdated_at()))
                .collect(Collectors.toList());
    }


//    public List<ChatRoomOfUserResDto> getChatRoomUsers(int chatRoomIdx) throws BaseException {
//        try {
//            // Use the repository method to get the chat room users
//            List<ChatRoomOfUser> chatRoomOfUsers = chatRoomOfUserRepository.findByChatRoomIdx(chatRoomIdx);
//
//            // Convert the entities to DTOs and return the list
//            return convertToChatRoomOfUserResDtoList(chatRoomOfUsers);
//        } catch (Exception e) {
//            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
//        }
//    }
//
//    public ChatRoomOfUserResDto inChatRoomUser(int chatRoomIdx, int userId) throws BaseException {
//        try {
//            // Create and save a new ChatRoomUser entity
//            ChatRoomOfUser chatRoomUser = new ChatRoomOfUser();
//            chatRoomUser.setChatRoomIdx(chatRoomIdx);
//            chatRoomUser.setUserId(userId);
//            chatRoomUser = chatRoomOfUserRepository.save(chatRoomUser);
//
//            return chatRoomUser;
//        } catch (Exception e) {
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }
//
//    public ChatRoomOfUserResDto outChatRoomUser(int chatRoomIdx, int userId) throws BaseException {
//        try {
//            // Delete the ChatRoomUser entity
//            chatRoomOfUserRepository.deleteByChatRoomIdxAndUserId(chatRoomIdx, userId);
//
//            // Retrieve and return the deleted entity if necessary
//            return chatRoomOfUserRepository.findByChatRoomIdxAndUserId(chatRoomIdx, userId)
//                    .orElseThrow(() -> new BaseException(DATABASE_ERROR));
//        } catch (Exception e) {
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }
//
//    public int leaveChatRoomUser(int chatRoomIdx, int userId) throws BaseException {
//        try {
//            // Delete the ChatRoomUser entity and return the number of affected rows
//            return chatRoomOfUserRepository.deleteByChatRoomIdxAndUserId(chatRoomIdx, userId);
//        } catch (Exception e) {
//            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
//        }
//    }
//
//    public ChatRoomUser getChatRoomUser(int chatRoomIdx, int userId) throws BaseException {
//        try {
//            // Retrieve the ChatRoomUser entity by chatRoomIdx and userId
//            return chatRoomOfUserRepository.findByChatRoomIdxAndUserId(chatRoomIdx, userId)
//                    .orElseThrow(() -> new BaseException(DATABASE_ERROR));
//        } catch (Exception e) {
//            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
//        }
//    }
//    private List<ChatRoomOfUserResDto> convertToChatRoomOfUserResDtoList(List<ChatRoomOfUser> chatRoomOf
}
