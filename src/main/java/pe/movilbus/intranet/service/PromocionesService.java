package pe.movilbus.intranet.service;

import java.util.List;

import pe.movilbus.intranet.beans.DataPromocionesCuponesCyber;
import pe.movilbus.intranet.beans.GrupoCupones;
import pe.movilbus.intranet.beans.MaestroCupones;
import pe.movilbus.intranet.beans.PromocionSispas;
import pe.movilbus.intranet.beans.RptPromocion;
import pe.movilbus.intranet.result.MensajeConfirmacionResult;

public interface PromocionesService {
	
	public List<PromocionSispas> getPromocionesVentasReservasRuc(int idRuc);
	
	public List<RptPromocion> getPromocionesSispas(int itinerarioIda, int rutaIda, int idServicioIda, String fechaRutaIda, int itinerarioVuelta, int rutaVuelta, int idServicioVuelta, String fechaRutaVuelta);
	
	public List<PromocionSispas> getPromocionesVentasReservas(int canalVenta);
	
	public List<MaestroCupones> getPromocionesCupones();
	
	public List<GrupoCupones> getGrupoCupones();
	
	public MensajeConfirmacionResult updateInsertPromocion(DataPromocionesCuponesCyber data);
	
	public MensajeConfirmacionResult eliminarPromocion(int cupon_id);
}