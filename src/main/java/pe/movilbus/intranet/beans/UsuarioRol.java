package pe.movilbus.intranet.beans;

public class UsuarioRol implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	private int rol_id;
	private String c_denominacion;
	
	public UsuarioRol(){
		super();
	}
	
	public UsuarioRol(int rol_id, String c_denominacion){
		this.rol_id = rol_id;
		this.c_denominacion = c_denominacion;
	}

	public int getRol_id() {
		return rol_id;
	}

	public void setRol_id(int rol_id) {
		this.rol_id = rol_id;
	}

	public String getC_denominacion() {
		return c_denominacion;
	}

	public void setC_denominacion(String c_denominacion) {
		this.c_denominacion = c_denominacion;
	}
	
}