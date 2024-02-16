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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RequiredArgsConstructor
@RequestMapping("/api/v1/transaction/")
@RestController
public class TransactionController {
    private final TransactionService transactionService;
    private final AccountService accountService;

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
    @GetMapping("/get/{id}")
    public ResponseEntity<Object> getTransactionsById(@PathVariable Integer id) throws TransactionNotFoundException {
        try{
            TransactionDto transactionDto =
                    transactionService.getTransaction(id);
            return new ResponseEntity<>(transactionDto, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Failed to get Transactions", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get")
    public ResponseEntity<Object> getTransactions() {
        List<Transaction> transactionDtoList = transactionService.getTransactions();
        return ResponseEntity.ok(transactionDtoList);
    }

}
