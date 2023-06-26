package com.proj.togedutch.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomOfUserReqDto {
    @ApiModelProperty(example = "채팅방 ID")
    private int chatRoom_id;
    @ApiModelProperty(example = "유저ID")
    private int user_id;
    @ApiModelProperty(example = "상태")
    private byte status;
    @ApiModelProperty(example = "읽었는지 확인")
    private int is_read;
    @ApiModelProperty(example = "읽음 업데이트")
    private Timestamp updated_at;
    @ApiModelProperty(example = "유저 이름")
    private String userName;
}
