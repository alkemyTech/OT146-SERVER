package com.alkemy.ong.web.controller;

import com.alkemy.ong.domain.organization.Organization;
import com.alkemy.ong.domain.organization.OrganizationService;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

@CrossOrigin
@RestController
@RequestMapping("/organization")
public class OrganizationController {

    private OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService){
        this.organizationService = organizationService;
    }

    @GetMapping("/public/{id}")
    public OrganizationSimpleDto showOrganization(@PathVariable long id) {
        Organization organization = organizationService.findById(id);
        return toSimpleDto(organization);
    }

    //   @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/public/{id}")
    public ResponseEntity<OrganizationDto> update(@Valid @RequestBody OrganizationDto organizationDto, @PathVariable long id){

        return new ResponseEntity<OrganizationDto>(toDto(organizationService.update(toDomain(organizationDto))), HttpStatus.CREATED);
    }

    public static Organization toDomain(OrganizationController.OrganizationDto dto){
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
                .build();
    }

    public static OrganizationController.OrganizationDto toDto(Organization organization){
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
                .build();
    }

    private OrganizationController.OrganizationSimpleDto toSimpleDto(Organization organization){
        return OrganizationSimpleDto.builder()
                .name(organization.getName())
                .image(organization.getImage())
                .address(organization.getAddress())
                .phone(organization.getPhone())
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
        @Column(nullable = false)
        private String name;

        @NotBlank(message="The image can´t be empty")
        @Size(min = 10, max = 256, message = "Image length must be between 10 and 256 characters")
        @Column(nullable = false)
        private String image;

        @Size(max = 45, message = "The maximum image length should be 45 characters")
        @Column
        private String address;

        //@DecimalMax(9999999999999)
        @Column
        private Integer phone;

        @NotBlank(message="The email can´t be empty")
        @Size(min = 10, max = 45, message = "Email length must be between 10 and 45 characters")
        @Email
        @Column(nullable = false)
        private String email;

        @Column
        private String about_us_text;

        @Column(nullable = false)
        private String welcome_text;

        @DateTimeFormat(pattern = "yyyy-mm-dd")
        @Column(nullable = false)
        private LocalDateTime createdAt;

        @DateTimeFormat(pattern = "yyyy-mm-dd")
        @Column(nullable = false)
        private LocalDateTime updatedAt;


        @Column(nullable = false)
        private Boolean deleted;
    }

    @Builder
    @Data
    @AllArgsConstructor
    public static class OrganizationSimpleDto {
        @Id
        @GeneratedValue(strategy= GenerationType.IDENTITY)
        private long idOrganization;

        @NotBlank(message="The name can´t be empty")
        @Size(min = 3, max = 45, message = "Name length must be between 3 and 45 characters")
        @Column(nullable = false)
        private String name;

        @NotBlank(message="The image can´t be empty")
        @Size(min = 10, max = 256, message = "Image length must be between 10 and 256 characters")
        @Column(nullable = false)
        private String image;

        @Size(max = 45, message = "The maximum image length should be 45 characters")
        @Column
        private String address;

        //@DecimalMax(9999999999999)
        @Column
        private Integer phone;

        @NotBlank(message="The email can´t be empty")
        @Size(min = 10, max = 45, message = "Email length must be between 10 and 45 characters")
        @Email
        @Column(nullable = false)
        private String email;
    }
}