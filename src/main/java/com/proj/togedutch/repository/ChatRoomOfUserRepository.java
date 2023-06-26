package com.proj.togedutch.repository;

import com.proj.togedutch.domain.ChatRoomOfUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomOfUserRepository extends JpaRepository<ChatRoomOfUser,Integer> {
    List<ChatRoomOfUser> findByChatRoomIdx(int chatRoomIdx);
}
