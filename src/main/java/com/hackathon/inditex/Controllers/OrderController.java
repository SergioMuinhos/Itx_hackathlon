package com.hackathon.inditex.Controllers;

import com.hackathon.inditex.Entities.Order;
import com.hackathon.inditex.Services.OrderService;
import com.hackathon.inditex.dto.OrderAssignationResponseDTO;
import com.hackathon.inditex.dto.OrderDTO;
import com.hackathon.inditex.dto.OrderResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Order Controller.
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * Assign Orders
     * @return
     */
    @PostMapping("/order-assignations")
    public ResponseEntity<OrderAssignationResponseDTO> assignOrders() {
        OrderAssignationResponseDTO orderAssignationResponseDTO = orderService.assignOrders();
        return ResponseEntity.ok(orderAssignationResponseDTO);
    }

    /**
     * Create Order
     * @param orderDTO order to create
     * @return
     */
    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderDTO orderDTO) {
        OrderResponseDTO orderResponseDTO = orderService.createOrder(orderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderResponseDTO);
    }

    /**
     * Get All Orders
     * @return
     */
    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
        List<OrderResponseDTO> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }
}
