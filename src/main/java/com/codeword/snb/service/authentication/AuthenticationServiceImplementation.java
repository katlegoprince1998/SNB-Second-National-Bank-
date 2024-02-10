package com.codeword.snb.service.authentication;

import com.codeword.snb.config.JSONWebToken.CustomeUserDetails;
import com.codeword.snb.config.JSONWebToken.JwtProvider;
import com.codeword.snb.dto.UserDto;
import com.codeword.snb.entity.User;

import com.codeword.snb.exception.UserAlreadyExistException;
import com.codeword.snb.repository.UserRepository;
import com.codeword.snb.request.UserRequest;
import com.codeword.snb.response.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationServiceImplementation implements AuthenticationService{
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomeUserDetails customeUserDetails;
    private final UserRepository userRepository;
    @Override
    public AuthResponse registerUser(UserDto userDto) throws Exception {
        String email = userDto.getEmail();
        String password = userDto.getPassword();
        String firstname = userDto.getFirstname();
        String lastname = userDto.getLastname();
        String cellNo = userDto.getCellNo();
        String idNo = userDto.getIdNo();


        User isEmailExist = userRepository.findByEmail(email);

        if(isEmailExist != null){
            throw new UserAlreadyExistException("Email is already used by another account");
        }
        User createUser = User
                .builder()
                .email(email)
                .idNo(idNo)
                .firstname(firstname)
                .lastname(lastname)
                .cellNo(cellNo)
                .password(passwordEncoder.encode(password))
                .build();
        BeanUtils.copyProperties(createUser, userDto);
        userRepository.save(createUser);
        userDto.setId(createUser.getId());

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        email, password);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider
                .generateToken(authentication);

        return AuthResponse
                .builder()
                .jwt(token)
                .message("User Created Successfully")
                .build();
    }
    @Override
    public AuthResponse logUserIn(UserRequest request) {
        String username = request.getEmail();
        String password = request.getPassword();


        Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);

        return AuthResponse
                .builder()
                .jwt(token)
                .message("User Logged In Successfully")
                .build();
    }
    private Authentication authenticate(String username, String password){
        UserDetails userDetails = customeUserDetails.loadUserByUsername(username);
        if(username == null){
            throw new BadCredentialsException("User Not Found");
        }
        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("Password is Incorrect");
        }
        return  new UsernamePasswordAuthenticationToken(
                userDetails, null,
                userDetails.getAuthorities());
    }
}
