package com.proj.togedutch.dto;

import com.proj.togedutch.domain.Notice;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeReqDto {

    private String content;
    private String title;

    public NoticeReqDto(String content, String title) {
        this.content = content;
        this.title = title;
    }

    public Notice dtoToEntity() {
        return Notice.builder()
                .content(content)
                .title(title)
                .build();
    }
}
