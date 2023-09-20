package com.bank.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PersonalDetails {

    @Id
    @Column(nullable =false)
    String email;

    @Column(nullable = false,unique = true)
    int accountNumber;
    @Column(nullable =false)
    String firstName;
    @Column(nullable =false)
    String lastName;

    @Column(nullable =false)
    String fatherName;

    @Column(nullable =false)
    String dob;
    @Column(nullable =false)
    String address;
    @Column(nullable =false,unique = true)
    String identityProofNumber;

    @Column(nullable =false,unique = true)
    String contactNumber;

    @Column(nullable =false)
    private String gender;

    @Column(unique = true)
    private String username;

    @Column
    @Size(min = 4, max = 2000, message = "Password length must be greater than 4 characters")
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account", referencedColumnName = "accountNumber")
    Account account;

    @Column
    String role;


}
