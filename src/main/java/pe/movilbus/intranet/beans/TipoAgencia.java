package pe.movilbus.intranet.beans;

public class TipoAgencia implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String denominacion;
	
	public TipoAgencia(){
		super();
	}

	public TipoAgencia(int id, String denominacion) {
		super();
		this.id = id;
		this.denominacion = denominacion;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDenominacion() {
		return denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}
}