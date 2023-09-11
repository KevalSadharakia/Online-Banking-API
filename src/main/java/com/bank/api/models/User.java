package com.bank.api.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class User {
    @Id
    private String username;
    @Column(nullable =false)
    String password;
    @Column(nullable =false)
    String name;
    @Column(nullable =false)
    String address;
    @Column(nullable =false)
    String identityProofNumber;
    @Column(nullable =false)
    int accountNumber;
    @Column(nullable =false)
    String contactNumber;
    @Column(nullable =false)
    @Email(message = "invalid email")
    String email;
    @Column
    Boolean adminAccess;

    @Column(nullable =false)
    @Pattern(regexp = "(Male|Female)",message = "Gender should be either Male or Female")
    private String gender;
    @Max(value = 110,message = "Age Should be less than 110 and greater than 12")
    @Min(value = 12,message = "Age Should be less than 110 and greater than 12")
    @Column(nullable =false)
    private int age;
}
