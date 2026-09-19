package com.marketplace.marketplace.cart;

import com.marketplace.marketplace.user.UserEntity;
import com.marketplace.marketplace.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    public CartService(CartRepository cartRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }

    public CartDto createCart(String userEmail) {
        if(userEmail==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User Email is required");
        }

        UserEntity user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (cartRepository.findByUserId(user.getId()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cart already exists for user");
        }

        CartEntity cart = new CartEntity(user);
        return CartDto.fromEntity(cartRepository.save(cart));
    }

    public void deleteCart(String userEmail) {

        CartEntity cart = cartRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found"));
        cartRepository.delete(cart);
    }

    public Optional<CartDto> getCartById(UUID id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cart id is required");
        }
        return cartRepository.findById(id).map(CartDto::fromEntity);
    }

    public Optional<CartDto> getCartByUserId(UUID userId) {
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User id is required");
        }
        return cartRepository.findByUserId(userId).map(CartDto::fromEntity);
    }
}
