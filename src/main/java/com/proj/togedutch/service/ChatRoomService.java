package com.proj.togedutch.service;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.config.BaseResponseStatus;
import com.proj.togedutch.domain.ChatRoom;
import com.proj.togedutch.dto.ChatRoomDto;
import com.proj.togedutch.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor // final로 설정된 필드를 자동으로 생성자 주입 해주는 어노테이션, @Autowired 안써도됨
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;

    @Transactional
    public int createChatRoom(ChatRoomDto chatRoomDto){
        return (chatRoomRepository.save(chatRoomDto.toEntity())).getChatRoomIdx();
    }

    public List<ChatRoomDto> getAllChatRooms() {
        List<ChatRoom> chatRoomList= chatRoomRepository.findAll();
        // ChatRoom List를 Dto List로 변환 후 return
        return chatRoomList.stream()
                .map(m-> new ChatRoomDto(m.getChatRoomIdx(), m.getCreatedAt()))
                .collect(Collectors.toList());
    }

    public ChatRoomDto getChatRoomById(int chatRoomIdx) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomIdx)
                .orElseThrow(()-> new IllegalArgumentException("해당 채팅방이 없습니다. id="+ chatRoomIdx));
        return new ChatRoomDto(chatRoom);
    }

    public void deleteChatRoom(int chatRoomIdx) {
        chatRoomRepository.deleteById(chatRoomIdx);
    }
}
