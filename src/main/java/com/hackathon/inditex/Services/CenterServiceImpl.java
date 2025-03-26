package com.hackathon.inditex.Services;

import com.hackathon.inditex.Entities.Center;
import com.hackathon.inditex.dto.CenterDTO;
import com.hackathon.inditex.dto.CenterUpdateDTO;
import com.hackathon.inditex.Controllers.exceptions.CenterNotFoundException;
import com.hackathon.inditex.Controllers.exceptions.CurrentLoadExceedsMaxCapacityException;
import com.hackathon.inditex.Controllers.exceptions.DuplicateCenterException;
import com.hackathon.inditex.Entities.mappers.MapperCenter;
import com.hackathon.inditex.Repositories.CenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CenterServiceImpl implements CenterService{

    @Autowired
    private CenterRepository centerRepository;

    @Override
    public Center createCenter(CenterDTO centerDTO) {
        if(centerRepository.findByCoordinatesLatitudeAndCoordinatesLongitude(
                centerDTO.getCoordinates().getLatitude(),
                centerDTO.getCoordinates().getLongitude()
                ).isPresent()){
            throw  new DuplicateCenterException("There is already a logistics center in that position.");
        }
        if(centerDTO.getCurrentLoad()>centerDTO.getMaxCapacity()){
            throw  new CurrentLoadExceedsMaxCapacityException("Current load cannot exceed max capacity.");
        }
        Center center = MapperCenter.getCenter(centerDTO);
        
        return centerRepository.save(center);
    }

    @Override
    public List<Center> getAllCenters() {
        return centerRepository.findAll();
    }

    @Override
    public Center updateCenter(Long id, CenterUpdateDTO centerUpdateDTO) {
        Center center = centerRepository.findById(id).orElseThrow(() -> new CenterNotFoundException("Center not found."));
        Optional<Center> existingCenter = centerRepository.findByCoordinatesLatitudeAndCoordinatesLongitude(
                centerUpdateDTO.getCoordinates().getLatitude(),
                centerUpdateDTO.getCoordinates().getLongitude()
        );
        if (existingCenter.isPresent() && !existingCenter.get().getId().equals(id)) {
            throw new DuplicateCenterException("There is already a logistics center in that position.");
        }
        center.setName(centerUpdateDTO.getName());
        center.setCapacity(centerUpdateDTO.getCapacity());
        center.setStatus(centerUpdateDTO.getStatus());
        center.getCoordinates().setLatitude(centerUpdateDTO.getCoordinates().getLatitude());
        center.getCoordinates().setLongitude(centerUpdateDTO.getCoordinates().getLongitude());
        return centerRepository.save(center);
    }

    @Override
    public void deleteCenter(Long id) {
    Center center = centerRepository.findById(id).orElseThrow(()-> new CenterNotFoundException("Center not found"));
    centerRepository.delete(center);
    }
}
