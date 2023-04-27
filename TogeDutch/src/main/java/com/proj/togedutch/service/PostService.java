package com.proj.togedutch.service;

import com.proj.togedutch.config.BaseException;
import static com.proj.togedutch.config.BaseResponseStatus.*;

import com.proj.togedutch.config.BaseResponseStatus;
import com.proj.togedutch.domain.Post;
import com.proj.togedutch.dto.CategoryReqDto;
import com.proj.togedutch.dto.PostReqDto;
import com.proj.togedutch.dto.PostResDto;
import com.proj.togedutch.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;

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

        postRepository.timezoneSetting();
        Post getPost = postRepository.save(newPost);
        System.out.println("Service에서 찍음 : post.createdAt은 " + getPost.getCreatedAt());

        return new PostResDto(getPost);
    }

    public List<PostResDto> getSortingPosts(String sort){
        List<Post> getPost = null;
        List<String> status = new ArrayList<>();
        status.add("모집완료");
        status.add("시간만료");
        status.add("공고사용불가");

        if(sort.equals("latest"))   // 최신순
            getPost = postRepository.findByStatusNotInOrderByCreatedAtDesc(status);
        else                        // 주문 임박
            getPost = postRepository.findByOrderImminent(status);

        return getPost.stream()
                .map(m->new PostResDto(m))
                .collect(Collectors.toList());
    }

    public PostResDto getPostByUserId(int postIdx, int userIdx) throws BaseException {
        Post getPost = postRepository.findByPostIdxAndUserIdx(postIdx, userIdx);
        return new PostResDto(getPost);
    }

    public String getImageUrl(int postIdx){
        Post getPost = postRepository.findById(postIdx).orElseThrow(IllegalArgumentException::new);
        return getPost.getImage();
    }

    public PostResDto modifyPost(int postIdx, PostReqDto post, int userIdx, String fileUrl){
        Post modifyPost = postRepository.findById(postIdx)
                .orElseThrow(IllegalArgumentException::new);
        postRepository.timezoneSetting();
        modifyPost.updatePost(post, fileUrl);
        Post getPost = postRepository.save(modifyPost);
        return new PostResDto(getPost);
    }

    public PostResDto insertChatRoom(int postIdx, int chatRoomIdx){
        Post modifyPost = postRepository.findById(postIdx)
                .orElseThrow(IllegalArgumentException::new);
        modifyPost.insertChatRoom(chatRoomIdx);
        Post getPost = postRepository.save(modifyPost);
        return new PostResDto(getPost);

    }

    public PostResDto modifyPostStatus(int postIdx){
        postRepository.timezoneSetting();
        postRepository.modifyPostStatus(postIdx);
        Post getPost = postRepository.findById(postIdx)
                .orElseThrow(IllegalArgumentException::new);
        return new PostResDto(getPost);
    }

    public List<PostResDto> getPostsByCategory(CategoryReqDto categoryReqDto) throws BaseException {
        postRepository.timezoneSetting();
        Optional<Post> getPosts = postRepository.findPostsByCategory(categoryReqDto);

        // 이샛기 왜이래?????????????????????????????????
        if(!getPosts.isPresent()) {
            System.out.println("결과가 null입니다.");
            throw new BaseException(BaseResponseStatus.FAILED_TO_FIND_BY_CATEGORY);
        }

        return getPosts.stream()
                .map(m->new PostResDto(m))
                .collect(Collectors.toList());
    }

    public PostResDto getPostByChatRoomId(int chatRoomIdx){
        Post getPost = postRepository.findByChatRoomIdx(chatRoomIdx);
        return new PostResDto(getPost);
    }
}
