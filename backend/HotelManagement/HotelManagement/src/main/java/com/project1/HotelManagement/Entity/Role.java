package com.project1.HotelManagement.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roleId")
    private int roleId;

    @Column(name = "roleName")
    private String roleName;

    @OneToMany(mappedBy = "role", cascade = {
            CascadeType.DETACH, CascadeType.REFRESH,
            CascadeType.MERGE, CascadeType.PERSIST}
    )
    private List<UserAccount> userAccount;
}
