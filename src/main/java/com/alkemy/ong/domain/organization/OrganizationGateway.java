package com.alkemy.ong.domain.organization;



import java.util.List;


public interface OrganizationGateway {
  Organization findById(long idOrganization);
  Organization update(Organization organization);
}
