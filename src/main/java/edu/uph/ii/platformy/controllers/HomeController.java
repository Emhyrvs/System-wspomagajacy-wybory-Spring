package edu.uph.ii.platformy.controllers;

import edu.uph.ii.platformy.controllers.commands.KandydatSpecifications;
import edu.uph.ii.platformy.models.Kandydat;
import edu.uph.ii.platformy.models.Wyborca;
import edu.uph.ii.platformy.repositories.KandydatRepository;
import edu.uph.ii.platformy.repositories.UserRepository;
import edu.uph.ii.platformy.repositories.WyborcaRepository;
import edu.uph.ii.platformy.services.KandydatService;
import edu.uph.ii.platformy.services.WyborcaService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
@Log4j2
@Controller
public class HomeController {
    @Autowired
    KandydatRepository kandydatRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    WyborcaRepository wyborcaRepository;


    @GetMapping(path = "/")
  //  @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public String home(Model model) {
        model.addAttribute("message", "Hello World!!!");
        return "home";
    }
    @RequestMapping(value="/kandydatedit.html")
    public String showKandydat(Model model){
        log.info("Pokazywanie szczegółów");
        Kandydat k =kandydatRepository.findByUser(userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));

        if(k != null) {
            model.addAttribute("kandydat", k);
            return "kandydatedit";
        }
        else
        {
            model.addAttribute("message", "nie złożyłeś wniosku");
            return "home";
        }



    }
    @RequestMapping(value="/wyborcastatus.html")
    public String showWyborca(Model model){
        log.info("Pokazywanie szczegółów");
      Wyborca w =wyborcaRepository.findByUser(userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));

        if(w!= null) {
            model.addAttribute("wyborca", w);
            return "wyborcastatus";
        }
        else
        {
            model.addAttribute("message", "nie złożyłeś wniosku");
            return "home";
        }



    }

}



