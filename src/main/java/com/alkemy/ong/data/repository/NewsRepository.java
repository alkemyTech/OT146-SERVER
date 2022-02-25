package com.alkemy.ong.data.repository;

import com.alkemy.ong.data.entity.NewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<NewsEntity,Long> {

    //Optional<NewsEntity> findById(Long id);

}
