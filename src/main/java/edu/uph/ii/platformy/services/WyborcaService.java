package edu.uph.ii.platformy.services;

import edu.uph.ii.platformy.controllers.commands.KandydatSpecifications;
import edu.uph.ii.platformy.controllers.commands.WyborcaSpecifications;
import edu.uph.ii.platformy.models.Kandydat;
import edu.uph.ii.platformy.models.Wyborca;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public interface WyborcaService {





    public void  odrzuc(Long id);

    public void  akceptuj(Long id);


    Wyborca getWyborce(Long id);
    public Page<Wyborca> getAllWyborcow(WyborcaSpecifications search, Pageable pageable);


    void saveWyborce(Wyborca wyborca);
    public void save(Wyborca wyborca);
    void deleteWyborca(Long id);

}
