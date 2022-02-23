package com.alkemy.ong.data.gateways;

import com.alkemy.ong.data.entity.OrganizationEntity;
import com.alkemy.ong.data.repository.OrganizationRepository;
import com.alkemy.ong.domain.organization.Organization;
import com.alkemy.ong.domain.organization.OrganizationGateway;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DefaultOrganizationGateway implements OrganizationGateway {

    private final OrganizationRepository organizationRepository;

    public DefaultOrganizationGateway(OrganizationRepository organizationRepository) {

        this.organizationRepository = organizationRepository;
    }

    @Override
    public List<Organization> findAll() {
        List<OrganizationEntity> ong = organizationRepository.findAll();
        return ong.stream().map(o -> toModel(o)).collect(Collectors.toList());
    }

    @Override
    public Organization findById(long idOrganization) {
        OrganizationEntity ong = organizationRepository.findById(idOrganization);
        return toModel(ong);
    }

    @Override
    public Organization save(Organization organization) {
        OrganizationEntity entity = toEntity(organization);
        return toModel(organizationRepository.save(entity));
    }

    @Override
    public void deleteById(long idOrganization) {

    }

    private Organization toModel(OrganizationEntity organizationEntity){
        return Organization.builder()
                .idOrganization(organizationEntity.getIdOrganization())
                .name(organizationEntity.getName())
                .image(organizationEntity.getImage())
                .address(organizationEntity.getAddress())
                .phone(organizationEntity.getPhone())
                .email(organizationEntity.getEmail())
                .about_us_text(organizationEntity.getAbout_us_text())
                .welcome_text(organizationEntity.getWelcome_text())
                .createdAt(organizationEntity.getCreatedAt())
                .updatedAt(organizationEntity.getUpdatedAt())
                .deleted(organizationEntity.getDeleted())
                .build();
    }

    private OrganizationEntity toEntity(Organization organization) {
        return OrganizationEntity.builder()
                .name(organization.getName())
                .image(organization.getImage())
                .address(organization.getAddress())
                .phone(organization.getPhone())
                .email(organization.getEmail())
                .about_us_text(organization.getAbout_us_text())
                .welcome_text(organization.getWelcome_text())
                .createdAt(organization.getCreatedAt())
                .updatedAt(organization.getUpdatedAt())
                .deleted(organization.getDeleted())
                .build();
    }

}
