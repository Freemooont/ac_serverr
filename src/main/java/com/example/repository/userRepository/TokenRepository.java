package com.example.repository.userRepository;

import com.example.entity.user.UserTokens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by mike on 11/2/16.
 */
public interface TokenRepository extends JpaRepository<UserTokens, Long> {

    String SELECT_TOKEN = "SELECT * FROM cl_user_tokens t where t.token = :token";
    String SELECT_USER_TOKENS = "SELECT t.token FROM cl_user_tokens t where t.user_id = :user_id";

    @Query(value = SELECT_TOKEN, nativeQuery = true)
    UserTokens findToken(@Param("token") String token);

    @Query(value = SELECT_USER_TOKENS, nativeQuery = true)
    List<String> getUsersTokens(@Param("user_id") long user_id);

}
