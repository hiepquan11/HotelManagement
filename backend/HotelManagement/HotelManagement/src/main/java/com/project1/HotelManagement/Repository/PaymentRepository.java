package com.project1.HotelManagement.Repository;

import com.project1.HotelManagement.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "payment")
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}
