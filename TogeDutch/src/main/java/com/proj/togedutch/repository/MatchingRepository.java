package com.proj.togedutch.repository;

import com.proj.togedutch.domain.Matching;
import com.proj.togedutch.domain.Post;
import com.proj.togedutch.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public interface MatchingRepository extends JpaRepository<Matching, Integer> {
    interface MatchingUser {
        int getUser_id();
        String getDistance();
        Integer getKeyword_keyword_id();
        String getName();
        String getRole();
        String getEmail();
        String getPassword();
        String getPhone();
        String getImage();
        String getStatus();
        Timestamp getCreated_at();
        Timestamp getUpdated_at();
        Double getLatitude();
        Double getLongitude();
    }

    @Query(value = "SELECT *, (6371*acos(cos(radians( :#{#post.latitude} ))*cos(radians(latitude))*cos(radians(longitude)-radians( :#{#post.longitude} ))+sin(radians( :#{#post.latitude} ))*sin(radians(latitude)))) as distance\n" +
            "FROM User u \n" +
            "WHERE u.user_id != :userIdx1 and u.user_id != :userIdx2 \n" +
            "HAVING  distance <= 0.5 \n" +
            "ORDER BY distance asc limit 1", nativeQuery = true)
    MatchingUser findUserLivingNearBy(@Param("post") Post post, @Param("userIdx1") int userIdx1, @Param("userIdx2") int userIdx2);


    Matching findByPostIdx(int postIdx);

    @Query(value = "SELECT *, (6371*acos(cos(radians( :lat ))*cos(radians(latitude))*cos(radians(longitude)-radians( :lon ))+sin(radians( :lat ))*sin(radians(latitude)))) as distance\n" +
            "FROM User u \n" +
            "HAVING  distance <= 0.5 \n" +
            "ORDER BY distance asc limit 1", nativeQuery = true)
    MatchingUser findUserLivingNearByFirst(@Param("lat") Double lat, @Param("lon") Double lon);
}
