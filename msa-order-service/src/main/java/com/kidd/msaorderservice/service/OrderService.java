package com.kidd.msaorderservice.service;

import com.kidd.msaorderservice.dto.OrderDto;
import com.kidd.msaorderservice.repository.OrderEntity;

public interface OrderService {
    OrderDto createOrder(OrderDto orderDto);
    OrderDto getOrderByOrderId(String orderId);
    Iterable<OrderEntity> getOrdersByUserId(String userId);
}
