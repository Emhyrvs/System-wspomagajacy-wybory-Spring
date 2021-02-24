package edu.uph.ii.platformy.services;

import edu.uph.ii.platformy.models.Kod;
import edu.uph.ii.platformy.repositories.KodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KodServiceImpl implements  KodService {
@Autowired
    KodRepository kodRepository;
    @Override
    public void savekod(Kod kod) {

        if(!kodRepository.findByKod(kod.getKod()).isPresent()) {
            kodRepository.save(kod);
        }
    }
}
