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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Order Service Implementation.
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CenterRepository centerRepository;

    /**
     * Create Order.
     * @param orderDTO order to create
     * @return order createdDTO
     */
    @Override
    public OrderResponseDTO createOrder(OrderDTO orderDTO) {
        Order order = MapperOrder.toEntity(orderDTO);
        order.setStatus("PENDING");
        Order savedOrder = orderRepository.save(order);
        return MapperOrder.toOrderResponseDTO(savedOrder);
    }

    /**
     * Get All Orders.
     *
     * @return
     */
    @Override
    public List<OrderResponseDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(MapperOrder::toOrderResponseDTO)
                .toList();
    }

    /**
     * Assign Orders.
     * @return
     */
    @Override
    @Transactional
    public OrderAssignationResponseDTO assignOrders() {
        List<Order> pendingOrders = orderRepository.findAll().stream()
                .filter(order -> order.getStatus().equals("PENDING"))
                .sorted(Comparator.comparingLong(Order::getId))
                .toList();

        List<Center> availableCenters = centerRepository.findAll().stream()
                .filter(center -> center.getStatus().equals("AVAILABLE"))
                .toList();
        List<ProcessedOrderDTO> processedOrders = new ArrayList<>();

        for (Order order : pendingOrders) {
            ProcessedOrderDTO processedOrderDTO = new ProcessedOrderDTO();
            processedOrderDTO.setOrderId(order.getId());
            Center assignedCenter = null;
            Double distance = null;

            Optional<Center> centerOptional = availableCenters.stream()
                    .filter(center -> center.getCapacity().contains(order.getSize()) && center.getCurrentLoad() < center.getMaxCapacity()) // Comprobación con capacity
                    .min((c1, c2) -> {
                        double dist1 = calculateDistance(order, c1);
                        double dist2 = calculateDistance(order, c2);
                        return Double.compare(dist1, dist2);
                    });

            if (centerOptional.isPresent()) {
                assignedCenter = centerOptional.get();
                distance = calculateDistance(order, assignedCenter);
                assignedCenter.setCurrentLoad(assignedCenter.getCurrentLoad() + 1);
                centerRepository.save(assignedCenter);
                order.setAssignedCenter(assignedCenter.getId().toString()); // Guarda el ID como String
                order.setStatus("ASSIGNED");
                orderRepository.save(order);
                processedOrderDTO.setAssignedLogisticsCenter(assignedCenter.getName());
                processedOrderDTO.setDistance(distance);
                processedOrderDTO.setStatus("ASSIGNED");
            } else {
                if (availableCenters.stream().noneMatch(center -> center.getCapacity().contains(order.getSize()))) { // Comprobación con capacity
                    processedOrderDTO.setMessage("No available centers support the order type.");
                } else {
                    processedOrderDTO.setMessage("All centers are at maximum capacity.");
                }
                processedOrderDTO.setStatus("PENDING");
            }
            processedOrders.add(processedOrderDTO);
        }
        return MapperOrder.toOrderAssignationResponseDTO(processedOrders);
    }

    /**
     * Calculate distance between order and center.
     * @param order order to calculate distance
     * @param center center to calculate distance
     * @return distance between order and center
     */
    private Double calculateDistance(Order order, Center center) {
        double latitude1 = order.getCoordinates().getLatitude();
        double longitude1 = order.getCoordinates().getLongitude();
        double latitude2 = center.getCoordinates().getLatitude();
        double longitude2 = center.getCoordinates().getLongitude();
        double earthRadius = 6371;
        double distLat = Math.toRadians(latitude2 - latitude1);
        double distLon = Math.toRadians(longitude2 - longitude1);
        double a = Math.sin(distLat / 2) * Math.sin(distLat / 2) +
                Math.cos(Math.toRadians(latitude1)) * Math.cos(Math.toRadians(latitude2)) *
                        Math.sin(distLon / 2) * Math.sin(distLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return earthRadius * c;
    }
}
