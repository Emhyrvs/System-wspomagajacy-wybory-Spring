package edu.uph.ii.platformy.controllers;

import edu.uph.ii.platformy.models.*;
import edu.uph.ii.platformy.services.KandydatService;
import edu.uph.ii.platformy.services.KomitetService;
import edu.uph.ii.platformy.services.VehicleService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@SessionAttributes(names={"kandydat"})
@Log4j2
public class KandydatFormController {
   private KandydatService kandydatService;
    private KomitetService komitetService;

    //Wstrzyknięcie zależności przez konstruktor. Od wersji 4.3 Springa nie trzeba używać adnontacji @Autowired, gdy mamy jeden konstruktor
    //@Autowired
    public KandydatFormController(KandydatService kandydatService)
    {
        this.kandydatService = kandydatService;
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value="/addkandydatForm.html", method= RequestMethod.GET)
    public String showForm(Model model, Optional<Long> id){

        model.addAttribute("kandydat",
                id.isPresent()?

                   kandydatService.getKandydata(id.get()):
                        new Kandydat());


        return "addkandydatForm";
    }



    @Secured("ROLE_ADMIN")
    @RequestMapping(value="/addkandydatForm.html", method= RequestMethod.POST)
    //@ResponseStatus(HttpStatus.CREATED)
    public String processForm(@Valid @ModelAttribute("kandydat") Kandydat k){




       kandydatService.saveKandydat(k);

        return "redirect:kandydatList.html";//po udanym dodaniu/edycji przekierowujemy na listę
    }
    @ModelAttribute("komitety")
    public List<Komitet> loadTypes(){
        List<Komitet> komitet = kandydatService.getallkomitety();
        log.info("Ładowanie listy "+komitet.size()+" komitet ");
        return komitet;
    }

}
