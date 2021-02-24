package edu.uph.ii.platformy.services;

import edu.uph.ii.platformy.controllers.commands.KandydatSpecifications;
import edu.uph.ii.platformy.controllers.commands.KomitetSpecifications;
import edu.uph.ii.platformy.models.Kandydat;
import edu.uph.ii.platformy.models.Komitet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


public interface KomitetService {
    Komitet getKomitet(Long id);
    public Page<Komitet> getAllKomitety(KomitetSpecifications search, Pageable pageable);


    void saveKomitet(Komitet k);
    public void save(Komitet komitet);
    void deleteKomitet(Long id);
}
