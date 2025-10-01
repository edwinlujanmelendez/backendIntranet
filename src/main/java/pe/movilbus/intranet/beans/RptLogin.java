package pe.movilbus.intranet.beans;

import java.util.List;

public class RptLogin implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	private String token;
	private Usuario usuario;
	private List<UsuarioRol> usuarioRol;
	private UsuarioRol rol;
	private UsuarioAprobador usuarioAprobador;
	private UsuarioHardware usuarioHardware;
	private TipoComprobante tipoComprobante;
	private Liquidacion liquidacion;
	private int login;							//1=Elegir Rol, 2=Inicio de Sesión
	
	public RptLogin(){
		super();
	}

	public String getToken() {
		return token;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public List<UsuarioRol> getUsuarioRol() {
		return usuarioRol;
	}

	public void setUsuarioRol(List<UsuarioRol> usuarioRol) {
		this.usuarioRol = usuarioRol;
	}
	
	public UsuarioRol getRol() {
		return rol;
	}

	public void setRol(UsuarioRol rol) {
		this.rol = rol;
	}

	public UsuarioAprobador getUsuarioAprobador() {
		return usuarioAprobador;
	}

	public void setUsuarioAprobador(UsuarioAprobador usuarioAprobador) {
		this.usuarioAprobador = usuarioAprobador;
	}

	public UsuarioHardware getUsuarioHardware() {
		return usuarioHardware;
	}

	public void setUsuarioHardware(UsuarioHardware usuarioHardware) {
		this.usuarioHardware = usuarioHardware;
	}

	public TipoComprobante getTipoComprobante() {
		return tipoComprobante;
	}

	public void setTipoComprobante(TipoComprobante tipoComprobante) {
		this.tipoComprobante = tipoComprobante;
	}

	public Liquidacion getLiquidacion() {
		return liquidacion;
	}

	public void setLiquidacion(Liquidacion liquidacion) {
		this.liquidacion = liquidacion;
	}

	public int getLogin() {
		return login;
	}

	public void setLogin(int login) {
		this.login = login;
	}
}
