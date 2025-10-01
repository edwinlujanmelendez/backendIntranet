package pe.movilbus.intranet.beans;

import java.util.Date;

public class Concesionario implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String ruc;
	private String razonSocial;
	private String direccion;
	private Date fechaActivacion;
	private Date fechaCaducidad;
	private Date fechaSuspension;
	private int comision;
	private int tipoComision;
	private int incluyeIgv;
	
	public Concesionario() {
		super();
	}

	public Concesionario(int id, String ruc, String razonSocial, String direccion, Date fechaActivacion,
			Date fechaCaducidad, Date fechaSuspension, int comision, int tipoComision, int incluyeIgv) {
		super();
		this.id = id;
		this.ruc = ruc;
		this.razonSocial = razonSocial;
		this.direccion = direccion;
		this.fechaActivacion = fechaActivacion;
		this.fechaCaducidad = fechaCaducidad;
		this.fechaSuspension = fechaSuspension;
		this.comision = comision;
		this.tipoComision = tipoComision;
		this.incluyeIgv = incluyeIgv;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Date getFechaActivacion() {
		return fechaActivacion;
	}

	public void setFechaActivacion(Date fechaActivacion) {
		this.fechaActivacion = fechaActivacion;
	}

	public Date getFechaCaducidad() {
		return fechaCaducidad;
	}

	public void setFechaCaducidad(Date fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}

	public Date getFechaSuspension() {
		return fechaSuspension;
	}

	public void setFechaSuspension(Date fechaSuspension) {
		this.fechaSuspension = fechaSuspension;
	}

	public int getComision() {
		return comision;
	}

	public void setComision(int comision) {
		this.comision = comision;
	}

	public int getTipoComision() {
		return tipoComision;
	}

	public void setTipoComision(int tipoComision) {
		this.tipoComision = tipoComision;
	}

	public int getIncluyeIgv() {
		return incluyeIgv;
	}

	public void setIncluyeIgv(int incluyeIgv) {
		this.incluyeIgv = incluyeIgv;
	}
}