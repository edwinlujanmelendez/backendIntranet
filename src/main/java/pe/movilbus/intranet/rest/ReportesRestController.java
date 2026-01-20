package pe.movilbus.intranet.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.movilbus.intranet.beans.Agencia;
import pe.movilbus.intranet.beans.BodyUrlPago;
import pe.movilbus.intranet.beans.ReporteDetallado;
import pe.movilbus.intranet.beans.ReporteSeguimientoFrotcom;
import pe.movilbus.intranet.beans.UsuariosCounter;
import pe.movilbus.intranet.service.ReportesService;

@RestController
@RequestMapping("/Reportes")
public class ReportesRestController {
	
	@Autowired
	private ReportesService reportesService;
	
	@GetMapping("/getAgencias/{idAgencia}")
	public List<Agencia> getAgencias(@PathVariable int idAgencia){
		return reportesService.getAgencias(idAgencia);
	}
	
	@GetMapping("/getUsuariosCounter/{agencia_id}/{fechaInicio}/{fechaFin}")
	public List<UsuariosCounter> getUsuariosCounter(@PathVariable int agencia_id, @PathVariable String fechaInicio, @PathVariable String fechaFin){
		return reportesService.getUsuariosCounter(agencia_id, fechaInicio, fechaFin);
	}
	
	@GetMapping("/getReporteDetallado/{agencia_id}/{usuario_id}/{fechaInicio}/{fechaFin}")
	public List<ReporteDetallado> getReporteDetallado(@PathVariable int agencia_id, @PathVariable int usuario_id, @PathVariable String fechaInicio, @PathVariable String fechaFin){
		return reportesService.getReporteDetallado(agencia_id, usuario_id, fechaInicio, fechaFin);
	}
	
	@PostMapping("/postEnviarUrlPago")
	public int postEnviarUrlPago(@RequestBody BodyUrlPago data){
		return reportesService.postEnviarUrlPago(data);
	}
	
	@GetMapping("/getSeguimientoFrotcom/{localidadOrigen}/{localidadDestino}/{fechaInicio}/{fechaFin}")
	public List<ReporteSeguimientoFrotcom> getSeguimientoFrotcom(@PathVariable int localidadOrigen, @PathVariable int localidadDestino, @PathVariable String fechaInicio, @PathVariable String fechaFin){
		return reportesService.getSeguimientoFrotcom(localidadOrigen, localidadDestino, fechaInicio, fechaFin);
	}
}