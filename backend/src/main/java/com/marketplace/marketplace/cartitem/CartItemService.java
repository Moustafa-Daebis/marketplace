package com.marketplace.marketplace.cartitem;

import com.marketplace.marketplace.cart.CartEntity;
import com.marketplace.marketplace.cart.CartRepository;
import com.marketplace.marketplace.cart.CartService;
import com.marketplace.marketplace.item.ItemEntity;
import com.marketplace.marketplace.item.ItemRepository;
import jakarta.transaction.Transactional;
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
    private final CartService cartService;

    public CartItemService(CartItemRepository cartItemRepository, CartRepository cartRepository, ItemRepository itemRepository, CartService cartService) {
        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
        this.itemRepository = itemRepository;
        this.cartService = cartService;
    }
    @Transactional
    public CartItemDto createCartItem(String userEmail, UUID itemId, int quantity) {

        if (userEmail == null || userEmail.isBlank()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        if (itemId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Item id is required");
        }
        if (quantity <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantity must be greater than zero");
        }

        Optional<CartEntity> cartExists = cartRepository.findByUserEmail(userEmail);
        UUID cartId = null;
        if(cartExists.isPresent()){
            cartId = cartExists.get().getId();
        }else{
            cartId = cartService.createCart(userEmail).getId();
        }

        ItemEntity item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found"));

        Optional<CartItemEntity> existingItem = cartItemRepository.findByCart_IdAndItem_Id(cartId, itemId);

        if (existingItem.isPresent()) {
            CartItemEntity cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            return CartItemDto.fromEntity(cartItemRepository.save(cartItem));
        }
        Optional<CartEntity> cartEntity = cartRepository.findById(cartId);
        if(!cartEntity.isPresent()){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Error from backend");
        }
        CartItemEntity newCartItem = new CartItemEntity(cartEntity.get(), item, quantity);
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

    public List<CartItemDto> getCartItemsByUserId(UUID userId) {
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User id is required");
        }

        return cartItemRepository.findByUserId(userId)
                .stream()
                .map(CartItemDto::fromEntity)
                .toList();
    }

}
