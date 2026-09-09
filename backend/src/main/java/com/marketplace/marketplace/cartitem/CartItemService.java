package com.marketplace.marketplace.cartitem;

import com.marketplace.marketplace.cart.CartEntity;
import com.marketplace.marketplace.cart.CartRepository;
import com.marketplace.marketplace.item.ItemEntity;
import com.marketplace.marketplace.item.ItemRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;

    public CartItemService(CartItemRepository cartItemRepository, CartRepository cartRepository, ItemRepository itemRepository) {
        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
        this.itemRepository = itemRepository;
    }

    public CartItemDto createCartItem(UUID cartId, UUID itemId, int quantity) {
        if (cartId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cart id is required");
        }
        if (itemId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Item id is required");
        }
        if (quantity <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantity must be greater than zero");
        }

        CartEntity cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found"));
        ItemEntity item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found"));

        Optional<CartItemEntity> existingItem = cartItemRepository.findByCart_IdAndItem_Id(cartId, itemId);

        if (existingItem.isPresent()) {
            CartItemEntity cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            return CartItemDto.fromEntity(cartItemRepository.save(cartItem));
        }

        CartItemEntity newCartItem = new CartItemEntity(cart, item, quantity);
        return CartItemDto.fromEntity(cartItemRepository.save(newCartItem));
    }

    public List<CartItemDto> getCartItemsByCartId(UUID cartId) {
        if (cartId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cart id is required");
        }

        cartRepository.findById(cartId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found"));

        return cartItemRepository.findByCart_Id(cartId).stream()
                .map(CartItemDto::fromEntity)
                .toList();
    }

}
