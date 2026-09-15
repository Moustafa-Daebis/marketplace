package com.marketplace.marketplace.web.home;

import com.marketplace.marketplace.web.authentication.AuthenticationViewService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final AuthenticationViewService authenticationViewService;

    public HomeController(AuthenticationViewService authenticationViewService) {
        this.authenticationViewService = authenticationViewService;
    }

    @GetMapping("/")
    public String home(Model model, Authentication authentication) {
        authenticationViewService.addAuthenticationModel(model, authentication);
        return "index";
    }
}
