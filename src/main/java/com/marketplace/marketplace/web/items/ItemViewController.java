package com.marketplace.marketplace.web.items;

import com.marketplace.marketplace.item.ItemDto;
import com.marketplace.marketplace.item.ItemService;
import com.marketplace.marketplace.web.authentication.AuthenticationViewService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ItemViewController {

    private final AuthenticationViewService authenticationViewService;
    private final ItemService itemService;

    public ItemViewController(AuthenticationViewService authenticationViewService, ItemService itemService) {
        this.authenticationViewService = authenticationViewService;
        this.itemService = itemService;
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
}
