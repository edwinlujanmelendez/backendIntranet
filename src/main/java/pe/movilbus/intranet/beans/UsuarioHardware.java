package pe.movilbus.intranet.beans;

public class UsuarioHardware implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private int usuhard_id;
	private int canven_id;
	private int agencia_id;
	private String c_codigo;
	private String c_dirmac;
	private String c_descripcion;
	private int n_priapp;
	
	private CanalVenta canalVenta;
	private Agencia agencia;
	
	public UsuarioHardware(){
		super();
	}
	
	public UsuarioHardware(int usuhard_id, int canven_id, int agencia_id, String c_codigo, String c_dirmac, String c_descripcion, int n_priapp){
		this.usuhard_id = usuhard_id;
		this.canven_id = canven_id;
		this.agencia_id = agencia_id;
		this.c_codigo = c_codigo;
		this.c_dirmac = c_dirmac;
		this.c_descripcion = c_descripcion;
		this.n_priapp = n_priapp;
	}

	public int getUsuhard_id() {
		return usuhard_id;
	}

	public void setUsuhard_id(int usuhard_id) {
		this.usuhard_id = usuhard_id;
	}

	public int getCanven_id() {
		return canven_id;
	}

	public void setCanven_id(int canven_id) {
		this.canven_id = canven_id;
	}

	public int getAgencia_id() {
		return agencia_id;
	}

	public void setAgencia_id(int agencia_id) {
		this.agencia_id = agencia_id;
	}

	public String getC_codigo() {
		return c_codigo;
	}

	public void setC_codigo(String c_codigo) {
		this.c_codigo = c_codigo;
	}

	public String getC_dirmac() {
		return c_dirmac;
	}

	public void setC_dirmac(String c_dirmac) {
		this.c_dirmac = c_dirmac;
	}

	public String getC_descripcion() {
		return c_descripcion;
	}

	public void setC_descripcion(String c_descripcion) {
		this.c_descripcion = c_descripcion;
	}

	public int getN_priapp() {
		return n_priapp;
	}

	public void setN_priapp(int n_priapp) {
		this.n_priapp = n_priapp;
	}
	
	public CanalVenta getCanalVenta() {
		return canalVenta;
	}

	public void setCanalVenta(CanalVenta canalVenta) {
		this.canalVenta = canalVenta;
	}

	public Agencia getAgencia() {
		return agencia;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}
}
