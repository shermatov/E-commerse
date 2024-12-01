package com.shermatov.dreamshops.service.order;

import com.shermatov.dreamshops.dto.OrderDto;
import com.shermatov.dreamshops.model.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId);
    OrderDto getOrder(Long id);

    List<OrderDto> getUserOrders(Long userId);

    OrderDto convertToDto(Order order);
}
