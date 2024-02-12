package com.codeword.snb.dto;

import com.codeword.snb.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoundUpGoalDto {
    private Integer id;
    private String goalName;
    private double targetAmount;
    private double currentAmount;
    private Account account;
}
