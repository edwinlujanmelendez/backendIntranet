package pe.movilbus.intranet.beans;

import java.io.Serializable;

public class NuevoLinkNiubiz implements Serializable, Cloneable {
	
	private static final long serialVersionUID = 1L;
	private String nroOperacion;
	private String fechaPartida;
	private double montoTotal;
	private String nombrePasajero;
	private String emailContacto;
	
	public NuevoLinkNiubiz(){
		super();
	}

	public NuevoLinkNiubiz(String nroOperacion, String fechaPartida, double montoTotal, String nombrePasajero,
			String emailContacto) {
		super();
		this.nroOperacion = nroOperacion;
		this.fechaPartida = fechaPartida;
		this.montoTotal = montoTotal;
		this.nombrePasajero = nombrePasajero;
		this.emailContacto = emailContacto;
	}

	public String getNroOperacion() {
		return nroOperacion;
	}

	public void setNroOperacion(String nroOperacion) {
		this.nroOperacion = nroOperacion;
	}

	public String getFechaPartida() {
		return fechaPartida;
	}

	public void setFechaPartida(String fechaPartida) {
		this.fechaPartida = fechaPartida;
	}

	public double getMontoTotal() {
		return montoTotal;
	}

	public void setMontoTotal(double montoTotal) {
		this.montoTotal = montoTotal;
	}

	public String getNombrePasajero() {
		return nombrePasajero;
	}

	public void setNombrePasajero(String nombrePasajero) {
		this.nombrePasajero = nombrePasajero;
	}

	public String getEmailContacto() {
		return emailContacto;
	}

	public void setEmailContacto(String emailContacto) {
		this.emailContacto = emailContacto;
	}

}
