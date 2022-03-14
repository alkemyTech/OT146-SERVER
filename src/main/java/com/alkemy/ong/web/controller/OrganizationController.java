package com.alkemy.ong.web.controller;

import com.alkemy.ong.domain.organization.Organization;
import com.alkemy.ong.domain.organization.OrganizationService;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@CrossOrigin
@RestController
@RequestMapping("/organization")
public class OrganizationController {

    private OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService){
        this.organizationService = organizationService;
    }

    @PreAuthorize("hasRole('USER') OR hasRole('ADMIN')")
    @GetMapping("/public/{id}")
    public OrganizationSimpleDto showOrganization(@PathVariable long id) {
        Organization organization = organizationService.findById(id);
        return toSimpleDto(organization);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/public/{id}")
    public ResponseEntity<OrganizationDto> update(@Valid @RequestBody OrganizationDto organizationDto, @PathVariable long id){
        organizationDto.setIdOrganization(id);
        OrganizationDto ong = toDto(organizationService.findById(id));
        organizationDto.setCreatedAt(ong.getCreatedAt());
        return new ResponseEntity<OrganizationDto>(toDto(organizationService.update(toDomain(organizationDto))), HttpStatus.CREATED);
    }

    public static Organization toDomain(OrganizationDto dto){
        return Organization.builder()
                .idOrganization(dto.getIdOrganization())
                .name(dto.getName())
                .image(dto.getImage())
                .address(dto.getAddress())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .about_us_text(dto.getAbout_us_text())
                .welcome_text(dto.getWelcome_text())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .deleted(dto.getDeleted())
                .facebookLink(dto.getFacebookLink())
                .instagramLink(dto.getInstagramLink())
                .linkedinLink((dto.getLinkedinLink()))
                .build();
    }

    public static OrganizationDto toDto(Organization organization){
        return OrganizationDto.builder()
                .idOrganization(organization.getIdOrganization())
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

    private OrganizationSimpleDto toSimpleDto(Organization organization){
        return OrganizationSimpleDto.builder()
                .name(organization.getName())
                .image(organization.getImage())
                .address(organization.getAddress())
                .phone(organization.getPhone())
                .facebookLink(organization.getFacebookLink())
                .instagramLink(organization.getInstagramLink())
                .linkedinLink(organization.getLinkedinLink())
                .slides(organization.getSlides()
                        .stream()
                        .map(slide -> SlidesController.toSimpleDto(slide))
                        .collect(toList()))
                .build();
    }

    @Builder
    @Data
    @AllArgsConstructor
    public static class OrganizationDto {
        @Id
        @GeneratedValue(strategy= GenerationType.IDENTITY)
        private long idOrganization;

        @NotBlank(message="The name can´t be empty")
        @Size(min = 3, max = 45, message = "Name length must be between 3 and 45 characters")
        private String name;

        @NotBlank(message="The image can´t be empty")
        @Size(min = 10, max = 256, message = "Image length must be between 10 and 256 characters")
        private String image;

        @Size(max = 45, message = "The maximum image length should be 45 characters")
        private String address;

        private Integer phone;

        @NotBlank(message="The email can´t be empty")
        @Size(min = 10, max = 45, message = "Email length must be between 10 and 45 characters")
        @Email
        private String email;

        private String about_us_text;

        @NotBlank(message="The welcome text field can´t be empty")
        @Size(min = 10, max = 65535 , message = "The welcome text length must be between 10 and 65.535  characters")
        private String welcome_text;

        @DateTimeFormat(pattern = "yyyy-mm-dd")
        @CreationTimestamp
        private LocalDateTime createdAt;

        @DateTimeFormat(pattern = "yyyy-mm-dd")
        @UpdateTimestamp
        private LocalDateTime updatedAt;

        private Boolean deleted;

        private String facebookLink;

        private String instagramLink;

        private String linkedinLink;

        private List<SlidesController.SimpleSlideDto> slides;

    }

    @Builder
    @Data
    @AllArgsConstructor
    public static class OrganizationSimpleDto {

        @NotBlank(message="The name can´t be empty")
        @Size(min = 3, max = 45, message = "Name length must be between 3 and 45 characters")
        private String name;

        @NotBlank(message="The image can´t be empty")
        @Size(min = 10, max = 256, message = "Image length must be between 10 and 256 characters")
        private String image;

        @Size(max = 45, message = "The maximum image length should be 45 characters")
        private String address;

        private Integer phone;

        private String facebookLink;

        private String instagramLink;

        private String linkedinLink;

        private List<SlidesController.SimpleSlideDto> slides;
    }
}