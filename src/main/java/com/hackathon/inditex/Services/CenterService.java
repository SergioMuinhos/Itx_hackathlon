package com.hackathon.inditex.Services;

import com.hackathon.inditex.dto.CenterDTO;
import com.hackathon.inditex.dto.CenterResponseDTO;
import com.hackathon.inditex.dto.CenterUpdateDTO;

import java.util.List;

/**
 * Center Service Interface.
 */
public interface CenterService {

    public CenterResponseDTO createCenter(CenterDTO centerDTO);

    public List<CenterResponseDTO> getAllCenters();

    public CenterResponseDTO updateCenter(Long id, CenterUpdateDTO centerUpdateDTO);

    public void deleteCenter(Long id);



}
