package pe.movilbus.intranet.beans;

import java.io.Serializable;

public class MantenimientoRuta implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int ruta_id;
	private String c_origen;
	private String c_destino;
	private double n_kilometros;
	private double n_horvia;
	private double precio_base;
	private double precio_economico;
	private double precio_ejecutivo;
	private double precio_presidencial;
	private double precio_premier;
	
	public MantenimientoRuta(){
		super();
	}

	public MantenimientoRuta(int ruta_id, String c_origen, String c_destino, double n_kilometros, double n_horvia,
			double precio_base, double precio_economico, double precio_ejecutivo, double precio_presidencial,
			double precio_premier) {
		this.ruta_id = ruta_id;
		this.c_origen = c_origen;
		this.c_destino = c_destino;
		this.n_kilometros = n_kilometros;
		this.n_horvia = n_horvia;
		this.precio_base = precio_base;
		this.precio_economico = precio_economico;
		this.precio_ejecutivo = precio_ejecutivo;
		this.precio_presidencial = precio_presidencial;
		this.precio_premier = precio_premier;
	}

	public int getRuta_id() {
		return ruta_id;
	}

	public void setRuta_id(int ruta_id) {
		this.ruta_id = ruta_id;
	}

	public String getC_origen() {
		return c_origen;
	}

	public void setC_origen(String c_origen) {
		this.c_origen = c_origen;
	}

	public String getC_destino() {
		return c_destino;
	}

	public void setC_destino(String c_destino) {
		this.c_destino = c_destino;
	}

	public double getN_kilometros() {
		return n_kilometros;
	}

	public void setN_kilometros(double n_kilometros) {
		this.n_kilometros = n_kilometros;
	}

	public double getN_horvia() {
		return n_horvia;
	}

	public void setN_horvia(double n_horvia) {
		this.n_horvia = n_horvia;
	}

	public double getPrecio_base() {
		return precio_base;
	}

	public void setPrecio_base(double precio_base) {
		this.precio_base = precio_base;
	}

	public double getPrecio_economico() {
		return precio_economico;
	}

	public void setPrecio_economico(double precio_economico) {
		this.precio_economico = precio_economico;
	}

	public double getPrecio_ejecutivo() {
		return precio_ejecutivo;
	}

	public void setPrecio_ejecutivo(double precio_ejecutivo) {
		this.precio_ejecutivo = precio_ejecutivo;
	}

	public double getPrecio_presidencial() {
		return precio_presidencial;
	}

	public void setPrecio_presidencial(double precio_presidencial) {
		this.precio_presidencial = precio_presidencial;
	}

	public double getPrecio_premier() {
		return precio_premier;
	}

	public void setPrecio_premier(double precio_premier) {
		this.precio_premier = precio_premier;
	}
}