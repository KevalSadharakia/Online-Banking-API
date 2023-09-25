package com.bank.api.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DetailsResponse {
    String firstName;
    String lastName;
    String fatherName;
    String dob;
    String address;
    String identityProofNumber;
    String contactNumber;
    String email;
    int accountNumber;
    String gender;
}

