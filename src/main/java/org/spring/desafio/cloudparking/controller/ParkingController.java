package org.spring.desafio.cloudparking.controller;

import org.spring.desafio.cloudparking.dto.ParkingCreateDTO;
import org.spring.desafio.cloudparking.dto.ParkingDTO;
import org.spring.desafio.cloudparking.exception.ParkingNotFoundException;
import org.spring.desafio.cloudparking.mapper.ParkingMapper;
import org.spring.desafio.cloudparking.model.Parking;
import org.spring.desafio.cloudparking.service.ParkingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parking")
public class ParkingController {

    private final ParkingService parkingService;
    private final ParkingMapper parkingMapper;

    public ParkingController(ParkingService parkingService, ParkingMapper parkingMapper) {
        this.parkingService = parkingService;
        this.parkingMapper = parkingMapper;
    }

    @GetMapping
    public ResponseEntity<List<ParkingDTO>> findAll(){
        List<Parking> parkingList = parkingService.findAll();
        List<ParkingDTO> result = parkingMapper.toParkingDTOList(parkingList);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingDTO> findById(@PathVariable(value = "id") String id){
        Parking parking = parkingService.findById(id);
        ParkingDTO result = parkingMapper.toParkingDTO(parking);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<ParkingDTO> create(@RequestBody ParkingCreateDTO dto){
        var parkingCreate = parkingMapper.toParkingCreate(dto);
        Parking parking = parkingService.create(parkingCreate);
        ParkingDTO result = parkingMapper.toParkingDTO(parking);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable(value = "id") String id){
        parkingService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParkingDTO> update(@PathVariable(value = "id") String id, @RequestBody ParkingCreateDTO dto){
        Parking parkingUpdate = parkingMapper.toParkingCreate(dto);
        Parking parking = parkingService.update(id, parkingUpdate);
        return ResponseEntity.ok(parkingMapper.toParkingDTO(parking));
    }

    @PostMapping("/{id}")
    public ResponseEntity<ParkingDTO> exit (@PathVariable(value = "id") String id){
        Parking parking = parkingService.findById(id);
        if(parking == null) throw new ParkingNotFoundException(id);
        parking = parkingService.exit(id);
        return ResponseEntity.ok(parkingMapper.toParkingDTO(parking));
    }
}
