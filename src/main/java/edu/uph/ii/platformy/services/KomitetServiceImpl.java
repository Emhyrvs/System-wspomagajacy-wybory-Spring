package edu.uph.ii.platformy.services;


import edu.uph.ii.platformy.controllers.commands.KomitetSpecifications;

import edu.uph.ii.platformy.exceptions.KomitetNotFoundException;
import edu.uph.ii.platformy.exceptions.VehicleNotFoundException;

import edu.uph.ii.platformy.models.Komitet;

import edu.uph.ii.platformy.repositories.KomitetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Service
public class KomitetServiceImpl implements KomitetService  {
    @Autowired
    KomitetRepository komitetRepository;



    @Transactional
    @Override
    public Komitet getKomitet(Long id) {
        Optional<Komitet> k = komitetRepository.findById(id);
        Komitet komitet = k.orElseThrow(()->new KomitetNotFoundException(id));

        return komitet;
    }

    @Override
    public Page<Komitet> getAllKomitety(KomitetSpecifications search, Pageable pageable) {
        KomitetSpecifications komitetSpecifications = null;

        return komitetRepository.findAll(
                Specification.where(

                        KomitetSpecifications.findByPhrase(search.getPhrase())
                ), pageable);
    }






    @Override
    public void saveKomitet(Komitet komitet) {


       komitetRepository.save(komitet);
    }

    @Override
    public void save(Komitet komitet) {



        komitetRepository.save(komitet);
    }

    @Override
    public void deleteKomitet(Long id) {
        // w przypadku usuwania obsługa wyjątku VehicleNotFoundException nie jest niezbędna dla bezpieczeństwa systemu
        if(komitetRepository.existsById(id)){
            komitetRepository.deleteById(id);
        }else{
            throw new KomitetNotFoundException(id);
        }
    }


}
