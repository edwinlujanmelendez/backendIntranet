package pe.movilbus.intranet.beans;

public class GrupoCupones implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	private int grupocupones_id;
	private String nombre;
	private int stock;
	
	public GrupoCupones(){
		super();
	}

	public GrupoCupones(int grupocupones_id, String nombre, int stock) {
		super();
		this.grupocupones_id = grupocupones_id;
		this.nombre = nombre;
		this.stock = stock;
	}

	public int getGrupocupones_id() {
		return grupocupones_id;
	}

	public void setGrupocupones_id(int grupocupones_id) {
		this.grupocupones_id = grupocupones_id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}
}
