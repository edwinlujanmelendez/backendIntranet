package pe.movilbus.intranet.wspagoefectivo;

import java.io.Serializable;

public class DataSignatureModel implements Serializable {
	
	
	private static final long serialVersionUID = 1L;

	private String cip;
	private String currency;
	private Double amount;
	private String paymentDate;
	private String transactionCode;
	
	public DataSignatureModel() {
		super();
	}

	public DataSignatureModel(String cip, String currency, double amount, String paymentDate, String transactionCode) {
		super();
		this.cip = cip;
		this.currency = currency;
		this.amount = amount;
		this.paymentDate = paymentDate;
		this.transactionCode = transactionCode;
	}

	public String getCip() {
		return cip;
	}

	public void setCip(String cip) {
		this.cip = cip;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}
	
	
	
}
