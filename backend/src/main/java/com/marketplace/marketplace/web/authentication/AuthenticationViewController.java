package com.marketplace.marketplace.web.authentication;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthenticationViewController {
    private final AuthenticationViewService authenticationViewService;

    public AuthenticationViewController(AuthenticationViewService authenticationViewService) {
        this.authenticationViewService = authenticationViewService;
    }

    @GetMapping("/login")
    public String login(Model model, Authentication authentication) {
        if (authentication!=null && authentication.isAuthenticated()){
            authenticationViewService.addAuthenticationModel(model, authentication);
            return "index"; // Redirect to home page if already logged in
        }
        authenticationViewService.addAuthenticationModel(model, authentication);
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model, Authentication authentication) {
        if (authentication!=null && authentication.isAuthenticated()){
            authenticationViewService.addAuthenticationModel(model, authentication);
            return "index"; // Redirect to home page if already logged in
        }
        authenticationViewService.addAuthenticationModel(model, authentication);
        return "register";
    }

}
