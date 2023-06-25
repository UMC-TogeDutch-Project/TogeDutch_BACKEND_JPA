package com.proj.togedutch.controller;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.config.BaseResponse;
import com.proj.togedutch.config.BaseResponseStatus;
import com.proj.togedutch.dto.NoticeReqDto;
import com.proj.togedutch.dto.NoticeResDto;
import com.proj.togedutch.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notice")
@Slf4j
public class NoticeController {
    private final NoticeService noticeService;

    // 공지사항 생성
    @PostMapping("")
    public BaseResponse<NoticeResDto> createNotice(@RequestBody NoticeReqDto noticeReqDto) throws BaseException{
        //NoticeReqDto noticeReqDto = new NoticeReqDto(content, title);
        System.out.println("로그");
        if(noticeReqDto.getTitle() == null){
            log.info("여기?");
            return new BaseResponse<>(BaseResponseStatus.POST_NOTICE_EMPTY_TITLE);
        }
        if(noticeReqDto.getContent() == null){
            log.info("저기?");
            return new BaseResponse<>(BaseResponseStatus.POST_NOTICE_EMPTY_CONTENT);
        }

        try {
            log.info("?");
            NoticeResDto noticeResDto = noticeService.createNotice(noticeReqDto);
            return new BaseResponse<>(noticeResDto);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    // 특정 공지사항 조회
    @GetMapping("/{noticeIdx}")
    public BaseResponse<NoticeResDto> getNoticeById (@PathVariable("noticeIdx") int noticeIdx) throws BaseException {
        try {
            NoticeResDto noticeResDto = noticeService.getNoticeById(noticeIdx);
            return new BaseResponse<>(noticeResDto);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    // 전체 공지사항 조회 (최신순)
    @GetMapping("")
    public BaseResponse<List<NoticeResDto>> getAllNotices(){
        try{
            List<NoticeResDto> getNoticesRes = noticeService.getAllNotices();
            return new BaseResponse<>(getNoticesRes);
        } catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    // 공지사항 삭제
    @DeleteMapping("/{noticeIdx}")
    public int deleteNotice(@PathVariable("noticeIdx") int noticeIdx) throws BaseException {
        try{
            int deleteNotice = noticeService.deleteNotice(noticeIdx);
            log.info("Delete success");
            return deleteNotice;
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus()).getCode();
        }
    }

    // 공지사항 수정
    @PutMapping("/{noticeIdx}")
    public BaseResponse<NoticeResDto> modifyNotice(@PathVariable("noticeIdx") int noticeIdx, @RequestBody NoticeReqDto noticeReqDto) throws BaseException{
        if(noticeReqDto.getTitle() == null){
            return new BaseResponse<>(BaseResponseStatus.POST_NOTICE_EMPTY_TITLE);
        }
        if(noticeReqDto.getContent() == null){
            return new BaseResponse<>(BaseResponseStatus.POST_NOTICE_EMPTY_CONTENT);
        }

        try {
            NoticeResDto noticeResDto = noticeService.modifyNotice(noticeIdx, noticeReqDto);
            return new BaseResponse<>(noticeResDto);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}
