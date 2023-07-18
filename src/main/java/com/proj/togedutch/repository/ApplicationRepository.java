package com.proj.togedutch.repository;

import com.proj.togedutch.domain.Application;
import com.proj.togedutch.domain.ChatRoom;
import com.proj.togedutch.domain.ChatRoomOfUser;
import com.proj.togedutch.domain.Post;
import com.proj.togedutch.dto.ApplicationResDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {
    Optional<Application> findByApplicationIdxAndUserIdx(int postIdx, int userIdx);

    /** Post_User_user_id=UploaderIdx 내가 업로드한 **/
    /** User_user_id =UserIdx 내가 참여한 **/

    List<Application> findAllByUploaderIdxOrderByApplicationIdxDesc(int userIdx);

    List<Application> findAllByUserIdxOrderByApplicationIdxDesc(int userIdx);



    @Query(value = "select * From ChatRoom c where c.chatRoom_id In ( select a.ChatRoom_chatRoom_id from Application a where (a.User_user_id = ? or a.Post_User_user_id = ?) and a.status = ?)", nativeQuery = true)
    List<BelongChatRoom> findChatRoomByJoinUserId( int userIdx,int userIdx2, String accept);
    interface BelongChatRoom {
        int getChatRoom_id();
        Timestamp getCreated_at();
    }


}
