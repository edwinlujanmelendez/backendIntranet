package pe.movilbus.intranet.dao;

import java.io.Serializable;

public class MensajeFlagResultIziPay implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Boolean result;
	private String tokenSession;
	private String keyRSA;
	private String merchantCode;
	private String transactionId;
	private String orderNumber;
	private String mensaje;
	
	public MensajeFlagResultIziPay(){
		super();
	}

	public MensajeFlagResultIziPay(Boolean result, String tokenSession, String keyRSA, String merchantCode,
			String transactionId, String orderNumber, String mensaje) {
		super();
		this.result = result;
		this.tokenSession = tokenSession;
		this.keyRSA = keyRSA;
		this.merchantCode = merchantCode;
		this.transactionId = transactionId;
		this.orderNumber = orderNumber;
		this.mensaje = mensaje;
	}

	public Boolean getResult() {
		return result;
	}

	public void setResult(Boolean result) {
		this.result = result;
	}

	public String getTokenSession() {
		return tokenSession;
	}

	public void setTokenSession(String tokenSession) {
		this.tokenSession = tokenSession;
	}

	public String getKeyRSA() {
		return keyRSA;
	}

	public void setKeyRSA(String keyRSA) {
		this.keyRSA = keyRSA;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}	
}