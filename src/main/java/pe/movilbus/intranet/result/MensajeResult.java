package pe.movilbus.intranet.result;

import java.io.Serializable;

public class MensajeResult implements Serializable {
	
	private Boolean result;
	private String mensaje;
	
	public MensajeResult(){
		super();
	}
	
	public MensajeResult(Boolean result, String mensaje) {
		this.result = result;
		this.mensaje = mensaje;
	}

	public Boolean getResult() {
		return result;
	}

	public void setResult(Boolean result) {
		this.result = result;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
}