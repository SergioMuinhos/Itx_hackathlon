package com.hackathon.inditex.Services;

import com.hackathon.inditex.Entities.Center;
import com.hackathon.inditex.dto.CenterDTO;
import com.hackathon.inditex.dto.CenterResponseDTO;
import com.hackathon.inditex.dto.CenterUpdateDTO;
import com.hackathon.inditex.application.exceptions.CenterNotFoundException;
import com.hackathon.inditex.application.exceptions.CurrentLoadExceedsMaxCapacityException;
import com.hackathon.inditex.application.exceptions.DuplicateCenterException;
import com.hackathon.inditex.Entities.mappers.MapperCenter;
import com.hackathon.inditex.Repositories.CenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Center Service Implementation.
 */
@Service
@Transactional
public class CenterServiceImpl implements CenterService{

    @Autowired
    private CenterRepository centerRepository;

    @Override
    public CenterResponseDTO createCenter(CenterDTO centerDTO) {
        validateDuplicateCoordinates(centerDTO.getCoordinates().getLatitude(), centerDTO.getCoordinates().getLongitude());

        if(centerDTO.getCurrentLoad()>centerDTO.getMaxCapacity()){
            throw  new CurrentLoadExceedsMaxCapacityException("Current load cannot exceed max capacity.");
        }
        Center center = MapperCenter.toEntity(centerDTO);
        
        return MapperCenter.toResponseDto(centerRepository.save(center));
    }

    @Override
    public List<CenterResponseDTO> getAllCenters() {
        return centerRepository.findAll().stream()
                .map(MapperCenter::toResponseDto).toList();
    }

    @Override
    public CenterResponseDTO updateCenter(Long id, CenterUpdateDTO centerUpdateDTO) {
        var center = centerRepository.findById(id)
                .orElseThrow(() -> new CenterNotFoundException("Center not found."));
        if (centerUpdateDTO.getCoordinates() != null) {
            validateDuplicateCoordinates(centerUpdateDTO.getCoordinates().getLatitude(),
                    centerUpdateDTO.getCoordinates().getLongitude(), id);
        }
        updateCenterFields(centerUpdateDTO, center);
        centerRepository.save(center);
        return MapperCenter.toResponseDto(center);
    }

    @Override
    public void deleteCenter(Long id) {
    Center center = centerRepository.findById(id).orElseThrow(()-> new CenterNotFoundException("Center not found"));
    centerRepository.delete(center);
    }

    /**
     * Update Center  Fields
     * @param centerUpdateDTO
     * @param center
     */
    private void updateCenterFields( CenterUpdateDTO centerUpdateDTO, Center center) {

        if (centerUpdateDTO.getName() != null) {
            center.setName(centerUpdateDTO.getName());
        }
        if (centerUpdateDTO.getCapacity() != null) {
            center.setCapacity(centerUpdateDTO.getCapacity());
        }
        if (centerUpdateDTO.getStatus() != null) {
            center.setStatus(centerUpdateDTO.getStatus());
        }
        if (centerUpdateDTO.getCoordinates() != null) {
            center.getCoordinates().setLatitude(centerUpdateDTO.getCoordinates().getLatitude());
            center.getCoordinates().setLongitude(centerUpdateDTO.getCoordinates().getLongitude());
        }
        if(centerUpdateDTO.getCurrentLoad()!=null && !centerUpdateDTO.getCurrentLoad().equals(center.getCurrentLoad())){
            center.setCurrentLoad(centerUpdateDTO.getCurrentLoad());
        }
        if(centerUpdateDTO.getMaxCapacity()!=null && !centerUpdateDTO.getMaxCapacity().equals(center.getMaxCapacity())){
            center.setMaxCapacity(centerUpdateDTO.getMaxCapacity());
        }
    }

    /**
     *
     * @param latitude
     * @param longitude
     */
    private void validateDuplicateCoordinates(double latitude, double longitude) {
        if (centerRepository.findByCoordinatesLatitudeAndCoordinatesLongitude(latitude, longitude).isPresent()) {
            throw new DuplicateCenterException("There is already a logistics center in that position.");
        }
    }

    /**
     *
     * @param latitude
     * @param longitude
     * @param id
     */
    private void validateDuplicateCoordinates(double latitude, double longitude, Long id) {
        centerRepository.findByCoordinatesLatitudeAndCoordinatesLongitude(latitude, longitude)
                .ifPresent(existingCenter -> {
                    if (!existingCenter.getId().equals(id)) {
                        throw new DuplicateCenterException("There is already a logistics center in that position.");
                    }
                });
    }

}
