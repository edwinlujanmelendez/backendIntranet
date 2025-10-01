package pe.movilbus.intranet.beans;

import java.io.Serializable;
import java.math.BigDecimal;

public class UpdateVrtVenpas implements Serializable, Cloneable {
	
	private static final long serialVersionUID = 1L;
	private BigDecimal venpas_id;
	private String cliente_id;
	private int agencia_id;
	private int c_tiptra;
	private String c_estdoc;
	private String c_email_contacto;
	private int parentesco_id;
	private int n_tipo_pasajero;
	
	public UpdateVrtVenpas(){
		super();
	}
	
	public UpdateVrtVenpas(BigDecimal venpas_id, String cliente_id, int agencia_id, int c_tiptra, String c_estdoc, String c_email_contacto, int parentesco_id, int n_tipo_pasajero) {
		super();
		this.venpas_id = venpas_id;
		this.cliente_id = cliente_id;
		this.agencia_id = agencia_id;
		this.c_tiptra = c_tiptra;
		this.c_estdoc = c_estdoc;
		this.c_email_contacto = c_email_contacto;
		this.parentesco_id = parentesco_id;
		this.n_tipo_pasajero = n_tipo_pasajero;
	}

	public BigDecimal getVenpas_id() {
		return venpas_id;
	}

	public void setVenpas_id(BigDecimal venpas_id) {
		this.venpas_id = venpas_id;
	}
	
	public String getCliente_id() {
		return cliente_id;
	}

	public void setCliente_id(String cliente_id) {
		this.cliente_id = cliente_id;
	}

	public int getAgencia_id() {
		return agencia_id;
	}

	public void setAgencia_id(int agencia_id) {
		this.agencia_id = agencia_id;
	}

	public int getC_tiptra() {
		return c_tiptra;
	}

	public void setC_tiptra(int c_tiptra) {
		this.c_tiptra = c_tiptra;
	}

	public String getC_estdoc() {
		return c_estdoc;
	}

	public void setC_estdoc(String c_estdoc) {
		this.c_estdoc = c_estdoc;
	}
	
	public String getC_email_contacto() {
		return c_email_contacto;
	}

	public void setC_email_contacto(String c_email_contacto) {
		this.c_email_contacto = c_email_contacto;
	}

	public int getParentesco_id() {
		return parentesco_id;
	}

	public void setParentesco_id(int parentesco_id) {
		this.parentesco_id = parentesco_id;
	}

	public int getN_tipo_pasajero() {
		return n_tipo_pasajero;
	}

	public void setN_tipo_pasajero(int n_tipo_pasajero) {
		this.n_tipo_pasajero = n_tipo_pasajero;
	}

}
