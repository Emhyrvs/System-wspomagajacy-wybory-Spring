package edu.uph.ii.platformy.controllers;

import edu.uph.ii.platformy.controllers.commands.KomitetSpecifications;


import edu.uph.ii.platformy.models.Kod;
import edu.uph.ii.platformy.models.Komitet;


import edu.uph.ii.platformy.services.KodService;
import edu.uph.ii.platformy.services.KomitetService;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
@Controller

public class KomitetListController {
    protected final Log log = LogFactory.getLog(getClass());//Dodatkowy komponent do logowania


    @Autowired
    KomitetService komitetService;
    @Autowired
    KodService kodService;

    @RequestMapping(value="/komitetList.html", params = "id", method = RequestMethod.GET)
    public String showKomitet(Model model, long id){
        log.info("Pokazywanie szczegółów");

        Komitet k  = komitetService.getKomitet(id);


        //obłużyć not found exception
        model.addAttribute("komitet", k);
        return "komitetlist";



    }



    @GetMapping(value="/komitetList.html", params = {"all"})
    public String resetkomitetList(@ModelAttribute("searchCommand") KomitetSpecifications komitetSpecifications){
        komitetSpecifications.clear();
        return "redirect:komitetList.html";
    }


    @RequestMapping(value="/komitetList.html", method = {RequestMethod.GET, RequestMethod.POST})
    public String showKomitetList(Model model, Pageable pageable, @Valid @ModelAttribute("searchCommand") KomitetSpecifications search){
        model.addAttribute("komitetListPage", komitetService.getAllKomitety(search, pageable));
        return "komitetList";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(path="/komitetList.html", params={"did"})
    public String deleteKomitet(long did, HttpServletRequest request){
        log.info("Usuwanie pojazdu o id "+did);
        komitetService.deleteKomitet(did);

        return "redirect:komitetList.html";//robimy przekierowanie, ale zachowując parametry pageingu


    }
    @GetMapping(path="/komitetList.html", params={"podpisz"})
    public String podpiszKomitet(long podpisz, HttpServletRequest request){
       String nazwak=komitetService.getKomitet(podpisz).getNazwa()+ SecurityContextHolder.getContext().getAuthentication().getName();
       log.info("haszownie"+nazwak.hashCode());
       Kod a =new Kod();
       a.setKod(String.valueOf(kodService.hashCode()));
kodService.savekod(a);

        return "redirect:komitetList.html";//robimy przekierowanie, ale zachowując parametry pageingu


    }

    private String prepareQueryString(String queryString) {//np., did=20&page=2&size=20
        return queryString.substring(queryString.indexOf("&")+1);//obcinamy parametr did, bo inaczej znowu będzie wywołana metoda deleteVihicle
    }
    @ModelAttribute("searchCommand")
    public KomitetSpecifications getSimpleSearch(){
        return new KomitetSpecifications();
    }


}
