package com.alkemy.ong.data.gateway;

import com.alkemy.ong.data.entity.OrganizationEntity;
import com.alkemy.ong.data.repository.OrganizationRepository;
import com.alkemy.ong.domain.gateway.OrganizationGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrganizationGatewayImplements implements OrganizationGateway {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Override
    @Transactional
    public List<OrganizationEntity> findAll() {
        return (List<OrganizationEntity>) organizationRepository.findAll();
    }

    @Override
    @Transactional
    public OrganizationEntity findById(long idOrganization) {
        return organizationRepository.findById(idOrganization).orElse(null);
    }

    @Override
    @Transactional
    public OrganizationEntity save(OrganizationEntity organization) {
        return organizationRepository.save(organization);
    }

    @Override
    @Transactional
    public void deleteById(long idOrganization) {
        organizationRepository.deleteById(idOrganization);
    }
}
