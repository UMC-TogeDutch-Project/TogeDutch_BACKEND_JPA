package com.proj.togedutch.dto;

import com.proj.togedutch.domain.Application;
import lombok.Builder;

public class ApplicationWaitingReqDto {

    private int userIdx;


    @Builder
    public ApplicationWaitingReqDto(int userIdx) {
        this.userIdx = userIdx;
    }

    // Dto to Entity
    public ApplicationWaitingReqDto toEntity() {
        return ApplicationWaitingReqDto.builder()
                .userIdx(userIdx)
                .build();
    }
}
