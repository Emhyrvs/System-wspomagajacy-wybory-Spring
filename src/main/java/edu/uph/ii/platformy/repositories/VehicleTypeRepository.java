package edu.uph.ii.platformy.repositories;

import edu.uph.ii.platformy.models.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleTypeRepository extends JpaRepository<VehicleType, Integer> {
}
