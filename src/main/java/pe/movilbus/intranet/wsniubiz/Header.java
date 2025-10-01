package pe.movilbus.intranet.wsniubiz;

import java.math.BigInteger;

public class Header {
	private String ecoreTransactionUUID;
	private BigInteger ecoreTransactionDate;
	private Integer millis;
	public Header() {
		
	}
	public String getEcoreTransactionUUID() {
		return ecoreTransactionUUID;
	}
	public void setEcoreTransactionUUID(String ecoreTransactionUUID) {
		this.ecoreTransactionUUID = ecoreTransactionUUID;
	}
	public BigInteger getEcoreTransactionDate() {
		return ecoreTransactionDate;
	}
	public void setEcoreTransactionDate(BigInteger ecoreTransactionDate) {
		this.ecoreTransactionDate = ecoreTransactionDate;
	}
	public Integer getMillis() {
		return millis;
	}
	public void setMillis(Integer millis) {
		this.millis = millis;
	}
}
