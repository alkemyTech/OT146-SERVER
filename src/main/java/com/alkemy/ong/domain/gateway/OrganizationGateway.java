package com.alkemy.ong.domain.gateway;


import com.alkemy.ong.data.entity.OrganizationEntity;

import java.util.List;

public interface OrganizationGateway {
    public List<OrganizationEntity> findAll();
    public OrganizationEntity findById(long idOrganization);
    public OrganizationEntity save(OrganizationEntity organization);
    public void deleteById(long idOrganization);
}
