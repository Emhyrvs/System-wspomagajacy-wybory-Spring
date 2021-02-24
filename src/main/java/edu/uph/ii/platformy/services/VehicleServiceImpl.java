package edu.uph.ii.platformy.services;

import edu.uph.ii.platformy.controllers.commands.VehicleSpecifications;
import edu.uph.ii.platformy.exceptions.VehicleNotFoundException;
import edu.uph.ii.platformy.models.Accessory;
import edu.uph.ii.platformy.models.Vehicle;
import edu.uph.ii.platformy.models.VehicleType;
import edu.uph.ii.platformy.repositories.AccessoryRepository;
import edu.uph.ii.platformy.repositories.VehicleRepository;
import edu.uph.ii.platformy.repositories.VehicleTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private AccessoryRepository accessoryRepository;

    @Autowired
    private VehicleTypeRepository vehicleTypeRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public List<Accessory> getAllAccessories() {
        return accessoryRepository.findAll();
    }

    @Override
    public List<VehicleType> getAllTypes() {
        return vehicleTypeRepository.findAll();
    }

    @Override
    public Page<Vehicle> getAllVehicles(VehicleSpecifications search, Pageable pageable) {
        Page page = vehicleRepository.findAll(
                Specification.where(

                        VehicleSpecifications.findByPhrase(search.getPhrase()).and(

                                VehicleSpecifications.findByPriceRange(search.getMinPrice(),
                                        search.getMaxPrice()))
                ), pageable);
return  page;

    }

    @Transactional
    @Override
    public Vehicle getVehicle(Long id) {
        Optional<Vehicle> optionalVehicle = vehicleRepository.findById(id);
        Vehicle vehicle = optionalVehicle.orElseThrow(()->new VehicleNotFoundException(id));
        vehicle.getAccessories().size();//dociągnięcie leniwych akcesoriów potrzebnych w widoku. Wymagana adnotacja @Transaction nad metodą lub klasą, aby findById nie zamknęło sesji
        return vehicle;
    }

    @Override
    public void deleteVehicle(Long id) {
    // w przypadku usuwania obsługa wyjątku VehicleNotFoundException nie jest niezbędna dla bezpieczeństwa systemu
        if(vehicleRepository.existsById(id) == true){
            vehicleRepository.deleteById(id);
        }else{
            throw new VehicleNotFoundException(id);
        }
    }

    @Override
    public void saveVehicle(Vehicle vehicle) {
        vehicleRepository.save(vehicle);
    }
}
