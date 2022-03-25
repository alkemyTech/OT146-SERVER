package com.alkemy.ong.data.repositories;

import com.alkemy.ong.data.entities.OrganizationEntity;
import com.alkemy.ong.domain.organizations.Organization;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface OrganizationRepository extends CrudRepository<OrganizationEntity, Long> {
    List<OrganizationEntity> findAll();
    Optional<OrganizationEntity> findById(long idOrganization);
    OrganizationEntity save(Organization organization);
}