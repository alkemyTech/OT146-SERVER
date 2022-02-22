package com.alkemy.ong.data.gateway;

import com.alkemy.ong.data.entity.OrganizationEntity;
import com.alkemy.ong.data.repository.OrganizationRepository;
import com.alkemy.ong.domain.gateway.OrganizationGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DefaultOrganizationGateway implements OrganizationGateway {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Override
    public List<OrganizationEntity> findAll() {
        return (List<OrganizationEntity>) organizationRepository.findAll();
    }

    @Override
    public OrganizationEntity findById(long idOrganization) {
        return organizationRepository.findById(idOrganization).orElseThrow();
    }

    @Override
    public OrganizationEntity save(OrganizationEntity organization) {
        return organizationRepository.save(organization);
    }

    @Override
    public void deleteById(long idOrganization) {
        organizationRepository.deleteById(idOrganization);
    }
}
