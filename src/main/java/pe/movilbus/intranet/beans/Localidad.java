package pe.movilbus.intranet.beans;

public class Localidad implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String denominacion;
	private int uniagetit_id;
	private String c_est_web;
	private String iata;
	
	public Localidad() {
		super();
	}

	public Localidad(int id, String denominacion, int uniagetit_id, String c_est_web, String iata) {
		super();
		this.id = id;
		this.denominacion = denominacion;
		this.uniagetit_id = uniagetit_id;
		this.c_est_web = c_est_web;
		this.iata = iata;
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

	public int getUniagetit_id() {
		return uniagetit_id;
	}

	public void setUniagetit_id(int uniagetit_id) {
		this.uniagetit_id = uniagetit_id;
	}

	public String getC_est_web() {
		return c_est_web;
	}

	public void setC_est_web(String c_est_web) {
		this.c_est_web = c_est_web;
	}

	public String getIata() {
		return iata;
	}

	public void setIata(String iata) {
		this.iata = iata;
	}	
}