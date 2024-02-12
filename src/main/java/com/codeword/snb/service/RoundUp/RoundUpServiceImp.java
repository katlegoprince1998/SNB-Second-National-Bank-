package com.codeword.snb.service.RoundUp;

import com.codeword.snb.dto.AccountDto;
import com.codeword.snb.dto.RoundUpGoalDto;
import com.codeword.snb.entity.Account;
import com.codeword.snb.entity.RoundUpGoal;
import com.codeword.snb.exception.BankAccountNotFoundException;
import com.codeword.snb.repository.AccountRepository;
import com.codeword.snb.repository.RoundUpGoalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RoundUpServiceImp implements RoundUpService {
    private final RoundUpGoalRepository roundUpGoalRepository;
    private final AccountRepository accountRepository;
    @Override
    public RoundUpGoalDto getRoundUp(AccountDto accountDto) throws BankAccountNotFoundException {
        return null;
    }

    @Override
    public void createRoundUp(AccountDto accountDto)
            throws BankAccountNotFoundException {
        Optional<Account> account = accountRepository.findById(accountDto.getId());
        if (account.isPresent()){
            Account account1 = account.get();
            double balance = account1.getBalance();
            String balanceAsString = Double.toString(balance);
            int delIndex = balanceAsString.indexOf(".");
            String valueAfterDelimiter  = balanceAsString.substring(delIndex + 1);
            String finalConversion  = "0." + valueAfterDelimiter ;
            double decimal = Double.parseDouble(finalConversion);
            account1.setBalance(balance - decimal);
            accountRepository.save(account1);
            RoundUpGoal roundUpGoal1 = new RoundUpGoal();

            if (decimal != 0.0){
                roundUpGoal1.setCurrentAmount(roundUpGoal1.getCurrentAmount() + decimal);
                roundUpGoalRepository.save(roundUpGoal1);
            }

        }else {
            throw new BankAccountNotFoundException(
                    "Account with this id was not found");
        }
    }
}
