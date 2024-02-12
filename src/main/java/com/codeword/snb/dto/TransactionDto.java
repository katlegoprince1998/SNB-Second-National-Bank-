package com.codeword.snb.dto;

import com.codeword.snb.entity.Account;
import com.codeword.snb.entity.transactionType.TransactionType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {
    private Integer id;
    private double amount;
    private LocalDate day;
    private LocalDate time;
    private double charges;
    private double chargesPercentage;
    private TransactionType transactionType;
    private Account account;
}
