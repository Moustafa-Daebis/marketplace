package com.marketplace.marketplace.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto createUser(UserDto userDto) {
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

    public Optional<UserDto> getUser(UUID id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        if (userEntity.isPresent()) {
            return Optional.of(new UserDto(
                    userEntity.get().getId(),
                    userEntity.get().getFirstName(),
                    userEntity.get().getLastName(),
                    userEntity.get().getEmail(),
                    userEntity.get().getPassword(),
                    userEntity.get().getPhoneNumber(),
                    userEntity.get().getRole(),
                    userEntity.get().isActive()
            ));
        } else {
            return Optional.empty();
        }

    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }
}
