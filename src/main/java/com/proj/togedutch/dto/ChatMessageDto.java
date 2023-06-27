package com.proj.togedutch.dto;

import com.proj.togedutch.domain.ChatMessage;
import com.proj.togedutch.domain.ChatPhoto;
import com.proj.togedutch.repository.ChatMessageRepository;
import lombok.*;

import javax.persistence.Column;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
public class ChatMessageDto {
    private int chatId;

    private int chatRoomId;

    private int userId;

    private Timestamp createAt;

    private String content;

    @Builder
    public ChatMessageDto(ChatMessage chatMessage){
        this.chatId = chatMessage.getChatId();
        this.chatRoomId = chatMessage.getChatRoomId();
        this.userId = chatMessage.getUserId();
        this.createAt = chatMessage.getCreateAt();
        this.content = chatMessage.getContent();
    }

}
