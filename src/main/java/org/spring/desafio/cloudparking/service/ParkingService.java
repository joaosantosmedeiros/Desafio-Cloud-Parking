package org.spring.desafio.cloudparking.service;

import org.spring.desafio.cloudparking.exception.ParkingNotFoundException;
import org.spring.desafio.cloudparking.model.Parking;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ParkingService {

    private static Map<String, Parking> parkingMap = new HashMap<>();

    static {
        var id = getUUID();
        var id1 = getUUID();
        Parking parking = new Parking(id, "DMS-1111", "SC", "CELTA", "PRETO");
        Parking parking1 = new Parking(id1, "MST-1234", "SP", "VW GOL", "VERDE");
        parkingMap.put(id, parking);
        parkingMap.put(id1, parking1);
    }

    public List<Parking> findAll(){
        return parkingMap.values().stream().collect(Collectors.toList());
    }

    private static String getUUID() {
        return UUID.randomUUID().toString().replace("-","");
    }

    public Parking findById(String id) {
        Parking parking = parkingMap.get(id);
        if(parking == null) throw new ParkingNotFoundException(id);
        return parking;
    }

    public Parking create(Parking parkingCreate) {
        String uuid = getUUID();
        parkingCreate.setId(uuid);
        parkingCreate.setEntryDate(LocalDateTime.now());
        parkingMap.put(uuid, parkingCreate);
        return parkingCreate;
    }

    public void delete(String id) {
        Parking parking = findById(id);
        if(parking == null) throw new ParkingNotFoundException(id);
        parkingMap.remove(id);
    }

    public Parking update(String id, Parking parkingCreate) {
        Parking parking = findById(id);
        if(parking == null) throw new ParkingNotFoundException(id);

        parking.setColor(parkingCreate.getColor());
        parking.setState(parkingCreate.getState());
        parking.setLicense(parkingCreate.getLicense());
        parking.setModel(parkingCreate.getModel());
        parkingMap.put(id, parking);

        return parking;
    }


    public Parking exit(String id) { //TODO:
        //
        return null;
    }
}
