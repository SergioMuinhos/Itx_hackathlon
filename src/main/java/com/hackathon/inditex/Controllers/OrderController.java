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
     * @return ResponseEntity Listo of OrderAssignationResponseDTO
     */
    @PostMapping("/order-assignations")
    public ResponseEntity<OrderAssignationResponseDTO> assignOrders() {
        return ResponseEntity.ok(orderService.assignOrders());
    }

    /**
     * Create Order
     * @param orderDTO order to create
     * @return OrderResponseDTO
     */
    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderDTO orderDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderService.createOrder(orderDTO));
    }

    /**
     * Get All Orders
     * @return List of OrderResponseDTO
     */
    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }
}
