package edu.uph.ii.platformy.controllers;





import edu.uph.ii.platformy.controllers.commands.WyborcaSpecifications;



import edu.uph.ii.platformy.models.Wyborca;

import edu.uph.ii.platformy.services.WyborcaService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

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


public class WyborcyListController {
    protected final Log log = LogFactory.getLog(getClass());//Dodatkowy komponent do logowania


    @Autowired
    WyborcaService wyborcaService;

    @RequestMapping(value="/wyborcaList.html", params = "id", method = RequestMethod.GET)
    public String showWyborca(Model model, long id){
        log.info("Pokazywanie szczegółów");

        Wyborca w = wyborcaService.getWyborce(id);


        //obłużyć not found exception
        model.addAttribute("wyborca", w);
        return "wyborcalist";



    }



    @GetMapping(value="/wyborcaList.html", params = {"all"})
    public String resetehicleList(@ModelAttribute("searchCommand") WyborcaSpecifications wyborcaSpecifications){
        wyborcaSpecifications.clear();
        return "redirect:wyborcaList.html";
    }


    @RequestMapping(value="/wyborcaList.html", method = {RequestMethod.GET, RequestMethod.POST})
    public String showWyborcaList(Model model, Pageable pageable, @Valid @ModelAttribute("searchCommand") WyborcaSpecifications search){
        model.addAttribute("wyborcaListPage", wyborcaService.getAllWyborcow(search, pageable));
        return "wyborcaList";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(path="/wyborcaList.html", params={"did"})
    public String deleteWyborca(long did, HttpServletRequest request){
        log.info("Usuwanie pojazdu o id "+did);
        wyborcaService.deleteWyborca(did);

        return "redirect:wyborcaList.html";//robimy przekierowanie, ale zachowując parametry pageingu


    }
    @Secured("ROLE_ADMIN")
    @GetMapping(path="/wyborcaList.html", params={"akceptuj"})
    public String akceptuj(long akceptuj, HttpServletRequest request){
        log.info("Akcpetacja kandydata o id "+akceptuj);
        wyborcaService.akceptuj(akceptuj);

        return "redirect:wyborcaList.html";//robimy przekierowanie, ale zachowując parametry pageingu


    }
    @Secured("ROLE_ADMIN")
    @GetMapping(path="/wyborcaList.html", params={"odrzuc"})
    public String odrzuc(long odrzuc, HttpServletRequest request){

        wyborcaService.odrzuc(odrzuc);

        return "redirect:wyborcaList.html";//robimy przekierowanie, ale zachowując parametry pageingu


    }
    private String prepareQueryString(String queryString) {//np., did=20&page=2&size=20
        return queryString.substring(queryString.indexOf("&")+1);//obcinamy parametr did, bo inaczej znowu będzie wywołana metoda deleteVihicle
    }
    @ModelAttribute("searchCommand")
    public WyborcaSpecifications getSimpleSearch(){
        return new WyborcaSpecifications();
    }


}
