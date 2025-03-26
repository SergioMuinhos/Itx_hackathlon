package com.hackathon.inditex.Controllers;

import com.hackathon.inditex.Entities.Center;
import com.hackathon.inditex.dto.CenterDTO;
import com.hackathon.inditex.dto.CenterUpdateDTO;
import com.hackathon.inditex.Controllers.exceptions.CenterNotFoundException;
import com.hackathon.inditex.dto.models.MessageResponse;
import com.hackathon.inditex.Services.CenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/centers")
public class CenterController {

    @Autowired
    private CenterService centerService;


    @PostMapping
    public ResponseEntity<?> createCenter(@RequestBody CenterDTO centerDTO){
        try{
            centerService.createCenter(centerDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Logistics center created successfully."));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse(e.getMessage()));
        }
    }

    @GetMapping
    public  ResponseEntity<List<Center>> getAllCenters(){
        List<Center> centers = centerService.getAllCenters();
        return ResponseEntity.ok(centers);
    }

    @PatchMapping("{id}")
    public ResponseEntity<?> updateCenter(@PathVariable Long id, @RequestBody CenterUpdateDTO centerUpdateDTO){
        try{
            centerService.updateCenter(id, centerUpdateDTO);
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Logistics center updated successfully."));
        }catch (CenterNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(e.getMessage()));
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteCenter(@PathVariable Long id){
        try{
            centerService.deleteCenter(id);
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Logistics center deleted succesfully."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(e.getMessage()));
        }
    }


}
