package com.codeword.snb.dto;

import com.codeword.snb.entity.Transaction;
import com.codeword.snb.entity.User;
import com.codeword.snb.entity.accountType.AccountType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class AccountDto {
    private Integer id;
    private Integer accountNo;
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    private double balance;
    private LocalDate creationDate;
    private User user;
    private List<Transaction> transactions = new ArrayList<>();
}
