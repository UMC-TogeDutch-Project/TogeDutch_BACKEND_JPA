package com.proj.togedutch.dto;

import com.proj.togedutch.domain.Notice;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class NoticeResDto {
    private int noticeId;
    private String title;
    private String content;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public NoticeResDto(Notice notice) {
        this.noticeId = notice.getNoticeId();
        this.title = notice.getTitle();
        this.content = notice.getContent();
        this.createdAt = notice.getCreatedAt();
        this.updatedAt = notice.getUpdatedAt();
    }
}
