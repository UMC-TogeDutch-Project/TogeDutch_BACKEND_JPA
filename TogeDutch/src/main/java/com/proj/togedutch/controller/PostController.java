package com.proj.togedutch.controller;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.config.BaseResponse;
import com.proj.togedutch.config.BaseResponseStatus;
import com.proj.togedutch.domain.Post;
import com.proj.togedutch.dto.CategoryReqDto;
import com.proj.togedutch.dto.ChatRoomDto;
import com.proj.togedutch.dto.PostReqDto;
import com.proj.togedutch.dto.PostResDto;
import com.proj.togedutch.service.AWSS3Service;
import com.proj.togedutch.service.ChatRoomService;
import com.proj.togedutch.service.PostService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final PostService postService;
    private final ChatRoomService chatRoomService;
    private final AWSS3Service awsS3Service;
    @Value("${cloud.aws.url}")
    private String url;

    // 공고 전체 조회
    @GetMapping("")
    public BaseResponse<List<PostResDto>> getAllPosts(){
        List<PostResDto> getPostsRes = postService.findAll();
        return new BaseResponse<>(getPostsRes);
    }

    // 공고 생성 (채팅방도 생성)
    @PostMapping("")
    public BaseResponse<PostResDto> createPost(@RequestParam int user, @RequestPart PostReqDto post, @RequestPart(value="file", required = false) MultipartFile file) throws BaseException, IOException {
        if (post.getTitle() == null) {
            return new BaseResponse<>(BaseResponseStatus.POST_POST_EMPTY_TITLE);
        }
        if (post.getUrl() == null) {
            return new BaseResponse<>(BaseResponseStatus.POST_POST_EMPTY_URL);
        }
        if (Integer.valueOf(post.getDelivery_tips()) == null) {
            return new BaseResponse<>(BaseResponseStatus.POST_POST_EMPTY_TIP);
        }
        if (Integer.valueOf(post.getMinimum()) == null) {
            return new BaseResponse<>(BaseResponseStatus.POST_POST_EMPTY_MINIMUM);
        }
        if (Integer.valueOf(post.getNum_of_recruits()) == null) {
            return new BaseResponse<>(BaseResponseStatus.POST_POST_EMPTY_RECRUIT);
        }
        if (post.getLatitude() == null || post.getLongitude() == null) {
            return new BaseResponse<>(BaseResponseStatus.POST_POST_EMPTY_LOCATION);
        }
        if(post.getCategory() == null){
            return new BaseResponse<>(BaseResponseStatus.POST_POST_EMPTY_CATEGORY);
        }

        String fileUrl = null;

        // 파일 없는 경우 오류 처리 다시 하기 (01.24 : 파일 없어도 uploadFile 메소드 실행됨)
        if(file != null && !file.isEmpty())
            fileUrl = url + awsS3Service.uploadFile(file, post, user);

        logger.info("fileUrl은 " + fileUrl);

        PostResDto createPost = postService.createPost(post, fileUrl, user);

        // 채팅방 생성
        int insertIdx = chatRoomService.createChatRoom(new ChatRoomDto());
        ChatRoomDto createChatRoom = chatRoomService.getChatRoomById(insertIdx);

        PostResDto modifyPost = postService.insertChatRoom(createPost.getPost_id(), createChatRoom.getChatRoomIdx());
        return new BaseResponse<>(modifyPost);
    }

    // 공고 전체 조회 (최신순 / 주문임박)
    @GetMapping("/")
    public BaseResponse<List<PostResDto>> getSortingPosts(@RequestParam String sort) throws BaseException {
        List<PostResDto> getPostsRes = postService.getSortingPosts(sort);
        return new BaseResponse<>(getPostsRes);
    }

    // 공고 특정 조회 (Post API 5 : 로그인 전과 로그인 후의 화면 버튼 달라짐)
    @GetMapping("/{postIdx}")
    public BaseResponse<PostResDto> getPostByUserId(@PathVariable("postIdx") int postIdx, @RequestParam int user) throws BaseException {
        PostResDto getPost = postService.getPostByUserId(postIdx, user);
        if(getPost == null)
            return new BaseResponse<>(BaseResponseStatus.NOT_FOUND_POST);
        return new BaseResponse<>(getPost);
    }

    // 공고 수정
    @PutMapping("/{postIdx}")
    public BaseResponse<PostResDto> modifyPost(@PathVariable("postIdx") int postIdx, @RequestPart PostReqDto post,
                                         @RequestParam int user, @RequestPart MultipartFile file) throws Exception {
        logger.info(post.toString());

        if (post.getTitle() == null) {
            return new BaseResponse<>(BaseResponseStatus.POST_POST_EMPTY_TITLE);
        }
        if (post.getUrl() == null) {
            return new BaseResponse<>(BaseResponseStatus.POST_POST_EMPTY_URL);
        }
        if (Integer.valueOf(post.getDelivery_tips()) == null) {
            return new BaseResponse<>(BaseResponseStatus.POST_POST_EMPTY_TIP);
        }
        if (Integer.valueOf(post.getMinimum()) == null) {
            return new BaseResponse<>(BaseResponseStatus.POST_POST_EMPTY_MINIMUM);
        }
        if (Integer.valueOf(post.getNum_of_recruits()) == null) {
            return new BaseResponse<>(BaseResponseStatus.POST_POST_EMPTY_RECRUIT);
        }
        if (post.getLatitude() == null || post.getLongitude() == null) {
            return new BaseResponse<>(BaseResponseStatus.POST_POST_EMPTY_LOCATION);
        }
        if(post.getCategory() == null){
            return new BaseResponse<>(BaseResponseStatus.POST_POST_EMPTY_CATEGORY);
        }

        String fileUrl = postService.getImageUrl(postIdx);
        if(fileUrl != null) {           // 기존에 서버에 등록된 이미지 삭제
            String[] url = fileUrl.split("/");
            logger.info("Delete Image start");
            awsS3Service.deleteImage(url[3]); // https:~ 경로 빼고 파일명으로 삭제
        }

        // 이미지 파일이 있으면 서버에 등록
        if(!file.isEmpty())
            fileUrl = url + awsS3Service.uploadFile(file, post, user);

        // 공고 내용 수정
        PostResDto modifyPost = postService.modifyPost(postIdx, post, user, fileUrl);
        logger.info("Modify success");
        return new BaseResponse<>(modifyPost);
    }

    // 공고 상태 변경
    @PutMapping("/status/{postIdx}")
    public BaseResponse<PostResDto> modifyPostStatus(@PathVariable("postIdx") int postIdx) throws BaseException {
        PostResDto modifyPost = postService.modifyPostStatus(postIdx);
        return new BaseResponse<>(modifyPost);
    }

    // 카테고리로 공고 조회
    // 파라미터를 기준으로 1km 이내의 공고 중 order_time이 유효하고, 아직 모집중인 상태의 공고 리스트 반환
    @PostMapping("/category")
    public BaseResponse<List<PostResDto>> getPostsByCategory(@RequestBody CategoryReqDto categoryReqDto) throws BaseException {
        // 에러 처리 필요
        List<PostResDto> getPostsByCategory = postService.getPostsByCategory(categoryReqDto);
        return new BaseResponse<>(getPostsByCategory);
    }

    // 채팅방 아이디로 공고 조회
    @GetMapping("/chatRoom/{chatRoomIdx}")
    public BaseResponse<PostResDto> getPostByChatRoomId(@PathVariable int chatRoomIdx) throws BaseException {
        PostResDto getPost = postService.getPostByChatRoomId(chatRoomIdx);
        return new BaseResponse<>(getPost);
    }
}
