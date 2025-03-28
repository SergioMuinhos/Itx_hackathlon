package com.hackathon.inditex.services;

import com.hackathon.inditex.dto.CenterDTO;
import com.hackathon.inditex.dto.CenterResponseDTO;
import com.hackathon.inditex.dto.CenterUpdateDTO;

import java.util.List;

/**
 * Center Service Interface.
 */
public interface CenterService {

    CenterResponseDTO createCenter(CenterDTO centerDTO);

    List<CenterResponseDTO> getAllCenters();

    CenterResponseDTO updateCenter(Long id, CenterUpdateDTO centerUpdateDTO);

    void deleteCenter(Long id);

}
