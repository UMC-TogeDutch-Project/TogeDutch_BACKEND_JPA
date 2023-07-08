package com.proj.togedutch.dto;

import com.proj.togedutch.domain.Declaration;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeclarationResDto {
    @ApiModelProperty(example = "신고 생성시 부여된 id")
    private int declarationIdx;
    @ApiModelProperty(example = "신고 내용")
    private String content;
    @ApiModelProperty(example = "최초 작성 시간")
    private Timestamp createdAt;
    @ApiModelProperty(example = "신고 상태")
    private String status;
    @ApiModelProperty(example = "채팅방 생성시 부여된 id")
    private int chatRoomIdx;

    public DeclarationResDto(Declaration entity) {
        this.declarationIdx = entity.getDeclarationIdx();
        this.content = entity.getContent();
        this.createdAt = entity.getCreatedAt();
        this.status = entity.getStatus();
        this.chatRoomIdx = entity.getChatRoomIdx();
    }
}
