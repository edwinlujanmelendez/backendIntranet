package pe.movilbus.intranet.beans;

public class DatosIziPaySimularPago {

	private static final long serialVersionUID = 1L;
	private String nro_operation_niubiz;
	private String venpas_id;
	private String itinerario_id;
	private String ruta_id;
	private String n_numpiso;
	private String n_numasiento;	
	
	public DatosIziPaySimularPago(){
		super();
	}
	
	public DatosIziPaySimularPago(String nro_operation_niubiz, String venpas_id, String itinerario_id, String ruta_id,
			String n_numpiso, String n_numasiento) {
		super();
		this.nro_operation_niubiz = nro_operation_niubiz;
		this.venpas_id = venpas_id;
		this.itinerario_id = itinerario_id;
		this.ruta_id = ruta_id;
		this.n_numpiso = n_numpiso;
		this.n_numasiento = n_numasiento;
	}

	public String getNro_operation_niubiz() {
		return nro_operation_niubiz;
	}

	public void setNro_operation_niubiz(String nro_operation_niubiz) {
		this.nro_operation_niubiz = nro_operation_niubiz;
	}

	public String getVenpas_id() {
		return venpas_id;
	}

	public void setVenpas_id(String venpas_id) {
		this.venpas_id = venpas_id;
	}

	public String getItinerario_id() {
		return itinerario_id;
	}

	public void setItinerario_id(String itinerario_id) {
		this.itinerario_id = itinerario_id;
	}

	public String getRuta_id() {
		return ruta_id;
	}

	public void setRuta_id(String ruta_id) {
		this.ruta_id = ruta_id;
	}

	public String getN_numpiso() {
		return n_numpiso;
	}

	public void setN_numpiso(String n_numpiso) {
		this.n_numpiso = n_numpiso;
	}

	public String getN_numasiento() {
		return n_numasiento;
	}

	public void setN_numasiento(String n_numasiento) {
		this.n_numasiento = n_numasiento;
	}
}
