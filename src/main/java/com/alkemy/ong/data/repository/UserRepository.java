package com.alkemy.ong.data.repository;

import com.alkemy.ong.data.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query(nativeQuery = true,
    value = "SELECT * FROM users u WHERE u.deleted = 0")
    List<UserEntity> showActives();

    @Query(nativeQuery = true,
            value = "SELECT * FROM users u WHERE u.deleted = 1")
    List<UserEntity> showDeleted();


}
