package com.marketplace.marketplace.orderitem;

import com.marketplace.marketplace.user.UserDto;
import com.marketplace.marketplace.user.UserEntity;
import com.marketplace.marketplace.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderItemService {

    private final UserService userService;
    private final OrderItemRepository orderItemRepository;

    public OrderItemService(UserService userService, OrderItemRepository orderItemRepository) {
        this.userService = userService;
        this.orderItemRepository = orderItemRepository;
    }

    public List<OrderItemDto>  getAllOrderItemDTOSByEmail(String userEmail){

        Optional<UserDto> user = userService.getUserByEmail(userEmail);
        if(user.isPresent()){
            Optional<OrderItemEntity> list = orderItemRepository.findOrdersItemsBySellerId(user.get().getId());
            if(list.isPresent()){
                return list.stream()
                        .map(OrderItemDto::fromEntity)
                        .collect(Collectors.toList());
            }else{
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No orders found");
            }
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

    }

    public OrderItemDto changeOrderItemStatus(UUID orderItemId, UUID user, String status, Authentication authentication){

        return null;
    }
}
