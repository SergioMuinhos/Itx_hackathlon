package com.hackathon.inditex.mappers;

import com.hackathon.inditex.Entities.Center;
import com.hackathon.inditex.Entities.Coordinates;
import com.hackathon.inditex.dto.CenterDTO;

public class MapperCenter {
    public static Center getCenter(CenterDTO centerDTO) {
        Center center = new Center();
        center.setName(centerDTO.getName());
        center.setCapacity(centerDTO.getCapacity());
        center.setStatus(centerDTO.getStatus());
        center.setMaxCapacity(centerDTO.getMaxCapacity());
        center.setCurrentLoad(centerDTO.getCurrentLoad());

        Coordinates coordinates = new Coordinates();
        coordinates.setLatitude(centerDTO.getCoordinates().getLatitude());
        coordinates.setLongitude(centerDTO.getCoordinates().getLongitude());
        center.setCoordinates(coordinates);
        return center;
    }
}
