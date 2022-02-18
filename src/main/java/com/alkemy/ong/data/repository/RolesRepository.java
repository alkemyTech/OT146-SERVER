package com.alkemy.ong.data.repository;

import com.alkemy.ong.data.entity.RolesEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends CrudRepository<RolesEntity, Long> {
}
