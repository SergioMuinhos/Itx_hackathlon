package com.hackathon.inditex.entities.mappers;

import com.hackathon.inditex.entities.Center;
import com.hackathon.inditex.entities.Coordinates;
import com.hackathon.inditex.dto.CenterDTO;
import com.hackathon.inditex.dto.CenterResponseDTO;
import com.hackathon.inditex.dto.CoordinatesDTO;

/**
 * Center Mapper.
 */
public class MapperCenter {
    /**
     * Constructor.
     */
    private MapperCenter() {
    }

    /**
     * Mapper to Entity
     *
     * @param centerDTO
     * @return
     */
    public static Center toEntity(CenterDTO centerDTO) {
        var center = new Center();
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

    public static CenterResponseDTO toResponseDto(Center center) {
        var centerDTO = new CenterResponseDTO();
        centerDTO.setId(center.getId());
        centerDTO.setName(center.getName());
        centerDTO.setCapacity(center.getCapacity());
        centerDTO.setStatus(center.getStatus());
        centerDTO.setMaxCapacity(center.getMaxCapacity());
        centerDTO.setCurrentLoad(center.getCurrentLoad());

        var coordinatesDTO = new CoordinatesDTO();
        coordinatesDTO.setLatitude(center.getCoordinates().getLatitude());
        coordinatesDTO.setLongitude(center.getCoordinates().getLongitude());
        centerDTO.setCoordinates(coordinatesDTO);
        return centerDTO;
    }
}
