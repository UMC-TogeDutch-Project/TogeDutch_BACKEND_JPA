package com.proj.togedutch.service;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.domain.Declaration;
import com.proj.togedutch.domain.Post;
import com.proj.togedutch.dto.DeclarationResDto;
import com.proj.togedutch.dto.PostReqDto;
import com.proj.togedutch.dto.PostResDto;
import com.proj.togedutch.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    // 모든 공고
    public List<PostResDto> findAll() {
        List<Post> getPost = postRepository.findAll();
        return getPost.stream()
                .map(m->new PostResDto(m))
                .collect(Collectors.toList());
    }

    @Transactional(rollbackFor = SQLException.class)
    public PostResDto createPost(PostReqDto post, String fileUrl, int userIdx){
        Post newPost = Post.builder()
                .title(post.getTitle())
                .url(post.getUrl())
                .delivery_tips(post.getDelivery_tips())
                .minimum(post.getMinimum())
                .order_time(post.getOrder_time())
                .num_of_recruits(post.getNum_of_recruits())
                .recruited_num(post.getRecruited_num())
                .status(post.getStatus())
                .latitude(post.getLatitude())
                .longitude(post.getLongitude())
                .category(post.getCategory())
                .image(fileUrl)
                .user_id(userIdx)
                .build();

        return new PostResDto(postRepository.save(newPost));
    }

    public List<PostResDto> getSortingPosts(String sort){
        List<Post> getPost = null;
        List<String> status = new ArrayList<>();
        status.add("모집완료");
        status.add("시간만료");
        status.add("공고사용불가");

        postRepository.timezoneSetting();
        if(sort.equals("latest"))   // 최신순
            getPost = postRepository.findByStatusNotInOrderByCreatedAtDesc(status);
        else                        // 주문 임박
            getPost = postRepository.findByOrderImminent(status);

        return getPost.stream()
                .map(m->new PostResDto(m))
                .collect(Collectors.toList());
    }

    public PostResDto getPostByUserId(int postIdx, int userIdx) throws BaseException {
        return new PostResDto(postRepository.findByPostIdxAndUserIdx(postIdx, userIdx));
    }

    public String getImageUrl(int postIdx){
        Post getPost = postRepository.findById(postIdx).orElseThrow(IllegalArgumentException::new);
        return getPost.getImage();
    }

    public PostResDto modifyPost(int postIdx, PostReqDto post, int userIdx, String fileUrl){
        Post modifyPost = postRepository.findById(postIdx)
                .orElseThrow(IllegalArgumentException::new);
        modifyPost.updatePost(post, fileUrl);
        return new PostResDto(postRepository.save(modifyPost));
    }

    public PostResDto insertChatRoom(int postIdx, int chatRoomIdx){
        Post modifyPost = postRepository.findById(postIdx)
                .orElseThrow(IllegalArgumentException::new);
        modifyPost.insertChatRoom(chatRoomIdx);
        return new PostResDto(postRepository.save(modifyPost));

    }
}
