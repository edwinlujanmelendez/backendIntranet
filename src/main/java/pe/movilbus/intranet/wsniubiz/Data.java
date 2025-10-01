package pe.movilbus.intranet.wsniubiz;

public class Data {
	private String CURRENCY;
	private String TRANSACTION_DATE;
	private String ACTION_CODE;
	private String STATUS;
	private String ACTION_DESCRIPTION;
	private String TRACE_NUMBER;
	private String AMOUNT;
	private String SIGNATURE;
	private String BRAND;
	private String MERCHANT;
	public Data () {
		
	}
	public String getCURRENCY() {
		return CURRENCY;
	}
	public void setCURRENCY(String cURRENCY) {
		CURRENCY = cURRENCY;
	}
	public String getTRANSACTION_DATE() {
		return TRANSACTION_DATE;
	}
	public void setTRANSACTION_DATE(String tRANSACTION_DATE) {
		TRANSACTION_DATE = tRANSACTION_DATE;
	}
	public String getACTION_CODE() {
		return ACTION_CODE;
	}
	public void setACTION_CODE(String aCTION_CODE) {
		ACTION_CODE = aCTION_CODE;
	}
	public String getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}
	public String getACTION_DESCRIPTION() {
		return ACTION_DESCRIPTION;
	}
	public void setACTION_DESCRIPTION(String aCTION_DESCRIPTION) {
		ACTION_DESCRIPTION = aCTION_DESCRIPTION;
	}
	public String getTRACE_NUMBER() {
		return TRACE_NUMBER;
	}
	public void setTRACE_NUMBER(String tRACE_NUMBER) {
		TRACE_NUMBER = tRACE_NUMBER;
	}
	public String getAMOUNT() {
		return AMOUNT;
	}
	public void setAMOUNT(String aMOUNT) {
		AMOUNT = aMOUNT;
	}
	public String getSIGNATURE() {
		return SIGNATURE;
	}
	public void setSIGNATURE(String sIGNATURE) {
		SIGNATURE = sIGNATURE;
	}
	public String getBRAND() {
		return BRAND;
	}
	public void setBRAND(String bRAND) {
		BRAND = bRAND;
	}
	public String getMERCHANT() {
		return MERCHANT;
	}
	public void setMERCHANT(String mERCHANT) {
		MERCHANT = mERCHANT;
	}
}
