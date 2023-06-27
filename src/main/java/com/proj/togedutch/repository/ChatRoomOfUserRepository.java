package com.proj.togedutch.repository;

import com.proj.togedutch.domain.ChatRoomOfUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomOfUserRepository extends JpaRepository<ChatRoomOfUser,Integer> {
    List<ChatRoomOfUser> findByChatRoomIdx(int chatRoomIdx);

    ChatRoomOfUser findByChatRoomIdxAndUserIdx(int chatRoomIdx,int userIdx);

    @Modifying
    @Query("UPDATE ChatRoomOfUser c set c.status = 1, c.updated_at = CURRENT_TIMESTAMP, c.is_read = 0  where c.chatRoomIdx = :chatRoom_id and c.userIdx = :user_id")
    void inChatRoomUser(@Param("chatRoom_id") int chatRoomId,@Param("user_id") int userId);

    @Modifying
    @Query("UPDATE ChatRoomOfUser c set c.status = 00 ,c.updated_at =CURRENT_TIMESTAMP where c.chatRoomIdx = :chatRoom_id and c.userIdx = :user_id")
    void outChatRoomUser(@Param("chatRoom_id") int chatRoomId,@Param("user_id") int userId);

    @Query("SELECT c.is_read FROM ChatRoomOfUser c WHERE c.chatRoomIdx = :chatRoom_id AND c.userIdx = :user_id")
    Integer getIsReadValue(@Param("chatRoom_id") int chatRoomId, @Param("user_id") int userId);

    @Modifying
    @Query("UPDATE ChatRoomOfUser c SET c.is_read = c.is_read + 1 WHERE c.status = 0 AND c.chatRoomIdx = :chatRoom_id") //안읽음 메세지 추가
    void   incrementIsReadCount(@Param("chatRoom_id") int chatRoomId);

}
