package com.hackathon.inditex.Services;

import com.hackathon.inditex.Entities.Coordinates;
import com.hackathon.inditex.Entities.Order;
import com.hackathon.inditex.Repositories.CenterRepository;
import com.hackathon.inditex.Repositories.OrderRepository;
import com.hackathon.inditex.dto.CoordinatesDTO;
import com.hackathon.inditex.dto.OrderDTO;
import com.hackathon.inditex.dto.OrderResponseDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

public class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CenterRepository centerRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
        orderRepository.deleteAll();
        centerRepository.deleteAll();
    }

    @Test
    void createOrder_Success() {

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setCustomerId(203L);
        orderDTO.setSize("B");
        CoordinatesDTO coordinatesDTO = new CoordinatesDTO();
        coordinatesDTO.setLatitude(51.5074);
        coordinatesDTO.setLongitude(-0.1278);
        orderDTO.setCoordinates(coordinatesDTO);

        Order savedOrder = new Order();
        savedOrder.setId(1L);
        savedOrder.setCustomerId(orderDTO.getCustomerId());
        savedOrder.setSize(orderDTO.getSize());
        savedOrder.setStatus("PENDING");
        Coordinates coordinates = new Coordinates();
        coordinates.setLatitude(orderDTO.getCoordinates().getLatitude());
        coordinates.setLongitude(orderDTO.getCoordinates().getLongitude());
        savedOrder.setCoordinates(coordinates);

        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);

        OrderResponseDTO result = orderService.createOrder(orderDTO);

        assertNotNull(result);
        assertEquals(1L, result.getOrderId());
        assertEquals(203L, result.getCustomerId());
        assertEquals("B", result.getSize());
        assertEquals("PENDING", result.getStatus());
        assertEquals("Order created successfully in PENDING status.", result.getMessage());
    }

    @Test
    void getAllOrders_Success() {
        Order order1 = new Order();
        order1.setId(1L);
        order1.setCustomerId(203L);
        order1.setSize("B");
        order1.setStatus("PENDING");
        Coordinates coordinates1 = new Coordinates();
        coordinates1.setLatitude(51.5074);
        coordinates1.setLongitude(-0.1278);
        order1.setCoordinates(coordinates1);

        Order order2 = new Order();
        order2.setId(2L);
        order2.setCustomerId(204L);
        order2.setSize("M");
        order2.setStatus("PENDING");
        Coordinates coordinates2 = new Coordinates();
        coordinates2.setLatitude(40.7128);
        coordinates2.setLongitude(-74.0060);
        order2.setCoordinates(coordinates2);

        when(orderRepository.findAll()).thenReturn(List.of(order1, order2));

        List<Order> result = orderService.getAllOrders();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(203L, result.get(0).getCustomerId());
        assertEquals("B", result.get(0).getSize());
        assertEquals("PENDING", result.get(0).getStatus());
        assertEquals(2L, result.get(1).getId());
        assertEquals(204L, result.get(1).getCustomerId());
        assertEquals("M", result.get(1).getSize());
        assertEquals("PENDING", result.get(1).getStatus());
    }
}
