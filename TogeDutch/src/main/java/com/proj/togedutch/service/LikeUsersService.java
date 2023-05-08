package com.proj.togedutch.service;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.domain.LikeUsers;
import com.proj.togedutch.domain.Post;
import com.proj.togedutch.dto.LikeUsersDto;
import com.proj.togedutch.dto.PostResDto;
import com.proj.togedutch.repository.LikeUsersRepository;
import com.proj.togedutch.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LikeUsersService {
    private final LikeUsersRepository likeUsersRepository;
    private final PostRepository postRepository;

    public int duplicateLikePost(int userIdx, int postIdx, int Uploader_userIdx) throws BaseException {
        LikeUsers likeUsers =
                likeUsersRepository.findByIdAndUploaderIdxAndKeeperIdx(postIdx, Uploader_userIdx, userIdx);

        if(likeUsers != null)
            return 1;
        return 0;
    }

    @Transactional
    public LikeUsersDto createLikePost(int userIdx, int postIdx, int uploaderIdx) throws BaseException {
        // Dto to Entity
        LikeUsers newLikeUsers = LikeUsers.builder()
                .postIdx(postIdx)
                .uploaderIdx(uploaderIdx)
                .keeperIdx(userIdx).build();

        LikeUsers likeUsers = likeUsersRepository.save(newLikeUsers);
        return new LikeUsersDto(likeUsers);
    }

    @Transactional
    public int deleteLikePost(int userIdx, int postIdx) throws BaseException {
        int result = likeUsersRepository.deleteByKeeperIdxAndPostIdx(userIdx, postIdx);
        return result;
    }

    public List<PostResDto> getLikePosts(int userIdx) throws BaseException {
        List<Post> likePosts = likeUsersRepository.findLikePostByUserIdx(userIdx);
        return likePosts.stream()
                .map(m->new PostResDto(m))
                .collect(Collectors.toList());
    }
}
