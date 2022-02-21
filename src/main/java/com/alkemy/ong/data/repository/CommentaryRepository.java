package com.alkemy.ong.data.repository;

import com.alkemy.ong.data.entity.CommentaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentaryRepository extends JpaRepository<CommentaryEntity, Long> {

    @Query(nativeQuery = true,
            value = "SELECT * FROM commentaries c ORDER BY c.created_at DESC")
    List<CommentaryEntity> findAll();

}