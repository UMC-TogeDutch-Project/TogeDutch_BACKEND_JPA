package com.proj.togedutch.dto;

import com.proj.togedutch.domain.LikeUsers;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class LikeUsersDto {
    private int likeIdx;
    private int postIdx;
    private int Post_User_userIdx;
    private int Like_userIdx;

    // Entity to Dto
    @Builder
    public LikeUsersDto(LikeUsers likeUsers){
        this.likeIdx = likeUsers.getLikeIdx();
        this.postIdx = likeUsers.getPostIdx();
        this.Post_User_userIdx = likeUsers.getUploaderIdx();
        this.Like_userIdx = likeUsers.getKeeperIdx();
    }
}
