package com.example.repository.userRepository;

import com.example.entity.user.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ProfileRepository extends JpaRepository<Profile,Long> {
    String FB_ID = "SELECT fb_id FROM cl_user limit 1";
    String SELECT_USER = "SELECT * FROM cl_user where fb_id = :fb_id";
    String SELECT_USERS = "SELECT * FROM cl_user u  WHERE concat(' ', u.first_name, u.last_name) LIKE :str limit :countt offset :indexx";
    @Query(value = FB_ID,nativeQuery = true)
    Long verifyFbId();

    @Query(value = SELECT_USER, nativeQuery = true)
    Profile slectUser(@Param("fb_id") Long fb_id);

    @Query(value = SELECT_USERS, nativeQuery = true)
    List<Profile> selectUsers(@Param("str") String str, @Param("countt") int count, @Param("indexx") int index);

}
