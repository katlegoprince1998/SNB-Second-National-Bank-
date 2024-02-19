package com.codeword.snb.service.transaction;

import com.codeword.snb.dto.AccountDto;
import com.codeword.snb.dto.TransactionDto;
import com.codeword.snb.entity.Transaction;
import com.codeword.snb.exception.BankAccountNotFoundException;
import com.codeword.snb.exception.InsufficientFundsException;

public interface TransactionService {

   Transaction createTransaction(TransactionDto transactionDto,
                                 AccountDto accountDto) throws InsufficientFundsException, BankAccountNotFoundException;

}
