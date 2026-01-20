package pe.movilbus.intranet.dao;

import java.util.List;

import pe.movilbus.intranet.beans.Bus;
import pe.movilbus.intranet.beans.FormularioReten;
import pe.movilbus.intranet.beans.MantenimientoRuta;
import pe.movilbus.intranet.beans.Personal;
import pe.movilbus.intranet.beans.ReporteTareoConductor;
import pe.movilbus.intranet.result.MensajeResult;

public interface ConductoresDao {

	public List<Personal> getPilotos();
	
	public List<FormularioReten> getReporteFormularioReten(String fecha_inicio, String fecha_fin);
	
	public Bus getDatosBus(String unidad);
	
	public MensajeResult insertFormularioReten(FormularioReten data);
	
	public List<MantenimientoRuta> getReporteMantenimientoRuta(int localidadOrigen, int localidadDestino);
	
	public List<ReporteTareoConductor> getReporteTareoConductor(String fecha_inicio, String fecha_fin, int conductor_id);
}
