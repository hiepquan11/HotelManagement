package com.project1.HotelManagement.Service.UserAccount;

import com.project1.HotelManagement.Entity.*;
import com.project1.HotelManagement.Repository.CustomerRepository;
import com.project1.HotelManagement.Repository.RoleRepository;
import com.project1.HotelManagement.Repository.UserAccountRepository;
import com.project1.HotelManagement.Security.JwtResponse;
import com.project1.HotelManagement.Security.LoginRequest;
import com.project1.HotelManagement.Service.JWT.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class UserAccountServiceImpl implements UserAccountService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    @Lazy
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public ResponseEntity<?> register(UserAccount userAccount) {
        if (userAccount.getCustomer().getCustomerName().isEmpty()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new Response("Customer Name is Empty", HttpStatus.CONFLICT.value()));
        }
        if(userAccount.getUserName().isEmpty()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new Response("User Name is Empty", HttpStatus.CONFLICT.value()));
        }
        if(userAccount.getPassword().isEmpty()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new Response("Customer Password is Empty", HttpStatus.CONFLICT.value()));
        }
        if(userAccount.getCustomer().getEmail().isEmpty()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new Response("Email is not empty", HttpStatus.CONFLICT.value()));
        }
        if(userAccount.getCustomer().getIdentificationNumber().isEmpty()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new Response("Identification Number is not empty", HttpStatus.CONFLICT.value()));
        }
        if(userAccount.getCustomer().getPhoneNumber().isEmpty()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new Response("Phone Number is not empty", HttpStatus.CONFLICT.value()));
        }
        if(userAccount.getCustomer().getPhoneNumber().length() != 10){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new Response("Phone Number is not 10 characters", HttpStatus.CONFLICT.value()));
        }
        Customer existingEmail = customerRepository.findByEmail(userAccount.getCustomer().getEmail());
        if(existingEmail != null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new Response("Email was exist", HttpStatus.CONFLICT.value()));
        }
        Customer existingPhoneNumber = customerRepository.findByPhoneNumber(userAccount.getCustomer().getPhoneNumber());
        if(existingPhoneNumber != null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new Response("Phone Number already exist", HttpStatus.CONFLICT.value()));
        }
        Customer existingIdentificationNumber = customerRepository.findByIdentificationNumber(userAccount.getCustomer().getIdentificationNumber());
        if(existingIdentificationNumber != null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new Response("Identification number was exist", HttpStatus.CONFLICT.value()));
        }
        UserAccount existingUsername = userAccountRepository.findByUserName(userAccount.getUserName());
        if(existingUsername != null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new Response("Username was exist", HttpStatus.CONFLICT.value()));
        }
        userAccount.setPassword(bCryptPasswordEncoder.encode(userAccount.getPassword()));
        userAccount.setRole(roleRepository.findByRoleName("CUSTOMER"));
        UserAccount newUserAccount = userAccountRepository.save(userAccount);
        Customer newCustomer = userAccount.getCustomer();
        if(newCustomer != null){
            newCustomer.setUserAccount(userAccount);
            customerRepository.save(newCustomer);
        } else{
            return ResponseEntity.badRequest().body(new Response("Customer info is missing", 400));
        }
        return ResponseEntity.ok(newUserAccount);
    }

    @Override
    public UserAccount findByUsername(String username) {
        return userAccountRepository.findByUserName(username);
    }

    @Override
    public ResponseEntity<?> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserAccount> userAccounts = userAccountRepository.findAll(pageable);
        List<UserAccountInfo> listUserAccountInfo = new ArrayList<>();
        for(UserAccount userAccount : userAccounts){
            UserAccountInfo info = new UserAccountInfo();
            info.setUserName(userAccount.getUserName());
            info.setRoleName(userAccount.getRole().getRoleName());
            info.setId(userAccount.getUserAccountId());

            listUserAccountInfo.add(info);
        }
        if(userAccounts.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("No user accounts found", HttpStatus.NOT_FOUND.value()));
        }
        return ResponseEntity.ok().body(listUserAccountInfo);
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
