package com.codeword.snb.service.account;

import com.codeword.snb.dto.AccountDto;
import com.codeword.snb.dto.UserDto;
import com.codeword.snb.exception.BankAccountNotFoundException;
import com.codeword.snb.exception.RoundUpAlreadyDisabledException;
import com.codeword.snb.exception.RoundUpAlreadyEnabledException;
import com.codeword.snb.exception.UserNotFoundException;

import java.util.List;

public interface AccountService {
    AccountDto createAccount(AccountDto accountDto, UserDto userDto) throws UserNotFoundException;
    AccountDto getAccountId(Integer id) throws BankAccountNotFoundException;
    List<AccountDto> getAccount();
    String enableRoundUp(Integer accountId) throws BankAccountNotFoundException, RoundUpAlreadyEnabledException;
    String disableRoundUp(Integer accountId) throws BankAccountNotFoundException, RoundUpAlreadyDisabledException;
    void deleteAccount(Integer accountId) throws BankAccountNotFoundException;

}
