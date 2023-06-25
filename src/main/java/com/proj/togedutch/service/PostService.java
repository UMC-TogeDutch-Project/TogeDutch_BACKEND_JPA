package com.proj.togedutch.service;

import com.proj.togedutch.config.BaseException;

import com.proj.togedutch.config.BaseResponseStatus;
import com.proj.togedutch.domain.Post;
import com.proj.togedutch.domain.User;
import com.proj.togedutch.dto.CategoryReqDto;
import com.proj.togedutch.dto.PostReqDto;
import com.proj.togedutch.dto.PostResDto;
import com.proj.togedutch.dto.UserResDto;
import com.proj.togedutch.repository.PostRepository;
import com.proj.togedutch.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.proj.togedutch.config.BaseResponseStatus.*;


@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;

    // 모든 공고
    public List<PostResDto> findAll() throws BaseException {
        List<Post> getPost = postRepository.findAll();
        return getPost.stream()
                .map(m->new PostResDto(m))
                .collect(Collectors.toList());
    }

    @Transactional(rollbackFor = SQLException.class)
    public int createPost(PostReqDto post, String fileUrl, int userIdx) throws BaseException {
        // Dto to Entity
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

        return postRepository.save(newPost).getPostIdx();
    }

    public List<PostResDto> getSortingPosts(String sort) throws BaseException {
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

    public String getImageUrl(int postIdx) throws BaseException {
        Post getPost = postRepository.findById(postIdx).orElseThrow(IllegalArgumentException::new);
        return getPost.getImage();
    }

    public PostResDto modifyPost(int postIdx, PostReqDto post, int userIdx, String fileUrl) throws BaseException {
        Post modifyPost = postRepository.findById(postIdx)
                .orElseThrow(IllegalArgumentException::new);
        modifyPost.updatePost(post, fileUrl);
        Post getPost = postRepository.save(modifyPost);
        return new PostResDto(getPost);
    }

    public PostResDto insertChatRoom(int postIdx, int chatRoomIdx) throws BaseException {
        Post modifyPost = postRepository.findById(postIdx)
                .orElseThrow(IllegalArgumentException::new);
        modifyPost.insertChatRoom(chatRoomIdx);
        Post getPost = postRepository.save(modifyPost);
        return new PostResDto(getPost);

    }

    public PostResDto modifyPostStatus(int postIdx) throws BaseException {
        postRepository.modifyPostStatus(postIdx);
        Post getPost = postRepository.findById(postIdx)
                .orElseThrow(IllegalArgumentException::new);
        return new PostResDto(getPost);
    }

    public List<PostResDto> getPostsByCategory(CategoryReqDto categoryReqDto) throws BaseException {
        Optional<Post> getPosts = postRepository.findPostsByCategory(categoryReqDto);
        if(!getPosts.isPresent())
            throw new BaseException(FAILED_TO_FIND_BY_CATEGORY);

        return getPosts.stream()
                .map(m->new PostResDto(m))
                .collect(Collectors.toList());
    }

    public PostResDto getPostByChatRoomId(int chatRoomIdx) throws BaseException {
        Post getPost = postRepository.findByChatRoomIdx(chatRoomIdx);
        return new PostResDto(getPost);
    }

    public List<UserResDto> getUsersInPost(int postIdx) throws BaseException {
        Post post = postRepository.findById(postIdx).orElseThrow(IllegalArgumentException::new);
        if(post.getStatus().equals("공고사용불가"))
            throw new BaseException(POST_NOT_ACCESSIBLE);

        List<UserRepository.UserInfo> getUsersInPost = postRepository.findUsersInPost(postIdx);
        return getUsersInPost.stream()
                .map(m->new UserResDto(m))
                .collect(Collectors.toList());
    }

    public PostResDto getPostById(int postIdx) throws BaseException {
        Post getPost = postRepository.findById(postIdx)
                .orElseThrow(IllegalArgumentException::new);
        return new PostResDto(getPost);
    }

    public List<Post> getPostByUserId(int userid) throws BaseException{
        return postRepository.findPostsByUserIdx(userid);

    }

    public int deletePost(int postIdx) throws BaseException {
        try{
            Post post = postRepository.findById(postIdx)
                    .orElseThrow(IllegalArgumentException::new);
            post.updateStatusPost();
            Post deletePost = postRepository.save(post);
            if(deletePost != null)
                return 1;
            else return 0;
        } catch(Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public List<Post> getPostByJoinUserId(int userIdx) throws BaseException {
        try{
            List<Post> joinPost = postRepository.findPostsByJoinApplicationUserId(userIdx);
            return joinPost;
        } catch(Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
