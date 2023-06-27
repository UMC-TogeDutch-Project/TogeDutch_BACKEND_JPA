package com.proj.togedutch.controller;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.config.BaseResponse;
import com.proj.togedutch.config.BaseResponseStatus;
import com.proj.togedutch.domain.Post;
import com.proj.togedutch.dto.ReviewDto;
import com.proj.togedutch.domain.Review;
import com.proj.togedutch.dto.ReviewResDto;
import com.proj.togedutch.service.AWSS3Service;
import com.proj.togedutch.service.PostService;
import com.proj.togedutch.service.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    AWSS3Service awsS3Service;

    @Autowired
    ReviewService reviewService;
    @Autowired
    PostService postService;

    @Value("${cloud.aws.url}")
    private String url;

    @ResponseBody
    @PostMapping("/{applicationId}")
    public int createReview(@RequestPart ReviewDto reviewDto, @PathVariable("applicationId") int applicationId) throws BaseException {

        Review review=reviewService.createReview(applicationId,reviewDto);
        if(review != null)
            return 1;
        else
            return 0;

    }


    @ResponseBody
    @GetMapping("/{postId}")
    public BaseResponse<List<Review>> getTextReview(@PathVariable("postId") int postId) {
        try {
            List<Review> getTextReview = reviewService.getTextReview(postId);
            return new BaseResponse<>(getTextReview);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @ResponseBody
    @GetMapping("/uploaduser/{userId}")
    public BaseResponse<List<ReviewResDto>> getUploadUserPostReview(@PathVariable("userId") int userId) {
        ReviewResDto reviewResDto;
        List<ReviewResDto> userpost = new ArrayList<ReviewResDto>();
        try {
            List<Post> post = postService.getPostByUserId(userId);
            for(int i=0; i<post.size(); i++){
                //System.out.println(post.get(i).getPost_id());
                reviewResDto = reviewService.getEmotionReview(post.get(i).getPostIdx());
                //System.out.println(getEmotion.getAvg());
                //System.out.println(getEmotion.getPost_id());
                userpost.add(reviewResDto);
            }
            return new BaseResponse<>(userpost);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }



}
