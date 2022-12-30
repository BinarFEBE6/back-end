package org.binaracademy.finalproject.services;

import org.binaracademy.finalproject.dto.Request.OrderTicketRequest;
import org.binaracademy.finalproject.entity.OrderEntity;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    OrderEntity create(OrderTicketRequest orderTicketRequest);
    OrderEntity getById(Long orderId);
    List<OrderEntity> FindMyOrders(String email);

}
