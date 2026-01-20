package pe.movilbus.intranet.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.movilbus.intranet.beans.Agencia;
import pe.movilbus.intranet.beans.BodyUrlPago;
import pe.movilbus.intranet.beans.ReporteDetallado;
import pe.movilbus.intranet.beans.ReporteSeguimientoFrotcom;
import pe.movilbus.intranet.beans.UsuariosCounter;
import pe.movilbus.intranet.dao.ReportesDao;
import pe.movilbus.intranet.service.ReportesService;

@Service("reportesService")
public class ReportesServiceImpl implements ReportesService{
	
	@Autowired
	private ReportesDao reportesDao;
	
	@Override
	public List<Agencia> getAgencias(int idAgencia){
		return reportesDao.getAgencias(idAgencia);
	}
	
	@Override
	public List<UsuariosCounter> getUsuariosCounter(int agencia_id, String fechaInicio, String fechaFin){
		return reportesDao.getUsuariosCounter(agencia_id, fechaInicio, fechaFin);
	}
	
	@Override
	public List<ReporteDetallado> getReporteDetallado(int agencia_id, int usuario_id, String fechaInicio, String fechaFin){
		return reportesDao.getReporteDetallado(agencia_id, usuario_id, fechaInicio, fechaFin);
	}

	@Override
	public int postEnviarUrlPago(BodyUrlPago data){
		return reportesDao.postEnviarUrlPago(data);
	}
	
	@Override
	public List<ReporteSeguimientoFrotcom> getSeguimientoFrotcom(int localidadOrigen, int localidadDestino, String fechaInicio, String fechaFin){
		return reportesDao.getSeguimientoFrotcom(localidadOrigen, localidadDestino, fechaInicio, fechaFin);
	}
}