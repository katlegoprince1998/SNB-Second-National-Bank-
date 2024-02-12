package com.codeword.snb.service.transaction;

import com.codeword.snb.dto.AccountDto;
import com.codeword.snb.dto.TransactionDto;
import com.codeword.snb.exception.BankAccountNotFoundException;
import com.codeword.snb.exception.InsufficientFundsException;

public interface TransactionService {
   TransactionDto createTransaction(TransactionDto transactionDto,
                                    AccountDto accountDto) throws InsufficientFundsException, BankAccountNotFoundException;

}
