package com.proj.togedutch.repository;

import com.proj.togedutch.domain.ChatPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatPhotoRepository extends JpaRepository<ChatPhoto, Integer> {
    ChatPhoto findByChatPhotoIdxAndChatRoomIdx(int chatPhotoIdx, int chatRoomIdx);

    List<ChatPhoto> findByChatRoomIdx(int chatRoomIdx);
}
