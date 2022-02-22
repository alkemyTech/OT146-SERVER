package com.alkemy.ong.web.controller;

import com.alkemy.ong.data.entity.OrganizationEntity;
import com.alkemy.ong.domain.organization.Organization;
import com.alkemy.ong.domain.organization.OrganizationGateway;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/organization")
public class OrganizationController {

    @Autowired
    private OrganizationGateway organizationGateway;

    @GetMapping("/public/{id}")
    public String showOrganization(@PathVariable long id) {
        OrganizationEntity organization = organizationGateway.findById(id);
            if (organization == null) {
                return "The organization doesn't exist in the database";
            }
        return organization.toString();
    }

    //   @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/public")
    public ResponseEntity<?> create(@Valid @RequestBody OrganizationEntity organization, BindingResult result) {
        OrganizationEntity newOrganization = null;
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try {

            newOrganization = organizationGateway.save(organization);

        } catch (DataAccessException dae) {
            response.put("message", "Failure to save organization in database");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "Organization saved with success");
        response.put("organization", newOrganization);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

 //   @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/public/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody OrganizationEntity organization, BindingResult result,
                                    @PathVariable long id){

        OrganizationEntity ong = organizationGateway.findById(id);
        OrganizationEntity updatedOng = null;

        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        if (ong == null){
            response.put("message", "The organization doesn't exist in the database");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            ong.setName(organization.getName());
            ong.setImage(organization.getImage());
            ong.setAddress(organization.getAddress());
            ong.setPhone(organization.getPhone());
            ong.setEmail(organization.getEmail());
            ong.setAboutUsText(organization.getAboutUsText());
            ong.setWelcomeText(organization.getWelcomeText());
            ong.setCreatedAt(organization.getCreatedAt());
            ong.setUpdatedAt(organization.getUpdatedAt());
            ong.setDeleted(organization.getDeleted());

            updatedOng = organizationGateway.save(ong);

        }catch (DataAccessException dae){
            response.put("message", "Failure to update organization in database");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "Organization saved with success");
        response.put("organization", updatedOng);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    private Organization toDomain(OrganizationController.OrganizationDto dto){
        return Organization.builder()
                .idOrganization(dto.getIdOrganization())
                .name(dto.getName())
                .image(dto.getImage())
                .address(dto.getAddress())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .build();
    }

    private OrganizationController.OrganizationDto toDto(Organization organization){
        return OrganizationDto.builder()
                .idOrganization(organization.getIdOrganization())
                .name(organization.getName())
                .image(organization.getImage())
                .address(organization.getAddress())
                .phone(organization.getPhone())
                .email(organization.getEmail())
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
    }
}