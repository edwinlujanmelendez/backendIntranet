package pe.movilbus.intranet.result;

import java.io.Serializable;

import pe.movilbus.intranet.beans.RequestPayBeans;
import pe.movilbus.intranet.wsexterno.VPOS2;
import pe.movilbus.intranet.wspagoefectivo.ResponseGeneradorCip;


public class MensajeFlagResult implements Serializable{

	private static final long serialVersionUID = 1L;
	private Boolean result;
	private String mensaje;
	private String URLPayment;
	private VPOS2 payment;
	private RequestPayBeans rqNiubiz;
	private ResponseGeneradorCip rqPECip;
	
	
	
	public MensajeFlagResult() {
		super();
	}

	public MensajeFlagResult(Boolean result, String mensaje, String uRLPayment, VPOS2 payment) {
		super();
		this.result = result;
		this.mensaje = mensaje;
		URLPayment = uRLPayment;
		this.payment = payment;
	}

	public MensajeFlagResult(Boolean result, String mensaje,RequestPayBeans rqNiubiz) {
		super();
		this.result = result;
		this.mensaje = mensaje;
		this.rqNiubiz = rqNiubiz;
	}

	public MensajeFlagResult(Boolean result, String mensaje,ResponseGeneradorCip rqPECip) {
		super();
		this.result = result;
		this.mensaje = mensaje;
		this.rqPECip = rqPECip;
	}

	
	public MensajeFlagResult(Boolean result, String mensaje) {
		super();
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


	public RequestPayBeans getRqNiubiz() {
		return rqNiubiz;
	}




	public void setRqNiubiz(RequestPayBeans rqNiubiz) {
		this.rqNiubiz = rqNiubiz;
	}

	public String getURLPayment() {
		return URLPayment;
	}

	public void setURLPayment(String uRLPayment) {
		URLPayment = uRLPayment;
	}

	public VPOS2 getPayment() {
		return payment;
	}

	public void setPayment(VPOS2 payment) {
		this.payment = payment;
	}

	public ResponseGeneradorCip getRqPECip() {
		return rqPECip;
	}

	public void setRqPECip(ResponseGeneradorCip rqPECip) {
		this.rqPECip = rqPECip;
	}
	
	
	

}
