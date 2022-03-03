package com.alkemy.ong.data.gateways;

import com.alkemy.ong.data.entity.OrganizationEntity;
import com.alkemy.ong.data.repository.OrganizationRepository;
import com.alkemy.ong.domain.organization.Organization;
import com.alkemy.ong.domain.organization.OrganizationGateway;
import com.alkemy.ong.web.controller.OrganizationController;
import com.alkemy.ong.web.exceptions.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DefaultOrganizationGateway implements OrganizationGateway {

    private final OrganizationRepository organizationRepository;

    public DefaultOrganizationGateway(OrganizationRepository organizationRepository) {

        this.organizationRepository = organizationRepository;
    }

    @Override
    public Organization findById(long idOrganization) {
        OrganizationEntity ong = organizationRepository.findById(idOrganization).orElseThrow(() -> new BadRequestException("mensaje"));
        return toModel(ong);
    }

    @GetMapping("/public/{id}")
    public Organization showOrganization(@PathVariable long id) {
        OrganizationEntity organization = organizationRepository.findById(id).orElseThrow(() -> new BadRequestException("mensaje"));
        return toModel(organization);
    }

    @PutMapping("/public/{id}")
    public Organization update(Organization organization) {
        OrganizationEntity ongEntity = organizationRepository.findById(organization.getIdOrganization()).orElseThrow(() -> new BadRequestException("mensaje"));
        return toModel(organizationRepository.save(newUpdate(ongEntity, organization)));
    }

    private OrganizationEntity newUpdate(OrganizationEntity organizationEntity, Organization organization) {
        organizationEntity.setName(organization.getName());
        organizationEntity.setImage(organization.getImage());
        organizationEntity.setAddress(organization.getAddress());
        organizationEntity.setPhone(organization.getPhone());
        organizationEntity.setEmail(organization.getEmail());
        organizationEntity.setAbout_us_text(organization.getAbout_us_text());
        organizationEntity.setWelcome_text(organization.getWelcome_text());
        organizationEntity.setCreatedAt(organization.getCreatedAt());
        organizationEntity.setUpdatedAt(organization.getUpdatedAt());
        organizationEntity.setDeleted(organization.getDeleted());
        return organizationEntity;
    }

    private Organization toModel(OrganizationEntity organizationEntity) {
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
                .facebookLink(organizationEntity.getFacebookLink())
                .instagramLink(organizationEntity.getInstagramLink())
                .linkedinLink(organizationEntity.getLinkedinLink())
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
                .facebookLink(organization.getFacebookLink())
                .instagramLink(organization.getInstagramLink())
                .linkedinLink(organization.getLinkedinLink())
                .build();
    }
}
