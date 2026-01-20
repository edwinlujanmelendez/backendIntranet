package pe.movilbus.intranet.beans;

public class ReporteTareoConductor {
	
	private static final long serialVersionUID = 1L;
	private String empresa;
	private String fecha_partida;
	private String turno;
	private String origen;
	private String destino;
	private String servicio;
	private String nro_bus;
	private String placa_bus;
	private String terramoza;
	private String nro_programacion;
	private String conductor;
	private String auxpiloto;
	private String adicional_fijo;
	private int capacidad_bus;
	private int t_boleto;
	private Double t_monto;
	
	public ReporteTareoConductor(){
		super();
	}

	public ReporteTareoConductor(String empresa, String fecha_partida, String turno, String origen, String destino,
			String servicio, String nro_bus, String placa_bus, String terramoza, String nro_programacion,
			String conductor, String auxpiloto, String adicional_fijo, int capacidad_bus, int t_boleto,
			Double t_monto) {
		this.empresa = empresa;
		this.fecha_partida = fecha_partida;
		this.turno = turno;
		this.origen = origen;
		this.destino = destino;
		this.servicio = servicio;
		this.nro_bus = nro_bus;
		this.placa_bus = placa_bus;
		this.terramoza = terramoza;
		this.nro_programacion = nro_programacion;
		this.conductor = conductor;
		this.auxpiloto = auxpiloto;
		this.adicional_fijo = adicional_fijo;
		this.capacidad_bus = capacidad_bus;
		this.t_boleto = t_boleto;
		this.t_monto = t_monto;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getFecha_partida() {
		return fecha_partida;
	}

	public void setFecha_partida(String fecha_partida) {
		this.fecha_partida = fecha_partida;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public String getServicio() {
		return servicio;
	}

	public void setServicio(String servicio) {
		this.servicio = servicio;
	}

	public String getNro_bus() {
		return nro_bus;
	}

	public void setNro_bus(String nro_bus) {
		this.nro_bus = nro_bus;
	}

	public String getPlaca_bus() {
		return placa_bus;
	}

	public void setPlaca_bus(String placa_bus) {
		this.placa_bus = placa_bus;
	}

	public String getTerramoza() {
		return terramoza;
	}

	public void setTerramoza(String terramoza) {
		this.terramoza = terramoza;
	}

	public String getNro_programacion() {
		return nro_programacion;
	}

	public void setNro_programacion(String nro_programacion) {
		this.nro_programacion = nro_programacion;
	}

	public String getConductor() {
		return conductor;
	}

	public void setConductor(String conductor) {
		this.conductor = conductor;
	}

	public String getAuxpiloto() {
		return auxpiloto;
	}

	public void setAuxpiloto(String auxpiloto) {
		this.auxpiloto = auxpiloto;
	}

	public String getAdicional_fijo() {
		return adicional_fijo;
	}

	public void setAdicional_fijo(String adicional_fijo) {
		this.adicional_fijo = adicional_fijo;
	}

	public int getCapacidad_bus() {
		return capacidad_bus;
	}

	public void setCapacidad_bus(int capacidad_bus) {
		this.capacidad_bus = capacidad_bus;
	}

	public int getT_boleto() {
		return t_boleto;
	}

	public void setT_boleto(int t_boleto) {
		this.t_boleto = t_boleto;
	}

	public Double getT_monto() {
		return t_monto;
	}

	public void setT_monto(Double t_monto) {
		this.t_monto = t_monto;
	}
}