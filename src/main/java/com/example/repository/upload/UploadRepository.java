package com.example.repository.upload;

import com.example.entity.upload.Upload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import static com.example.repository.upload.UploadRepository.PHOTO_PATH_QUERY;

public interface UploadRepository extends JpaRepository<Upload, Long> {
    String PHOTO_PATH_QUERY = "SELECT u.path_media FROM cl_temp_media u WHERE u.id =:id";
    String PHOTO_MEDIA_TYPE_QUERY = "SELECT u.media_type FROM cl_temp_media u WHERE u.id =:id";

    @Query(value = PHOTO_PATH_QUERY, nativeQuery = true)
    String getMediaPath(@Param("id") Long id);

    @Query(value = PHOTO_MEDIA_TYPE_QUERY, nativeQuery = true)
    Integer getMediaType(@Param("id") Long id);
}
