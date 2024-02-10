package com.codeword.snb.service.account;

import com.codeword.snb.dto.AccountDto;
import com.codeword.snb.dto.UserDto;
import com.codeword.snb.entity.Account;
import com.codeword.snb.entity.User;
import com.codeword.snb.entity.accountType.AccountType;
import com.codeword.snb.exception.BankAccountNotFoundException;
import com.codeword.snb.exception.UserNotFoundException;
import com.codeword.snb.repository.AccountRepository;
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
    @Override
    public AccountDto createAccount(AccountDto accountDto,
                                    UserDto userDto) throws UserNotFoundException {
        Optional<User> existingUser = userRepository.findById(userDto.getId());
        if(existingUser.isPresent()){
            Account account = Account.builder()
                    .accountNo(accountDto.getAccountNo())

                    .user(existingUser.get())
                    .creationDate(LocalDate.now())
                    .balance(accountDto.getBalance())
                    .build();

            accountRepository.save(account);
            accountDto.setId(account.getId());
            accountDto.setCreationDate(LocalDate.now());
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

}
