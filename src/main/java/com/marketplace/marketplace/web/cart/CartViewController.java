package com.marketplace.marketplace.web.cart;

import com.marketplace.marketplace.cartitem.CartItemDto;
import com.marketplace.marketplace.cartitem.CartItemService;
import com.marketplace.marketplace.web.authentication.AuthenticationViewService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.UUID;

@Controller
public class CartViewController {

    private final AuthenticationViewService authenticationViewService;
    private final CartItemService cartItemService;

    public CartViewController(AuthenticationViewService authenticationViewService, CartItemService cartItemService) {
        this.authenticationViewService = authenticationViewService;
        this.cartItemService = cartItemService;
    }

    @GetMapping("/cart")
    public String cart(Model model, Authentication authentication) {
        authenticationViewService.addAuthenticationModel(model, authentication);

        UUID userId = model.getAttribute("user") != null ? ((com.marketplace.marketplace.user.UserDto) model.getAttribute("user")).getId() : null;
        System.out.println("User in model: " + userId);
        List<CartItemDto> cartItems = cartItemService.getCartItemsByUserId(userId);
        model.addAttribute("cartItems", cartItems);
        System.out.println("Cart items: " + cartItems);
        return "cart";
    }
}
