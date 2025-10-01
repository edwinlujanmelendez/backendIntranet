package pe.movilbus.intranet.beans;

import java.io.Serializable;

public class Ubigeo implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
    private int codigoDepartamento;
    private int codigoProvincia;
    private int codigoDistrito;
    private String nombreUbigeo;
     
    public Ubigeo() {
    	super();
    }

	public Ubigeo(int id, int codigoDepartamento, int codigoProvincia, int codigoDistrito, String nombreUbigeo) {
		super();
		this.id = id;
		this.codigoDepartamento = codigoDepartamento;
		this.codigoProvincia = codigoProvincia;
		this.codigoDistrito = codigoDistrito;
		this.nombreUbigeo = nombreUbigeo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCodigoDepartamento() {
		return codigoDepartamento;
	}

	public void setCodigoDepartamento(int codigoDepartamento) {
		this.codigoDepartamento = codigoDepartamento;
	}

	public int getCodigoProvincia() {
		return codigoProvincia;
	}

	public void setCodigoProvincia(int codigoProvincia) {
		this.codigoProvincia = codigoProvincia;
	}

	public int getCodigoDistrito() {
		return codigoDistrito;
	}

	public void setCodigoDistrito(int codigoDistrito) {
		this.codigoDistrito = codigoDistrito;
	}

	public String getNombreUbigeo() {
		return nombreUbigeo;
	}

	public void setNombreUbigeo(String nombreUbigeo) {
		this.nombreUbigeo = nombreUbigeo;
	}
}