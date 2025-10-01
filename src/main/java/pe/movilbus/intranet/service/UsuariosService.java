package pe.movilbus.intranet.service;

import pe.movilbus.intranet.beans.Liquidacion;
import pe.movilbus.intranet.beans.LoginSispas;
import pe.movilbus.intranet.beans.RptLogin;

public interface UsuariosService {
	
	public RptLogin loginSispas(LoginSispas loginSispas);
	
	public Liquidacion getVerificarCajaAbierta(int idUsuario, int idAgencia);
}
