package com.bank.api.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnableNetBankingModel {

    int accountNumber;

    @Size(min = 4, max = 2000, message = "Transaction password length must be greater than 4 characters")
    String transactionPassword;

    @Size(min = 4, max = 2000, message = "Transaction password length must be greater than 4 characters")
    String accountPassword;

    @Size(min = 4, max = 2000, message = "Username length must be greater than 4 characters")
    String username;

    @Pattern(regexp = "[9876][0-9]{9}",message = "Phone number is not valid")
    @Size(min = 10, max = 10, message = "Phone Number must be of 10 numbers")
    String phoneNumber;



}
