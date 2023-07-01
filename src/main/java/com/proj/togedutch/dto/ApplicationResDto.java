package com.proj.togedutch.dto;
import com.proj.togedutch.domain.Application;

import lombok.Data;

@Data
public class ApplicationResDto {
    private int application_id;
    private String status;
    private int post_id;
    private int user_id;
    private int chatRoom_id;

    public ApplicationResDto(Application application) {
        this.application_id = application.getApplicationIdx();
        this.status = application.getStatus();
        this.post_id = application.getPostIdx();
        this.user_id = application.getUserIdx();
        this.chatRoom_id = application.getChatRoomIdx();
    }
}
