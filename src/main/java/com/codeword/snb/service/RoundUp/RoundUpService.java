package com.codeword.snb.service.RoundUp;


import com.codeword.snb.dto.AccountDto;
import com.codeword.snb.dto.RoundUpGoalDto;
import com.codeword.snb.exception.BankAccountNotFoundException;

public interface RoundUpService {

    RoundUpGoalDto getRoundUp(Integer id)
            throws BankAccountNotFoundException;

    void createRoundUp(Integer id, AccountDto accountDto) throws BankAccountNotFoundException;





}
