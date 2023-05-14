package com.proj.togedutch.controller;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.config.BaseResponse;
import com.proj.togedutch.domain.User;
import com.proj.togedutch.dto.UserResDto;
import com.proj.togedutch.service.MatchingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/matching")
public class MatchingController {
    private final MatchingService matchingService;

    // 유저 매칭 (1-3 매칭 -> 현재 유저와 다른 유저 매칭)
    @GetMapping("/rematching/{postIdx}")
    public BaseResponse<UserResDto> getReMatching(@PathVariable("postIdx") int postIdx) {
        try {
            UserResDto getMatching = matchingService.getReMatching(postIdx);
            return new BaseResponse<>(getMatching);
        } catch(BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }

    }

    // 유저가 수락
    @GetMapping("/accept/{userIdx}/{postIdx}")
    public BaseResponse<Integer> getAcceptUserId(@PathVariable("userIdx") int userIdx, @PathVariable("postIdx") int postIdx) {
        try {
            int getMatching = matchingService.getAcceptUserId(userIdx,postIdx);
            return new BaseResponse<>(getMatching);
        } catch(BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}
