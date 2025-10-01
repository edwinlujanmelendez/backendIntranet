package pe.movilbus.intranet.beans;

public class BodyUrlPago extends GenericBean implements java.io.Serializable, Cloneable{
	
	private static final long serialVersionUID = 1L;
	private String email;
	private String url_pago;
	private String numoperacion;
	
	public BodyUrlPago(){
		super();
	}

	public BodyUrlPago(String email, String url_pago, String numoperacion) {
		super();
		this.email = email;
		this.url_pago = url_pago;
		this.numoperacion = numoperacion;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUrl_pago() {
		return url_pago;
	}

	public void setUrl_pago(String url_pago) {
		this.url_pago = url_pago;
	}

	public String getNumoperacion() {
		return numoperacion;
	}

	public void setNumoperacion(String numoperacion) {
		this.numoperacion = numoperacion;
	}

}
