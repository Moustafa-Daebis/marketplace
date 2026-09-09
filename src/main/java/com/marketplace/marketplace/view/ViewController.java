package com.marketplace.marketplace.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AnonymousAuthenticationToken;

@Controller
public class ViewController {

    @GetMapping("/")
    public String home(Model model, Authentication authentication) {
        addAuthModel(model, authentication);
        return "index";
    }

    @GetMapping("/items")
    public String items(Model model, Authentication authentication) {

        addAuthModel(model, authentication);
        return "items";
    }

    @GetMapping("/login")
    public String login(Model model, Authentication authentication) {
        if (authentication!=null && authentication.isAuthenticated()){
            addAuthModel(model, authentication);
            return "index"; // Redirect to home page if already logged in
        }
        addAuthModel(model, authentication);
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model, Authentication authentication) {
        if (authentication!=null && authentication.isAuthenticated()){
            addAuthModel(model, authentication);
            return "index"; // Redirect to home page if already logged in
        }
        addAuthModel(model, authentication);
        return "register";
    }

    @GetMapping("/add-item")
    public String addItem(Model model, Authentication authentication) {
        addAuthModel(model, authentication);
        return "add-item";
    }

    private void addAuthModel(Model model, Authentication authentication) {
        boolean loggedIn = authentication != null
                && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken);

        model.addAttribute("loggedIn", loggedIn);
        if (loggedIn && authentication.getPrincipal() != null) {
            model.addAttribute("currentUserName", authentication.getName());
        }
    }
}
