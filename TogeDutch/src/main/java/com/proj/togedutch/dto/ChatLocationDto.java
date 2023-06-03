package com.proj.togedutch.dto;

import com.proj.togedutch.domain.ChatLocation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class ChatLocationDto {
    private int chatLocationIdx;
    private int chatRoomId;
    private int userId;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Timestamp createdAt;

    @Builder
    public ChatLocationDto(ChatLocation chatLocation) {
        this.chatLocationIdx = chatLocation.getChatLocationIdx();
        this.chatRoomId = chatLocation.getChatRoomIdx();
        this.userId = chatLocation.getUserIdx();
        this.latitude = chatLocation.getLatitude();
        this.longitude = chatLocation.getLongitude();
        this.createdAt = chatLocation.getCreatedAt();
    }

}
