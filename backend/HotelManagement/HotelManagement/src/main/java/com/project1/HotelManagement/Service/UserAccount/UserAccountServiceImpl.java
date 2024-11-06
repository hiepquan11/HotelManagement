package com.project1.HotelManagement.Service.UserAccount;

import com.project1.HotelManagement.Entity.Customer;
import com.project1.HotelManagement.Entity.Role;
import com.project1.HotelManagement.Entity.UserAccount;
import com.project1.HotelManagement.Repository.CustomerRepository;
import com.project1.HotelManagement.Repository.RoleRepository;
import com.project1.HotelManagement.Repository.UserAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class UserAccountServiceImpl implements UserAccountService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserAccountRepository userAccountRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserAccountServiceImpl.class);

    @Override
    public ResponseEntity<?> register(UserAccount userAccount) {
        if (userAccount.getCustomer().getCustomerName().isEmpty()){
            return ResponseEntity.badRequest().body("Customer Name is Empty");
        }
        if(userAccount.getPassword().isEmpty()){
            return ResponseEntity.badRequest().body("Customer Password is Empty");
        }
        if(userAccount.getCustomer().getEmail().isEmpty()){
            return ResponseEntity.badRequest().body("Email is not empty");
        }
        if(userAccount.getCustomer().getAddress().isEmpty()){
            return ResponseEntity.badRequest().body("Address is not empty");
        }
        if(userAccount.getCustomer().getCountryName().isEmpty()){
            return ResponseEntity.badRequest().body("Country is not empty");
        }
        if(userAccount.getCustomer().getIdentificationNumber().isEmpty()){
            return ResponseEntity.badRequest().body("Identification Number is not empty");
        }
        if(userAccount.getCustomer().getPhoneNumber().isEmpty()){
            return ResponseEntity.badRequest().body("Phone Number is not empty");
        }
        if(userAccount.getCustomer().getGender().isEmpty()){
            return ResponseEntity.badRequest().body("Gender is not empty");
        }
        Customer existingEmail = customerRepository.findByEmail(userAccount.getCustomer().getEmail());
        if(existingEmail != null){
            return ResponseEntity.badRequest().body("Email was exist");
        }
        Customer existingPhoneNumber = customerRepository.findByPhoneNumber(userAccount.getCustomer().getPhoneNumber());
        if(existingPhoneNumber != null){
            return ResponseEntity.badRequest().body("Phone number was exist");
        }
        Customer existingIdentificationNumber = customerRepository.findByIdentificationNumber(userAccount.getCustomer().getIdentificationNumber());
        if(existingIdentificationNumber != null){
            return ResponseEntity.badRequest().body("Identification number was exist");
        }
        UserAccount existingUsername = userAccountRepository.findByUserName(userAccount.getUserName());
        if(existingUsername != null){
            return ResponseEntity.badRequest().body("Username was exist");
        }
        UserAccount newUserAccount = userAccountRepository.save(userAccount);
        return ResponseEntity.ok(newUserAccount);
    }

    @Override
    public UserAccount findByUsername(String username) {
        return userAccountRepository.findByUserName(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount userAccount = userAccountRepository.findByUserName(username);
        if(userAccount == null){
            throw new UsernameNotFoundException("User not found");
        }
        System.out.println("User authorities: " + userAccount.getRole().getRoleName());
        return new User(userAccount.getUserName(), userAccount.getPassword(), getAuthorities(userAccount.getRole()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Role role) {
        return Collections.singletonList(new SimpleGrantedAuthority(role.getRoleName()));
    }
}