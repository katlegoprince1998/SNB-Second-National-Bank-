package com.codeword.snb.controller.roundUp;

import com.codeword.snb.dto.AccountDto;
import com.codeword.snb.exception.BankAccountNotFoundException;
import com.codeword.snb.service.RoundUp.RoundUpService;
import com.codeword.snb.service.account.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/round/")
public class RoundUp {
    private final RoundUpService roundUpService;
    private final AccountService accountService;

    @PutMapping("/round/{id}/{round_id}")
    public ResponseEntity<Object> roundUp(@PathVariable  Integer id,
                                          @PathVariable  Integer round_id) throws BankAccountNotFoundException {
        AccountDto accountDto = accountService.getAccountId(id);
        roundUpService.createRoundUp(round_id, accountDto);
        return new ResponseEntity<>("Created", HttpStatus.CREATED);
    }
}
