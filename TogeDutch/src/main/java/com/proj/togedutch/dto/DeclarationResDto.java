package com.proj.togedutch.dto;

import com.proj.togedutch.domain.Declaration;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeclarationResDto {
    private int declarationIdx;
    private String content;
    private Timestamp createdAt;
    private String status;
    private int chatRoomIdx;

    public DeclarationResDto(Declaration entity) {
        this.declarationIdx = entity.getDeclarationIdx();
        this.content = entity.getContent();
        this.createdAt = entity.getCreatedAt();
        this.status = entity.getStatus();
        this.chatRoomIdx = entity.getChatRoomIdx();
    }
}
