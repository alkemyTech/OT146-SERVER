package com.alkemy.ong.data.gateways;

import com.alkemy.ong.data.entity.OrganizationEntity;
import com.alkemy.ong.data.repository.OrganizationRepository;
import com.alkemy.ong.domain.organization.Organization;
import com.alkemy.ong.domain.organization.OrganizationGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DefaultOrganizationGateway implements OrganizationGateway {

    @Autowired
    private OrganizationRepository organizationRepository;

    public DefaultOrganizationGateway(OrganizationRepository organizationRepository) {

        this.organizationRepository = organizationRepository;
    }

    @Override
    public Organization create(Organization organization) {
        OrganizationEntity entity = toEntity(organization);
        return toModel(organizationRepository.save(entity));
    }

    public Organization toModel(OrganizationEntity organizationEntity){
        return Organization.builder()
                .idOrganization(organizationEntity.getIdOrganization())
                .name(organizationEntity.getName())
                .image(organizationEntity.getImage())
                .address(organizationEntity.getAddress())
                .phone(organizationEntity.getPhone())
                .email(organizationEntity.getEmail())
                .build();
    }

    public OrganizationEntity toEntity(Organization organization) {
        return OrganizationEntity.builder()
                .name(organization.getName())
                .image(organization.getImage())
                .address(organization.getAddress())
                .phone(organization.getPhone())
                .email(organization.getEmail())
                .build();
    }


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
