package pe.movilbus.intranet.dao;

import pe.movilbus.intranet.beans.BloquearAsiento;
import pe.movilbus.intranet.result.GenericoResult;

public interface AsientosDao {
	
	public GenericoResult SetBloquearAsiento(BloquearAsiento bloq);
	
	public GenericoResult eliminarLiberarAsiento(BloquearAsiento bloq);
	
	public GenericoResult actualizarLiberarAsiento(BloquearAsiento bloq);

}
