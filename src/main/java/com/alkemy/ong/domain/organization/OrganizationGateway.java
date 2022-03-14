package com.alkemy.ong.domain.organization;





public interface OrganizationGateway {
  Organization findById(long idOrganization);
  Organization update(Organization organization);
}
