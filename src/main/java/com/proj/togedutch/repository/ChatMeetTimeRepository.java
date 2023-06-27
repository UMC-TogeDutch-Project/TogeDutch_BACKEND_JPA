package com.proj.togedutch.repository;

import com.proj.togedutch.domain.ChatMeetTime;
import com.proj.togedutch.domain.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMeetTimeRepository extends JpaRepository<ChatMeetTime, Integer>{
}