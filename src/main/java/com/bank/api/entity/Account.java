package com.bank.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Account {

    @Column(nullable =false,unique = true)
    @Id
    int accountNumber;

    @Column(nullable =false)
    @Size(min = 4, max = 2000, message = "Transaction password length must be greater than 4 characters")
    String transactionPassword;

    @Column(nullable =false,unique = true)
    @Pattern(regexp = "[9876][0-9]{9}",message = "Phone number is not valid")
    @Size(min = 10, max = 10, message = "Phone Number must be of 10 numbers")
    String phoneNumber;

    @Column(nullable = false)
    long balance;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "transactions", referencedColumnName = "accountNumber")
    private Set<Transaction> transactions;

}
