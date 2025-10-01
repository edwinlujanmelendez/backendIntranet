package pe.movilbus.intranet.beans;

public class TipoComprobante implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String denominacion;
	private String abreviatura;
	private int rubro;
	
	public TipoComprobante() {
		super();
	}

	public TipoComprobante(int id, String denominacion, String abreviatura, int rubro) {
		super();
		this.id = id;
		this.denominacion = denominacion;
		this.abreviatura = abreviatura;
		this.rubro = rubro;
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

	public String getAbreviatura() {
		return abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	public int getRubro() {
		return rubro;
	}

	public void setRubro(int rubro) {
		this.rubro = rubro;
	}
}