package com.alkemy.ong.data.repository;

import com.alkemy.ong.data.entity.CommentaryEntity;
import com.alkemy.ong.domain.comments.Commentary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentaryRepository extends JpaRepository<CommentaryEntity, Long> {
    @Query(value = "SELECT * FROM commentaries WHERE news_id=?1;", nativeQuery = true)
    List<Commentary> findByNewsId(Long newsId);
}
