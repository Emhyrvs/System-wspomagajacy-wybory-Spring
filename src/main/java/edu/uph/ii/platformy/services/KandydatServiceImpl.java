package edu.uph.ii.platformy.services;

import edu.uph.ii.platformy.controllers.commands.KandydatSpecifications;
import edu.uph.ii.platformy.exceptions.KandydatNotFoundException;
import edu.uph.ii.platformy.exceptions.VehicleNotFoundException;
import edu.uph.ii.platformy.models.*;
import edu.uph.ii.platformy.repositories.KandydatRepository;
import edu.uph.ii.platformy.repositories.KomitetRepository;
import edu.uph.ii.platformy.repositories.UserRepository;
import javassist.NotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Log4j2
@Service
public class KandydatServiceImpl implements KandydatService {


    @Autowired
     KandydatRepository kandydatRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    KomitetRepository komitetRepository;

    @Transactional
    @Override
    public Kandydat getKandydata(Long id) {
        Optional<Kandydat> k = kandydatRepository.findById(id);
        Kandydat kandydat = k.orElseThrow(()->new KandydatNotFoundException(id));

        return kandydat;
    }




    @Override
    public Page getAllKandydatow(KandydatSpecifications search, Pageable pageable) {

        KandydatSpecifications kandydatSpecifications = null;
        return kandydatRepository.findAll(
                Specification.where(

                        KandydatSpecifications.findByPhrase(search.getPhrase())
                ), pageable);

    }

    @Override
    public void saveKandydat(Kandydat kandydat) {
       Kandydat kandydat1=kandydatRepository.findByUser(userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));

     if(kandydat1==null) {
         kandydat.setUser(userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
         log.info("user id" + kandydat.getUser().getId());

         kandydatRepository.save(kandydat);
     }
     else
     {log.info("wyjatek ");
         throw new KandydatNotFoundException("aaaa");
     }

    }
    @Override
    public void  akceptuj(Long id)
    {
     Kandydat k=this.getKandydata(id);
     k.setStatus(true);
     kandydatRepository.save(k);
    }
    @Override
    public void  odrzuc(Long id)
    {
        Kandydat k=this.getKandydata(id);
        k.setStatus(false);
        kandydatRepository.save(k);
    }

    @Override
    public void save(Kandydat kandydat) {
        UserRepository userRepository = null;
        log.info("user id" + kandydat.getUser().getId());
        log.info("user id" +userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
kandydat.setUser( userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));

kandydatRepository.save(kandydat);
    }

    @Override
    public void deleteKandydata(Long id) {
        // w przypadku usuwania obsługa wyjątku VehicleNotFoundException nie jest niezbędna dla bezpieczeństwa systemu
        if(kandydatRepository.existsById(id)){
            kandydatRepository.deleteById(id);
        }else{
            throw new KandydatNotFoundException(id);
        }
    }
    @Override
    public List<Komitet> getallkomitety() {
        return komitetRepository.findAll();
    }



}
