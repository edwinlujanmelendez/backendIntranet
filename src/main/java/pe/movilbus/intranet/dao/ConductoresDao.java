package pe.movilbus.intranet.dao;

import java.util.List;

import pe.movilbus.intranet.beans.Bus;
import pe.movilbus.intranet.beans.FormularioReten;
import pe.movilbus.intranet.beans.Personal;
import pe.movilbus.intranet.result.MensajeResult;

public interface ConductoresDao {

	public List<Personal> getPilotos();
	
	public List<FormularioReten> getReporteFormularioReten(String fecha_inicio, String fecha_fin);
	
	public Bus getDatosBus(String unidad);
	
	public MensajeResult insertFormularioReten(FormularioReten data);
}
