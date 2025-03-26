package com.hackathon.inditex.Entities.mappers;

import com.hackathon.inditex.Entities.Coordinates;
import com.hackathon.inditex.Entities.Order;
import com.hackathon.inditex.dto.*;

import java.util.List;

public class MapperOrder {

    public static Order getOrder(OrderDTO orderDTO) {
        Order order = new Order();
        order.setCustomerId(orderDTO.getCustomerId());
        order.setSize(orderDTO.getSize());
        order.setCoordinates(getCoordinates(orderDTO.getCoordinates()));
        return order;
    }

    public static Coordinates getCoordinates(CoordinatesDTO coordinatesDTO) {
        Coordinates coordinates = new Coordinates();
        coordinates.setLatitude(coordinatesDTO.getLatitude());
        coordinates.setLongitude(coordinatesDTO.getLongitude());
        return coordinates;
    }

    public static OrderResponseDTO getOrderResponseDTO(Order order) {
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setOrderId(order.getId());
        orderResponseDTO.setCustomerId(order.getCustomerId());
        orderResponseDTO.setSize(order.getSize());
        orderResponseDTO.setAssignedLogisticsCenter(order.getAssignedCenter());
        orderResponseDTO.setCoordinates(getCoordinatesDTO(order.getCoordinates()));
        orderResponseDTO.setStatus(order.getStatus());
        orderResponseDTO.setMessage("Order created successfully in PENDING status.");
        return orderResponseDTO;
    }

    public static CoordinatesDTO getCoordinatesDTO(Coordinates coordinates) {
        CoordinatesDTO coordinatesDTO = new CoordinatesDTO();
        coordinatesDTO.setLatitude(coordinates.getLatitude());
        coordinatesDTO.setLongitude(coordinates.getLongitude());
        return coordinatesDTO;
    }

    public static OrderAssignationResponseDTO getOrderAssignationResponseDTO(List<ProcessedOrderDTO> processedOrders) {
        OrderAssignationResponseDTO orderAssignationResponseDTO = new OrderAssignationResponseDTO();
        orderAssignationResponseDTO.setProcessedOrders(processedOrders);
        return orderAssignationResponseDTO;
    }
}
