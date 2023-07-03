package com.proj.togedutch.service;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.config.BaseResponseStatus;
import com.proj.togedutch.domain.ChatRoomOfUser;
import com.proj.togedutch.dto.ChatRoomOfUserResDto;
import com.proj.togedutch.dto.PostResDto;
import com.proj.togedutch.repository.ChatRoomOfUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatRoomOfUserService {

    private final ChatRoomOfUserRepository chatRoomOfUserRepository;

    @Transactional
    public int inviteUser(ChatRoomOfUserResDto chatRoomOfUserResDto,int chatRoom_id,int user_id) throws BaseException {
        try {
            chatRoomOfUserResDto.setChatRoom_id(chatRoom_id);
            chatRoomOfUserResDto.setUser_id(user_id);
            return (chatRoomOfUserRepository.save(chatRoomOfUserResDto.toEntity()).getChatRoomIdx());
        } catch (DataIntegrityViolationException e) {
            if (e.getCause() instanceof SQLException) {
                SQLException sqlException = (SQLException) e.getCause();
                if (sqlException.getErrorCode() == 1062) {
                    throw new BaseException(BaseResponseStatus.DUPLICATE_KEY_ORROR);
                } else if (sqlException.getErrorCode() == 1452) {
                    throw new BaseException(BaseResponseStatus.FOREIGN_KEY_ORROR);
                } else throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
            } else throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    // 현재 채팅방에 있는 유저
    public ChatRoomOfUserResDto getChatRoomOfUser(int chatRoomIdx){
        ChatRoomOfUser chatRoomOfUser = chatRoomOfUserRepository.findById(chatRoomIdx)
                .orElseThrow(()-> new IllegalArgumentException("해당 채팅방이 없습니다. ID = "+chatRoomIdx ));
        return new ChatRoomOfUserResDto(chatRoomOfUser);
    }

    // 현재 채팅방에 있는 모든 유저
    public List<ChatRoomOfUserResDto> getChatRoomOfUsers(int chatRoomIdx){
        List<ChatRoomOfUser> chatRoomOfUsers = chatRoomOfUserRepository.findByChatRoomIdx(chatRoomIdx);
        return chatRoomOfUsers.stream()
                .map(m-> new ChatRoomOfUserResDto(m.getChatRoomIdx(), m.getUserIdx(),m.getStatus(),m.getIs_read(),m.getUpdated_at()))
                .collect(Collectors.toList());
    }

    // 채팅방에 입장
    @Transactional
    public ChatRoomOfUserResDto inChatRoomUser(int chatRoomIdx, int userId) throws BaseException {
        chatRoomOfUserRepository.inChatRoomUser(chatRoomIdx,userId);
        ChatRoomOfUser chatRoomOfUser =  chatRoomOfUserRepository.findByChatRoomIdxAndUserIdx(chatRoomIdx,userId);
        return new ChatRoomOfUserResDto(chatRoomOfUser);
    }

    @Transactional
    public ChatRoomOfUserResDto outChatRoomUser(int chatRoomIdx, int userId) throws BaseException {
        chatRoomOfUserRepository.outChatRoomUser(chatRoomIdx,userId);
        ChatRoomOfUser chatRoomOfUser =  chatRoomOfUserRepository.findByChatRoomIdxAndUserIdx(chatRoomIdx,userId);
        return new ChatRoomOfUserResDto(chatRoomOfUser);
    }


    @Transactional
    public int leaveChatRoomUser(int chatRoomIdx, int userId) throws BaseException {
        try {
            ChatRoomOfUser chatRoomOfUser =  chatRoomOfUserRepository.findByChatRoomIdxAndUserIdx(chatRoomIdx,userId);
            int deleteId = chatRoomOfUser.getId();
            chatRoomOfUserRepository.delete(chatRoomOfUser);
            return deleteId;
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    @Transactional
    public int unreadChatRoomUser(int chatRoomIdx,int userIdx) throws BaseException{
        try{
            ChatRoomOfUser chatRoomOfUser = chatRoomOfUserRepository.findByChatRoomIdxAndUserIdx(chatRoomIdx,userIdx);
            int unreadCnt = chatRoomOfUser.getIs_read();
            return unreadCnt;
        } catch (Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
}
