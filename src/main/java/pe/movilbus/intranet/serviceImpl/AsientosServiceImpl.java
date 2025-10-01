package pe.movilbus.intranet.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.movilbus.intranet.beans.BloquearAsiento;
import pe.movilbus.intranet.dao.AsientosDao;
import pe.movilbus.intranet.result.GenericoResult;
import pe.movilbus.intranet.service.AsientosService;

@Service("asientoService")
public class AsientosServiceImpl implements AsientosService {
	
	@Autowired
	private AsientosDao asientoDao;
	
	@Override
	public GenericoResult SetBloquearAsiento(BloquearAsiento bloq) {
		return asientoDao.SetBloquearAsiento(bloq);
	}
	
	@Override
	public GenericoResult eliminarLiberarAsiento(BloquearAsiento bloq) {
		return asientoDao.eliminarLiberarAsiento(bloq);
	}
	
	@Override
	public GenericoResult actualizarLiberarAsiento(BloquearAsiento bloq) {
		return asientoDao.actualizarLiberarAsiento(bloq);
	}
}
