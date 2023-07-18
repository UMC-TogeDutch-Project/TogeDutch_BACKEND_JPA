package com.proj.togedutch.controller;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.config.BaseResponse;
import com.proj.togedutch.domain.Declaration;
import com.proj.togedutch.dto.DeclarationReqDto;
import com.proj.togedutch.dto.DeclarationResDto;
import com.proj.togedutch.service.DeclarationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chatRoom")
@Api(tags = {"신고 API"})
public class DeclarationController {
    private final DeclarationService declarationService;

    // 신고 생성 (채팅방 안)
    @ResponseBody
    @PostMapping(value = "/{chatRoomIdx}/declaration")
    @ApiOperation(value = "신고 생성", notes = "특정 채팅방에서 신고를 생성합니다.")
    @ApiImplicitParam(name = "chatRoomIdx", value = "채팅방 생성시 발급되는 id")
    public BaseResponse<DeclarationResDto> createDeclaration(@PathVariable("chatRoomIdx") int chatRoomIdx, @RequestBody DeclarationReqDto declarationReq) {
        DeclarationResDto de = declarationService.getDeclarationById(
                declarationService.createDeclaration(declarationReq, chatRoomIdx));
        return new BaseResponse<>(de);
    }

    // 신고 전체 조회
    @GetMapping("/declaration")
    @ApiOperation(value = "신고 전체 조회", notes = "모든 신고를 조회합니다.")
    public BaseResponse<List<DeclarationResDto>> getAllDeclarations() throws BaseException {
        List<DeclarationResDto> declarations = declarationService.getAllDeclarations();
        return new BaseResponse<>(declarations);
    }

    // 특정 채팅방의 신고만 필터링해서 조회
    @GetMapping("/{chatRoomIdx}/declaration")
    @ApiOperation(value = "특정 채팅방의 신고 조회", notes = "특정 채팅방의 신고만 필터링해서 조회합니다.")
    @ApiImplicitParam(name = "chatRoomIdx", value = "채팅방 생성시 발급되는 id")
    public BaseResponse<List<DeclarationResDto>> getDeclarationByChatRoomId(@PathVariable("chatRoomIdx") int chatRoomIdx) throws BaseException {
        List<DeclarationResDto> declarations = declarationService.getDeclarationByChatRoomId(chatRoomIdx);
        return new BaseResponse<>(declarations);
    }
}
