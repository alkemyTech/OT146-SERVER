package com.alkemy.ong.data.OrganizationRepo;

import com.alkemy.ong.data.OrganizationEntity.OrganizationEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IOrganizationRepo extends CrudRepository<OrganizationEntity, Long> {
    public List<OrganizationEntity> findByNameStarsWith(String name);
    public List<OrganizationEntity> findByAddress(String address);
    public List<OrganizationEntity> findByImage(String image);
    public List<OrganizationEntity> findByPhone(String phone);
}
