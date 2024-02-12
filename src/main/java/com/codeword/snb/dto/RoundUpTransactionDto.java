package com.codeword.snb.dto;

import com.codeword.snb.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoundUpTransactionDto {
    private Integer id;
    private double roundedUpAmount;
    private LocalDate day;
    private Account account;
}
