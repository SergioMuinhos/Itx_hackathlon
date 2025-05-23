package com.hackathon.inditex.controllers;

import com.hackathon.inditex.dto.CenterDTO;
import com.hackathon.inditex.dto.CenterResponseDTO;
import com.hackathon.inditex.dto.CenterUpdateDTO;
import com.hackathon.inditex.application.exceptions.CenterNotFoundException;
import com.hackathon.inditex.dto.models.MessageResponse;
import com.hackathon.inditex.services.CenterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Center Controller.
 */
@RestController
@RequestMapping("/api/centers")
public class CenterController {


    private CenterService centerService;

    public CenterController(CenterService centerService) {
        this.centerService = centerService;
    }

    /**
     * Create Center
     *
     * @param centerDTO data to create center
     * @return ResponseEntity with message
     */
    @PostMapping
    public ResponseEntity<MessageResponse> createCenter(@RequestBody CenterDTO centerDTO) {
        try {
            centerService.createCenter(centerDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Logistics center created successfully."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse(e.getMessage()));
        }
    }

    /**
     * Get All Centers
     *
     * @return List of Centers
     */
    @GetMapping
    public ResponseEntity<List<CenterResponseDTO>> getAllCenters() {
        return ResponseEntity.ok(centerService.getAllCenters());
    }

    /**
     * Update Center
     *
     * @param id              Identifier of center to update
     * @param centerUpdateDTO Center to update
     * @return ResponseEntity with message
     */
    @PatchMapping("/{id}")
    public ResponseEntity<MessageResponse> updateCenter(@PathVariable Long id, @RequestBody CenterUpdateDTO centerUpdateDTO) {
        try {
            centerService.updateCenter(id, centerUpdateDTO);
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Logistics center updated successfully."));
        } catch (CenterNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(e.getMessage()));
        }
    }

    /**
     * Delete Center
     *
     * @param id
     * @return ResponseEntity with message
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteCenter(@PathVariable Long id) {
        try {
            centerService.deleteCenter(id);
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Logistics center deleted successfully."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(e.getMessage()));
        }
    }


}
