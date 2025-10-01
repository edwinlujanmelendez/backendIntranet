package pe.movilbus.intranet.beans;

public class CanalVenta implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String denominacion;
	private String nombreCorto;
	private String color;
	
	public CanalVenta() {
		super();
	}

	/**
	 * @param id
	 */
	public CanalVenta(int id, String denominacion, String nombreCorto, String color) {
		this.id = id;
		this.denominacion = denominacion;
		this.nombreCorto = nombreCorto;
		this.color = color;
	}

	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getDenominacion() {
		return this.denominacion;
	}
	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

	public String getNombreCorto() {
		return this.nombreCorto;
	}
	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}

	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.getDenominacion();
	}

}
