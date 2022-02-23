package com.alkemy.ong.web.controller;

import com.alkemy.ong.domain.organization.Organization;
import com.alkemy.ong.domain.organization.OrganizationGateway;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private OrganizationGateway organizationGateway;

    @GetMapping("/public/{id}")
    public String showOrganization(@PathVariable long id) {
        Organization organization = organizationGateway.findById(id);
        return organization.toString();
    }

    //   @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/public")
    public ResponseEntity<OrganizationDto> create(@Valid @RequestBody OrganizationDto organizationDto){
        Organization newOng = null;
        Organization ong = toDomain(organizationDto);

        newOng = organizationGateway.save(ong);
        return new ResponseEntity<OrganizationDto>(toDto(ong), HttpStatus.CREATED);
    }

 //   @PreAuthorize("hasRole('ADMIN')")
      @PutMapping("/public/{id}")
      public ResponseEntity<OrganizationDto> update(@Valid @RequestBody OrganizationDto organizationDto, @PathVariable long id) {
          Organization updateOng = toDomain(organizationDto);
          Organization ong = organizationGateway.findById(id);
          Organization org = null;

          ong.setName(updateOng.getName());
          ong.setImage(updateOng.getImage());
          ong.setAddress(updateOng.getAddress());
          ong.setPhone(updateOng.getPhone());
          ong.setEmail(updateOng.getEmail());
          ong.setCreatedAt(updateOng.getCreatedAt());
          ong.setUpdatedAt(updateOng.getUpdatedAt());
          ong.setAbout_us_text(updateOng.getAbout_us_text());
          ong.setWelcome_text(updateOng.getWelcome_text());
          ong.setCreatedAt(updateOng.getCreatedAt());
          ong.setUpdatedAt(updateOng.getUpdatedAt());
          ong.setDeleted(updateOng.getDeleted());

          org = organizationGateway.save(ong);
          return new ResponseEntity<OrganizationDto>(toDto(org), HttpStatus.CREATED);
      }

    /*@PutMapping("/public/{id}")
    public ResponseEntity<OrganizationDto> update(@Valid @RequestBody OrganizationDto organization, BindingResult result,
                                    @PathVariable long id){

        Organization ong = organizationGateway.findById(id);
        Organization updatedOng = null;

        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<OrganizationDto>((OrganizationDto) response, HttpStatus.BAD_REQUEST);
        }

        if (ong == null){
            response.put("message", "The organization doesn't exist in the database");
            return new ResponseEntity<OrganizationDto>((OrganizationDto) response, HttpStatus.BAD_REQUEST);
        }

        try {

            //ong.setAboutUsText(organization.getAboutUsText());
            //ong.setWelcomeText(organization.getWelcomeText());
            //ong.setCreatedAt(organization.getCreatedAt());
            //ong.setUpdatedAt(organization.getUpdatedAt());
            //ong.setDeleted(organization.getDeleted());

           updatedOng = organizationGateway.save(ong);

        }catch (DataAccessException dae){
            response.put("message", "Failure to update organization in database");
            return new ResponseEntity<OrganizationDto>((OrganizationDto) response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "Organization saved with success");
        response.put("organization", toDomain(updatedOng));
        Organization org = toDomain(updatedOng);
        response.put("organization", org);
        ResponseEntity<HashMap<String, Object>(response, HttpStatus.CREATED);
        return new ResponseEntity<HashMap<String, Organization>>(response, HttpStatus.CREATED);
    }
*/
    private Organization toDomain(OrganizationController.OrganizationDto dto){
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

    private OrganizationController.OrganizationDto toDto(Organization organization){
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
}