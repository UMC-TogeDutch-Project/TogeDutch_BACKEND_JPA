package com.proj.togedutch.service;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.domain.Application;
import com.proj.togedutch.domain.Post;
import com.proj.togedutch.domain.Review;
import com.proj.togedutch.domain.User;
import com.proj.togedutch.dto.ReviewDto;
import com.proj.togedutch.dto.ReviewResDto;
import com.proj.togedutch.repository.ApplicationRepository;
import com.proj.togedutch.repository.PostRepository;
import com.proj.togedutch.repository.ReviewRepository;
import com.proj.togedutch.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.proj.togedutch.config.BaseResponseStatus.DATABASE_ERROR;
@Service
public class ReviewService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    ReviewRepository reviewRepository;
    ApplicationRepository applicationRepository;

    UserRepository userRepository;

    PostRepository postRepository;

    public Review createReview(int applicationId, ReviewDto review) throws BaseException {
        try {
            Application application =applicationRepository.findById(applicationId).orElseThrow(() -> new IllegalArgumentException("해당 신청이 존재하지 않습니다"));
            User user = userRepository.findById(application.getUserIdx()).orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다"));
            Post post =postRepository.findById(application.getPostIdx()).orElseThrow(() -> new IllegalArgumentException("해당 공고 존재하지 않습니다"));
            return reviewRepository.save(review.toEntity(user,application,post));
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }

    }
    //키워드 조회(키워드 id)
    public List<Review> getTextReview(int postId) throws BaseException{
        try {
            //Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당 공고 존재하지 않습니다"));
            return reviewRepository.findReviewByPost(postId);
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public ReviewResDto getEmotionReview(int postId) throws BaseException{
        try {

            return reviewRepository.findAvgByPostId(postId);
        } catch (Exception e) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
