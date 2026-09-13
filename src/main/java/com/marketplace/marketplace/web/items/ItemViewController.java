package com.marketplace.marketplace.web.items;

import com.marketplace.marketplace.web.authentication.AuthenticationViewService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ItemViewController {

    private final AuthenticationViewService authenticationViewService;

    public ItemViewController(AuthenticationViewService authenticationViewService) {
        this.authenticationViewService = authenticationViewService;
    }

    @GetMapping("/items")
    public String items(Model model, Authentication authentication) {

        authenticationViewService.addAuthenticationModel(model, authentication);
        return "items";
    }

    @GetMapping("/add-item")
    public String addItem(Model model, Authentication authentication) {
        authenticationViewService.addAuthenticationModel(model, authentication);
        return "add-item";
    }
}
