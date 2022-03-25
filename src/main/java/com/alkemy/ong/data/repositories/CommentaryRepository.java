package com.alkemy.ong.data.repositories;

import com.alkemy.ong.data.entities.CommentaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentaryRepository extends JpaRepository<CommentaryEntity, Long> {
    List<CommentaryEntity> findByNewsEntityId(Long newsId);
}
