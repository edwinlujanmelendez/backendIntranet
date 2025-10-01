package pe.movilbus.intranet.wsniubiz;

public class TransaccionDenegada {
	
	private Integer errorCode;
	private String errorMessage;
	private Header header;
	private Data data;
	public TransaccionDenegada() {
		
	}
	public Integer getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public Header getHeader() {
		return header;
	}
	public void setHeader(Header header) {
		this.header = header;
	}
	public Data getData() {
		return data;
	}
	public void setData(Data data) {
		this.data = data;
	}
}
