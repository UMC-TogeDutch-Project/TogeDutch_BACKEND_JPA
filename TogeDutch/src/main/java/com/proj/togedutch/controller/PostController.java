package com.proj.togedutch.controller;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.config.BaseResponse;
import com.proj.togedutch.domain.Post;
import com.proj.togedutch.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    private PostService postService;

    // 공고 전체 조회
    @GetMapping("")
    public BaseResponse<List<Post>> getAllPosts(){
        List<Post> getPostsRes = postService.findAll();
        return new BaseResponse<>(getPostsRes);
    }
}
