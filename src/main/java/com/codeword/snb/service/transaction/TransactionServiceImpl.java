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
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @Override
    public TransactionDto createTransaction(TransactionDto transactionDto, AccountDto accountDto)
            throws InsufficientFundsException, BankAccountNotFoundException {
        Account account = getAccountById(accountDto.getId());
        double balance = account.getBalance();
        String accountType = account.getAccountType().toString();
        String transactionType = transactionDto.getTransactionType().toString();
        double amount = transactionDto.getAmount();
        double charges = calculateCharges(balance, accountType, transactionType, amount);

        // Additional check for withdrawal and transfer
        if ((transactionType.equals("WITHDRAWAL") || transactionType.equals("TRANSFER")) && amount > balance) {
            throw new InsufficientFundsException("Insufficient funds");
        }

        Transaction transaction = createNewTransaction(account, transactionType, amount, charges);

        saveTransactionAndAdjustBalance(account, transaction);

        return copyTransactionToDto(transaction, transactionDto);
    }

    private Account getAccountById(Integer accountId) throws BankAccountNotFoundException {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new BankAccountNotFoundException("Account was not found"));
    }

    private double calculateCharges(double balance, String accountType, String transactionType, double amount) {
        double chargePercentage;
        if (accountType.equals("SAVINGS")) {
            chargePercentage = transactionType.equals("DEPOSIT") ?
                    Charges.SAVINGS_DEPOSIT_PERCENTAGE_CHARGE :
                    (transactionType.equals("WITHDRAWAL") ?
                            Charges.SAVINGS_WITHDRWAL_PERCENTAGE_CHARGE :
                            Charges.SAVINGS_TRANSFER_PERCENTAGE_CHARGE);
        } else {
            chargePercentage = transactionType.equals("DEPOSIT") ?
                    Charges.CHECKING_DEPOSIT_PERCENTAGE_CHARGE :
                    (transactionType.equals("WITHDRAWAL") ?
                            Charges.CHECKING_WITHDRWAL_PERCENTAGE_CHARGE :
                            Charges.CHECKING_TRANSFER_PERCENTAGE_CHARGE);
        }
        return (balance + amount) * chargePercentage;
    }

    private Transaction createNewTransaction(Account account, String transactionType,
                                             double amount, double charges) {
        return Transaction.builder()
                .day(LocalDate.now())
                .charges(charges)
                .chargesPercentage(charges / (account.getBalance() + amount))
                .account(account)
                .transactionType(TransactionType.valueOf(transactionType))
                .amount(amount)
                .time(LocalTime.now())
                .build();
    }

    private void saveTransactionAndAdjustBalance(Account account, Transaction transaction) {
        transactionRepository.save(transaction);
        double newBalance = account.getBalance() - transaction.getAmount() - transaction.getCharges();
        account.setBalance(newBalance);
        accountRepository.save(account);
    }

    private TransactionDto copyTransactionToDto(Transaction transaction, TransactionDto transactionDto) {
        BeanUtils.copyProperties(transaction, transactionDto);
        transactionDto.setId(transaction.getId());
        transactionDto.setAccount(transaction.getAccount());
        return transactionDto;
    }
}
