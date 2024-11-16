package com.project1.HotelManagement.Repository;

import com.project1.HotelManagement.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "customer")
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Customer findCustomerByCustomerId(int customerId);
    Customer findByEmail(String email);
    Customer findByPhoneNumber(String PhoneNumber);
    Customer findByIdentificationNumber(String identificationNumber);
}
