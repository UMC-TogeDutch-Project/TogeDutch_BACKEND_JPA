package com.proj.togedutch.repository;

import com.proj.togedutch.domain.Application;
import com.proj.togedutch.domain.ChatRoom;
import com.proj.togedutch.domain.ChatRoomOfUser;
import com.proj.togedutch.domain.Post;
import com.proj.togedutch.dto.ApplicationResDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {
    Application findByApplicationIdxAndUserIdx(int postIdx, int userIdx);

    /**Post_User_user_id=UploaderIdx 내가 업로드한*/
    /**User_user_id =UserIdx 내가 참여한*/

    List<Application> findAllByUploaderIdx(int userIdx);

    List<Application> findAllByUserIdx(int userIdx);



    @Query(value = "select * From ChatRoom c where c.chatRoom_id In ( select a.ChatRoom_chatRoom_id from Application a where (a.User_user_id = :userIdx or a.Post_User_user_id = :userIdx) and a.status = :accept)", nativeQuery = true)
    List <ChatRoom> findChatRoomByJoinUserId(int userIdx,String accept);

}
