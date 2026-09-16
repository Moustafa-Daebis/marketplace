package com.marketplace.marketplace.orderitem;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class ChangeOrderItemStatusRequest {
    UUID orderItemId;
    String status;

    public ChangeOrderItemStatusRequest() {
    }

    public ChangeOrderItemStatusRequest(UUID orderItemId, String status) {
        this.orderItemId = orderItemId;
        this.status = status;
    }
}
