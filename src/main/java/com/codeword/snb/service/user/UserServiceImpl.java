package com.codeword.snb.service.user;

import com.codeword.snb.dto.UserDto;
import com.codeword.snb.entity.User;
import com.codeword.snb.exception.UserNotFoundException;
import com.codeword.snb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    @Override
    public UserDto getUserById(Integer id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);
        UserDto userDto = new UserDto();
        if (user.isPresent()){
            User user1 = user.get();
            BeanUtils.copyProperties(user1, userDto);
        }else{
            throw new UserNotFoundException("User was not found");
        }
        return userDto;
    }

}
