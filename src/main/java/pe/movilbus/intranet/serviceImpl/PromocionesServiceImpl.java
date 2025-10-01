package pe.movilbus.intranet.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.movilbus.intranet.beans.DataPromocionesCuponesCyber;
import pe.movilbus.intranet.beans.GrupoCupones;
import pe.movilbus.intranet.beans.MaestroCupones;
import pe.movilbus.intranet.beans.PromocionSispas;
import pe.movilbus.intranet.beans.RptPromocion;
import pe.movilbus.intranet.dao.PromocionesDao;
import pe.movilbus.intranet.result.MensajeConfirmacionResult;
import pe.movilbus.intranet.service.PromocionesService;

@Service("promocionesService")
public class PromocionesServiceImpl implements PromocionesService{
	
	@Autowired
	private PromocionesDao promocionesDao;
	
	@Override
	public List<PromocionSispas> getPromocionesVentasReservasRuc(int idRuc){
		return promocionesDao.getPromocionesVentasReservasRuc(idRuc);
	}
	
	@Override
	public List<RptPromocion> getPromocionesSispas(int itinerarioIda, int rutaIda, int idServicioIda, String fechaRutaIda, int itinerarioVuelta, int rutaVuelta, int idServicioVuelta, String fechaRutaVuelta){
		return promocionesDao.getPromocionesSispas(itinerarioIda, rutaIda, idServicioIda, fechaRutaIda, itinerarioVuelta, rutaVuelta, idServicioVuelta, fechaRutaVuelta);
	}
	
	@Override
	public List<PromocionSispas> getPromocionesVentasReservas(int canalVenta){
		return promocionesDao.getPromocionesVentasReservas(canalVenta);
	}
	
	@Override
	public List<MaestroCupones> getPromocionesCupones(){
		return promocionesDao.getPromocionesCupones();
	}
	
	@Override
	public List<GrupoCupones> getGrupoCupones(){
		return promocionesDao.getGrupoCupones();
	}
	
	@Override
	public MensajeConfirmacionResult updateInsertPromocion(DataPromocionesCuponesCyber data){
		return promocionesDao.updateInsertPromocion(data);
	}
	
	@Override
	public MensajeConfirmacionResult eliminarPromocion(int cupon_id){
		return promocionesDao.eliminarPromocion(cupon_id);
	}
}
