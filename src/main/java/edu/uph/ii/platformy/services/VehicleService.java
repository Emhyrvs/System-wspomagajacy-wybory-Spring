package edu.uph.ii.platformy.services;

import edu.uph.ii.platformy.controllers.commands.VehicleSpecifications;
import edu.uph.ii.platformy.models.Accessory;
import edu.uph.ii.platformy.models.Vehicle;
import edu.uph.ii.platformy.models.VehicleType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VehicleService {

    List<Accessory> getAllAccessories();

    List<VehicleType> getAllTypes();



    Vehicle getVehicle(Long id);

    void deleteVehicle(Long id);

    void saveVehicle(Vehicle vehicle);

   Page<Vehicle> getAllVehicles(VehicleSpecifications search, Pageable pageable);
}
