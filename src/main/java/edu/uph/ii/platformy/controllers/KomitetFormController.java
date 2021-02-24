package edu.uph.ii.platformy.controllers;


import edu.uph.ii.platformy.models.Komitet;

import edu.uph.ii.platformy.services.KomitetService;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@SessionAttributes(names={"komitet"})
@Log4j2

public class KomitetFormController {
    private KomitetService komitetService;

    //Wstrzyknięcie zależności przez konstruktor. Od wersji 4.3 Springa nie trzeba używać adnontacji @Autowired, gdy mamy jeden konstruktor
    //@Autowired
    public KomitetFormController(KomitetService komitetService)
    {
        this.komitetService = komitetService;
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value="/komitetForm.html", method= RequestMethod.GET)
    public String showForm(Model model, Optional<Long> id){

        model.addAttribute("komitet",
                id.isPresent()?

                        komitetService.getKomitet(id.get()):
                        new Komitet());


        return "komitetForm";
    }



    @Secured("ROLE_ADMIN")
    @RequestMapping(value="/komitetForm.html", method= RequestMethod.POST)
    //@ResponseStatus(HttpStatus.CREATED)
    public String processForm(@Valid @ModelAttribute("komitet") Komitet k){


log.info("tooooooo"+SecurityContextHolder.getContext().getAuthentication().getName());

        komitetService.saveKomitet(k);

        return "redirect:komitetList.html";//po udanym dodaniu/edycji przekierowujemy na listę
    }


}
