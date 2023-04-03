package com.proj.togedutch.service;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.domain.Post;
import com.proj.togedutch.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import static com.proj.togedutch.config.BaseResponseStatus.DATABASE_ERROR;
import static com.proj.togedutch.config.BaseResponseStatus.NOT_FOUND_POST;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    // 모든 공고
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Transactional(rollbackFor = SQLException.class)
    public Post createPost(Post post, String fileUrl, int userIdx){
        Post newPost = Post.builder()
                .title(post.getTitle())
                .url(post.getUrl())
                .delivery_tips(post.getDeliveryTips())
                .minimum(post.getMinimum())
                .order_time(post.getOrderTime())
                .num_of_recruits(post.getNumOfRecruits())
                .recruited_num(post.getRecruitedNum())
                .status(post.getStatus())
                .latitude(post.getLatitude())
                .longitude(post.getLongitude())
                .category(post.getCategory())
                .image(post.getImage())
                .user_id(userIdx)
                .build();

        return postRepository.save(newPost);
    }

    public List<Post> getSortingPosts(String sort){
        List<String> status = new ArrayList<>();
        status.add("모집완료");
        status.add("시간만료");
        status.add("공고사용불가");

        postRepository.timezoneSetting();

        if(sort.equals("latest"))   // 최신순
            return postRepository.findByStatusNotInOrderByCreatedAtDesc(status);
        else                        // 주문 임박
            return postRepository.findByOrderImminent(status);
    }

    public Post getPostByUserId(int postIdx, int userIdx) throws BaseException {
        return postRepository.findByPostIdxAndUserIdx(postIdx, userIdx);
    }

    public String getImageUrl(int postIdx){
        Post getPost = postRepository.findByPostIdx(postIdx);
        return getPost.getImage();
    }

    public Post modifyPost(int postIdx, Post post, int userIdx, String fileUrl){
        System.out.println("order_time은 " + post.getOrderTime());

        Post modifyPost = Post.builder()
                .post_id(postIdx)
                .title(post.getTitle())
                .url(post.getUrl())
                .delivery_tips(post.getDeliveryTips())
                .minimum(post.getMinimum())
                .order_time(post.getOrderTime())
                .num_of_recruits(post.getNumOfRecruits())
                .recruited_num(post.getRecruitedNum())
                .status(post.getStatus())
                .latitude(post.getLatitude())
                .longitude(post.getLongitude())
                .category(post.getCategory())
                .image(fileUrl)
                .user_id(userIdx)
                .build();

        return postRepository.save(modifyPost);
    }
}
