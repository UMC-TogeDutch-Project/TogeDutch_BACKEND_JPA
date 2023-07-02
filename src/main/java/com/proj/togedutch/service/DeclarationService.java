package com.proj.togedutch.service;

import com.proj.togedutch.domain.Declaration;
import com.proj.togedutch.dto.DeclarationReqDto;
import com.proj.togedutch.dto.DeclarationResDto;
import com.proj.togedutch.repository.DeclarationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DeclarationService {
    private final DeclarationRepository declarationRepository;

    public Integer createDeclaration(DeclarationReqDto declarationReq, int chatRoomIdx) {
        declarationReq.setChatRoomIdx(chatRoomIdx);
        return declarationRepository.save(declarationReq.toEntity()).getDeclarationIdx();
    }

    public List<DeclarationResDto> getAllDeclarations() {
        List<Declaration> declarationList = declarationRepository.findAll();
        return declarationList.stream()
                .map(m-> new DeclarationResDto(m.getDeclarationIdx(), m.getContent(), m.getCreatedAt(), m.getStatus(), m.getChatRoomIdx()))
                .collect(Collectors.toList());
    }

    public List<DeclarationResDto> getDeclarationByChatRoomId(int chatRoomIdx) {
        List<Declaration> declarationList = declarationRepository.findByChatRoomIdx(chatRoomIdx);
        return declarationList.stream()
                .map(m-> new DeclarationResDto(m.getDeclarationIdx(), m.getContent(), m.getCreatedAt(), m.getStatus(), m.getChatRoomIdx()))
                .collect(Collectors.toList());
    }

    public DeclarationResDto getDeclarationById(int declarationIdx) {
        Declaration declaration = declarationRepository.findById(declarationIdx)
                .orElseThrow(()-> new IllegalArgumentException("해당 신고가 없습니다. id="+ declarationIdx));
        return new DeclarationResDto(declaration);
    }
}
