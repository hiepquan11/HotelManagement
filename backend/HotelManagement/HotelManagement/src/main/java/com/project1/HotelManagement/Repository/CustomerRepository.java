package com.project1.HotelManagement.Repository;

import com.project1.HotelManagement.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "customer")
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    public Customer findByEmail(String email);
    public Customer findByPhoneNumber(String PhoneNumber);
    public Customer findByIdentificationNumber(String identificationNumber);
}
