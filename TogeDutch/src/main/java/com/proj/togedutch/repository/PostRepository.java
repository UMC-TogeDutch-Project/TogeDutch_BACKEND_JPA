package com.proj.togedutch.repository;

import com.proj.togedutch.domain.Post;
import com.proj.togedutch.dto.CategoryReqDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "SET time_zone = 'Asia/Seoul'")
    void timezoneSetting();

    // 최신순 조회
    List<Post> findByStatusNotInOrderByCreatedAtDesc(List<String> status);

    // 주문 임박 조회
    @Query(nativeQuery = true, value = "SELECT * FROM Post as p WHERE p.order_time between now() and DATE_ADD(NOW(), INTERVAL 10 MINUTE) and p.status NOT IN (:status) order by p.order_time asc")
    List<Post> findByOrderImminent(@Param("status") List<String> status);

    // 공고 특정 조회 (Post API 5번)
    Post findByPostIdxAndUserIdx(int postIdx, int userIdx);

    // 공고 상태 변경
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE Post p "
            + "SET p.status = \"시간만료\" "
            + "where p.post_id = :postIdx and p.order_time < current_timestamp and p.num_of_recruits != p.recruited_num")
    void modifyPostStatus(@Param("postIdx") int postIdx);

    // 카테고리로 공고 조회
    @Query(nativeQuery = true, value = "SELECT\n" +
            "    * , (\n" +
            "       6371 * acos ( cos ( radians(:#{#categoryReq.latitude}) ) * cos( radians(latitude) ) * cos( radians(longitude) - radians(:#{#categoryReq.longitude}) )\n" +
            "          + sin ( radians(:#{#categoryReq.latitude}) ) * sin( radians(latitude) )\n" +
            "       )\n" +
            "   ) AS distance\n" +
            "FROM Post p\n" +
            "where p.order_time > now() and (p.category = :#{#categoryReq.category1} or p.category = :#{#categoryReq.category2} or p.category = :#{#categoryReq.category3} or p.category = :#{#categoryReq.category4} or p.category = :#{#categoryReq.category5} or p.category = :#{#categoryReq.category6}) and p.status = \"모집중\"\n" +
            "HAVING distance <= 1\n" +
            "ORDER BY distance;")
    Optional<Post> findPostsByCategory(@Param("categoryReq") CategoryReqDto categoryReq);

    // 채팅방 아이디로 공고 조회
    Post findByChatRoomIdx(int chatRoomIdx);
}
