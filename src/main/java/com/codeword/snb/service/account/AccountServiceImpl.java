package com.codeword.snb.service.account;

import com.codeword.snb.dto.AccountDto;
import com.codeword.snb.dto.UserDto;
import com.codeword.snb.entity.Account;
import com.codeword.snb.entity.RoundUpGoal;
import com.codeword.snb.entity.User;
import com.codeword.snb.entity.accountType.AccountType;
import com.codeword.snb.exception.BankAccountNotFoundException;
import com.codeword.snb.exception.RoundUpAlreadyDisabledException;
import com.codeword.snb.exception.RoundUpAlreadyEnabledException;
import com.codeword.snb.exception.UserNotFoundException;
import com.codeword.snb.repository.AccountRepository;
import com.codeword.snb.repository.RoundUpGoalRepository;
import com.codeword.snb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService{
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final RoundUpGoalRepository roundUpGoalRepository;
    @Override
    public AccountDto createAccount(AccountDto accountDto,
                                    UserDto userDto) throws UserNotFoundException {
        Optional<User> existingUser = userRepository.findById(userDto.getId());
        if(existingUser.isPresent()){
            Account account = Account.builder()
                    .accountNo(accountDto.getAccountNo())
                    .accountType(accountDto.getAccountType())
                    .user(existingUser.get())
                    .creationDate(LocalDate.now())
                    .balance(0.0)
                    .roundUpEnabled("Disabled")
                    .build();

            accountRepository.save(account);
            accountDto.setId(account.getId());
            accountDto.setCreationDate(LocalDate.now());
            accountDto.setRoundUpEnabled("Disabled");
            accountDto.setUser(existingUser.get());

        }else{
            throw new UserNotFoundException("User was not found");
        }
        return accountDto;
    }

    @Override
    public AccountDto getAccountId(Integer id) throws BankAccountNotFoundException {
        Optional<Account> account = accountRepository.findById(id);
        AccountDto accountDto = new AccountDto();
        if(account.isPresent()){
            Account account1 = account.get();
            BeanUtils.copyProperties(account1, accountDto);

        }else {
            throw new BankAccountNotFoundException("Account was not found");
        }
        return accountDto;
    }

    @Override
    public List<AccountDto> getAccount() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream()
                .map(account -> {
                    AccountDto accountDto = new AccountDto();
                    accountDto.setId(account.getId());
                    accountDto.setAccountNo(account.getAccountNo());
                    accountDto.setAccountType(account.getAccountType());
                    accountDto.setBalance(account.getBalance());
                    accountDto.setCreationDate(account.getCreationDate());
                    accountDto.setUser(account.getUser());
                    return accountDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public String enableRoundUp(Integer accountId) throws BankAccountNotFoundException, RoundUpAlreadyEnabledException {
        Optional<Account> account = accountRepository.findById(accountId);
        if (account.isPresent()){
            Account account1 = account.get();
            if (!account1.getRoundUpEnabled().equals("Enabled")){
                account1.setRoundUpEnabled("Enabled");
                accountRepository.save(account1);
                RoundUpGoal roundUpGoal = RoundUpGoal
                        .builder()
                        .goalName("Round Up Savings")
                        .account(account1)
                        .currentAmount(0.0)
                        .build();
                roundUpGoalRepository.save(roundUpGoal);
            }else{
                throw new RoundUpAlreadyEnabledException("Round up already enabled");
            }
        }
        return "Round Up Enabled";
    }

    @Override
    public String disableRoundUp(Integer accountId) throws BankAccountNotFoundException, RoundUpAlreadyDisabledException {
        Optional<Account> account = accountRepository.findById(accountId);
        if (account.isPresent()){
            Account account1 = account.get();
            if (!account1.getRoundUpEnabled().equals("Disabled")){
                account1.setRoundUpEnabled("Disabled");
                accountRepository.save(account1);

            }else{
                throw new RoundUpAlreadyDisabledException("Round up already disabled");
            }
        }
        return "Round Up Disabled";
    }

    @Override
    public void deleteAccount(Integer accountId) throws BankAccountNotFoundException {
        Optional<Account> existingAccount = accountRepository.findById(accountId);
        if (existingAccount.isPresent()){
            accountRepository.delete(existingAccount.get());

        }else{
            throw new BankAccountNotFoundException("Account with this id was not found");
        }

    }

}
