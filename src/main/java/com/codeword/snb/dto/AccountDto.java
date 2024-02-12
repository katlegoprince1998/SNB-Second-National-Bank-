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
    private String accountNo;
    private String cvv;
    private String pin;
    private String cardNo;
    private String expiryDate;
    private AccountType accountType;
    private double balance;
    private String roundUpEnabled;
    private LocalDate creationDate;
    private User user;

    public AccountDto(Integer id, Integer accountNo, AccountType accountType, double balance, LocalDate creationDate) {
    }

    public AccountDto(Integer id, Integer accountNo, AccountType accountType, double balance, LocalDate creationDate, Integer id1) {
    }
}
