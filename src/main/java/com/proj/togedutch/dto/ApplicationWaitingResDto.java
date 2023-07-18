package com.proj.togedutch.dto;


import com.proj.togedutch.repository.ApplicationRepository;
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

    public ApplicationWaitingResDto(ApplicationRepository.ApplicationWaiting applicationWaiting) {
        this.application_id = applicationWaiting.getApplication_id();
        this.status = applicationWaiting.getStatus();
        this.post_id = applicationWaiting.getPost_id();
        this.uploader = applicationWaiting.getUploader();
        this.user_id = applicationWaiting.getUser_id();
        this.chatRoom_id = applicationWaiting.getChatRoom_id();
        this.title = applicationWaiting.getTitle();
        this.applicant = applicationWaiting.getApplicant();
    }
}
