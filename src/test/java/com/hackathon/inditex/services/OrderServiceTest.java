package com.hackathon.inditex.services;

import com.hackathon.inditex.entities.Center;
import com.hackathon.inditex.entities.Coordinates;
import com.hackathon.inditex.entities.Order;
import com.hackathon.inditex.repositories.CenterRepository;
import com.hackathon.inditex.repositories.OrderRepository;
import com.hackathon.inditex.dto.CoordinatesDTO;
import com.hackathon.inditex.dto.OrderAssignationResponseDTO;
import com.hackathon.inditex.dto.OrderDTO;
import com.hackathon.inditex.dto.OrderResponseDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

 class OrderServiceTest {
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

        List<OrderResponseDTO> result = orderService.getAllOrders();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getOrderId());
        assertEquals(203L, result.get(0).getCustomerId());
        assertEquals("B", result.get(0).getSize());
        assertEquals("PENDING", result.get(0).getStatus());
        assertEquals(2L, result.get(1).getOrderId());
        assertEquals(204L, result.get(1).getCustomerId());
        assertEquals("M", result.get(1).getSize());
        assertEquals("PENDING", result.get(1).getStatus());
    }


    @Test
    void assignOrders_Success() {
        Order order1 = new Order();
        order1.setId(1L);
        order1.setCustomerId(204L);
        order1.setSize("B");
        order1.setStatus("PENDING");
        Coordinates coordinates1 = new Coordinates();
        coordinates1.setLatitude(51.5074);
        coordinates1.setLongitude(-0.1278);
        order1.setCoordinates(coordinates1);

        Order order2 = new Order();
        order2.setId(2L);
        order2.setCustomerId(205L);
        order2.setSize("B");
        order2.setStatus("PENDING");
        Coordinates coordinates2 = new Coordinates();
        coordinates2.setLatitude(52.5074);
        coordinates2.setLongitude(-1.1278);
        order2.setCoordinates(coordinates2);

        List<Order> orders = new ArrayList<>();
        orders.add(order1);
        orders.add(order2);

        Center center1 = new Center();
        center1.setId(1L);
        center1.setName("Center France");
        center1.setCapacity("B");
        center1.setStatus("AVAILABLE");
        center1.setCurrentLoad(4);
        center1.setMaxCapacity(5);
        Coordinates centerCoordinates1 = new Coordinates();
        centerCoordinates1.setLatitude(47.8566);
        centerCoordinates1.setLongitude(2.3522);
        center1.setCoordinates(centerCoordinates1);

        List<Center> centers = new ArrayList<>();
        centers.add(center1);

        when(orderRepository.findAll()).thenReturn(orders);
        when(centerRepository.findAll()).thenReturn(centers);
        when(centerRepository.save(any(Center.class))).thenReturn(center1);
        when(orderRepository.save(any(Order.class))).thenReturn(order1);

        OrderAssignationResponseDTO result = orderService.assignOrders();

        assertNotNull(result);
        assertEquals(2, result.getProcessedOrders().size());
        assertEquals("PENDING", result.getProcessedOrders().get(0).getStatus());
        assertEquals("Center France", result.getProcessedOrders().get(0).getAssignedLogisticsCenter());
        assertEquals("PENDING", result.getProcessedOrders().get(1).getStatus());
        assertEquals("All centers are at maximum capacity.", result.getProcessedOrders().get(1).getMessage());
    }
    @Test
    void assignOrders_NoCentersSupportOrderType() {
        Order order1 = new Order();
        order1.setId(2L);
        order1.setCustomerId(204L);
        order1.setSize("S");
        order1.setStatus("PENDING");
        Coordinates coordinates1 = new Coordinates();
        coordinates1.setLatitude(51.5074);
        coordinates1.setLongitude(-0.1278);
        order1.setCoordinates(coordinates1);

        List<Order> orders = new ArrayList<>();
        orders.add(order1);

        Center center1 = new Center();
        center1.setId(1L);
        center1.setName("Center France");
        center1.setCapacity("B");
        center1.setStatus("AVAILABLE");
        center1.setCurrentLoad(0);
        center1.setMaxCapacity(5);
        Coordinates centerCoordinates1 = new Coordinates();
        centerCoordinates1.setLatitude(47.8566);
        centerCoordinates1.setLongitude(2.3522);
        center1.setCoordinates(centerCoordinates1);

        List<Center> centers = new ArrayList<>();
        centers.add(center1);

        when(orderRepository.findAll()).thenReturn(orders);
        when(centerRepository.findAll()).thenReturn(centers);

        OrderAssignationResponseDTO result = orderService.assignOrders();

        assertNotNull(result);
        assertEquals(1, result.getProcessedOrders().size());
        assertEquals("PENDING", result.getProcessedOrders().get(0).getStatus());
        assertEquals("No available centers support the order type.", result.getProcessedOrders().get(0).getMessage());
    }
}
