package com.proj.togedutch.service;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.domain.Post;
import com.proj.togedutch.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import static com.proj.togedutch.config.BaseResponseStatus.DATABASE_ERROR;

@Service
@Transactional
public class PostService {
    @Autowired
    private PostRepository postRepository;

    // 모든 공고
    public List<Post> findAll() {
        return postRepository.findAll();
    }

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

        //postRepository.timezoneSetting();

        if(sort.equals("latest"))   // 최신순
            return postRepository.findByStatusNotInOrderByCreatedAtDesc(status);
        else                        // 주문 임박
            return postRepository.findByOrderImminent(status);
    }
}
