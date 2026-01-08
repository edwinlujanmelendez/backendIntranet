package pe.movilbus.intranet.dao;

import java.util.List;

import pe.movilbus.intranet.beans.Bus;
import pe.movilbus.intranet.beans.Personal;

public interface ConductoresDao {

	public List<Personal> getPilotos();
	
	public Bus getDatosBus(String unidad);
}
