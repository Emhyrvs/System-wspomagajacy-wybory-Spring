package edu.uph.ii.platformy.services;

import edu.uph.ii.platformy.controllers.commands.KandydatSpecifications;
import edu.uph.ii.platformy.controllers.commands.WyborcaSpecifications;
import edu.uph.ii.platformy.exceptions.KandydatNotFoundException;
import edu.uph.ii.platformy.exceptions.VehicleNotFoundException;
import edu.uph.ii.platformy.exceptions.WyborcaNotFoundException;
import edu.uph.ii.platformy.models.Kandydat;
import edu.uph.ii.platformy.models.Wyborca;
import edu.uph.ii.platformy.repositories.KandydatRepository;
import edu.uph.ii.platformy.repositories.UserRepository;
import edu.uph.ii.platformy.repositories.WyborcaRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Service
@Log4j2
public class WyborcaServiceImpl implements WyborcaService {
    @Autowired
    WyborcaRepository wyborcaRepository;
    @Autowired
    UserRepository userRepository;

    @Transactional
    @Override
    public Wyborca getWyborce(Long id) {
        Optional<Wyborca> w = wyborcaRepository.findById(id);
        Wyborca wyborca= w.orElseThrow(()->new WyborcaNotFoundException(id));

        return wyborca;
    }


    @Override
    public Page getAllWyborcow(WyborcaSpecifications search, Pageable pageable) {

       WyborcaSpecifications wyborcaSpecifications = null;
        return wyborcaRepository.findAll(
                Specification.where(

                       WyborcaSpecifications.findByPhrase(search.getPhrase())
                ), pageable);

    }




    @Override
    public void saveWyborce(Wyborca wyborca) {

        Wyborca wyborca1=wyborcaRepository.findByUser(userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));

        if(wyborca1==null) {
            wyborca.setUser(userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
            log.info("user id" + wyborca.getUser().getId());
            wyborcaRepository.save(wyborca);
        }
        else
        {log.info("wyjatek ");
            throw new WyborcaNotFoundException();
        }


    }

@Override
    public void  akceptuj(Long id)
    {
        Wyborca w=this.getWyborce(id);
        w.setStatus(true);
        wyborcaRepository.save(w);
    }
    @Override
    public void  odrzuc(Long id)
    {
        Wyborca w=this.getWyborce(id);
        w.setStatus(false);
        wyborcaRepository.save(w);
    }


    @Override
    public void save(Wyborca wyborca) {

        wyborca.setUser( userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));


        wyborcaRepository.save(wyborca);
    }

    @Override
    public void deleteWyborca(Long id) {
// w przypadku usuwania obsługa wyjątku VehicleNotFoundException nie jest niezbędna dla bezpieczeństwa systemu
        if(wyborcaRepository.existsById(id)){
            wyborcaRepository.deleteById(id);
        }else{
            throw new WyborcaNotFoundException(id);
        }

    }




}
