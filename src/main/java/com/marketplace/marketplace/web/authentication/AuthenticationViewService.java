package com.marketplace.marketplace.web.authentication;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class AuthenticationViewService {
    public void addAuthenticationModel(Model model, Authentication authentication) {
        boolean loggedIn = authentication != null
                && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken);

        model.addAttribute("loggedIn", loggedIn);
        if (loggedIn && authentication.getPrincipal() != null) {
            model.addAttribute("currentUserName", authentication.getName());
        }
    }
}
