package pe.movilbus.intranet.beans;

import java.math.BigDecimal;

public class DataTransbordos implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	private BigDecimal venpas_id;
	private String tipo_de_pago;
	private String pasajero;
	private String nro_boleto;
	private String ruta;
	private Double importe_total;
	private Double tarifa_asiento;
	private int itinerario_idorigen;
	private String servicio_de_origen;
	private String fecha_origen;
	private String hora_partida;
	private int asiento_origen;
	private int itinerario_destino;
	private String servicio_destino;
	private String fecha_par_despues;
	private String hora_despues;
	private int asiento_despues;
	private String fecha_modificada;
	private String usuario_modifica;
	private String agencia_venta;
	
	public DataTransbordos(){
		super();
	}

	public DataTransbordos(BigDecimal venpas_id, String tipo_de_pago, String pasajero, String nro_boleto, String ruta,
			Double importe_total, Double tarifa_asiento, int itinerario_idorigen, String servicio_de_origen,
			String fecha_origen, String hora_partida, int asiento_origen, int itinerario_destino,
			String servicio_destino, String fecha_par_despues, String hora_despues, int asiento_despues,
			String fecha_modificada, String usuario_modifica, String agencia_venta) {
		super();
		this.venpas_id = venpas_id;
		this.tipo_de_pago = tipo_de_pago;
		this.pasajero = pasajero;
		this.nro_boleto = nro_boleto;
		this.ruta = ruta;
		this.importe_total = importe_total;
		this.tarifa_asiento = tarifa_asiento;
		this.itinerario_idorigen = itinerario_idorigen;
		this.servicio_de_origen = servicio_de_origen;
		this.fecha_origen = fecha_origen;
		this.hora_partida = hora_partida;
		this.asiento_origen = asiento_origen;
		this.itinerario_destino = itinerario_destino;
		this.servicio_destino = servicio_destino;
		this.fecha_par_despues = fecha_par_despues;
		this.hora_despues = hora_despues;
		this.asiento_despues = asiento_despues;
		this.fecha_modificada = fecha_modificada;
		this.usuario_modifica = usuario_modifica;
		this.agencia_venta = agencia_venta;
	}

	public BigDecimal getVenpas_id() {
		return venpas_id;
	}

	public void setVenpas_id(BigDecimal venpas_id) {
		this.venpas_id = venpas_id;
	}

	public String getTipo_de_pago() {
		return tipo_de_pago;
	}

	public void setTipo_de_pago(String tipo_de_pago) {
		this.tipo_de_pago = tipo_de_pago;
	}

	public String getPasajero() {
		return pasajero;
	}

	public void setPasajero(String pasajero) {
		this.pasajero = pasajero;
	}

	public String getNro_boleto() {
		return nro_boleto;
	}

	public void setNro_boleto(String nro_boleto) {
		this.nro_boleto = nro_boleto;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public Double getImporte_total() {
		return importe_total;
	}

	public void setImporte_total(Double importe_total) {
		this.importe_total = importe_total;
	}

	public Double getTarifa_asiento() {
		return tarifa_asiento;
	}

	public void setTarifa_asiento(Double tarifa_asiento) {
		this.tarifa_asiento = tarifa_asiento;
	}

	public int getItinerario_idorigen() {
		return itinerario_idorigen;
	}

	public void setItinerario_idorigen(int itinerario_idorigen) {
		this.itinerario_idorigen = itinerario_idorigen;
	}

	public String getServicio_de_origen() {
		return servicio_de_origen;
	}

	public void setServicio_de_origen(String servicio_de_origen) {
		this.servicio_de_origen = servicio_de_origen;
	}

	public String getFecha_origen() {
		return fecha_origen;
	}

	public void setFecha_origen(String fecha_origen) {
		this.fecha_origen = fecha_origen;
	}

	public String getHora_partida() {
		return hora_partida;
	}

	public void setHora_partida(String hora_partida) {
		this.hora_partida = hora_partida;
	}

	public int getAsiento_origen() {
		return asiento_origen;
	}

	public void setAsiento_origen(int asiento_origen) {
		this.asiento_origen = asiento_origen;
	}

	public int getItinerario_destino() {
		return itinerario_destino;
	}

	public void setItinerario_destino(int itinerario_destino) {
		this.itinerario_destino = itinerario_destino;
	}

	public String getServicio_destino() {
		return servicio_destino;
	}

	public void setServicio_destino(String servicio_destino) {
		this.servicio_destino = servicio_destino;
	}

	public String getFecha_par_despues() {
		return fecha_par_despues;
	}

	public void setFecha_par_despues(String fecha_par_despues) {
		this.fecha_par_despues = fecha_par_despues;
	}

	public String getHora_despues() {
		return hora_despues;
	}

	public void setHora_despues(String hora_despues) {
		this.hora_despues = hora_despues;
	}

	public int getAsiento_despues() {
		return asiento_despues;
	}

	public void setAsiento_despues(int asiento_despues) {
		this.asiento_despues = asiento_despues;
	}

	public String getFecha_modificada() {
		return fecha_modificada;
	}

	public void setFecha_modificada(String fecha_modificada) {
		this.fecha_modificada = fecha_modificada;
	}

	public String getUsuario_modifica() {
		return usuario_modifica;
	}

	public void setUsuario_modifica(String usuario_modifica) {
		this.usuario_modifica = usuario_modifica;
	}

	public String getAgencia_venta() {
		return agencia_venta;
	}

	public void setAgencia_venta(String agencia_venta) {
		this.agencia_venta = agencia_venta;
	}
}