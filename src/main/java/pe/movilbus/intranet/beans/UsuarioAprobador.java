package pe.movilbus.intranet.beans;

import java.io.Serializable;

import pe.movilbus.intranet.beans.GenericBean;

public class UsuarioAprobador extends GenericBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int id;
	private int nivelAprobacion;
	
	public UsuarioAprobador(){
		super();
	}
	
	public UsuarioAprobador(int id, int nivelAprobacion){
		this.id = id;
		this.nivelAprobacion = nivelAprobacion;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNivelAprobacion() {
		return nivelAprobacion;
	}

	public void setNivelAprobacion(int nivelAprobacion) {
		this.nivelAprobacion = nivelAprobacion;
	}
}