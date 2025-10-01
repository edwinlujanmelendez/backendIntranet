package pe.movilbus.intranet.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.movilbus.intranet.dao.ProgrammerDao;
import pe.movilbus.intranet.service.ProgrammerService;

@Service("programmerService")
public class ProgrammerServiceImpl implements ProgrammerService{
	
	@Autowired
	private ProgrammerDao programmerDao;
	
	@Override
	public List<Integer> cantidadPasajesPorCanjear(){
		return programmerDao.cantidadPasajesPorCanjear();
	}

}
