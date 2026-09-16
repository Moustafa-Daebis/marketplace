package com.marketplace.marketplace.item;

import com.marketplace.marketplace.user.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ItemDto>> createItem(@Valid @RequestBody ItemRequest request, Authentication authentication) {
        try {
            ItemDto createdItem = itemService.createItem(authentication.getName(),
                    request.getName(),
                    request.getDescription(),
                    request.getPrice()
            );

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Item created successfully", createdItem));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode())
                    .body(new ApiResponse<>(false, e.getReason(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Failed to create item", null));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ItemDto>>> getAllItems() {
        try {
            List<ItemDto> items = itemService.getAllItems();
            return ResponseEntity.ok(new ApiResponse<>(true, "Items retrieved successfully", items));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Failed to retrieve items", null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ItemDto>> getItemById(@PathVariable java.util.UUID id) {
        try {
            Optional<ItemDto> itemDto = itemService.getItemById(id);
            if (itemDto.isPresent()) {
                return ResponseEntity.ok(new ApiResponse<>(true, "Item retrieved successfully", itemDto.get()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Item not found", null));
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
}
