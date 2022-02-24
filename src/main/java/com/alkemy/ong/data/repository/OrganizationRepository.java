package com.alkemy.ong.data.repository;

import com.alkemy.ong.data.entity.OrganizationEntity;
import com.alkemy.ong.domain.organization.Organization;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface OrganizationRepository extends CrudRepository<OrganizationEntity, Long> {
    List<OrganizationEntity> findAll();
    Optional<OrganizationEntity> findById(long idOrganization);
    OrganizationEntity save(Organization organization);
}