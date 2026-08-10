package com.marketplace.marketplace.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto createUser(UserDto userDto){
        userRepository.save(new UserEntity(
            userDto.getFirstName(),
            userDto.getLastName(),
            userDto.getEmail(),
            userDto.getPassword(),
            userDto.getPhoneNumber(),
            userDto.getRole()
        ));
        return userDto;
    }
}
