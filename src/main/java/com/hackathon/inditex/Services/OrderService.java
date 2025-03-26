package com.hackathon.inditex.Services;

import com.hackathon.inditex.Entities.Order;
import com.hackathon.inditex.dto.OrderAssignationResponseDTO;
import com.hackathon.inditex.dto.OrderDTO;
import com.hackathon.inditex.dto.OrderResponseDTO;

import java.util.List;

public interface OrderService {

    OrderResponseDTO createOrder(OrderDTO orderDTO);
    List<Order> getAllOrders();
    OrderAssignationResponseDTO assignOrders();
}
