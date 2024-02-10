package com.codeword.snb.controller.user;

import com.codeword.snb.dto.UserDto;
import com.codeword.snb.exception.UserNotFoundException;
import com.codeword.snb.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/vi/user/")
@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @GetMapping("/get/{id}")
    public UserDto get(@PathVariable Integer id) throws UserNotFoundException {
        return userService.getUserById(id);
    }
}
