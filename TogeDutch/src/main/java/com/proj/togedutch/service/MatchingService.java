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
import com.proj.togedutch.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MatchingService {
    private final MatchingRepository matchingRepository;
    private final PostRepository postRepository;
    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;

    @Transactional(rollbackFor = Exception.class)
    public UserResDto getReMatching(int postIdx) throws BaseException {
        /*
        * jdbc도 변경 필요
        * 1. 추천가능한 조건에 맞는 유저가 0명 / 1명 / 2명인 경우 각각 에러 처리 필요 (각각의 경우 NullPointerException 처리하면 누적 정상적으로 될 듯)
        * 2. 매칭 count가 3회 초과된 경우 User.status를 3회 초과로 설정 (jdbc)
        * -> status는 사장 / 일반유저 표시하는 칼럼이라 상태 표현하는 새로운 칼럼 추가 필요
        */
        int MatchingCount = 500;
        MatchingRepository.MatchingUser userResult = null;

        try{
            Post post = postRepository.findById(postIdx).orElseThrow(IllegalStateException::new);
            Matching matching = matchingRepository.findByPostIdx(postIdx);

            System.out.println("이미 매칭된 적 있음 : " + matching.getMatchingIdx());
            int first = -1;
            int second = -1;

            if(matching.getCount() == 1){
                first = matching.getUserFirstIdx();
                System.out.println("first : " + first);

                // 추천 가능한 조건에 맞는 user가 1명 뿐인 경우
                try {
                    userResult = matchingRepository.findUserLivingNearBy(post, first, second);
                    MatchingCount = matchingCountChange(matching, userResult);
                } catch(NullPointerException e){
                   
                }
            }
            else if (matching.getCount() == 2) {
                first = matching.getUserFirstIdx();
                second = matching.getUserSecondIdx();
                userResult = matchingRepository.findUserLivingNearBy(post, first , second);
                MatchingCount = matchingCountChange(matching, userResult);
            }
            else if (matching.getCount() == 3){
                MatchingCount = 400; //횟수 초과
                // 요기도 변경 필요 !!!!!!!!!!!!!!!!!!!
                User user = new User("3번 횟수 초과");
                return new UserResDto(user);
            }
            return new UserResDto(userRepository.findByUserIdx(userResult.getUser_id()));
        } catch(NullPointerException e){
            // 매칭을 한 번도 한 적이 없는 경우
            // 추천 가능한 조건에 맞는 유저가 0명인 경우 에러 처리 필요
            MatchingCount = 300;
            Post post = postRepository.findById(postIdx).orElseThrow(IllegalStateException::new);
            userResult = matchingRepository.findUserLivingNearByFirst(post.getLatitude(), post.getLongitude());

            Matching newMatching = Matching.builder()
                            .userFirstIdx(userResult.getUser_id())
                            .count(1)
                            .postIdx(postIdx)
                            .build();
            matchingRepository.save(newMatching);

            return new UserResDto(userRepository.findByUserIdx(userResult.getUser_id()));
        }
    }

    //매칭 횟수 변경
    @Transactional(rollbackFor = Exception.class)
    public int matchingCountChange(Matching matching, MatchingRepository.MatchingUser user) throws BaseException {
        int count = matching.getCount();

        if(count == 0){
            matching.updateCountToOne(user.getUser_id(), 1);
            matchingRepository.save(matching);
            return 1;
        }
        else if(count == 1){
            matching.updateCountToTwo(user.getUser_id(), 2);
            matchingRepository.save(matching);
            return 1;
        }
        else if(count == 2){
            matching.updateCountToThree(user.getUser_id(), 3);
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
