package pe.movilbus.intranet.service;

import java.util.List;

import pe.movilbus.intranet.beans.Bus;
import pe.movilbus.intranet.beans.Personal;

public interface ConductoresService {
	
	public List<Personal> getPilotos();
	
	public Bus getDatosBus(String unidad);
}