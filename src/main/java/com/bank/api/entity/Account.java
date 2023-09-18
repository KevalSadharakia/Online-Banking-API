package com.bank.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
    @JoinTable(
            name = "account_transaction",
            joinColumns = @JoinColumn(name = "accountNumber"),
            inverseJoinColumns = @JoinColumn(name = "transactionId")
    )
    private List<Transaction> transactions = new ArrayList<>();

}
