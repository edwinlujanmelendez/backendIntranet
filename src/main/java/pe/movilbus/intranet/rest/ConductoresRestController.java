package pe.movilbus.intranet.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.movilbus.intranet.beans.Bus;
import pe.movilbus.intranet.beans.FormularioReten;
import pe.movilbus.intranet.beans.MantenimientoRuta;
import pe.movilbus.intranet.beans.Personal;
import pe.movilbus.intranet.beans.ReporteTareoConductor;
import pe.movilbus.intranet.result.MensajeResult;
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
	
	@GetMapping("/getReporteFormularioReten/{fecha_inicio}/{fecha_fin}")
	public List<FormularioReten> getReporteFormularioReten(@PathVariable String fecha_inicio, @PathVariable String fecha_fin){
		return conductoresService.getReporteFormularioReten(fecha_inicio, fecha_fin);
	}
	
	@GetMapping("/getDatosBus/{unidad}")
	public ResponseEntity<Bus> getDatosBus(@PathVariable String unidad){
		Bus bus = conductoresService.getDatosBus(unidad);
	    return ResponseEntity.ok(bus);
	}
	
	@PostMapping("/insertUpdateFormularioReten")
	public MensajeResult insertUpdateFormularioReten(@RequestBody FormularioReten data){
		return conductoresService.insertUpdateFormularioReten(data);
	}
	
	@GetMapping("/getReporteMantenimientoRuta/{localidadOrigen}/{localidadDestino}")
	public List<MantenimientoRuta> getReporteMantenimientoRuta(@PathVariable int localidadOrigen, @PathVariable int localidadDestino){
		return conductoresService.getReporteMantenimientoRuta(localidadOrigen, localidadDestino);
	}
	
	@GetMapping("/getReporteTareoConductor/{fecha_inicio}/{fecha_fin}/{conductor_id}")
	public List<ReporteTareoConductor> getReporteTareoConductor(@PathVariable String fecha_inicio, @PathVariable String fecha_fin, @PathVariable int conductor_id){
		return conductoresService.getReporteTareoConductor(fecha_inicio, fecha_fin, conductor_id);
	}
	
	@PostMapping("/insertDatosMantenimientoRuta")
	public MensajeResult insertDatosMantenimientoRuta(@RequestBody List<MantenimientoRuta> data){
		return conductoresService.insertDatosMantenimientoRuta(data);
	}
	
	@GetMapping("/eliminarRegistroFormularioReten/{id}")
	public MensajeResult eliminarRegistroFormularioReten(@PathVariable int id){
		return conductoresService.eliminarRegistroFormularioReten(id);
	}
}