package com.hackathon.inditex.Services;

import com.hackathon.inditex.Entities.Center;
import com.hackathon.inditex.Entities.Order;
import com.hackathon.inditex.Entities.mappers.MapperOrder;
import com.hackathon.inditex.Repositories.CenterRepository;
import com.hackathon.inditex.Repositories.OrderRepository;
import com.hackathon.inditex.dto.OrderAssignationResponseDTO;
import com.hackathon.inditex.dto.OrderDTO;
import com.hackathon.inditex.dto.OrderResponseDTO;
import com.hackathon.inditex.dto.ProcessedOrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CenterRepository centerRepository;

    @Override
    public OrderResponseDTO createOrder(OrderDTO orderDTO) {
        Order order = MapperOrder.getOrder(orderDTO);
        order.setStatus("PENDING");
        Order savedOrder = orderRepository.save(order);
        return MapperOrder.getOrderResponseDTO(savedOrder);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public OrderAssignationResponseDTO assignOrders() {
        return null;
    }

   /* @Override
    public OrderAssignationResponseDTO assignOrders() {
        List<Order> pendingOrders = orderRepository.findAll().stream().filter(order -> order.getStatus().equals("PENDING")).toList();
        List<Center> availableCenters = centerRepository.findAll().stream().filter(center -> center.getStatus().equals("AVAILABLE")).toList();
        List<ProcessedOrderDTO> processedOrders = new ArrayList<>();

        for (Order order : pendingOrders) {
            ProcessedOrderDTO processedOrderDTO = new ProcessedOrderDTO();
            processedOrderDTO.setOrderId(order.getId());
            Center assignedCenter = null;
            Double distance = null;

            for (Center center : availableCenters) {
                if (center.supportsOrderType(order.getSize())) {
                    if (assignedCenter == null) {
                        assignedCenter = center;
                        distance = calculateDistance(order, center);
                    } else {
                        Double currentDistance = calculateDistance(order, center);
                        if (currentDistance < distance) {
                            assignedCenter = center;
                            distance = currentDistance;
                        }
                    }
                }
            }
            if (assignedCenter != null) {
                processedOrderDTO.setAssignedLogisticsCenter(assignedCenter.getName());
                processedOrderDTO.setDistance(distance);
                processedOrderDTO.setStatus("ASSIGNED");
                order.setAssignedCenter(assignedCenter.getId().toString());
                order.setStatus("ASSIGNED");
                orderRepository.save(order);
            } else {
                if (availableCenters.isEmpty()) {
                    processedOrderDTO.setMessage("All centers are at maximum capacity.");
                } else {
                    processedOrderDTO.setMessage("No available centers support the order type.");
                }
                processedOrderDTO.setStatus("PENDING");
            }
            processedOrders.add(processedOrderDTO);
        }
        return MapperOrder.getOrderAssignationResponseDTO(processedOrders);
    }*/

    private Double calculateDistance(Order order, Center center) {
        // Calculate the distance between the order and the center
        // Using the Haversine formula
        double lat1 = order.getCoordinates().getLatitude();
        double lon1 = order.getCoordinates().getLongitude();
        double lat2 = center.getCoordinates().getLatitude();
        double lon2 = center.getCoordinates().getLongitude();
        double earthRadius = 6371; // in kilometers
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return earthRadius * c;
    }
}
