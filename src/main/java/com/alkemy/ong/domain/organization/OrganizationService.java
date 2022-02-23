package com.alkemy.ong.domain.organization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationService {

    @Autowired
    private final OrganizationGateway organizationGateway;

    public OrganizationService(OrganizationGateway organizationGateway) {

        this.organizationGateway = organizationGateway;
    }

    public List<Organization> findAll() {

        return organizationGateway.findAll();
    }
}
