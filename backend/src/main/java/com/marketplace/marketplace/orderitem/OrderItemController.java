package com.marketplace.marketplace.orderitem;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

}
