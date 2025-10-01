package pe.movilbus.intranet.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.movilbus.intranet.beans.Liquidacion;
import pe.movilbus.intranet.beans.LoginSispas;
import pe.movilbus.intranet.beans.RptLogin;
import pe.movilbus.intranet.dao.UsuariosDao;
import pe.movilbus.intranet.service.UsuariosService;

@Service("usuariosService")
public class UsuariosServiceImpl implements UsuariosService{
	
	@Autowired
	private UsuariosDao usuariosDao;
	
	@Override
	public RptLogin loginSispas(LoginSispas loginSispas){
		return usuariosDao.loginSispas(loginSispas);
	}
	
	@Override
	public Liquidacion getVerificarCajaAbierta(int idUsuario, int idAgencia){
		return usuariosDao.getVerificarCajaAbierta(idUsuario, idAgencia);
	}
}