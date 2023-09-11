package com.bank.api.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Column(nullable =false)
    @Id
    String username;

    @Column(nullable =false)
    int accountNumber;

    @Column(nullable =false)
    @Size(min = 4, max = 2000, message = "Password length must be greater than 4 characters")
    String accountPassword;

    @Column(nullable =false)
    @Size(min = 4, max = 2000, message = "Transaction password length must be greater than 4 characters")
    String transactionPassword;

    @Column(nullable =false)
    @Pattern(regexp = "[9876][0-9]{9}",message = "Phone number is not valid")
    @Size(min = 10, max = 10, message = "Phone Number must be of 10 numbers")
    String phoneNumber;

}
