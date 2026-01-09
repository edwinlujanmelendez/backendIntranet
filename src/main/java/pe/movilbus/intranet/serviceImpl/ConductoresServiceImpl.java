package pe.movilbus.intranet.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.movilbus.intranet.beans.Bus;
import pe.movilbus.intranet.beans.FormularioReten;
import pe.movilbus.intranet.beans.Personal;
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
	public MensajeResult insertFormularioReten(FormularioReten data){
		return conductoresDao.insertFormularioReten(data);
	}
}