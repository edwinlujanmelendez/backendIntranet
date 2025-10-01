package pe.movilbus.intranet.dao;

import pe.movilbus.intranet.beans.AuditoriaLoginSispas;
import pe.movilbus.intranet.beans.Liquidacion;
import pe.movilbus.intranet.beans.LoginSispas;
import pe.movilbus.intranet.beans.RptLogin;

public interface UsuariosDao {
	
	public RptLogin loginSispas(LoginSispas loginSispas);
	
	public void controlLoginSispas(AuditoriaLoginSispas auditoria);
	
	public Liquidacion getVerificarCajaAbierta(int idUsuario, int idAgencia);
	
}