package pe.movilbus.intranet.beans;

import java.io.Serializable;

public class UsuariosCounter implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int usuario_id;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String nombre;
	private String login;
	
	public UsuariosCounter(){
		super();
	}

	public UsuariosCounter(int usuario_id, String apellidoPaterno, String apellidoMaterno, String nombre,
			String login) {
		super();
		this.usuario_id = usuario_id;
		this.apellidoPaterno = apellidoPaterno;
		this.apellidoMaterno = apellidoMaterno;
		this.nombre = nombre;
		this.login = login;
	}

	public int getUsuario_id() {
		return usuario_id;
	}

	public void setUsuario_id(int usuario_id) {
		this.usuario_id = usuario_id;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}	

}
