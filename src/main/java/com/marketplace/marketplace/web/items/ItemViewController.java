package com.marketplace.marketplace.web.items;

import com.marketplace.marketplace.cart.CartDto;
import com.marketplace.marketplace.cart.CartEntity;
import com.marketplace.marketplace.cart.CartService;
import com.marketplace.marketplace.item.ItemDto;
import com.marketplace.marketplace.item.ItemService;
import com.marketplace.marketplace.user.UserDto;
import com.marketplace.marketplace.web.authentication.AuthenticationViewService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class ItemViewController {

    private final AuthenticationViewService authenticationViewService;
    private final ItemService itemService;
    private final CartService cartService;

    public ItemViewController(AuthenticationViewService authenticationViewService, ItemService itemService, CartService cartService) {
        this.authenticationViewService = authenticationViewService;
        this.itemService = itemService;
        this.cartService = cartService;
    }

    @GetMapping("/items")
    public String items(Model model, Authentication authentication) {

        authenticationViewService.addAuthenticationModel(model, authentication);
        List<ItemDto> items = itemService.getAllItems();
        model.addAttribute("items", items);
        return "items";
    }

    @GetMapping("/add-item")
    public String addItem(Model model, Authentication authentication) {
        authenticationViewService.addAuthenticationModel(model, authentication);
        return "add-item";
    }

    @GetMapping("/items/{id}")
    public String itemDetails(Model model, Authentication authentication, @PathVariable("id") String itemId) {

        if(itemId == null || itemId.isEmpty() || !itemId.matches("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$")) {
            return "redirect:/items";
        }
        authenticationViewService.addAuthenticationModel(model, authentication);
        Optional<ItemDto> item = itemService.getItemById(UUID.fromString(itemId));
        UUID userId = model.getAttribute("user") != null ? ((UserDto) model.getAttribute("user")).getId() : null;
        Optional<CartDto> cart = cartService.getCartByUserId(userId);
        if(!cart.isPresent()) {
            CartDto newCart = cartService.createCart(userId);
            model.addAttribute("cartId", newCart.getId());
        } else {
            model.addAttribute("cartId", cart.get().getId());
        }

        if(item.isPresent()) {
            model.addAttribute("item", item.get());
        } else {
           return "redirect:/items";
        }
        return "item-details";
    }
}
