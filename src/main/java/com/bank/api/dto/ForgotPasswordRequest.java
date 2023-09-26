package com.bank.api.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForgotPasswordRequest {
    String userId;
    int accountNumber;
    String aadharNumber;
    String newPassword;
}
