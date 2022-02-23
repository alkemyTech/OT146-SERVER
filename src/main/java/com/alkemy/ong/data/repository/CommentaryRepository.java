package com.alkemy.ong.data.repository;

import com.alkemy.ong.data.entity.CommentaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentaryRepository extends JpaRepository<CommentaryEntity, Long> {
}