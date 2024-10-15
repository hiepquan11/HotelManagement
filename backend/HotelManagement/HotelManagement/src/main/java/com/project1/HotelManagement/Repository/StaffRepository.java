package com.project1.HotelManagement.Repository;

import com.project1.HotelManagement.Entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "staff")
public interface StaffRepository extends JpaRepository<Staff, Integer> {
}
