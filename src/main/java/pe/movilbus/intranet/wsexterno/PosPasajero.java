package pe.movilbus.intranet.wsexterno;

import java.io.Serializable;

public class PosPasajero implements Serializable{

	private static final long serialVersionUID = 1L;

	private String DNI;
	private String Nombre;
	private String Paterno;
	private String Materno;
	private String DigitoVerificacion;
	private String NombreCompleto;
	
	public String getDNI() {
		return DNI;
	}
	public void setDNI(String dNI) {
		DNI = dNI;
	}
	public String getNombre() {
		return Nombre;
	}
	public void setNombre(String nombre) {
		Nombre = nombre;
	}
	public String getPaterno() {
		return Paterno;
	}
	public void setPaterno(String paterno) {
		Paterno = paterno;
	}
	public String getMaterno() {
		return Materno;
	}
	public void setMaterno(String materno) {
		Materno = materno;
	}
	public String getDigitoVerificacion() {
		return DigitoVerificacion;
	}
	public void setDigitoVerificacion(String digitoVerificacion) {
		DigitoVerificacion = digitoVerificacion;
	}
	public String getNombreCompleto() {
		return NombreCompleto;
	}
	public void setNombreCompleto(String nombreCompleto) {
		NombreCompleto = nombreCompleto;
	}
				
	
	
}
