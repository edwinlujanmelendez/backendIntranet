package pe.movilbus.intranet.dao;

import java.util.List;

import org.springframework.http.ResponseEntity;

import pe.movilbus.intranet.beans.NuevoLinkNiubiz;
import pe.movilbus.intranet.beans.VentasGeneral;
import pe.movilbus.intranet.beans.VentasGeneralPasajeros;
import pe.movilbus.intranet.beans.DataTransbordos;
import pe.movilbus.intranet.beans.DescargarPdfPasajes;
import pe.movilbus.intranet.result.MensajeConfirmacionResult;
import pe.movilbus.intranet.result.MensajeFlagResult;

public interface VentasDao {
		
	public MensajeConfirmacionResult pagoLinkNiubiz(VentasGeneral venta);
		
	public MensajeConfirmacionResult pagoLinkPagoEfectivo(VentasGeneral venta);
	
	public ResponseEntity<String> actualizarVentasPEfectivo(String PESignature, String signatureBody);
	
	public void registrarResponseFinalPagoEfectivo(MensajeConfirmacionResult resultado,String urlRespuesta);
		
	//public int verificarPagoNiubiz(String numOperacion);
	
	public MensajeConfirmacionResult simularPago(String numOperacion);
	
	public MensajeConfirmacionResult actualizarVentasPagoLink(String jsonPagoLink);
	
	public List<VentasGeneralPasajeros> getBuscarPasajes(String txt_input);
	
	public List<DescargarPdfPasajes> DescargarPdfPasajes(List<String> lstPasajes);
	
	public List<DataTransbordos> getBuscarTransbordos(String boleto);

	void actualizarDatosHistorialVentas(String email_contacto, String urlPago, String orderId, String numOperacion);
}