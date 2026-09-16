package com.marketplace.marketplace.orderitem;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orderitems")
public class OrderItemController {

    private  final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @GetMapping
    public List<OrderItemDto> getAllOrderItemsByUserEmail(Authentication authentication){
        List<OrderItemDto> list = orderItemService.getAllOrderItemDTOSByEmail(authentication.getName());

        return list;
    }

    @PatchMapping
    public OrderItemDto changeOrderItemStatus(Authentication authentication, @Valid @RequestBody ChangeOrderItemStatusRequest request){
        OrderItemDto orderItemDto = orderItemService.changeOrderItemStatus(request.getOrderItemId(),authentication.getName(),request.getStatus());

        return orderItemDto;
    }

}
