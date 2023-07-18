package com.proj.togedutch.controller;


import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.config.BaseResponse;
import com.proj.togedutch.config.BaseResponseStatus;
import com.proj.togedutch.domain.Application;
import com.proj.togedutch.domain.Post;
import com.proj.togedutch.dto.*;
import com.proj.togedutch.service.ApplicationService;
import com.proj.togedutch.service.PostService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class ApplicationController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ApplicationService applicationService;
    private final PostService postService;


     //공고 신청
    @ResponseBody
    @PostMapping("/post/{postIdx}/application")
    public BaseResponse<ApplicationResDto> applyPost(@PathVariable("postIdx") int postIdx) throws IOException, BaseException {
        try{
            PostResDto getPost = postService.getPostById(postIdx);

            String status = getPost.getStatus();
            if(status.equals("모집완료") || status.equals("공고사용불가"))
                return new BaseResponse<>(BaseResponseStatus.APPLICATION_IMPOSSIBLE);

            ApplicationResDto newApplication = applicationService.applyPost(postIdx);
            return new BaseResponse<>(newApplication);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**-------------------------------------------------------------*/
    //신청 수락
    @ResponseBody
    @PostMapping("/application/{applicationIdx}/accept")
    public BaseResponse<ApplicationResDto> modifyStatus(@PathVariable("applicationIdx") int applicationIdx){
        try{
            ApplicationResDto applicationResDto=applicationService.modifyStatus(applicationIdx);
            return new BaseResponse<>(applicationResDto);
        }catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }


    //신청 거절
    @ResponseBody
    @PostMapping("/application/{applicationIdx}/deny")
    public BaseResponse<ApplicationResDto> modifyStatus_deny(@PathVariable("applicationIdx") int applicationIdx){
        try{
            ApplicationResDto applicationResDto=applicationService.modifyStatus_deny(applicationIdx);
            return new BaseResponse<>(applicationResDto);
        }catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    //신청 상태 전체 조회 (내가 참여한 공고)
    @ResponseBody
    @GetMapping("/user/{userIdx}/application/join")
    public BaseResponse<List<ApplicationResDto>> getApplicationByJoinUserId(@PathVariable("userIdx") int userIdx) throws BaseException {
        try {
            List<ApplicationResDto> getApplication = applicationService.getApplicationByJoinUserId(userIdx);

            return new BaseResponse<>(getApplication);
        } catch(BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    //신청 상태 전체 조회 (내가 업로드)
    @ResponseBody
    @GetMapping("/user/{userIdx}/application/upload")
    public BaseResponse<List<ApplicationResDto>> getApplicationBuUploadUserId(@PathVariable("userIdx") int userIdx) throws BaseException {
        try {
            List<ApplicationResDto> getApplication = applicationService.getApplicationByUploadUserId(userIdx);
            return new BaseResponse<>(getApplication);
        } catch(BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    //채팅방 전체 조회 (내가 업로드 + 참여)
    @ResponseBody
    @GetMapping("/user/{userIdx}/chatroom")
    public BaseResponse<List<ChatRoomDto>> getChatRoomByJoinUserId(@PathVariable("userIdx") int userIdx) throws BaseException {
        try {
            List<ChatRoomDto> getChatRoom = applicationService.getChatRoomByJoinUserId(userIdx);
            return new BaseResponse<>(getChatRoom);
        } catch(BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }


    //공고 상태 변경
    @PutMapping("/application/post/status/{postIdx}")
    public BaseResponse<PostResDto> modifyPostStatusById(@PathVariable("postIdx") int postIdx) throws BaseException{
        PostResDto modifyPost=postService.getPostById(postIdx);

        int num_of_recruits = modifyPost.getNum_of_recruits();
        int recruited_num=modifyPost.getRecruited_num();
        if(num_of_recruits!=recruited_num){
            return new BaseResponse<>(BaseResponseStatus.NOT_FULL_NUM_OF_RECRUITS);
        }

        if(modifyPost.getNum_of_recruits() == 0){
            return new BaseResponse<>(BaseResponseStatus.NUM_Of_RECRUITS_EMPTY);
        }
        if(modifyPost.getRecruited_num() == 0){
            return new BaseResponse<>(BaseResponseStatus.NUM_Of_RECRUITS_EMPTY);
        }

        try {
            PostResDto modifyPostStatus = applicationService.modifyPostStatusById(postIdx);
            return new BaseResponse<>(modifyPostStatus);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
//

//    @GetMapping("/application/waiting/{userIdx}")
//    public BaseResponse<List<ApplicationWaitingResDto>> getApplicationWaitings(@PathVariable int userIdx) throws BaseException {
//        try{
//            List<ApplicationWaitingResDto> getApplicationWaitings = applicationService.getApplicationWaitings(userIdx);
//            return new BaseResponse<>(getApplicationWaitings);
//        } catch (BaseException e) {
//            return new BaseResponse<>(e.getStatus());
//        }
//    }

    // 신청 삭제
    @DeleteMapping("/application/{applicationIdx}")
    public int deleteApplication(@PathVariable("applicationIdx") int applicationIdx) throws BaseException {
        try{
            int deleteApplication = applicationService.deleteApplication(applicationIdx);
            return deleteApplication;
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus()).getCode();
        }
    }



}