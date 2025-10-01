package pe.movilbus.intranet.beans;

import java.io.Serializable;

public class LoginSispas implements Serializable{

	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private int rol_id;
	
	public LoginSispas(){
		super();
	}
	
	public LoginSispas(String username, String password, int rol_id){
		this.username = username;
		this.password = password;
		this.rol_id = rol_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getRol_id() {
		return rol_id;
	}

	public void setRol_id(int rol_id) {
		this.rol_id = rol_id;
	}
}