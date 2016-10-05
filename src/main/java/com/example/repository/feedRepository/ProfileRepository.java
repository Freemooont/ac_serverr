package com.example.repository.feedRepository;

import com.example.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import static com.example.repository.feedRepository.ProfileRepository.FB_ID;
import static com.example.repository.feedRepository.ProfileRepository.SELECT_USER;


public interface ProfileRepository extends JpaRepository<Profile,Long> {
    String FB_ID = "SELECT fb_id FROM cl_user limit 1";
    String SELECT_USER = "SELECT * FROM cl_user where fb_id = :fb_id";

    @Query(value = FB_ID,nativeQuery = true)
    Long verifyFbId();

    @Query(value = SELECT_USER, nativeQuery = true)
    Profile slectUser(@Param("fb_id") Long fb_id);
}
