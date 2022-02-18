package com.alkemy.ong.data.repository;

import com.alkemy.ong.data.entity.OrganizationEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrganizationRepository extends CrudRepository<OrganizationEntity, Long> {
}