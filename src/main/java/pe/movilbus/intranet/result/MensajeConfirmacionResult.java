package pe.movilbus.intranet.result;

import java.io.Serializable;

import pe.movilbus.intranet.wspagoefectivo.ResponseGeneradorCip;

public class MensajeConfirmacionResult implements Serializable{

	private static final long serialVersionUID = 1L;
	private boolean result;
	private String nroOperacion;
	private String mensaje;
	private ResponseGeneradorCip rqPECip;
	
	public MensajeConfirmacionResult() {
		super();
	}
	
	public MensajeConfirmacionResult(boolean result, String mensaje) {
		super();
		this.result = result;
		this.mensaje = mensaje;
	}
	
	public MensajeConfirmacionResult(boolean result, String nroOperacion, String mensaje) {
		super();
		this.result = result;
		this.nroOperacion = nroOperacion;
		this.mensaje = mensaje;
	}
	
	public MensajeConfirmacionResult(Boolean result, String nroOperacion,ResponseGeneradorCip rqPECip) {
		super();
		this.result = result;
		this.nroOperacion = nroOperacion;
		this.rqPECip = rqPECip;
	}
	
	public boolean getResult() {
		return result;
	}
	
	public void setResult(boolean result) {
		this.result = result;
	}
	
	public String getNroOperacion() {
		return nroOperacion;
	}

	public void setNroOperacion(String nroOperacion) {
		this.nroOperacion = nroOperacion;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public ResponseGeneradorCip getRqPECip() {
		return rqPECip;
	}

	public void setRqPECip(ResponseGeneradorCip rqPECip) {
		this.rqPECip = rqPECip;
	}
}
