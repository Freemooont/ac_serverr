package com.example.repository.userRepository;

import com.example.entity.user.UserTokens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by mike on 11/2/16.
 */
public interface TokenRepository extends JpaRepository<UserTokens, Long> {

    String SELECT_TOKEN = "SELECT * FROM cl_user_tokens t where t.token = :token";

    @Query(value = SELECT_TOKEN, nativeQuery = true)
    UserTokens findToken(@Param("token") String token);

}
