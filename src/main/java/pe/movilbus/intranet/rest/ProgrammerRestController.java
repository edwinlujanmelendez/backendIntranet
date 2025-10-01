package pe.movilbus.intranet.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.movilbus.intranet.service.ProgrammerService;

@RestController
@RequestMapping("/Programmer")
public class ProgrammerRestController {
	
	@Autowired
	private ProgrammerService programmerService;
	
	@GetMapping("/cantidadPasajesPorCanjear")
	public List<Integer> cantidadPasajesPorCanjear(){
		return programmerService.cantidadPasajesPorCanjear();
	}
}