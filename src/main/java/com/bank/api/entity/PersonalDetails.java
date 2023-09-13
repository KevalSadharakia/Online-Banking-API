package com.bank.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity
public class PersonalDetails {

    @Id
    @Column(nullable =false)
    String email;

    @Column(nullable = false)
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
    @Column(nullable =false)
    String identityProofNumber;

    @Column(nullable =false)
    String contactNumber;


    @Column(nullable =false)
    private String gender;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account", referencedColumnName = "accountNumber")
    Account account;

}
