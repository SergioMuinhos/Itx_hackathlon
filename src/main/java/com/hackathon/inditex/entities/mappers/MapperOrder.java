package com.hackathon.inditex.entities.mappers;

import com.hackathon.inditex.entities.Coordinates;
import com.hackathon.inditex.entities.Order;
import com.hackathon.inditex.dto.*;

import java.util.List;

/**
 * Order Mapper.
 */
public class MapperOrder {

    /**
     * Constructor.
     */
    private MapperOrder() {
    }

    /**
     * @param orderDTO
     * @return
     */
    public static Order toEntity(OrderDTO orderDTO) {
        var order = new Order();
        order.setCustomerId(orderDTO.getCustomerId());
        order.setSize(orderDTO.getSize());
        order.setCoordinates(getCoordinates(orderDTO.getCoordinates()));
        return order;
    }

    /**
     * @param coordinatesDTO
     * @return
     */
    public static Coordinates getCoordinates(CoordinatesDTO coordinatesDTO) {
        var coordinates = new Coordinates();
        coordinates.setLatitude(coordinatesDTO.getLatitude());
        coordinates.setLongitude(coordinatesDTO.getLongitude());
        return coordinates;
    }

    /**
     * @param order
     * @return
     */
    public static OrderResponseDTO toOrderResponseDTO(Order order) {
        var orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setOrderId(order.getId());
        orderResponseDTO.setCustomerId(order.getCustomerId());
        orderResponseDTO.setSize(order.getSize());
        orderResponseDTO.setAssignedLogisticsCenter(order.getAssignedCenter());
        orderResponseDTO.setCoordinates(getCoordinatesDTO(order.getCoordinates()));
        orderResponseDTO.setStatus(order.getStatus());
        orderResponseDTO.setMessage("Order created successfully in PENDING status.");
        return orderResponseDTO;
    }

    /**
     * @param coordinates
     * @return
     */
    public static CoordinatesDTO getCoordinatesDTO(Coordinates coordinates) {
        var coordinatesDTO = new CoordinatesDTO();
        coordinatesDTO.setLatitude(coordinates.getLatitude());
        coordinatesDTO.setLongitude(coordinates.getLongitude());
        return coordinatesDTO;
    }

    public static OrderAssignationResponseDTO toOrderAssignationResponseDTO(List<ProcessedOrderDTO> processedOrders) {
        var orderAssignationResponseDTO = new OrderAssignationResponseDTO();
        orderAssignationResponseDTO.setProcessedOrders(processedOrders);
        return orderAssignationResponseDTO;
    }
}
