package pe.movilbus.intranet.service;

import pe.movilbus.intranet.beans.BloquearAsiento;
import pe.movilbus.intranet.result.GenericoResult;

public interface AsientosService {
	
	public GenericoResult SetBloquearAsiento(BloquearAsiento bloq);
	
	public GenericoResult eliminarLiberarAsiento(BloquearAsiento bloq);
	
	public GenericoResult actualizarLiberarAsiento(BloquearAsiento bloq);

}
