package com.proj.togedutch.service;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.domain.Application;
import com.proj.togedutch.domain.Matching;
import com.proj.togedutch.domain.Post;
import com.proj.togedutch.domain.User;
import com.proj.togedutch.dto.UserResDto;
import com.proj.togedutch.repository.ApplicationRepository;
import com.proj.togedutch.repository.MatchingRepository;
import com.proj.togedutch.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MatchingService {
    private final MatchingRepository matchingRepository;
    private final PostRepository postRepository;
    private final ApplicationRepository applicationRepository;

    @Transactional(rollbackFor = Exception.class)
    public UserResDto getReMatching(int postIdx) throws BaseException {
        int MatchingCount = 500;
        User user = null;

        try{
            Post post = postRepository.findById(postIdx).orElseThrow(IllegalStateException::new);
            Matching matching = matchingRepository.findByPostIdx(postIdx);

            int first = -1;
            int second = -1;

            if(matching.getCount() == 1){
                first = matching.getUserFirstIdx();
                user = matchingRepository.findUserLivingNearBy(post, first , second);
                MatchingCount = matchingCountChange(matching, user);
            }
            else if (matching.getCount() == 2) {
                first = matching.getUserFirstIdx();
                second = matching.getUserSecondIdx();
                user = matchingRepository.findUserLivingNearBy(post, first , second);
                MatchingCount = matchingCountChange(matching, user);
            }
            else if (matching.getCount() == 3){
                MatchingCount = 400; //횟수 초과
                user = new User("3번 횟수 초과");
            }
            return new UserResDto(user);
        } catch(EmptyResultDataAccessException e){
            MatchingCount = 300;
            Post post = postRepository.findById(postIdx).orElseThrow(IllegalStateException::new);
            user = matchingRepository.findUserLivingNearByFirst(post);

            Matching newMatching = Matching.builder()
                            .userFirstIdx(user.getUserIdx())
                            .count(1)
                            .postIdx(postIdx)
                            .build();
            matchingRepository.save(newMatching);

            return new UserResDto(user);
        }
    }

    //매칭 횟수 변경
    @Transactional(rollbackFor = Exception.class)
    public int matchingCountChange(Matching matching, User user) throws BaseException {
        int count = matching.getCount();

        if(count == 0){
            matching.updateCountToOne(user.getUserIdx(), 1);
            matchingRepository.save(matching);
            return 1;
        }
        else if(count == 1){
            matching.updateCountToTwo(user.getUserIdx(), 2);
            matchingRepository.save(matching);
            return 1;
        }
        else if(count == 2){
            matching.updateCountToThree(user.getUserIdx(), 3);
            matchingRepository.save(matching);
            return 1;
        }

        return 100;
    }

    public int getAcceptUserId(int userIdx,int postIdx) throws BaseException {
        Post post = postRepository.findById(postIdx).orElseThrow(IllegalStateException::new);
        Application newApplication = new Application("매칭 성공", postIdx, post.getUserIdx(), post.getChatRoomIdx(), userIdx);
        return applicationRepository.save(newApplication).getApplicationIdx();
    }
}
