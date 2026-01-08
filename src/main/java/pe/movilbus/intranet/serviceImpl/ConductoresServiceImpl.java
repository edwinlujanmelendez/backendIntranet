package pe.movilbus.intranet.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.movilbus.intranet.beans.Bus;
import pe.movilbus.intranet.beans.Personal;
import pe.movilbus.intranet.dao.ConductoresDao;
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
	public Bus getDatosBus(String unidad){
		return conductoresDao.getDatosBus(unidad);
	}
}