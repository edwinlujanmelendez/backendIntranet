package pe.movilbus.intranet.wspagoefectivo;

import java.io.Serializable;

public class ResponseGeneradorCip implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	private String transactionCode;
	private String cipUrlItem;
	private String codeCip;
	private String url;
	
	public ResponseGeneradorCip() {
		super();
	}

	
	public ResponseGeneradorCip(String transactionCode) {
		super();
		this.transactionCode = transactionCode;
	}


	public ResponseGeneradorCip(String transactionCode, String cipUrlItem, String codeCip, String url) {
		super();
		this.transactionCode = transactionCode;
		this.cipUrlItem = cipUrlItem;
		this.codeCip = codeCip;
		this.url = url;
	}

	public String getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}

	public String getCipUrlItem() {
		return cipUrlItem;
	}

	public void setCipUrlItem(String cipUrlItem) {
		this.cipUrlItem = cipUrlItem;
	}

	public String getCodeCip() {
		return codeCip;
	}

	public void setCodeCip(String codeCip) {
		this.codeCip = codeCip;
	}

	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
}
