package com.bank.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
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

    @JsonIgnore
    @ManyToMany(mappedBy = "transactions")
    private Set<Account> accounts = new HashSet<>();

}
