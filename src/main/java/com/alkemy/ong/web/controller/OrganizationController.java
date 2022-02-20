package com.alkemy.ong.web.controller;

import com.alkemy.ong.data.entity.OrganizationEntity;
import com.alkemy.ong.domain.gateway.OrganizationGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
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
        try {
            OrganizationEntity organization = organizationGateway.findById(id);
            if (organization == null) {
                return "The organization doesn't exist in the database";
            }
            return organization.toString();
        } catch (ConstraintViolationException cve) {
            return cve.getMessage();
        }
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
            ong.setAbout(organization.getAbout());
            ong.setWelcome(organization.getWelcome());
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


}