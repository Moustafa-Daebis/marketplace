package com.marketplace.marketplace.orderitem;

import com.marketplace.marketplace.user.UserDto;
import com.marketplace.marketplace.user.UserEntity;
import com.marketplace.marketplace.user.UserService;
import jakarta.transaction.Transactional;
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
    @Transactional
    public OrderItemDto changeOrderItemStatus(UUID orderItemId,String userEmail, String status){

        Optional<UserDto> user = userService.getUserByEmail(userEmail);
        if(!user.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        Optional<UUID> itemSellerId = orderItemRepository.findSellerIdByOrderItemId(orderItemId);
        if(!itemSellerId.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found");
        }
        if(!itemSellerId.get().equals(user.get().getId())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authorized to change the status of this order");
        }
        Optional<OrderItemEntity> orderItem = orderItemRepository.findById(orderItemId);

        if(orderItem.isPresent()){
            orderItem.get().setStatus(status);
            return OrderItemDto.fromEntity(orderItem.get());
        }else{
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error from backend");
        }




    }
}
