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
public class UserRequest {

    private String username;

    String password;

    String firstName;

    String lastName;

    String address;

    String fatherName;
    String motherName;

    String identityProofNumber;

    String contactNumber;

    @Email(message = "invalid email")
    String email;

    @Pattern(regexp = "(Male|Female)",message = "Gender should be either Male or Female")
    private String gender;

    String dob;

}
