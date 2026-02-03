package pe.movilbus.intranet.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.movilbus.intranet.beans.Bus;
import pe.movilbus.intranet.beans.FormularioReten;
import pe.movilbus.intranet.beans.MantenimientoRuta;
import pe.movilbus.intranet.beans.Personal;
import pe.movilbus.intranet.beans.ReporteTareoConductor;
import pe.movilbus.intranet.dao.ConductoresDao;
import pe.movilbus.intranet.result.MensajeResult;
import pe.movilbus.intranet.service.ConductoresService;

@Service("conductoresService")
public class ConductoresServiceImpl implements ConductoresService{
	
	@Autowired
	private ConductoresDao conductoresDao;
	
	@Override
	public List<Personal> getPilotos(){
		return conductoresDao.getPilotos();
	}
	
	@Override
	public List<FormularioReten> getReporteFormularioReten(String fecha_inicio, String fecha_fin){
		return conductoresDao.getReporteFormularioReten(fecha_inicio, fecha_fin);
	}
	
	@Override
	public Bus getDatosBus(String unidad){
		return conductoresDao.getDatosBus(unidad);
	}
	
	@Override
	public MensajeResult insertUpdateFormularioReten(FormularioReten data){
		return conductoresDao.insertUpdateFormularioReten(data);
	}
	
	@Override
	public List<MantenimientoRuta> getReporteMantenimientoRuta(int localidadOrigen, int localidadDestino){
		return conductoresDao.getReporteMantenimientoRuta(localidadOrigen, localidadDestino);
	}
	
	@Override
	public List<ReporteTareoConductor> getReporteTareoConductor(String fecha_inicio, String fecha_fin, int conductor_id){
		return conductoresDao.getReporteTareoConductor(fecha_inicio, fecha_fin, conductor_id);
	}
	
	@Override
	public MensajeResult insertDatosMantenimientoRuta(List<MantenimientoRuta> data){
		return conductoresDao.insertDatosMantenimientoRuta(data);
	}
	
	@Override
	public MensajeResult eliminarRegistroFormularioReten(int id){
		return conductoresDao.eliminarRegistroFormularioReten(id);
	}
}