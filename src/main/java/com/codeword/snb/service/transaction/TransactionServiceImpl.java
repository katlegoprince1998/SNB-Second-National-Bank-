package com.codeword.snb.service.transaction;

import com.codeword.snb.constants.Charges;
import com.codeword.snb.dto.AccountDto;
import com.codeword.snb.dto.TransactionDto;
import com.codeword.snb.entity.Account;
import com.codeword.snb.entity.Transaction;
import com.codeword.snb.entity.transactionType.TransactionType;
import com.codeword.snb.exception.BankAccountNotFoundException;
import com.codeword.snb.exception.InsufficientFundsException;
import com.codeword.snb.repository.AccountRepository;
import com.codeword.snb.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;


    @Override
    public Transaction createTransaction(TransactionDto transactionDto, AccountDto accountDto)
            throws InsufficientFundsException, BankAccountNotFoundException {
        Optional<Account> getAccount = accountRepository.findById(accountDto.getId());

        if (getAccount.isPresent()){
            Account account = getAccount.get();
            String accountType = String.valueOf(account.getAccountType());
            String transactionType = String.valueOf(transactionDto.getTransactionType());
            double balance =  account.getBalance();

            switch (accountType){
                case "SAVINGS", "CHECKING":
                    switch (transactionType){
                        case "DEPOSIT":

                            double funds = transactionDto.getAmount() - Charges.SAVINGS_TRANSFER_PERCENTAGE_CHARGE;
                            saveTransactions(
                                    String.valueOf(transactionDto.getTransactionType()),
                                    account,
                                    LocalDate.now(),
                                    LocalTime.now(),
                                    transactionDto.getAmount() - funds,
                                    account.getBalance(),
                                    transactionDto.getAmount()

                            );

                            account.setBalance(balance + funds);
                            accountRepository.save(account);

                            break;
                        case "WITHDRAWAL", "TRANSFER":
                            if(balance >= transactionDto.getAmount()){
                                double fund = transactionDto.getAmount() - Charges.CHECKING_WITHDRWAL_PERCENTAGE_CHARGE;
                                saveTransactions(
                                        String.valueOf(transactionDto.getTransactionType()),
                                        account,
                                        LocalDate.now(),
                                        LocalTime.now(),
                                        transactionDto.getAmount() - fund,
                                        account.getBalance(),
                                        transactionDto.getAmount()

                                );
                                account.setBalance(balance - transactionDto.getAmount()
                                        - (Charges.CHECKING_WITHDRWAL_PERCENTAGE_CHARGE));
                                accountRepository.save(account);

                            }else{
                                throw new InsufficientFundsException("Insufficient Funds");
                            }
                            break;
                    }
                    break;
            }
        }else{
            throw new BankAccountNotFoundException("Account with this account was not found");
        }
        return null;
    }
    private void saveTransactions( String transType,
                                  Account accId, LocalDate day, LocalTime time,
                                  double charges, double balance, double amount){

        Transaction transaction = Transaction
                .builder()
                .amount(amount)
                .transactionType(TransactionType.valueOf(transType))
                .day(day)
                .time(time)
                .account(accId)
                .charges(charges)
                .build();
        transactionRepository.save(transaction);

    }


}
