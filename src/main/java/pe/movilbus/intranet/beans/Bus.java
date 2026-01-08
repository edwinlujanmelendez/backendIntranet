package pe.movilbus.intranet.beans;

public class Bus {
	
	private int bus_id;
	private String c_codigo;
	private String c_numplaca;
	private int n_capacidad;
	private String c_nomcor;
	
	public Bus(int bus_id, String c_codigo, String c_numplaca, int n_capacidad, String c_nomcor) {
		super();
		this.bus_id = bus_id;
		this.c_codigo = c_codigo;
		this.c_numplaca = c_numplaca;
		this.n_capacidad = n_capacidad;
		this.c_nomcor = c_nomcor;
	}

	public int getBus_id() {
		return bus_id;
	}

	public void setBus_id(int bus_id) {
		this.bus_id = bus_id;
	}

	public String getC_codigo() {
		return c_codigo;
	}

	public void setC_codigo(String c_codigo) {
		this.c_codigo = c_codigo;
	}

	public String getC_numplaca() {
		return c_numplaca;
	}

	public void setC_numplaca(String c_numplaca) {
		this.c_numplaca = c_numplaca;
	}

	public int getN_capacidad() {
		return n_capacidad;
	}

	public void setN_capacidad(int n_capacidad) {
		this.n_capacidad = n_capacidad;
	}

	public String getC_nomcor() {
		return c_nomcor;
	}

	public void setC_nomcor(String c_nomcor) {
		this.c_nomcor = c_nomcor;
	}
}