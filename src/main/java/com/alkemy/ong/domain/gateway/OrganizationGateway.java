package com.alkemy.ong.domain.gateway;


import com.alkemy.ong.data.entity.OrganizationEntity;

import java.util.List;

public interface OrganizationGateway {
  List<OrganizationEntity> findAll();
  OrganizationEntity findById(long idOrganization);
  OrganizationEntity save(OrganizationEntity organization);
  void deleteById(long idOrganization);
}
