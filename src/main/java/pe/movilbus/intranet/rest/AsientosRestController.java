package pe.movilbus.intranet.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.movilbus.intranet.beans.BloquearAsiento;
import pe.movilbus.intranet.result.GenericoResult;
import pe.movilbus.intranet.service.AsientosService;

@RestController
@RequestMapping("/Asiento")
public class AsientosRestController {
	
	@Autowired
	private AsientosService asientosService;
	
	private static final Logger logger = LoggerFactory.getLogger(AsientosRestController.class);
	
	@PostMapping("/bloquear")
	public GenericoResult SetBloquearAsiento(@RequestBody BloquearAsiento bloq) {
		logger.info("POST /Asiento/bloquear Body:"+bloq.toString());
		return asientosService.SetBloquearAsiento(bloq);
	}
	
	@DeleteMapping("/liberar")
	public GenericoResult eliminarLiberarAsiento(@RequestBody BloquearAsiento bloq) {
		logger.info("DELETE /Asiento/liberar Body:"+bloq.toString());
		return asientosService.eliminarLiberarAsiento(bloq);
	}
	
	@PutMapping("/actualizar")
	public GenericoResult actualizarLiberarAsiento(@RequestBody BloquearAsiento bloq) {
		logger.info("PUT /Asiento/actualizar Body:"+bloq.toString());
		return asientosService.actualizarLiberarAsiento(bloq);
	}
}
