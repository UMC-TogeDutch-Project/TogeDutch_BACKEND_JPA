package com.proj.togedutch.dto;

import com.proj.togedutch.domain.ChatRoomOfUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomOfUserResDto {
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

    @Builder
    public ChatRoomOfUserResDto(ChatRoomOfUser chatRoomOfUser) {
        this.chatRoom_id = chatRoomOfUser.getChatRoomIdx();
        this.user_id = chatRoomOfUser.getUserIdx();
        this.status = chatRoomOfUser.getStatus();
        this.is_read = chatRoomOfUser.getIs_read();
        this.updated_at = chatRoomOfUser.getUpdated_at();
    }

    public ChatRoomOfUser toEntity(){
        return ChatRoomOfUser.builder()
                .chatRoomIdx(chatRoom_id)
                .userIdx(user_id)
                .status(status)
                .is_read(is_read)
                .updated_at(updated_at)
                .build();
    }
}
