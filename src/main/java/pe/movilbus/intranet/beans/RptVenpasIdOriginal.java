package pe.movilbus.intranet.beans;

public class RptVenpasIdOriginal implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	private String n_numopeban;
	private String nro_operation_niubiz;
	private String nro_cip_pagoefectivo;
	
	public RptVenpasIdOriginal(){
		super();
	}

	public RptVenpasIdOriginal(String n_numopeban, String nro_operation_niubiz, String nro_cip_pagoefectivo) {
		super();
		this.n_numopeban = n_numopeban;
		this.nro_operation_niubiz = nro_operation_niubiz;
		this.nro_cip_pagoefectivo = nro_cip_pagoefectivo;
	}

	public String getN_numopeban() {
		return n_numopeban;
	}

	public void setN_numopeban(String n_numopeban) {
		this.n_numopeban = n_numopeban;
	}

	public String getNro_operation_niubiz() {
		return nro_operation_niubiz;
	}

	public void setNro_operation_niubiz(String nro_operation_niubiz) {
		this.nro_operation_niubiz = nro_operation_niubiz;
	}

	public String getNro_cip_pagoefectivo() {
		return nro_cip_pagoefectivo;
	}

	public void setNro_cip_pagoefectivo(String nro_cip_pagoefectivo) {
		this.nro_cip_pagoefectivo = nro_cip_pagoefectivo;
	}
}
