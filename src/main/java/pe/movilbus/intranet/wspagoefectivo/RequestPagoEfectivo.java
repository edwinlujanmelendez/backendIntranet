package pe.movilbus.intranet.wspagoefectivo;

import java.io.Serializable;

public class RequestPagoEfectivo implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	private String nroOperacion;
	private String tipoDocumento;
	private String nroDocumento;
	private String nroTelefono;
	private String nombreUsuario;
	private String apellidoUsuario;
	private String emailcontacto;
	private double montoTotal;
	private String codePaisPhone;
	
	public RequestPagoEfectivo() {
		super();
	}
	
	public RequestPagoEfectivo(String nroOperacion, String tipoDocumento, String nroDocumento, String nroTelefono,
			String nombreUsuario, String apellidoUsuario, String emailcontacto, double montoTotal,
			String codePaisPhone) {
		super();
		this.nroOperacion = nroOperacion;
		this.tipoDocumento = tipoDocumento;
		this.nroDocumento = nroDocumento;
		this.nroTelefono = nroTelefono;
		this.nombreUsuario = nombreUsuario;
		this.apellidoUsuario = apellidoUsuario;
		this.emailcontacto = emailcontacto;
		this.montoTotal = montoTotal;
		this.codePaisPhone = codePaisPhone;
	}








	public String getNroOperacion() {
		return nroOperacion;
	}




	public void setNroOperacion(String nroOperacion) {
		this.nroOperacion = nroOperacion;
	}




	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getNroDocumento() {
		return nroDocumento;
	}

	public void setNroDocumento(String nroDocumento) {
		this.nroDocumento = nroDocumento;
	}

	public String getNroTelefono() {
		return nroTelefono;
	}

	public void setNroTelefono(String nroTelefono) {
		this.nroTelefono = nroTelefono;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getApellidoUsuario() {
		return apellidoUsuario;
	}

	public void setApellidoUsuario(String apellidoUsuario) {
		this.apellidoUsuario = apellidoUsuario;
	}

	public String getEmailcontacto() {
		return emailcontacto;
	}

	public void setEmailcontacto(String emailcontacto) {
		this.emailcontacto = emailcontacto;
	}

	public double getMontoTotal() {
		return montoTotal;
	}

	public void setMontoTotal(double montoTotal) {
		this.montoTotal = montoTotal;
	}




	public String getCodePaisPhone() {
		return codePaisPhone;
	}




	public void setCodePaisPhone(String codePaisPhone) {
		this.codePaisPhone = codePaisPhone;
	}
	
	
	

}
