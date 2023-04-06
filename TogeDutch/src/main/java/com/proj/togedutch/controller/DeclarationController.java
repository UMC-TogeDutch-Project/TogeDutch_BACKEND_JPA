package com.proj.togedutch.controller;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.config.BaseResponse;
import com.proj.togedutch.domain.Declaration;
import com.proj.togedutch.dto.DeclarationReqDto;
import com.proj.togedutch.dto.DeclarationResDto;
import com.proj.togedutch.service.DeclarationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chatRoom")
public class DeclarationController {
    private final DeclarationService declarationService;

    // 신고 생성 (채팅방 안)
    @ResponseBody
    @PostMapping(value = "/{chatRoomIdx}/declaration")
    public BaseResponse<DeclarationResDto> createDeclaration(@PathVariable("chatRoomIdx") int chatRoomIdx, @RequestBody DeclarationReqDto declarationReq) {
        DeclarationResDto de = declarationService.getDeclarationById(
                declarationService.createDeclaration(declarationReq, chatRoomIdx));
        return new BaseResponse<>(de);
    }

    // 신고 전체 조회
    @GetMapping("/declaration")
    public BaseResponse<List<DeclarationResDto>> getAllDeclarations() throws BaseException {
        List<DeclarationResDto> declarations = declarationService.getAllDeclarations();
        return new BaseResponse<>(declarations);
    }

    // 특정 채팅방의 신고만 필터링해서 조회
    @GetMapping("/{chatRoomIdx}/declaration")
    public BaseResponse<List<DeclarationResDto>> getDeclarationByChatRoomId(@PathVariable("chatRoomIdx") int chatRoomIdx) throws BaseException {
        List<DeclarationResDto> declarations = declarationService.getDeclarationByChatRoomId(chatRoomIdx);
        return new BaseResponse<>(declarations);
    }
}
