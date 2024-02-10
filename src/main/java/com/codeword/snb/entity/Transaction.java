package com.codeword.snb.entity;

import com.codeword.snb.entity.transactionType.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private double amount;
    private LocalDate day;
    private LocalDate time;
    @Enumerated
    private TransactionType transactionType;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
}
