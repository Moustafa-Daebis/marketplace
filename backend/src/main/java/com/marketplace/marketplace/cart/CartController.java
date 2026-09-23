package com.marketplace.marketplace.cart;

import com.marketplace.marketplace.authentication.entity.User;
import com.marketplace.marketplace.cartitem.CartItemDto;
import com.marketplace.marketplace.cartitem.CartItemService;
import com.marketplace.marketplace.user.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    private final CartService cartService;
    private final CartItemService cartItemService;

    public CartController(CartService cartService, CartItemService cartItemService) {
        this.cartService = cartService;
        this.cartItemService = cartItemService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CartDto>> createCart(Authentication authentication) {
        try {
            CartDto created = cartService.createCart(authentication.getName());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Cart created successfully", created));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .body(new ApiResponse<>(false, e.getReason(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Failed to create cart", null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CartDto>> getCartById(@PathVariable UUID id) {
        try {
            Optional<CartDto> dto = cartService.getCartById(id);
            if (dto.isPresent()) {
                return ResponseEntity.ok(new ApiResponse<>(true, "Cart retrieved successfully", dto.get()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Cart not found", null));
            }
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .body(new ApiResponse<>(false, e.getReason(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Failed to retrieve cart", null));
        }
    }

    @GetMapping("/items")
    public ResponseEntity<ApiResponse<List<CartItemDto>>> getCart(Authentication authentication) {
        try {
            User user = (User)authentication.getPrincipal();
            List<CartItemDto> cartItemDtos = cartItemService.getCartItemsByUserId(user.getId());

            if (!cartItemDtos.isEmpty()) {
                return ResponseEntity.ok(new ApiResponse<>(true, "Cart retrieved successfully", cartItemDtos));
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body(new ApiResponse<>(false, "Cart not found", null));
            }
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .body(new ApiResponse<>(false, e.getReason(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Failed to retrieve cart", null));
        }
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteCart(Authentication authentication) {
        try {
            cartService.deleteCart(authentication.getName());
            return ResponseEntity.ok(new ApiResponse<>(true, "Cart deleted successfully", null));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .body(new ApiResponse<>(false, e.getReason(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Failed to delete cart", null));
        }
    }
}
