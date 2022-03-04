package com.alkemy.ong.data.repository;

import com.alkemy.ong.data.entity.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    Page<MemberEntity> findByDeleted(boolean deleted, Pageable pageable);
}
