package com.proj.togedutch.dto;


import com.proj.togedutch.domain.ChatRoom;
import com.proj.togedutch.repository.ChatRoomRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChatRoomDto {
    private int chatRoomIdx;
    private Timestamp createdAt;

    // Entity to Dto
    @Builder
    public ChatRoomDto(ChatRoom chatRoom) {
        this.chatRoomIdx = chatRoom.getChatRoomIdx();
        this.createdAt = chatRoom.getCreatedAt();
    }
    // Dto to Entity
    public ChatRoom toEntity() {
        return ChatRoom.builder()
                .chatRoomIdx(chatRoomIdx)
                .createdAt(createdAt)
                .build();
    }
}
