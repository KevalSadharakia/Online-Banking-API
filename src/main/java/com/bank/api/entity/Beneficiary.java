package com.bank.api.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Beneficiary {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    @Id
    int beneficiaryId;
    @Column
    String firstName;
    @Column
    String lastName;
    @Column
    int accountNumber;
    @Column
    String addedByEmail;
}
