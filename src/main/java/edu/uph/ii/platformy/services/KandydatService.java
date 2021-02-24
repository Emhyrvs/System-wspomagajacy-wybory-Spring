package edu.uph.ii.platformy.services;

import edu.uph.ii.platformy.controllers.commands.KandydatSpecifications;
import edu.uph.ii.platformy.models.Kandydat;
import edu.uph.ii.platformy.models.Komitet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface KandydatService {










Kandydat getKandydata(Long id);
    public void  akceptuj(Long id);

    public Page<Kandydat> getAllKandydatow(KandydatSpecifications search, Pageable pageable);


    void saveKandydat(Kandydat k);
    public void save(Kandydat kandydat);
    void deleteKandydata(Long id);
    public void  odrzuc(Long id);
    public List<Komitet> getallkomitety();

}
