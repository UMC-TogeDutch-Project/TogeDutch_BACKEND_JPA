package com.proj.togedutch.service;

import com.proj.togedutch.config.BaseException;
import com.proj.togedutch.config.BaseResponseStatus;
import com.proj.togedutch.domain.Application;
import com.proj.togedutch.domain.Matching;
import com.proj.togedutch.domain.Post;
import com.proj.togedutch.domain.User;
import com.proj.togedutch.dto.MatchingResDto;
import com.proj.togedutch.dto.UserResDto;
import com.proj.togedutch.repository.ApplicationRepository;
import com.proj.togedutch.repository.MatchingRepository;
import com.proj.togedutch.repository.PostRepository;
import com.proj.togedutch.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.proj.togedutch.config.BaseResponseStatus.EXCEEDING_MATCHING_APPLICATION;
import static com.proj.togedutch.config.BaseResponseStatus.NO_USERS_MATCHING_CONDITION;

@Service
@RequiredArgsConstructor
public class MatchingService {
    private final MatchingRepository matchingRepository;
    private final PostRepository postRepository;
    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;

    @Transactional(rollbackFor = Exception.class)
    public MatchingResDto getMatching(int postIdx) throws BaseException {
        Post post = postRepository.findById(postIdx).orElseThrow(NullPointerException::new);

        try{
            Matching matching = matchingRepository.findByPostIdx(postIdx);
            return getReMatching(post, matching);
        } catch(NullPointerException e){
            // 매칭 신청이 처음인 경우에 해당
            return getFirstMatching(postIdx);
        }
    }

    // 매칭 신청을 처음 신청한 경우
    @Transactional(rollbackFor = Exception.class)
    public MatchingResDto getFirstMatching(int postIdx) throws BaseException {
        Post post = postRepository.findById(postIdx).orElseThrow(IllegalStateException::new);
        MatchingRepository.MatchingUser userResult = matchingRepository.findUserLivingNearByFirst(post.getLatitude(), post.getLongitude());

        // 조건에 맞는 유저가 1명도 없는 경우
        if(userResult == null)
            throw new BaseException(NO_USERS_MATCHING_CONDITION);

        Matching newMatching = Matching.builder()
                .userFirstIdx(userResult.getUser_id())
                .count(1)
                .postIdx(postIdx)
                .build();
        matchingRepository.save(newMatching);

        MatchingResDto matchingRes = new MatchingResDto(userRepository.findByUserIdx(userResult.getUser_id()));
        matchingRes.setMatchingResult("매칭 1회 신청");
        return matchingRes;
    }

    // 매칭 신청이 처음이 아닌 경우 (재매칭)
    @Transactional(rollbackFor = Exception.class)
    public MatchingResDto getReMatching(Post post, Matching matching) throws BaseException {
        MatchingRepository.MatchingUser userResult = null;
        MatchingResDto matchingRes = null;

        if(matching.getCount() == 1){   // 매칭 2회차
            try {
                userResult = matchingRepository.findUserLivingNearBy(post, matching.getUserFirstIdx(), -1);
                matchingCountChange(matching, userResult);

                matchingRes = new MatchingResDto(userRepository.findByUserIdx(userResult.getUser_id()));
                matchingRes.setMatchingResult("매칭 2회 신청");
            } catch(NullPointerException e){
                // 추천 가능한 조건에 맞는 user가 1명 뿐인 경우 -> 이미 1명 추천 완료 -> 재추천 불가능
                throw new BaseException(NO_USERS_MATCHING_CONDITION);
            }
        }
        else if (matching.getCount() == 2) {    // 매칭 3회차
            try{
                userResult = matchingRepository.findUserLivingNearBy(post, matching.getUserFirstIdx(), matching.getUserSecondIdx());
                matchingCountChange(matching, userResult);

                matchingRes = new MatchingResDto(userRepository.findByUserIdx(userResult.getUser_id()));
                matchingRes.setMatchingResult("매칭 3회 신청");
            } catch(NullPointerException e){
                // 추천 가능한 조건에 맞는 user가 2명 뿐인 경우 -> 이미 2명 추천 완료 -> 재추천 불가능
                throw new BaseException(NO_USERS_MATCHING_CONDITION);
            }
        }
        else if (matching.getCount() == 3)  // 매칭 3회 초과
            throw new BaseException(EXCEEDING_MATCHING_APPLICATION);
        
        return matchingRes;
    }

    // 매칭 횟수 변경
    @Transactional(rollbackFor = Exception.class)
    public void matchingCountChange(Matching matching, MatchingRepository.MatchingUser user) throws BaseException {
        int count = matching.getCount();

        if(count == 0){
            matching.updateCountToOne(user.getUser_id(), 1);
            matchingRepository.save(matching);
        }
        else if(count == 1){
            matching.updateCountToTwo(user.getUser_id(), 2);
            matchingRepository.save(matching);
        }
        else if(count == 2){
            matching.updateCountToThree(user.getUser_id(), 3);
            matchingRepository.save(matching);
        }
    }

    public int getAcceptUserId(int userIdx,int postIdx) throws BaseException {
        Post post = postRepository.findById(postIdx).orElseThrow(IllegalStateException::new);
        Application newApplication = new Application("매칭 성공", postIdx, post.getUserIdx(), post.getChatRoomIdx(), userIdx);
        return applicationRepository.save(newApplication).getApplicationIdx();
    }
}
