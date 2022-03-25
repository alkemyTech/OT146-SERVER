package com.alkemy.ong.domain.organizations;

import org.springframework.stereotype.Service;

@Service
public class OrganizationService {

    private final OrganizationGateway organizationGateway;

    public OrganizationService(OrganizationGateway organizationGateway) {

        this.organizationGateway = organizationGateway;
    }

    public Organization findById(long idOrganization){
        return organizationGateway.findById(idOrganization);
    }

    public Organization update(Organization organization){
        return organizationGateway.update(organization);
    }
}
