package edu.uph.ii.platformy.controllers;


import edu.uph.ii.platformy.models.Wyborca;

import edu.uph.ii.platformy.services.WyborcaService;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@SessionAttributes(names={"wyborca"})
@Log4j2
public class WyborcaFormController {

    private WyborcaService wyborcaService;

    //Wstrzyknięcie zależności przez konstruktor. Od wersji 4.3 Springa nie trzeba używać adnontacji @Autowired, gdy mamy jeden konstruktor
    //@Autowired
    public WyborcaFormController(WyborcaService wyborcaService) {
        this.wyborcaService=wyborcaService;
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/wyborcaForm.html", method = RequestMethod.GET)
    public String showForm(Model model, Optional<Long> id) {

        model.addAttribute("wyborca",
                id.isPresent() ?

                        wyborcaService.getWyborce(id.get()) :
                        new Wyborca());


        return "wyborcaForm";
    }


    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/wyborcaForm.html", method = RequestMethod.POST)
    //@ResponseStatus(HttpStatus.CREATED)
    public String processForm(@Valid @ModelAttribute("wyborca") Wyborca w) {


    wyborcaService.saveWyborce(w);

        return "redirect:wyborcaList.html";//po udanym dodaniu/edycji przekierowujemy na listę
    }


}
