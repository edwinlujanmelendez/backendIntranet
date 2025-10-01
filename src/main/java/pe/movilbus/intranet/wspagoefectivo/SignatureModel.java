package pe.movilbus.intranet.wspagoefectivo;

import java.io.Serializable;

public class SignatureModel implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	private String eventType;
	private String operationNumber;
	private DataSignatureModel data;
	
	public SignatureModel() {
		super();
	}

	public SignatureModel(String eventType, String operationNumber, DataSignatureModel data) {
		super();
		this.eventType = eventType;
		this.operationNumber = operationNumber;
		this.data = data;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getOperationNumber() {
		return operationNumber;
	}

	public void setOperationNumber(String operationNumber) {
		this.operationNumber = operationNumber;
	}

	public DataSignatureModel getData() {
		return data;
	}

	public void setData(DataSignatureModel data) {
		this.data = data;
	}

	
	
	
}