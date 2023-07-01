package com.proj.togedutch.dto;

import com.proj.togedutch.domain.Application;
import com.proj.togedutch.domain.Declaration;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApplicationReqDto {

    private int postIdx;
    private int userIdx;


    @Builder
    public ApplicationReqDto(int postIdx, int userIdx) {
        this.postIdx = postIdx;
        this.userIdx = userIdx;
    }

    // Dto to Entity
    public Application toEntity() {
        return Application.builder()
                .postIdx(postIdx)
                .userIdx(userIdx)
                .build();
    }
}
