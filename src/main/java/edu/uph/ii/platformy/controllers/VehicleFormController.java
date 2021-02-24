package edu.uph.ii.platformy.controllers;

import edu.uph.ii.platformy.models.Accessory;
import edu.uph.ii.platformy.models.Kandydat;
import edu.uph.ii.platformy.models.Vehicle;
import edu.uph.ii.platformy.models.VehicleType;
import edu.uph.ii.platformy.services.KandydatService;
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
@SessionAttributes(names={"vehicleTypes", "accessoryList", "vehicle"})
@Log4j2
public class VehicleFormController {

	private VehicleService vehicleService;

	//Wstrzyknięcie zależności przez konstruktor. Od wersji 4.3 Springa nie trzeba używać adnontacji @Autowired, gdy mamy jeden konstruktor
	//@Autowired
	public VehicleFormController(VehicleService vehicleService)
	{
		this.vehicleService = vehicleService;
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value="/vehicleForm.html", method= RequestMethod.GET)
	public String showForm(Model model, Optional<Long> id){
log.info("cososososo");
		model.addAttribute("vehicle",
				id.isPresent()?
						vehicleService.getVehicle(id.get()):
						new Vehicle());

		return "vehicleForm";
	}



	@Secured("ROLE_ADMIN")
	@RequestMapping(value="/vehicleForm.html", method= RequestMethod.POST)
	//@ResponseStatus(HttpStatus.CREATED)
	public String processForm(@Valid @ModelAttribute("vehicle") Vehicle v, BindingResult errors){

		if(errors.hasErrors()){
			return "vehicleForm";
		}

		log.info("Data utworzenia komponentu "+v.getCreationDate());
		log.info("Data edycji komponentu "+new Date());

		vehicleService.saveVehicle(v);

		return "redirect:vehicleList.html";//po udanym dodaniu/edycji przekierowujemy na listę
	}

	@ModelAttribute("vehicleTypes")
	public List<VehicleType> loadTypes(){
		List<VehicleType> types = vehicleService.getAllTypes();
		log.info("Ładowanie listy "+types.size()+" typów ");
		return types;
	}

	@ModelAttribute("accessoryList")
	public List<Accessory> loadAccessories(){
		List<Accessory> accessories = vehicleService.getAllAccessories();
		log.info("Ładowanie listy "+accessories.size()+" akcesoriów ");
		return accessories;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {//Rejestrujemy edytory właściwości

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		CustomDateEditor dateEditor = new CustomDateEditor(dateFormat, false);
		binder.registerCustomEditor(Date.class, "productionDate", dateEditor);

		DecimalFormat numberFormat = new DecimalFormat("#0.00");
		numberFormat.setMaximumFractionDigits(2);
		numberFormat.setMinimumFractionDigits(2);
		numberFormat.setGroupingUsed(false);
		binder.registerCustomEditor(Float.class, "price", new CustomNumberEditor(Float.class, numberFormat, false));

		binder.setDisallowedFields("creationdDate");//ze względu na bezpieczeństwo aplikacji to pole nie może zostać przesłane w formularzu

	}

}








