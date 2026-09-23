package com.marketplace.marketplace.order;

import com.marketplace.marketplace.cart.CartEntity;
import com.marketplace.marketplace.cart.CartRepository;
import com.marketplace.marketplace.cartitem.CartItemEntity;
import com.marketplace.marketplace.cartitem.CartItemRepository;
import com.marketplace.marketplace.item.ItemRepository;
import com.marketplace.marketplace.orderitem.OrderItemEntity;
import com.marketplace.marketplace.orderitem.OrderItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.RecursiveTask;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ItemRepository itemRepository;
    private final OrderItemRepository orderItemRepository;


    public OrderService(OrderRepository orderRepository,
                        CartRepository cartRepository,
                        CartItemRepository cartItemRepository, ItemRepository itemRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.itemRepository = itemRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Transactional
    public OrderDto createOrder(UUID userId) {

        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "userId not found");
        }

        Optional<CartEntity> cartEntity = cartRepository.findByUserId(userId);
        if(!cartEntity.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not required");
        }
        UUID cartId = cartEntity.get().getId();
        if (cartId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cart id is required");
        }

        CartEntity cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found"));

        if (cart.getUser() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cart is not assigned to a user");
        }
        System.out.println("Cart found: " + cart.getId() + ", User: " + cart.getUser().getId());
        List<CartItemEntity> cartItems = cartItemRepository.findByCart_Id(cartId);
        if (cartItems == null || cartItems.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cart is empty");
        }

        BigDecimal totalAmount = BigDecimal.ZERO;

        OrderEntity order = new OrderEntity(cart.getUser(), totalAmount);
        orderRepository.save(order);
        List<OrderItemEntity> orderItems = cartItems.stream()
                .map(cartItem -> new OrderItemEntity(order.getId(),order,itemRepository.findById(cartItem.getItem().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found")), cartItem.getQuantity(),new BigDecimal(0),order.getStatus()))
                .toList();
        orderItemRepository.saveAll(orderItems);

        cartItemRepository.deleteAll(cartItems);
        cartRepository.delete(cart);


        return OrderDto.fromEntity(order);
    }
}
