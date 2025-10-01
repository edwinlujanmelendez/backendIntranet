package pe.movilbus.intranet.beans;

import java.io.Serializable;

public class TipoMoneda implements Serializable{

	private static final long serialVersionUID = 1L;
	private int id;
	private String denominacion;
	private String unidadMonetaria;
	private String simboloMonetario;
	
	public TipoMoneda(){
		super();
	}

	public TipoMoneda(int id, String denominacion, String unidadMonetaria, String simboloMonetario) {
		super();
		this.id = id;
		this.denominacion = denominacion;
		this.unidadMonetaria = unidadMonetaria;
		this.simboloMonetario = simboloMonetario;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDenominacion() {
		return denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

	public String getUnidadMonetaria() {
		return unidadMonetaria;
	}

	public void setUnidadMonetaria(String unidadMonetaria) {
		this.unidadMonetaria = unidadMonetaria;
	}

	public String getSimboloMonetario() {
		return simboloMonetario;
	}

	public void setSimboloMonetario(String simboloMonetario) {
		this.simboloMonetario = simboloMonetario;
	}
}