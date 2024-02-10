package com.codeword.snb.service.user;

import com.codeword.snb.dto.UserDto;
import com.codeword.snb.exception.UserNotFoundException;

public interface UserService {
    UserDto getUserById(Integer id) throws UserNotFoundException;

}
