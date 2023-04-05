package com.proj.togedutch.repository;

import com.proj.togedutch.domain.ChatRoom;
import com.proj.togedutch.domain.Declaration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeclarationRepository extends JpaRepository<Declaration, Integer> {
    List<Declaration> findByChatRoomIdx(int chatRoomIdx);
}
