package pe.movilbus.intranet.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.movilbus.intranet.beans.Bus;
import pe.movilbus.intranet.beans.Personal;
import pe.movilbus.intranet.service.ConductoresService;

@RestController
@RequestMapping("/Conductores")
public class ConductoresRestController {
	
	@Autowired
	private ConductoresService conductoresService;
	
	@GetMapping("/getPilotos")
	public List<Personal> getPilotos(){
		return conductoresService.getPilotos();
	}
	
	@GetMapping("/getDatosBus/{unidad}")
	public ResponseEntity<Bus> getDatosBus(@PathVariable String unidad){
		Bus bus = conductoresService.getDatosBus(unidad);
	    return ResponseEntity.ok(bus);
	}
}