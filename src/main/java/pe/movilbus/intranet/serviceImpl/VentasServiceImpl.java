package pe.movilbus.intranet.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import pe.movilbus.intranet.beans.NuevoLinkNiubiz;
import pe.movilbus.intranet.beans.VentasGeneral;
import pe.movilbus.intranet.beans.VentasGeneralPasajeros;
import pe.movilbus.intranet.beans.DataTransbordos;
import pe.movilbus.intranet.beans.DescargarPdfPasajes;
import pe.movilbus.intranet.dao.VentasDao;
import pe.movilbus.intranet.daoImpl.VentasDaoImpl;
import pe.movilbus.intranet.result.MensajeConfirmacionResult;
import pe.movilbus.intranet.result.MensajeFlagResult;
import pe.movilbus.intranet.service.VentasService;
import pe.movilbus.intranet.util.Constantes;

@Service("ventasService")
public class VentasServiceImpl implements VentasService{
	
	@Autowired
	private VentasDao ventasDao;
	
	@Override
	public MensajeConfirmacionResult pagoLinkIziPay(VentasGeneral venta){
		return ventasDao.pagoLinkIziPay(venta);
	}
	
	@Override
	public ResponseEntity<String> actualizarVentasPagoLinkIziPay(String signature, String body){
		return ventasDao.actualizarVentasPagoLinkIziPay(signature, body);
	}
	
	@Override
	public MensajeConfirmacionResult pagoLinkNiubiz(VentasGeneral venta){
		return ventasDao.pagoLinkNiubiz(venta);
	}
		
	@Override
	public MensajeConfirmacionResult pagoLinkPagoEfectivo(VentasGeneral venta){
		String url = "";
		MensajeConfirmacionResult obj = ventasDao.pagoLinkPagoEfectivo(venta);
		
		if(obj.getResult()){
			url = Constantes.URL_BASE_RESPUESTAPEF + obj.getRqPECip().getCipUrlItem() + ".html";
			// Control de respuesta en pagoEfectivo
			ventasDao.registrarResponseFinalPagoEfectivo(obj,url);
			ventasDao.actualizarDatosHistorialVentas("", url, "", obj.getNroOperacion());
			
			return new MensajeConfirmacionResult(Constantes.RESULT_TRUE, "", url);
		}else{
			return new MensajeConfirmacionResult(Constantes.RESULT_FALSE, "", "Error al generar el número CIP");
		}
	}
	
	@Override
	public ResponseEntity<String> actualizarVentasPEfectivo(String  PESignature,String signatureBody) {
		return ventasDao.actualizarVentasPEfectivo(PESignature, signatureBody);
	}
		
	/*@Override
	public int verificarPagoNiubiz(String numOperacion){
		return ventasDao.verificarPagoNiubiz(numOperacion);
	}*/
	
	@Override
	public MensajeConfirmacionResult simularPago(String numOperacion){
		return ventasDao.simularPago(numOperacion);
	}
	
	@Override
	public MensajeConfirmacionResult actualizarVentasPagoLink(String jsonPagoLink){
		return ventasDao.actualizarVentasPagoLink(jsonPagoLink);
	}
	
	@Override
	public List<VentasGeneralPasajeros> getBuscarPasajes(String txt_input){
		return ventasDao.getBuscarPasajes(txt_input);
	}
	
	@Override
	public List<DescargarPdfPasajes> DescargarPdfPasajes(List<String> lstPasajes){
		return ventasDao.DescargarPdfPasajes(lstPasajes);
	}
	
	@Override
	public List<DataTransbordos> getBuscarTransbordos(String boleto){
		return ventasDao.getBuscarTransbordos(boleto);
	}
}