package edu.uph.ii.platformy.controllers;

import edu.uph.ii.platformy.controllers.commands.VehicleFilter;
import edu.uph.ii.platformy.controllers.commands.VehicleSpecifications;
import edu.uph.ii.platformy.models.Vehicle;
import edu.uph.ii.platformy.services.VehicleService;
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


@Controller

public class VehiclesListController{

	protected final Log log = LogFactory.getLog(getClass());//Dodatkowy komponent do logowania


	@Autowired
	VehicleService vehicleService;

	@Secured("IS_AUTHENTICATED_FULLY")
	@RequestMapping(value="/vehicleList.html", params = "id", method = RequestMethod.GET)
	public String showVehicleDetails(Model model, long id){
		log.info("Pokazywanie szczegółów2222");

		Vehicle v = vehicleService.getVehicle(id);
		//obłużyć not found exception
		model.addAttribute("vehicle", v);
		return "vehicleDetails";
	}

	@ModelAttribute("searchCommand")
	public VehicleSpecifications getSimpleSearch(){
		return new VehicleSpecifications();
	}

	@GetMapping(value="/vehicleList.html", params = {"all"})
	public String resetehicleList(@ModelAttribute("searchCommand") VehicleSpecifications vehicleSpecifications){
vehicleSpecifications.clear();
		return "redirect:vehicleList.html";
	}


	@RequestMapping(value="/vehicleList.html", method = {RequestMethod.GET, RequestMethod.POST})
	public String showVehicleList(Model model, Pageable pageable, @Valid @ModelAttribute("searchCommand") VehicleSpecifications search, BindingResult result){
		model.addAttribute("vehicleListPage", vehicleService.getAllVehicles(search, pageable));
		return "vehicleList";
	}

	@Secured("ROLE_ADMIN")
	@GetMapping(path="/vehicleList.html", params={"did"})
	public String deleteVehicle(long did, HttpServletRequest request){
		log.info("Usuwanie pojazdu o id "+did);
		vehicleService.deleteVehicle(did);
		String queryString = prepareQueryString(request.getQueryString());
		return String.format("redirect:vehicleList.html?%s", queryString);//robimy przekierowanie, ale zachowując parametry pageingu

	}

	private String prepareQueryString(String queryString) {//np., did=20&page=2&size=20
		return queryString.substring(queryString.indexOf("&")+1);//obcinamy parametr did, bo inaczej znowu będzie wywołana metoda deleteVihicle
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {//Rejestrujemy edytory właściwości
		DecimalFormat numberFormat = new DecimalFormat("#0.00");
		numberFormat.setMaximumFractionDigits(2);
		numberFormat.setMinimumFractionDigits(2);
		numberFormat.setGroupingUsed(false);
		CustomNumberEditor priceEditor = new CustomNumberEditor(Float.class, numberFormat, true);
		binder.registerCustomEditor(Float.class, "minPrice", priceEditor);
		binder.registerCustomEditor(Float.class, "maxPrice", priceEditor);
	}

}
