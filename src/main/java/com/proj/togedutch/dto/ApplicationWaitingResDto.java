package com.proj.togedutch.dto;


import lombok.Data;

@Data
public class ApplicationWaitingResDto {
    private int application_id;
    private String status;
    private int post_id;
    private String uploader;    // 공고 업로드한 유저 이름
    private int user_id;
    private int chatRoom_id;
    private String title;
    private String applicant;

    public ApplicationWaitingResDto(int application_id, String status, int post_id, String uploader, int user_id, int chatRoom_id, String title, String applicant) {
        this.application_id = application_id;
        this.status = status;
        this.post_id = post_id;
        this.uploader = uploader;
        this.user_id = user_id;
        this.chatRoom_id = chatRoom_id;
        this.title = title;
        this.applicant = applicant;
    }
}
