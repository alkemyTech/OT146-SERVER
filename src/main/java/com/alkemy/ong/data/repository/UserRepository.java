package com.alkemy.ong.data.repository;

import com.alkemy.ong.data.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    List<UserEntity> findByDeleted(boolean isDeleted);

    Optional<UserEntity> findByEmail(String email);
}
