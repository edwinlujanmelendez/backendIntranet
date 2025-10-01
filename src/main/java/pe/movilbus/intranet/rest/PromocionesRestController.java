package pe.movilbus.intranet.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.movilbus.intranet.beans.DataPromocionesCuponesCyber;
import pe.movilbus.intranet.beans.GrupoCupones;
import pe.movilbus.intranet.beans.MaestroCupones;
import pe.movilbus.intranet.beans.PromocionSispas;
import pe.movilbus.intranet.beans.RptPromocion;
import pe.movilbus.intranet.result.MensajeConfirmacionResult;
import pe.movilbus.intranet.service.PromocionesService;

@RestController
@RequestMapping("/Promociones")
public class PromocionesRestController {
	
	@Autowired
	private PromocionesService promocionesService;
	
	@GetMapping("/getPromocionesVentasReservasRuc/{idRuc}")
	public List<PromocionSispas> getPromocionesVentasReservasRuc(@PathVariable int idRuc){
		return promocionesService.getPromocionesVentasReservasRuc(idRuc);
	}
	
	@GetMapping("/getPromocionesSispas/{itinerarioIda}/{rutaIda}/{idServicioIda}/{fechaRutaIda}/{itinerarioVuelta}/{rutaVuelta}/{idServicioVuelta}/{fechaRutaVuelta}")
	public List<RptPromocion> getPromocionesSispas(@PathVariable int itinerarioIda, @PathVariable int rutaIda, @PathVariable int idServicioIda, @PathVariable String fechaRutaIda, @PathVariable int itinerarioVuelta, @PathVariable int rutaVuelta, @PathVariable int idServicioVuelta, @PathVariable String fechaRutaVuelta){
		return promocionesService.getPromocionesSispas(itinerarioIda, rutaIda, idServicioIda, fechaRutaIda, itinerarioVuelta, rutaVuelta, idServicioVuelta, fechaRutaVuelta);
	}
	
	@GetMapping("/getPromocionesVentasReservas/{canalVenta}")
	public List<PromocionSispas> getPromocionesVentasReservas(@PathVariable int canalVenta){
		return promocionesService.getPromocionesVentasReservas(canalVenta);
	}
	
	@GetMapping("/getPromocionesCupones")
	public List<MaestroCupones> getPromocionesCupones(){
		return promocionesService.getPromocionesCupones();
	}
	
	@GetMapping("/getGrupoCupones")
	public List<GrupoCupones> getGrupoCupones(){
		return promocionesService.getGrupoCupones();
	}
	
	@PostMapping("/updateInsertPromocion")
	public MensajeConfirmacionResult updateInsertPromocion(@RequestBody DataPromocionesCuponesCyber data){
		return promocionesService.updateInsertPromocion(data);
	}
	
	@GetMapping("/eliminarPromocion/{cupon_id}")
	public MensajeConfirmacionResult eliminarPromocion(@PathVariable int cupon_id){
		return promocionesService.eliminarPromocion(cupon_id);
	}
}