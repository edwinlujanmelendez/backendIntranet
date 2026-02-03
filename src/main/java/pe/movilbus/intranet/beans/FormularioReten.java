package pe.movilbus.intranet.beans;

public class FormularioReten {
	
	public int formularioreten_id;
	public String fecha_partida;
	public int id_conductor;
	public String nombre_conductor;
	public int agencia_id;
	public String nombre_agencia;
	public String tipo_conductor;
	public String unidad;
	public String placa;
	public String servicio;
	public String tipo;
	public String observaciones;
	
	public FormularioReten(){
		super();
	}

	public FormularioReten(int formularioreten_id, String fecha_partida, int id_conductor, String nombre_conductor, 
			int agencia_id, String nombre_agencia, String tipo_conductor, String unidad, String placa, String servicio, 
			String tipo, String observaciones) {
		this.formularioreten_id = formularioreten_id;
		this.fecha_partida = fecha_partida;
		this.id_conductor = id_conductor;
		this.nombre_conductor = nombre_conductor;
		this.agencia_id = agencia_id;
		this.nombre_agencia = nombre_agencia;
		this.tipo_conductor = tipo_conductor;
		this.unidad = unidad;
		this.placa = placa;
		this.servicio = servicio;
		this.tipo = tipo;
		this.observaciones = observaciones;
	}
	
	public int getFormularioreten_id() {
		return formularioreten_id;
	}

	public void setFormularioreten_id(int formularioreten_id) {
		this.formularioreten_id = formularioreten_id;
	}

	public String getFecha_partida() {
		return fecha_partida;
	}

	public void setFecha_partida(String fecha_partida) {
		this.fecha_partida = fecha_partida;
	}

	public int getId_conductor() {
		return id_conductor;
	}

	public void setId_conductor(int id_conductor) {
		this.id_conductor = id_conductor;
	}

	public String getNombre_conductor() {
		return nombre_conductor;
	}

	public void setNombre_conductor(String nombre_conductor) {
		this.nombre_conductor = nombre_conductor;
	}

	public int getAgencia_id() {
		return agencia_id;
	}

	public void setAgencia_id(int agencia_id) {
		this.agencia_id = agencia_id;
	}

	public String getNombre_agencia() {
		return nombre_agencia;
	}

	public void setNombre_agencia(String nombre_agencia) {
		this.nombre_agencia = nombre_agencia;
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