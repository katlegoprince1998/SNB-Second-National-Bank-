package com.codeword.snb.service.RoundUp;

import com.codeword.snb.dto.AccountDto;
import com.codeword.snb.exception.BankAccountNotFoundException;

public interface RoundUpService {

    AccountDto getRoundUp(Integer accountId) throws BankAccountNotFoundException;


}
