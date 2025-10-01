package pe.movilbus.intranet.beans;

import java.io.Serializable;

public class ResultPagoLink implements Serializable{

	private static final long serialVersionUID = 1L;
	private String batchId;
	private String externalId;
	private String orderId;
	private String link;
	private String status;
	private String message;
	
	public ResultPagoLink(){
		super();
	}
	
	public ResultPagoLink(String batchId, String externalId, String orderId, String link, String status,
			String message) {
		super();
		this.batchId = batchId;
		this.externalId = externalId;
		this.orderId = orderId;
		this.link = link;
		this.status = status;
		this.message = message;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}