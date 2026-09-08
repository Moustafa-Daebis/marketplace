package com.marketplace.marketplace.cart;

import com.marketplace.marketplace.user.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CartDto>> createCart(@jakarta.validation.Valid @RequestBody CreateCartRequest request) {
        try {
            CartDto created = cartService.createCart(request.getUserId());
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

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCart(@PathVariable UUID id) {
        try {
            cartService.deleteCart(id);
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
