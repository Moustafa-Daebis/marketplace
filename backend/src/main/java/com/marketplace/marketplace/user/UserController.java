package com.marketplace.marketplace.user;

import com.marketplace.marketplace.authentication.entity.User;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDto>> getUser(@PathVariable UUID id) {
        try {
            Optional<UserDto> userDto = userService.getUser(id);
            if (userDto.isPresent()) {
                return ResponseEntity.ok(new ApiResponse<>(true, "User retrieved successfully", userDto.get()));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ApiResponse<>(false, "User not found", null));
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<UserEntity>>> getAllUsers() {
        try {
            List<UserEntity> users = userService.getAllUsers();
            return ResponseEntity.ok(new ApiResponse<>(true, "Users retrieved successfully", users));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Failed to retrieve users", null));
            }
    }
    @PatchMapping
    public ResponseEntity<ApiResponse<UserDto>> modifyUserDetails(@Valid @RequestBody ChangeUserDetailsRequest request, Authentication authentication){
        UserDto user = userService.modifyUserDetails(request,authentication.getName());

        return ResponseEntity.ok(new ApiResponse<>(true, "User details modified successfully", user));
    }
}
