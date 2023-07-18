package com.proj.togedutch.service;

import com.proj.togedutch.config.BaseException;

import com.proj.togedutch.domain.Application;

import com.proj.togedutch.domain.ChatRoom;
import com.proj.togedutch.domain.Post;
import com.proj.togedutch.dto.*;
import com.proj.togedutch.repository.ApplicationRepository;
import com.proj.togedutch.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.proj.togedutch.utils.JwtService;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.proj.togedutch.config.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ApplicationRepository applicationRepository;
    private final JwtService jwtService;
    private final PostRepository postRepository;


    // 공고 신청
    @Transactional(rollbackFor = SQLException.class)
    public ApplicationResDto applyPost(int postIdx) throws BaseException {
        int userIdx = jwtService.getUserIdx();;
        Optional<Post> getPost = postRepository.findById(postIdx);

        // 공고 신청자와 공고 작성자가 같은 경우
        if (userIdx == getPost.get().getUserIdx())
            throw new BaseException(POST_UPLOAD_MINE);

        // 해당 공고에 userIdx가 신청한 적이 있는지 검사
        Optional<Application> checkDuplicated = applicationRepository.findByApplicationIdxAndUserIdx(userIdx, postIdx);

        // 값이 없었으면 아직 신청을 안 한것이므로!!
        if (checkDuplicated.isPresent())
            throw new BaseException(DUPLICATED_APPLICATION);

        Post post = getPost.get();
        Application application = Application.builder()
                .postIdx(postIdx)
                .uploaderIdx(post.getUserIdx())
                .userIdx(userIdx)
                .chatRoomIdx(post.getChatRoomIdx())
                .build();

        return new ApplicationResDto(applicationRepository.save(application));
    }

    //신청 수락
    @Transactional(rollbackFor = SQLException.class)
    public ApplicationResDto modifyStatus(int applicationIdx) throws BaseException {
        Optional<Application> checkDuplicated = applicationRepository.findById(applicationIdx);

        if(checkDuplicated.isEmpty())
            throw new BaseException(MODIFY_FAIL_USER);

        Application application = checkDuplicated.get();
        String status = checkDuplicated.get().getStatus();

        if (status.equals("모집완료"))
            throw new BaseException(COMPLETED_STATUS);

        if (status.equals("수락완료"))
            throw new BaseException(ACCEPT_STATUS);

        if (status.equals("수락거절"))
            throw new BaseException(REJECTD_STATUS);

        String accept = "수락완료";
        application.modifyApplication(accept);

        return new ApplicationResDto(applicationRepository.save(application));
    }


    //신청 거절
    @Transactional(rollbackFor = SQLException.class)
    public ApplicationResDto modifyStatus_deny(int applicationIdx) throws BaseException {
        Optional<Application> checkDuplicated = applicationRepository.findById(applicationIdx);

        if(checkDuplicated.isEmpty())
            throw new BaseException(MODIFY_FAIL_USER);

        Application application = checkDuplicated.get();
        String status = checkDuplicated.get().getStatus();

        if (status.equals("모집완료"))
            throw new BaseException(COMPLETED_STATUS);

        if (status.equals("수락완료"))
            throw new BaseException(ACCEPT_STATUS);

        if (status.equals("수락거절"))
            throw new BaseException(REJECTD_STATUS);

        String accept = "수락거절";
        application.modifyApplication(accept);

        return new ApplicationResDto(applicationRepository.save(application));
    }

    //신청 상태 전체 조회 (내가 참여한 공고)
    public List<ApplicationResDto> getApplicationByJoinUserId(int userIdx) throws BaseException {
        List<Application> joinApplication = applicationRepository.findAllByUserIdxOrderByApplicationIdxDesc(userIdx);
        return joinApplication.stream()
                .map(m->new ApplicationResDto(m))
                .collect(Collectors.toList());
    }

    //신청 상태 전체 조회 (내가 업로드)
    public List<ApplicationResDto> getApplicationByUploadUserId(int userIdx) throws BaseException {
        List<Application> uploadApplication = applicationRepository.findAllByUploaderIdxOrderByApplicationIdxDesc(userIdx);
        return uploadApplication.stream()
                .map(m->new ApplicationResDto(m))
                .collect(Collectors.toList());
    }

    //채팅방 전체 조회 (내가 업로드 + 내가 참여)
    public List<ChatRoomDto> getChatRoomByJoinUserId(int userIdx) throws BaseException {
        String accept = "수락완료";
        List<ApplicationRepository.BelongChatRoom> joinChatRoom = applicationRepository.findChatRoomByJoinUserId(userIdx, userIdx, accept);

        return joinChatRoom.stream()
                .map(m->new ChatRoomDto(m))
                .collect(Collectors.toList());
    }

    //공고 상태 변경
    @Transactional(rollbackFor = SQLException.class)
    public PostResDto modifyPostStatusById(int postIdx) throws BaseException {
        Optional<Post> getPost = postRepository.findById(postIdx);
        if(getPost.isEmpty())
            throw new BaseException(NOT_FOUND_POST);

        Post post = getPost.get();
        String complete = "수락완료";
        post.modifyStatusPost(complete);
        return new PostResDto(postRepository.save(post));
    }

     //채팅방 삭제 후 Application의 chatRoom_id로 null로 변경
     @Transactional(rollbackFor = SQLException.class)
    public void modifyApplicationByChatRoomId(int chatRoomIdx) throws BaseException {
        Optional<Application> getApplication = applicationRepository.findById(chatRoomIdx);
        if(getApplication.isEmpty())
            throw new BaseException(FOREIGN_KEY_ORROR);

        Application application = getApplication.get();
        application.modifyChatRoomStatus();
        applicationRepository.save(application);
    }

    // 내가 업로드한 공고 status 수락 대기 or 랜덤 매칭 대기인 [ Application + 공고제목 + 신청자 ]
    public List<ApplicationWaitingResDto> getApplicationWaitings(int userIdx) throws BaseException {
        List<ApplicationRepository.ApplicationWaiting> getApplicationWaitings = applicationRepository.getApplicationWaitings(userIdx);
        if (getApplicationWaitings.isEmpty())
            throw new BaseException(NOBODY_WAITING);

        return getApplicationWaitings.stream()
                .map(m->new ApplicationWaitingResDto(m))
                .collect(Collectors.toList());
    }


    // 신청 삭제
    @Transactional(rollbackFor = SQLException.class)
    public int deleteApplication(int applicationIdx) throws BaseException {
        try {
            applicationRepository.deleteById(applicationIdx);
            return 1;
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}