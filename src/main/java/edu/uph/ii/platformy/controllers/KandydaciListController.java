package edu.uph.ii.platformy.controllers;

import edu.uph.ii.platformy.controllers.commands.KandydatSpecifications;

import edu.uph.ii.platformy.controllers.commands.KomitetSpecifications;
import edu.uph.ii.platformy.models.Kandydat;

import edu.uph.ii.platformy.models.Vehicle;
import edu.uph.ii.platformy.services.KandydatService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.DecimalFormat;
import java.util.Optional;

@Controller


public class KandydaciListController {
    protected final Log log = LogFactory.getLog(getClass());//Dodatkowy komponent do logowania


    @Autowired
    KandydatService kandydatService;

    @RequestMapping(value="/kandydatList.html", params = "id", method = RequestMethod.GET)
    public String showKandydat(Model model, long id){
        log.info("Pokazywanie szczegółów");

      Kandydat k = kandydatService.getKandydata(id);
        log.info("Kandydat"+ kandydatService.getKandydata(id).getNazwisko());

        //obłużyć not found exception
        model.addAttribute("kandydat", k);
        return "kandydatedit";



    }



    @GetMapping(value="/kandydatList.html", params = {"all"})
    public String resetehicleList(@ModelAttribute("searchCommand") KandydatSpecifications kandydatSpecifications){
        kandydatSpecifications.clear();
        return "redirect:kandydatList.html";
    }


    @RequestMapping(value="/kandydatList.html", method = {RequestMethod.GET, RequestMethod.POST})
    public String showKomitetList(Model model, Pageable pageable, @Valid @ModelAttribute("searchCommand") KandydatSpecifications search){
        model.addAttribute("kandydatListPage", kandydatService.getAllKandydatow(search, pageable));
        return "kandydatList";
    }
    @Secured("ROLE_ADMIN")
    @GetMapping(path="/kandydatList.html", params={"did"})
    public String deleteVehicle(long did, HttpServletRequest request){
        log.info("Usuwanie pojazdu o id "+did);
       kandydatService.deleteKandydata(did);

        return "redirect:kandydatList.html";//robimy przekierowanie, ale zachowując parametry pageingu


    }
    @Secured("ROLE_ADMIN")
    @GetMapping(path="/kandydatList.html", params={"akceptuj"})
    public String akceptuj(long akceptuj, HttpServletRequest request){
        log.info("Akcpetacja kandydata o id "+akceptuj);
        kandydatService.akceptuj(akceptuj);

        return "redirect:kandydatList.html";//robimy przekierowanie, ale zachowując parametry pageingu


    }
    @Secured("ROLE_ADMIN")
    @GetMapping(path="/kandydatList.html", params={"odrzuc"})
    public String odrzuc(long odrzuc, HttpServletRequest request){

        kandydatService.odrzuc(odrzuc);

        return "redirect:kandydatList.html";//robimy przekierowanie, ale zachowując parametry pageingu


    }

    private String prepareQueryString(String queryString) {//np., did=20&page=2&size=20
        return queryString.substring(queryString.indexOf("&")+1);//obcinamy parametr did, bo inaczej znowu będzie wywołana metoda deleteVihicle
    }
    @ModelAttribute("searchCommand")
    public KandydatSpecifications getSimpleSearch(){
        return new KandydatSpecifications();
    }


}
