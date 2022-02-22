package com.alkemy.ong.domain.organization;


import com.alkemy.ong.data.entity.OrganizationEntity;

import java.util.List;

public interface OrganizationGateway {
  public Organization create(Organization organization);
  List<OrganizationEntity> findAll();
  OrganizationEntity findById(long idOrganization);
  OrganizationEntity save(OrganizationEntity organization);
  void deleteById(long idOrganization);
}
