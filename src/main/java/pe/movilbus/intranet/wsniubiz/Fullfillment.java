package pe.movilbus.intranet.wsniubiz;

public class Fullfillment {
	private String channel;
	private String merchantId;
	private String terminalId;
	private String captureType;
	private Boolean countable;
	private Boolean fastPayment;
	private String signature;
	public Fullfillment() {
		
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getTerminalId() {
		return terminalId;
	}
	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}
	public String getCaptureType() {
		return captureType;
	}
	public void setCaptureType(String captureType) {
		this.captureType = captureType;
	}
	public Boolean getCountable() {
		return countable;
	}
	public void setCountable(Boolean countable) {
		this.countable = countable;
	}
	public Boolean getFastPayment() {
		return fastPayment;
	}
	public void setFastPayment(Boolean fastPayment) {
		this.fastPayment = fastPayment;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
}
