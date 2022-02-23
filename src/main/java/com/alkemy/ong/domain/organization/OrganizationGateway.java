package com.alkemy.ong.domain.organization;



import java.util.List;


public interface OrganizationGateway {
  List<Organization> findAll();
  Organization findById(long idOrganization);
  Organization save(Organization organization);
  void deleteById(long idOrganization);
}
