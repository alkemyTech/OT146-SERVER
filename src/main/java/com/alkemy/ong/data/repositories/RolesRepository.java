package com.alkemy.ong.data.repositories;

import com.alkemy.ong.data.entities.RolesEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends CrudRepository<RolesEntity, Long> {

}
