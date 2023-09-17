package com.bank.api.entity;

import jakarta.persistence.*;
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
public class Transaction {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int transactionId;
    @Column
    int fromAccountId;
    @Column
    int toAccountId;
    @Column
    long amount;
    @Column
    long timestamp;

    @ManyToMany(mappedBy = "transactions")
    private Set<Account> accounts;

}
