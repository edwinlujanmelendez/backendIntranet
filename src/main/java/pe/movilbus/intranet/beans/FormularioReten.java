package pe.movilbus.intranet.beans;

public class FormularioReten {
	
	public String fecha_partida;
	public int idConductor;
	public String nombre_conductor;
	public String tipo_conductor;
	public String unidad;
	public String placa;
	public String servicio;
	public String tipo;
	public String observaciones;
	
	public FormularioReten(){
		super();
	}
	
	public FormularioReten(String fecha_partida, int idConductor, String nombre_conductor, String tipo_conductor, String unidad, String placa,
			String servicio, String tipo, String observaciones) {
		this.fecha_partida = fecha_partida;
		this.idConductor = idConductor;
		this.nombre_conductor = nombre_conductor;
		this.tipo_conductor = tipo_conductor;
		this.unidad = unidad;
		this.placa = placa;
		this.servicio = servicio;
		this.tipo = tipo;
		this.observaciones = observaciones;
	}

	public String getFecha_partida() {
		return fecha_partida;
	}

	public void setFecha_partida(String fecha_partida) {
		this.fecha_partida = fecha_partida;
	}

	public int getIdConductor() {
		return idConductor;
	}

	public void setIdConductor(int idConductor) {
		this.idConductor = idConductor;
	}

	public String getNombre_conductor() {
		return nombre_conductor;
	}

	public void setNombre_conductor(String nombre_conductor) {
		this.nombre_conductor = nombre_conductor;
	}

	public String getTipo_conductor() {
		return tipo_conductor;
	}

	public void setTipo_conductor(String tipo_conductor) {
		this.tipo_conductor = tipo_conductor;
	}

	public String getUnidad() {
		return unidad;
	}

	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getServicio() {
		return servicio;
	}

	public void setServicio(String servicio) {
		this.servicio = servicio;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
}