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
    public ApplicationResDto applyPost(int postIdx) throws BaseException {
        int userIdx;
        Optional<Post> getPost;
        Application checkDuplicated;
        // userIdx와 post_id가 같으면 내가 올린 공고임
        try {
            getPost = postRepository.findById(postIdx);
            userIdx = jwtService.getUserIdx();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }

        if (userIdx == getPost.get().getUserIdx())
            throw new BaseException(POST_UPLOAD_MINE);

        // userIdx랑 post_id가 같은 application이 있는지 체크 이미 있으면 이미 신청한 공고임
        try {
            checkDuplicated = applicationRepository.findByApplicationIdxAndUserIdx(userIdx, postIdx);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
        // 값이 없었으면 아직 신청을 안 한것이므로!!
        if (checkDuplicated != null)
            throw new BaseException(DUPLICATED_APPLICATION);

        try {
            Application application = new Application();
            application.setPostIdx(postIdx); // entity에있는 setter사용
            application.setUserIdx(userIdx);
            application.setChatRoomIdx(getPost.get().getChatRoomIdx());
            application.setUploaderIdx(getPost.get().getUserIdx());

            Application save = applicationRepository.save(application);

            ApplicationResDto createApplication = new ApplicationResDto(save);
            return createApplication;

        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }



    /**-------------------------------------------------------------*/

    //신청 수락
    public ApplicationResDto modifyStatus(int applicationIdx) throws BaseException {

        try {
            Application checkDuplicated = applicationRepository.findById(applicationIdx).orElseThrow();
            String status = checkDuplicated.getStatus();

            if (status.equals("모집완료"))
                throw new BaseException(COMPLETED_STATUS);

            if (status.equals("수락완료"))
                throw new BaseException(ACCEPT_STATUS);

            if (status.equals("수락거절"))
                throw new BaseException(REJECTD_STATUS);

            String accept="수락완료";
            checkDuplicated.modifyApplication(accept);

            applicationRepository.save(checkDuplicated);

            return new ApplicationResDto(checkDuplicated);
        } catch (BaseException be) {
            throw be;
        } catch (Exception e) {
            throw new BaseException(MODIFY_FAIL_USER);
        }
    }


    //신청 거절
    public ApplicationResDto modifyStatus_deny(int applicationIdx) throws BaseException {
        try {
            Application checkDuplicated = applicationRepository.findById(applicationIdx).orElseThrow();
            String status = checkDuplicated.getStatus();

            if (status.equals("모집완료"))
                throw new BaseException(COMPLETED_STATUS);

            if (status.equals("수락완료"))
                throw new BaseException(ACCEPT_STATUS);

            if (status.equals("수락거절"))
                throw new BaseException(REJECTD_STATUS);

            String accept="수락거절";
            checkDuplicated.modifyApplication(accept);
            applicationRepository.save(checkDuplicated);

            return new ApplicationResDto(checkDuplicated);
        }catch (BaseException be) {
            throw be;
        } catch (Exception e) {
            throw new BaseException(MODIFY_FAIL_USER);
        }
    }

    /**User_user_id*/
    //신청 상태 전체 조회 (내가 참여한 공고)
    public List<ApplicationResDto> getApplicationByJoinUserId(int userIdx) throws BaseException {
        try {
            List<Application> joinApplication = applicationRepository.findAllByUserIdx(userIdx);

            List<ApplicationResDto> joinApplicationResDtos = new ArrayList<>();
            for (int i = 0; i < joinApplication.size(); i++) {
                joinApplicationResDtos.add(new ApplicationResDto(joinApplication.get(i)));
            }

            return joinApplicationResDtos;
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /**Post_User_user_id*/
    //신청 상태 전체 조회 (내가 업로드)
    public List<ApplicationResDto> getApplicationByUploadUserId(int userIdx) throws BaseException {
        try {
            List<Application> UploadApplication = applicationRepository.findAllByUploaderIdx(userIdx);

            List<ApplicationResDto> applicationResDtos = new ArrayList<>();
            for (int i = 0; i < UploadApplication.size(); i++) {
                applicationResDtos.add(new ApplicationResDto(UploadApplication.get(i)));
            }

            return applicationResDtos;
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //채팅방 전체 조회 (내가 참여)
    public List<ChatRoomDto> getChatRoomByJoinUserId(int userIdx) throws BaseException {

        //유저아이디 값이 어플리케이션에 없으면 에러
        try {
            applicationRepository.findById(userIdx);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(NO_USER_ERROR);
        }


        try{
            String accept="수락완료";
            List<ApplicationRepository.BelongChatRoom> joinChatRoom = applicationRepository.findChatRoomByJoinUserId(userIdx,userIdx,accept);

            return joinChatRoom.stream()
                .map(m->new ChatRoomDto(m))
                .collect(Collectors.toList());
        }catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //공고 상태 변경
    public PostResDto modifyPostStatusById(int postIdx) throws BaseException {
        try {
            Post modifyPostStatusById = postRepository.findById(postIdx).orElseThrow();

            String complete="수락완료";

            modifyPostStatusById.modifyStatusPost(complete);

            postRepository.save(modifyPostStatusById);

            return new PostResDto(modifyPostStatusById);
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
//
//     //채팅방 삭제 후 Application의 chatRoom_id로 null로 변경
//    public int modifyApplicationByChatRoomId(int chatRoomIdx) throws BaseException {
//        try {
//            //int result = applicationRepository.modifyApplicationByChatRoomId(chatRoomIdx);
//
//
//            Optional<Application> application = applicationRepository.findById(chatRoomIdx);
//
//            application.get().modifyChatRoomStatus();
//
//            applicationRepository.save(application);
//
//            return result;
//        } catch (BaseException e) {
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }


//    public List<ApplicationWaiting> getApplicationWaitings(int userIdx) throws BaseException {
//        List<ApplicationWaiting> getApplicationWaitings;
//
//        try {
//            getApplicationWaitings = applicationDao.getApplicationWaitings(userIdx);
//        } catch (BaseException e) {
//            throw new BaseException(DATABASE_ERROR);
//        }
//
//        if (getApplicationWaitings.isEmpty())
//            throw new BaseException(NOBODY_WAITING);
//
//        return getApplicationWaitings;
//    }


}