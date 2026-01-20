package pe.movilbus.intranet.dao;

import java.util.List;

import pe.movilbus.intranet.beans.Agencia;
import pe.movilbus.intranet.beans.BodyUrlPago;
import pe.movilbus.intranet.beans.ReporteDetallado;
import pe.movilbus.intranet.beans.ReporteSeguimientoFrotcom;
import pe.movilbus.intranet.beans.UsuariosCounter;

public interface ReportesDao {
	
	public List<Agencia> getAgencias(int idAgencia);
	
	public List<UsuariosCounter> getUsuariosCounter(int agencia_id, String fechaInicio, String fechaFin);
	
	public List<ReporteDetallado> getReporteDetallado(int agencia_id, int usuario_id, String fechaInicio, String fechaFin);
	
	public int postEnviarUrlPago(BodyUrlPago data);
	
	public List<ReporteSeguimientoFrotcom> getSeguimientoFrotcom(int localidadOrigen, int localidadDestino, String fechaInicio, String fechaFin);
}