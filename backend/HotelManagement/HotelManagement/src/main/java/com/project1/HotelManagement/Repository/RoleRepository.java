package com.project1.HotelManagement.Repository;

import com.project1.HotelManagement.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "role")
public interface RoleRepository extends JpaRepository<Role, Integer> {
    public Role findByRoleName(String roleName);
}
