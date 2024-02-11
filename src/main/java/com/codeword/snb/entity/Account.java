package com.codeword.snb.entity;

import com.codeword.snb.entity.accountType.AccountType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer accountNo;
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    private double balance;
    private String roundUpEnabled;
    private LocalDate creationDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "account", orphanRemoval = true)
    private List<Transaction> transactions = new ArrayList<>();
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "account", orphanRemoval = true)
    private List<RoundUpTransaction> roundUpTransaction = new ArrayList<>();
}
