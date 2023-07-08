package com.proj.togedutch.dto;


import com.proj.togedutch.domain.Declaration;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DeclarationReqDto {
    @ApiModelProperty(example = "신고 내용")
    private String content;
    @ApiModelProperty(example = "채팅방 생성시 부여된 id")
    private int chatRoomIdx;

    @Builder
    public DeclarationReqDto(String content, int chatRoomIdx) {
        this.content = content;
        this.chatRoomIdx = chatRoomIdx;
    }

    // Dto to Entity
    public Declaration toEntity() {
        return Declaration.builder()
                .content(content)
                .chatRoomIdx(chatRoomIdx)
                .build();
    }
}
