package com.project1.HotelManagement.Repository;

import com.project1.HotelManagement.Entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "useraccount")
public interface UserAccountRepository extends JpaRepository<UserAccount, Integer> {
    public UserAccount findByUserName(String userName);
}
