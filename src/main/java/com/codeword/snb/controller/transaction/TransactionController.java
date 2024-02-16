package com.codeword.snb.controller.transaction;

import com.codeword.snb.dto.AccountDto;
import com.codeword.snb.dto.TransactionDto;
import com.codeword.snb.entity.Transaction;
import com.codeword.snb.exception.AccountAlreadyExistExcetion;
import com.codeword.snb.exception.BankAccountNotFoundException;
import com.codeword.snb.exception.InsufficientFundsException;
import com.codeword.snb.exception.TransactionNotFoundException;
import com.codeword.snb.service.account.AccountService;
import com.codeword.snb.service.transaction.TransactionService;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RequestMapping("/api/v1/transaction/")
@RestController
public class TransactionController {
    private final TransactionService transactionService;
    private final AccountService accountService;

    @Autowired
    public TransactionController(TransactionService transactionService, AccountService accountService) {
        this.transactionService = transactionService;
        this.accountService = accountService;
    }


    @PutMapping("/create/{accountId}")

    public ResponseEntity<Object> create(@RequestBody TransactionDto transactionDto,
                                          @PathVariable Integer accountId) {

        try {
            AccountDto accountDto = accountService.getAccountId(accountId);
             transactionService.createTransaction(transactionDto, accountDto);
            return new ResponseEntity<>("Created", HttpStatus.CREATED);
        } catch (BankAccountNotFoundException ex) {
            // Handle BankAccountNotFoundException
            return new ResponseEntity<>("Bank account not found", HttpStatus.NOT_FOUND);
        } catch (InsufficientFundsException ex) {
            // Handle InsufficientFundsException
            return new ResponseEntity<>("Insufficient funds", HttpStatus.BAD_REQUEST);
        }
    }


}
