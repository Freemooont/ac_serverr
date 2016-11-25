package com.example.repository.feedRepository;

import com.example.entity.feeds.Notify;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import static com.example.repository.feedRepository.NotifyRepository.SELECT_BY_USER_ID;

/**
 * Created by mike on 11/9/16.
 */
public interface NotifyRepository extends JpaRepository<Notify, Long> {

    String SELECT_BY_USER_ID = "SELECT * FROM cl_notify n WHERE n.user_id = :user_id";
    String FIND_BY_ALL = "SELECT count(n.id) FROM cl_notify n WHERE n.user_id = :user_id AND n.feed_id = :feed_id AND n.feed_type = :feed_type";

    @Query(value = SELECT_BY_USER_ID,nativeQuery = true)
    List<Notify> getAllNotifies(@Param("user_id")Long user_id);

    @Query(value = FIND_BY_ALL,nativeQuery = true)
    Integer verifyIfExist(@Param("user_id")Long user_id, @Param("feed_id")Long feed_id, @Param("feed_type")Integer feed_type);
}
