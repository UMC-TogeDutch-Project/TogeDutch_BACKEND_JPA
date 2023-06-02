package com.proj.togedutch.dto;

import com.proj.togedutch.domain.ChatPhoto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChatPhotoDto {
    private int chatPhoto_id;
    private Timestamp created_at;
    private int chatRoom_id;
    private int user_id;
    private String image;

    // Entity to Dto
    @Builder
    public ChatPhotoDto(ChatPhoto chatPhoto){
        this.chatPhoto_id = chatPhoto.getChatPhotoIdx();
        this.created_at = chatPhoto.getCreatedAt();
        this.chatRoom_id = chatPhoto.getChatRoomIdx();
        this.user_id = chatPhoto.getUserIdx();
        this.image = chatPhoto.getImage();
    }
}
