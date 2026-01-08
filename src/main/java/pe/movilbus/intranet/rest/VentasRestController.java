package pe.movilbus.intranet.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.movilbus.intranet.beans.VentasGeneralPasajeros;
import pe.movilbus.intranet.result.MensajeConfirmacionResult;
import pe.movilbus.intranet.result.MensajeFlagResult;
import pe.movilbus.intranet.beans.JsonRest;
import pe.movilbus.intranet.beans.NuevoLinkNiubiz;
import pe.movilbus.intranet.beans.ResultPagoLink;
import pe.movilbus.intranet.beans.VentasGeneral;
import pe.movilbus.intranet.beans.DataTransbordos;
import pe.movilbus.intranet.beans.DescargarPdfPasajes;
import pe.movilbus.intranet.service.VentasService;

@RestController
@RequestMapping("/Ventas")
public class VentasRestController {
	
	@Autowired
	private VentasService ventasService;
	
	@PostMapping("/pagoLinkIziPay")
	public MensajeConfirmacionResult pagoLinkIziPay(@RequestBody VentasGeneral venta){
		return ventasService.pagoLinkIziPay(venta);
	}
	
	@PostMapping("/actualizarVentasPagoLinkIziPay")
    public ResponseEntity<String> actualizarVentasPagoLinkIziPay(@RequestHeader("Signature") String signature, @RequestBody String body){
		return ventasService.actualizarVentasPagoLinkIziPay(signature, body);
	}
	
	@PostMapping("/pagoLinkNiubiz")
	public MensajeConfirmacionResult pagoLinkNiubiz(@RequestBody VentasGeneral venta){
		return ventasService.pagoLinkNiubiz(venta);
	}
		
	@PostMapping("/pagoLinkPagoEfectivo")
	public MensajeConfirmacionResult pagoLinkPagoEfectivo(@RequestBody VentasGeneral venta){
		return ventasService.pagoLinkPagoEfectivo(venta);
	}
	
	@PostMapping("/actualizarVentasPEfectivo")
    public ResponseEntity<String> actualizarVentasPEfectivo(@RequestHeader("PE-Signature") String PESignature, @RequestBody String signatureBody){			
		return ventasService.actualizarVentasPEfectivo(PESignature,signatureBody);
	}
	
	@PostMapping("/actualizarVentasPagoLink")
    public MensajeConfirmacionResult actualizarVentasPagoLink(@RequestBody String jsonPagoLink){
		return ventasService.actualizarVentasPagoLink(jsonPagoLink);
	}
	
	/*@GetMapping("/verificarPagoNiubiz/{numOperacion}")
	public int verificarPagoNiubiz(@PathVariable String numOperacion){
		return ventasService.verificarPagoNiubiz(numOperacion);
	}*/
	
	@GetMapping("/simularPago/{numOperacion}")
	public MensajeConfirmacionResult simularPago(@PathVariable String numOperacion){
		return ventasService.simularPago(numOperacion);
	}
	
	@GetMapping("/getBuscarPasajes/{txt_input}")
	public List<VentasGeneralPasajeros> getBuscarPasajes(@PathVariable String txt_input){
		return ventasService.getBuscarPasajes(txt_input);
	}
	
	@PostMapping("/DescargarPdfPasajes")
    public List<DescargarPdfPasajes> DescargarPdfPasajes(@RequestBody List<String> lstPasajes){
		return ventasService.DescargarPdfPasajes(lstPasajes);
	}
	
	@GetMapping("/getBuscarTransbordos/{boleto}")
	public List<DataTransbordos> getBuscarTransbordos(@PathVariable String boleto){
		return ventasService.getBuscarTransbordos(boleto);
	}
}