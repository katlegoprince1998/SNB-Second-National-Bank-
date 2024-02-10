package com.codeword.snb.service.authentication;

import com.codeword.snb.dto.UserDto;
import com.codeword.snb.request.UserRequest;
import com.codeword.snb.response.AuthResponse;


public interface AuthenticationService {
    AuthResponse registerUser(UserDto userDto) throws Exception;
    AuthResponse logUserIn(UserRequest request);
}
