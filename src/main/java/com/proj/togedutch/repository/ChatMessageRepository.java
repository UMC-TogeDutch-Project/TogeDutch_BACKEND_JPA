package com.proj.togedutch.repository;

import com.proj.togedutch.domain.ChatMessage;
import com.proj.togedutch.domain.ChatPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Integer> {
    List<ChatMessage> findByChatRoomId(int chatRoomId);

}
