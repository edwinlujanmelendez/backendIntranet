package pe.movilbus.intranet.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.movilbus.intranet.beans.Liquidacion;
import pe.movilbus.intranet.beans.LoginSispas;
import pe.movilbus.intranet.beans.RptLogin;
import pe.movilbus.intranet.service.UsuariosService;

@RestController
@RequestMapping("/Usuarios")
public class UsuariosRestController {
	
	@Autowired
	private UsuariosService usuariosService;
	
	private static final Logger logger = LoggerFactory.getLogger(UsuariosRestController.class);
	
	@PostMapping("/loginSispas")
	public RptLogin loginSispas(@RequestBody LoginSispas loginSispas){
		logger.info("POST /Usuarios/loginSispas Body:"+loginSispas.toString());
		return usuariosService.loginSispas(loginSispas);
	}
	
	@GetMapping("/getVerificarCajaAbierta/{idUsuario}/{idAgencia}")
	public Liquidacion getVerificarCajaAbierta(@PathVariable int idUsuario, @PathVariable int idAgencia){
		logger.info("GET /Usuarios/getVerificarCajaAbierta Variables, idUsuario:"+idUsuario+", idAgencia:"+idAgencia);
		return usuariosService.getVerificarCajaAbierta(idUsuario, idAgencia);
	}
}