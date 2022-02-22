package com.alkemy.ong.domain.organization;

import org.springframework.stereotype.Service;

@Service
public class OrganizationService {
    private OrganizationGateway organizationGateway;

    public OrganizationService(OrganizationGateway organizationGateway) {

        this.organizationGateway = organizationGateway;
    }

    public Organization save(Organization organization){

        return organizationGateway.create(organization);
    }
}
