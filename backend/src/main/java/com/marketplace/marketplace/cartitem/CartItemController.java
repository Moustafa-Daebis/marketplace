package com.marketplace.marketplace.cartitem;

import com.marketplace.marketplace.user.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/cart-items")
public class CartItemController {

    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CartItemDto>> createCartItem(@Valid @RequestBody CreateCartItemRequest request, Authentication authentication) {
        try {
            CartItemDto createdItem = cartItemService.createCartItem(
                    authentication.getName(),
                    request.getItemId(),
                    request.getQuantity()
            );
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Cart item created successfully", createdItem));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .body(new ApiResponse<>(false, e.getReason(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Failed to create cart item", null));
        }
    }

    @GetMapping("/cart/{cartId}")
    public ResponseEntity<ApiResponse<List<CartItemDto>>> getCartItemsByCartId(@PathVariable UUID cartId) {
        try {
            List<CartItemDto> cartItems = cartItemService.getCartItemsByCartId(cartId);
            return ResponseEntity.ok(new ApiResponse<>(true, "Cart items retrieved successfully", cartItems));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .body(new ApiResponse<>(false, e.getReason(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Failed to retrieve cart items", null));
        }
    }

}
