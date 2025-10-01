package pe.movilbus.intranet.beans;

import java.io.Serializable;

public class Nacionalidad implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String denominacion;
	private int tipmond_id;
	
	private TipoMoneda tipoMoneda;
	
	public Nacionalidad() {
		super();
	}

	public Nacionalidad(int id, String denominacion, int tipmond_id) {
		super();
		this.id = id;
		this.denominacion = denominacion;
		this.tipmond_id = tipmond_id;
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

	public TipoMoneda getTipoMoneda() {
		return tipoMoneda;
	}

	public void setTipoMoneda(TipoMoneda tipoMoneda) {
		this.tipoMoneda = tipoMoneda;
	}

	public int getTipmond_id() {
		return tipmond_id;
	}

	public void setTipmond_id(int tipmond_id) {
		this.tipmond_id = tipmond_id;
	}
}