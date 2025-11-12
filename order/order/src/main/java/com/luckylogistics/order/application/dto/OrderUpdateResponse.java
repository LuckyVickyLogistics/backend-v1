package com.luckylogistics.order.application.dto;

import java.util.UUID;

public record OrderUpdateResponse (
        UUID orderId,
        int quantity,
        String request
){

}
