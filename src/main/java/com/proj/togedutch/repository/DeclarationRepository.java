package com.proj.togedutch.repository;

import com.proj.togedutch.domain.Declaration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeclarationRepository extends JpaRepository<Declaration, Integer> {
    List<Declaration> findByChatRoomIdx(int chatRoomIdx);
}
