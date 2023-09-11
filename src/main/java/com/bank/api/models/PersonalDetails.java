package com.bank.api.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity
public class PersonalDetails {

    @Id
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
    String email;
    @Column(nullable =false)
    private String gender;

}
