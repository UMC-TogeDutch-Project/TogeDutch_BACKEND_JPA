package com.proj.togedutch.controller;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.config.BaseResponse;
import com.proj.togedutch.config.BaseResponseStatus;
import com.proj.togedutch.domain.Post;
import com.proj.togedutch.service.AWSS3Service;
import com.proj.togedutch.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private PostService postService;
    @Autowired
    private AWSS3Service awsS3Service;
    @Value("${cloud.aws.url}")
    private String url;

    // 공고 전체 조회
    @GetMapping("")
    public BaseResponse<List<Post>> getAllPosts(){
        List<Post> getPostsRes = postService.findAll();
        return new BaseResponse<>(getPostsRes);
    }

    // 공고 생성 (채팅방도 생성)
    @PostMapping("")
    public BaseResponse<Post> createPost(@RequestParam int userIdx, @RequestPart Post post, @RequestPart(value="file", required = false) MultipartFile file) throws BaseException, IOException {
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
            fileUrl = url + awsS3Service.uploadFile(file, post, userIdx);

        logger.info("fileUrl은 " + fileUrl);

        Post createPost = postService.createPost(post, fileUrl, userIdx);
        // 채팅방 API 추가 후 수정할 예정
        // ChatRoom newChatRoom = chatRoomService.createChatRoom();
        // Post modifyPost = postService.insertChatRoom(newPost.getPost_id(), newChatRoom.getChatRoomIdx());
        //return new BaseResponse<>(modifyPost);
        return new BaseResponse<>(createPost);
    }
}
