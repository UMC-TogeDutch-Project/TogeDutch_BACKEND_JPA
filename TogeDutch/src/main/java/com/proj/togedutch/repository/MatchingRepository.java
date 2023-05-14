package com.proj.togedutch.repository;

import com.proj.togedutch.domain.Matching;
import com.proj.togedutch.domain.Post;
import com.proj.togedutch.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchingRepository extends JpaRepository<Matching, Integer> {
    @Query(value = "SELECT *, \n" +
            "\t(6371*acos(cos(radians( :#{#post.latitude} ))*cos(radians(latitude))*cos(radians(longitude)-radians( :#{#post.longitude} ))+sin(radians( :#{#post.latitude} ))*sin(radians(latitude)))) AS distance\n" +
            "FROM User u\n" +
            "WHERE u.user_id != :userIdx1 and u.user_id != :userIdx2 " +
            "HAVING distance <= 0.5\n" +
            "ORDER BY distance asc limit 1", nativeQuery = true)
    User findUserLivingNearBy(@Param("post") Post post, @Param("userIdx1") int userIdx1, @Param("userIdx2") int userIdx2);

    @Query(value = "SELECT *, \n" +
            "\t(6371*acos(cos(radians( :#{#post.latitude} ))*cos(radians(latitude))*cos(radians(longitude)-radians( :#{#post.longitude} ))+sin(radians( :#{#post.latitude} ))*sin(radians(latitude)))) AS distance\n" +
            "FROM User u\n" +
            "HAVING distance <= 0.5\n" +
            "ORDER BY distance asc limit 1", nativeQuery = true)
    User findUserLivingNearByFirst(@Param("post") Post post);
    Matching findByPostIdx(int postIdx);
}
