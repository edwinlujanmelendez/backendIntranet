package pe.movilbus.intranet.wsexterno;

import java.io.Serializable;

public class PosCliente implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	private String RUC;
	private String RazonSocial;
	private String Estado;
	private String Condicion;
	private String Direccion;
	private String Departamento;
	private String Provincia;
	private String Distrito;
	
	
	public String getRUC() {
		return RUC;
	}
	public void setRUC(String rUC) {
		RUC = rUC;
	}
	public String getRazonSocial() {
		return RazonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		RazonSocial = razonSocial;
	}
	public String getEstado() {
		return Estado;
	}
	public void setEstado(String estado) {
		Estado = estado;
	}
	public String getCondicion() {
		return Condicion;
	}
	public void setCondicion(String condicion) {
		Condicion = condicion;
	}
	public String getDireccion() {
		return Direccion;
	}
	public void setDireccion(String direccion) {
		Direccion = direccion;
	}
	public String getDepartamento() {
		return Departamento;
	}
	public void setDepartamento(String departamento) {
		Departamento = departamento;
	}
	public String getProvincia() {
		return Provincia;
	}
	public void setProvincia(String provincia) {
		Provincia = provincia;
	}
	public String getDistrito() {
		return Distrito;
	}
	public void setDistrito(String distrito) {
		Distrito = distrito;
	}
	
	

}
