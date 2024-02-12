package com.codeword.snb.controller.account;

import com.codeword.snb.dto.AccountDto;
import com.codeword.snb.dto.UserDto;
import com.codeword.snb.entity.User;
import com.codeword.snb.exception.BankAccountNotFoundException;
import com.codeword.snb.exception.UserNotFoundException;
import com.codeword.snb.service.account.AccountService;
import com.codeword.snb.service.user.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/account/")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final UserService userService;

    @PostMapping("/create/{user_id}")
    public ResponseEntity<Object> create(
            @RequestBody AccountDto accountDto,
            @PathVariable("user_id") Integer user_id
            ) throws UserNotFoundException {
        try{
            UserDto user = userService.getUserById(user_id);
            AccountDto createdAccount =
                    accountService.createAccount(accountDto, user);
            return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException("User not found");
        }
    }
    @GetMapping("/get")
    public ResponseEntity<Object> getAllAccounts(){
        try{
            List<AccountDto> accounts =
                    accountService.getAccount();
            return new ResponseEntity<>(accounts, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Failed to get Accounts", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Object> getAccountById(@PathVariable Integer id){
        try{
            AccountDto account =
                    accountService.getAccountId(id);
            return new ResponseEntity<>(account, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Failed to get Accounts", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Integer id) throws BankAccountNotFoundException {
        Map<String, String> deleted = new HashMap<>();

            accountService.deleteAccount(id);
            deleted.put("message", "Account deleted successfully");
            return ResponseEntity.ok(deleted);

    }
    @PutMapping("/enable/{id}")
    public ResponseEntity<String> enable(@PathVariable Integer id){
        try{
            String enabled = accountService.enableRoundUp(id);
            return new ResponseEntity<>(enabled, HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>("Failed to enable Round up", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @PutMapping("/disable/{id}")
    public ResponseEntity<String> disable(@PathVariable Integer id){
        try{
            String disable = accountService.disableRoundUp(id);
            return new ResponseEntity<>(disable, HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>("Failed to disable Round up", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
