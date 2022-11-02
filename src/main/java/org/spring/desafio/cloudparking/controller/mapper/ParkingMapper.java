package org.spring.desafio.cloudparking.controller.mapper;

import org.modelmapper.ModelMapper;
import org.spring.desafio.cloudparking.controller.dto.ParkingDTO;
import org.spring.desafio.cloudparking.model.Parking;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ParkingMapper {

    private static final ModelMapper MODEL_MAPPER = new ModelMapper();

    public ParkingDTO parkingDTO(Parking parking){
        return MODEL_MAPPER.map(parking, ParkingDTO.class);
    }

    public List<ParkingDTO> toParkingDTOList(List<Parking> parkingList) {
        return parkingList.stream().map(this::parkingDTO).collect(Collectors.toList());
    }
}
