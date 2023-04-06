package com.proj.togedutch.dto;


import com.proj.togedutch.domain.Declaration;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DeclarationReqDto {
    private String content;
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
