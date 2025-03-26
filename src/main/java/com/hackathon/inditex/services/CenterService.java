package com.hackathon.inditex.services;

import com.hackathon.inditex.Entities.Center;
import com.hackathon.inditex.dto.CenterDTO;
import com.hackathon.inditex.dto.CenterUpdateDTO;

import java.util.List;

public interface CenterService {

    public Center createCenter(CenterDTO centerDTO);

    public List<Center> getAllCenters();

    public Center updateCenter(Long id, CenterUpdateDTO centerUpdateDTO);

    public void deleteCenter(Long id);



}
