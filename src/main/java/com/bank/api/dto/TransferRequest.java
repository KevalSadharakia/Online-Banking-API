package com.bank.api.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferRequest {
    int accountNumber;
    long amount;
    String transactionPassword;
    String firstName;
    String lastName;
}
