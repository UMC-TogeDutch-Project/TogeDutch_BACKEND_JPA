package com.proj.togedutch.repository;

import com.proj.togedutch.domain.Advertisement;
import com.proj.togedutch.domain.Post;
import com.proj.togedutch.dto.AdvertisementResDto;
import com.proj.togedutch.dto.CategoryReqDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface AdRepository extends JpaRepository<Advertisement, Integer> {
    List<Advertisement> findAllByUserIdx(int userIdx);

    // 위도 경도 반경 1km, 2주이내 광고 검색
    @Query(value = "SELECT\n" +
            "    * , (\n" +
            "       6371 * acos ( cos ( radians(:latitude) ) * cos( radians(latitude) ) * cos( radians(longitude) - radians(:longitude) )\n" +
            "          + sin ( radians(:latitude) ) * sin( radians(latitude) )\n" +
            "       )\n" +
            "   ) AS distance\n" + //반경 1km
            "FROM Advertisement a\n" +
            "where DATE(a.created_at) >= DATE_SUB(NOW(), INTERVAL 14 DAY)" + //14일 이내
            "HAVING distance <= 1\n" +
            "ORDER BY rand() LIMIT 10", nativeQuery = true) //랜덤으로 10개만
    List<Advertisement> findRandomAds(double latitude, double longitude);

    @Modifying
    @Transactional
    @Query(value = "delete from Advertisement where tid=:tid", nativeQuery = true)
    void deleteByTid(String tid);
}
