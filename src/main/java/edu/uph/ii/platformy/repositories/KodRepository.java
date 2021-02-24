package edu.uph.ii.platformy.repositories;

import edu.uph.ii.platformy.models.Kod;
import edu.uph.ii.platformy.models.Wyborca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface KodRepository extends JpaRepository<Kod, Long>, JpaSpecificationExecutor<Kod> {



    Optional<Kod> findByKod(String string);
}
