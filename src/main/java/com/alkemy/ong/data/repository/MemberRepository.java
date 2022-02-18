package com.alkemy.ong.data.repository;

import com.alkemy.ong.data.entity.MemberEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends CrudRepository<MemberEntity, Long> {
}
