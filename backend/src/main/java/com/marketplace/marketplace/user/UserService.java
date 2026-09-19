package com.marketplace.marketplace.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.ObjectInputFilter;
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


    public Optional<UserDto> getUser(UUID id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        if (userEntity.isPresent()) {
            return Optional.of(new UserDto(
                    userEntity.get().getId(),
                    userEntity.get().getFirstName(),
                    userEntity.get().getLastName(),
                    userEntity.get().getEmail(),
                    userEntity.get().getPhoneNumber(),
                    userEntity.get().getRole(),
                    userEntity.get().isActive()
            ));
        } else {
            return Optional.empty();
        }

    }

    public Optional<UserDto> getUserByEmail(String email) {
        Optional<UserEntity> userEntity = userRepository.findByEmail(email);
        if (userEntity.isPresent()) {
            return Optional.of(new UserDto(
                    userEntity.get().getId(),
                    userEntity.get().getFirstName(),
                    userEntity.get().getLastName(),
                    userEntity.get().getEmail(),
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

    public UserDto modifyUserDetails(ChangeUserDetailsRequest request,String userEmail){
        Optional<UserEntity> userEntity = userRepository.findByEmail(userEmail);

        if(!userEntity.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found");
        }
        if(request.firstName().isPresent()){
            userEntity.get().setFirstName(request.firstName().get());
        }

        if(request.lastName().isPresent()){
            userEntity.get().setLastName(request.lastName().get());
        }

        if(request.phoneNumber().isPresent()){
            userEntity.get().setPhoneNumber(request.phoneNumber().get());
        }
        userRepository.save(userEntity.get());
        return UserDto.fromEntity(userEntity.get());
    }
}
