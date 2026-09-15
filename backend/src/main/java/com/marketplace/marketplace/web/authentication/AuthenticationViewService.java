package com.marketplace.marketplace.web.authentication;

import com.marketplace.marketplace.user.UserDto;
import com.marketplace.marketplace.user.UserService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Optional;

@Service
public class AuthenticationViewService {

    private final UserService userService;

    public AuthenticationViewService(UserService userService) {
        this.userService = userService;
    }

    public void addAuthenticationModel(Model model, Authentication authentication) {
        boolean loggedIn = authentication != null
                && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken);

        model.addAttribute("loggedIn", loggedIn);
        if (loggedIn && authentication.getPrincipal() != null) {
            Optional<UserDto> user = userService.getUserByEmail(authentication.getName());
            user.ifPresent(userDto -> model.addAttribute("user", userDto));
        }
    }
}
