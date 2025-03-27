package com.hackathon.inditex.Services;

import com.hackathon.inditex.dto.OrderAssignationResponseDTO;
import com.hackathon.inditex.dto.OrderDTO;
import com.hackathon.inditex.dto.OrderResponseDTO;

import java.util.List;

/**
 * Order Service Interface.
 */
public interface OrderService {

    OrderResponseDTO createOrder(OrderDTO orderDTO);
    List<OrderResponseDTO> getAllOrders();
    OrderAssignationResponseDTO assignOrders();
}
