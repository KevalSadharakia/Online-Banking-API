package com.bank.api.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class PersonalDetailsRequest {

    @Size(min = 2, max = 50, message = "FirstName must be between 2 and 50 characters")
    String firstName;

    @Size(min = 2, max = 50, message = "LastName must be between 2 and 50 characters")
    String lastName;

    @Size(min = 2, max = 50, message = "FatherName must be between 2 and 50 characters")
    String fatherName;

    @Pattern(regexp = "[0-9]{4}[-][0-9]{2}[-][0-9]{2}",message = "Put dob in YYYY-MM-DD format")
    String dob;

    @Size(min = 2, max = 50, message = "Address must be between 2 and 50 characters")
    String address;

    @Size(min = 12, max = 12, message = "IdentityProofNumber must be of 12 characters")
    String identityProofNumber;

    @Pattern(regexp = "[9876][0-9]{9}",message = "Phone number is not valid")
    @Size(min = 10, max = 10, message = "Phone Number must be of 10 numbers")
    String contactNumber;
    @Email(message = "invalid email")
    String email;
    @Column(nullable =false)
    @Pattern(regexp = "(Male|Female)",message = "Gender should be either Male or Female")
    private String gender;
}
