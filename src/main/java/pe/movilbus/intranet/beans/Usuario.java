package pe.movilbus.intranet.beans;

import java.io.Serializable;

import pe.movilbus.intranet.beans.GenericBean;

public class Usuario implements Serializable, Cloneable {
	
	private static final long serialVersionUID = 1L;
	private int usuario_id;
	private int personal_id;
	private int agencia_id;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String nombre;
	private String codigo;
	private String login;
	private String password;
	private int tipoPassword;
	private int tipoSeguridad;
	private int usuarioHardware;
	private String emailFuncionario;
	
	private Personal personal;
	
	public Usuario(){
		super();
	}
	
	public Usuario(int usuario_id, int personal_id, int agencia_id, String apellidoPaterno, String apellidoMaterno, String nombre, String codigo, String login, String password, int tipoPassword,
				   int tipoSeguridad, int usuarioHardware, String emailFuncionario){
		this.usuario_id = usuario_id;
		this.personal_id = personal_id;
		this.agencia_id = agencia_id;
		this.apellidoPaterno = apellidoPaterno;
		this.apellidoMaterno = apellidoMaterno;
		this.nombre = nombre;
		this.codigo = codigo;
		this.login = login;
		this.password = password;
		this.tipoPassword = tipoPassword;
		this.tipoSeguridad = tipoSeguridad;
		this.usuarioHardware = usuarioHardware;
		this.emailFuncionario = emailFuncionario;
	}

	public int getUsuario_id() {
		return usuario_id;
	}

	public void setUsuario_id(int usuario_id) {
		this.usuario_id = usuario_id;
	}

	public int getPersonal_id() {
		return personal_id;
	}

	public void setPersonal_id(int personal_id) {
		this.personal_id = personal_id;
	}

	public int getAgencia_id() {
		return agencia_id;
	}

	public void setAgencia_id(int agencia_id) {
		this.agencia_id = agencia_id;
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

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getTipoPassword() {
		return tipoPassword;
	}

	public void setTipoPassword(int tipoPassword) {
		this.tipoPassword = tipoPassword;
	}

	public int getTipoSeguridad() {
		return tipoSeguridad;
	}

	public void setTipoSeguridad(int tipoSeguridad) {
		this.tipoSeguridad = tipoSeguridad;
	}

	public int getUsuarioHardware() {
		return usuarioHardware;
	}

	public void setUsuarioHardware(int usuarioHardware) {
		this.usuarioHardware = usuarioHardware;
	}

	public String getEmailFuncionario() {
		return emailFuncionario;
	}

	public void setEmailFuncionario(String emailFuncionario) {
		this.emailFuncionario = emailFuncionario;
	}

	public Personal getPersonal() {
		return personal;
	}

	public void setPersonal(Personal personal) {
		this.personal = personal;
	}

}
