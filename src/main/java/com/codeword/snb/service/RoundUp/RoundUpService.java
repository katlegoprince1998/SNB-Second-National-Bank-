package com.codeword.snb.service.RoundUp;


import com.codeword.snb.dto.AccountDto;
import com.codeword.snb.dto.RoundUpGoalDto;
import com.codeword.snb.exception.BankAccountNotFoundException;

public interface RoundUpService {

    RoundUpGoalDto getRoundUp(AccountDto accountDto)
            throws BankAccountNotFoundException;

    void createRoundUp(AccountDto accountDto) throws BankAccountNotFoundException;





}
