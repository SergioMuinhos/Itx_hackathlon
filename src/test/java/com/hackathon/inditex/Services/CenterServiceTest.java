package com.hackathon.inditex.Services;

import com.hackathon.inditex.Entities.Center;
import com.hackathon.inditex.Entities.Coordinates;
import com.hackathon.inditex.dto.CenterDTO;
import com.hackathon.inditex.dto.CenterResponseDTO;
import com.hackathon.inditex.dto.CenterUpdateDTO;
import com.hackathon.inditex.dto.CoordinatesDTO;
import com.hackathon.inditex.application.exceptions.CenterNotFoundException;
import com.hackathon.inditex.application.exceptions.CurrentLoadExceedsMaxCapacityException;
import com.hackathon.inditex.application.exceptions.DuplicateCenterException;
import com.hackathon.inditex.Repositories.CenterRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CenterServiceTest {
    @Mock
    private CenterRepository centerRepository;

    @InjectMocks
    private CenterServiceImpl centerService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown(){
        centerRepository.deleteAll();
    }


    @Test
    void createCenter_Success(){
        CenterDTO centerDTO = new CenterDTO();
        centerDTO.setName("Center A");
        centerDTO.setCapacity("MS");
        centerDTO.setStatus("AVAILABLE");
        centerDTO.setMaxCapacity(5);
        centerDTO.setCurrentLoad(4);
        CoordinatesDTO coordinatesDTO = new CoordinatesDTO();
        coordinatesDTO.setLatitude(42.3601);
        coordinatesDTO.setLongitude(-71.0589);
        centerDTO.setCoordinates(coordinatesDTO);

        when(centerRepository.findByCoordinatesLatitudeAndCoordinatesLongitude(any(Double.class), any(Double.class)))
                .thenReturn(Optional.empty());

        Center savedCenter = new Center();
        savedCenter.setId(1L);
        savedCenter.setName(centerDTO.getName());
        savedCenter.setCapacity(centerDTO.getCapacity());
        savedCenter.setStatus(centerDTO.getStatus());
        savedCenter.setMaxCapacity(centerDTO.getMaxCapacity());
        savedCenter.setCurrentLoad(centerDTO.getCurrentLoad());
        Coordinates coordinates = new Coordinates();
        coordinates.setLatitude(centerDTO.getCoordinates().getLatitude());
        coordinates.setLongitude(centerDTO.getCoordinates().getLongitude());
        savedCenter.setCoordinates(coordinates);

        when(centerRepository.save(any(Center.class))).thenReturn(savedCenter);
        CenterResponseDTO result = centerService.createCenter(centerDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Center A", result.getName());
    }

    @Test
    void createCenter_DuplicateCenter(){
        CenterDTO centerDTO = new CenterDTO();
        centerDTO.setName("Center A");
        centerDTO.setCapacity("MS");
        centerDTO.setStatus("AVAILABLE");
        centerDTO.setMaxCapacity(5);
        centerDTO.setCurrentLoad(4);
        CoordinatesDTO coordinatesDTO = new CoordinatesDTO();
        coordinatesDTO.setLatitude(42.3601);
        coordinatesDTO.setLongitude(-71.0589);
        centerDTO.setCoordinates(coordinatesDTO);

        Center existingCenter = new Center();
        existingCenter.setId(1L);
        existingCenter.setName("Center A");
        existingCenter.setCapacity("MS");
        existingCenter.setStatus("AVAILABLE");
        existingCenter.setMaxCapacity(5);
        existingCenter.setCurrentLoad(4);
        Coordinates coordinates = new Coordinates();
        coordinates.setLatitude(42.3601);
        coordinates.setLongitude(-71.0589);
        existingCenter.setCoordinates(coordinates);

        when(centerRepository.findByCoordinatesLatitudeAndCoordinatesLongitude(any(Double.class), any(Double.class)))
                .thenReturn(Optional.of(existingCenter));
        assertThrows(DuplicateCenterException.class, () -> centerService.createCenter(centerDTO));
    }


    @Test
    void createCenter_CurrentLoadExceedsMaxCapacity() {
        CenterDTO centerDTO = new CenterDTO();
        centerDTO.setName("Center A");
        centerDTO.setCapacity("MS");
        centerDTO.setStatus("AVAILABLE");
        centerDTO.setMaxCapacity(5);
        centerDTO.setCurrentLoad(6);
        CoordinatesDTO coordinatesDTO = new CoordinatesDTO();
        coordinatesDTO.setLatitude(42.3601);
        coordinatesDTO.setLongitude(-71.0589);
        centerDTO.setCoordinates(coordinatesDTO);

        when(centerRepository.findByCoordinatesLatitudeAndCoordinatesLongitude(any(Double.class), any(Double.class)))
                .thenReturn(Optional.empty());

        assertThrows(CurrentLoadExceedsMaxCapacityException.class, () -> centerService.createCenter(centerDTO));
    }

    @Test
    void getAllCenters_Success() {
        Center center1 = new Center();
        center1.setId(1L);
        center1.setName("Center A");
        Center center2 = new Center();
        center2.setId(2L);
        center2.setName("Center B");
        when(centerRepository.findAll()).thenReturn(List.of(center1, center2));

        List<CenterResponseDTO> result = centerService.getAllCenters();

        assertEquals(2, result.size());
        assertEquals("Center A", result.get(0).getName());
        assertEquals("Center B", result.get(1).getName());
    }

    @Test
    void updateCenter_Success() {
        Long id = 1L;
        CenterUpdateDTO centerUpdateDTO = new CenterUpdateDTO();
        centerUpdateDTO.setName("New Center Name");
        centerUpdateDTO.setCapacity("M");
        centerUpdateDTO.setStatus("OCCUPIED");
        CoordinatesDTO coordinatesDTO = new CoordinatesDTO();
        coordinatesDTO.setLatitude(43.3601);
        coordinatesDTO.setLongitude(-72.0589);
        centerUpdateDTO.setCoordinates(coordinatesDTO);

        Center existingCenter = new Center();
        existingCenter.setId(id);
        existingCenter.setName("Center A");
        existingCenter.setCapacity("MS");
        existingCenter.setStatus("AVAILABLE");
        Coordinates coordinates = new Coordinates();
        coordinates.setLatitude(42.3601);
        coordinates.setLongitude(-71.0589);
        existingCenter.setCoordinates(coordinates);

        when(centerRepository.findById(id)).thenReturn(Optional.of(existingCenter));
        when(centerRepository.findByCoordinatesLatitudeAndCoordinatesLongitude(any(Double.class), any(Double.class))).thenReturn(Optional.empty());
        when(centerRepository.save(any(Center.class))).thenReturn(existingCenter);

        CenterResponseDTO result = centerService.updateCenter(id, centerUpdateDTO);

        assertNotNull(result);
        assertEquals("New Center Name", result.getName());
        assertEquals("M", result.getCapacity());
        assertEquals("OCCUPIED", result.getStatus());
        assertEquals(43.3601, result.getCoordinates().getLatitude());
        assertEquals(-72.0589, result.getCoordinates().getLongitude());
    }

    @Test
    void updateCenter_CenterNotFound(){
        Long id = 1L;
        CenterUpdateDTO centerUpdateDTO = new CenterUpdateDTO();
        centerUpdateDTO.setName("New Center Name");
        centerUpdateDTO.setCapacity("M");
        centerUpdateDTO.setStatus("OCCUPIED");
        CoordinatesDTO coordinatesDTO = new CoordinatesDTO();
        coordinatesDTO.setLatitude(43.3601);
        coordinatesDTO.setLongitude(-72.0589);
        centerUpdateDTO.setCoordinates(coordinatesDTO);

        when(centerRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(CenterNotFoundException.class, () -> centerService.updateCenter(id, centerUpdateDTO));
    }

    }
