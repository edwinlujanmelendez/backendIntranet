package pe.movilbus.intranet.beans;

import java.math.BigDecimal;

public class ReporteSeguimientoFrotcom {
	private static final long serialVersionUID = 1L;
	private BigDecimal itinerario_id;
	private String servicio;
	private String origen;
	private String destino;
	private String embarque_1_nombre;
	private String embarque_1_hora;
	private String embarque_2_nombre;
	private String embarque_2_hora;
	private String escala_comercial_1_nombre;
	private String escala_comercial_1_hora;
	private String escala_comercial_2_nombre;
	private String escala_comercial_2_hora;
	private String escala_comercial_3_nombre;
	private String escala_comercial_3_hora;
	private String escala_comercial_4_nombre;
	private String escala_comercial_4_hora;
	private String escala_comercial_5_nombre;
	private String escala_comercial_5_hora;
	private String escala_comercial_6_nombre;
	private String escala_comercial_6_hora;
	private String escala_comercial_7_nombre;
	private String escala_comercial_7_hora;
	private String escala_comercial_8_nombre;
	private String escala_comercial_8_hora;
	private String desembarque_1_nombre;
	private String desembarque_1_hora;
	private String desembarque_2_nombre;
	private String desembarque_2_hora;
	private String desembarque_3_nombre;
	private String desembarque_3_hora;
	private String desembarque_4_nombre;
	private String desembarque_4_hora;
	private String desembarque_5_nombre;
	private String desembarque_5_hora;
	private String desembarque_6_nombre;
	private String desembarque_6_hora;
	private String desembarque_7_nombre;
	private String desembarque_7_hora;
	private String desembarque_8_nombre;
	private String desembarque_8_hora;
	
	public ReporteSeguimientoFrotcom(){
		super();
	}

	public ReporteSeguimientoFrotcom(BigDecimal itinerario_id, String servicio, String origen, String destino, String embarque_1_nombre,
			String embarque_1_hora, String embarque_2_nombre, String embarque_2_hora, String escala_comercial_1_nombre,
			String escala_comercial_1_hora, String escala_comercial_2_nombre, String escala_comercial_2_hora,
			String escala_comercial_3_nombre, String escala_comercial_3_hora, String escala_comercial_4_nombre,
			String escala_comercial_4_hora, String escala_comercial_5_nombre, String escala_comercial_5_hora,
			String escala_comercial_6_nombre, String escala_comercial_6_hora, String escala_comercial_7_nombre,
			String escala_comercial_7_hora, String escala_comercial_8_nombre, String escala_comercial_8_hora,
			String desembarque_1_nombre, String desembarque_1_hora, String desembarque_2_nombre,
			String desembarque_2_hora, String desembarque_3_nombre, String desembarque_3_hora,
			String desembarque_4_nombre, String desembarque_4_hora, String desembarque_5_nombre,
			String desembarque_5_hora, String desembarque_6_nombre, String desembarque_6_hora,
			String desembarque_7_nombre, String desembarque_7_hora, String desembarque_8_nombre,
			String desembarque_8_hora) {
		super();
		this.itinerario_id = itinerario_id;
		this.servicio = servicio;
		this.origen = origen;
		this.destino = destino;
		this.embarque_1_nombre = embarque_1_nombre;
		this.embarque_1_hora = embarque_1_hora;
		this.embarque_2_nombre = embarque_2_nombre;
		this.embarque_2_hora = embarque_2_hora;
		this.escala_comercial_1_nombre = escala_comercial_1_nombre;
		this.escala_comercial_1_hora = escala_comercial_1_hora;
		this.escala_comercial_2_nombre = escala_comercial_2_nombre;
		this.escala_comercial_2_hora = escala_comercial_2_hora;
		this.escala_comercial_3_nombre = escala_comercial_3_nombre;
		this.escala_comercial_3_hora = escala_comercial_3_hora;
		this.escala_comercial_4_nombre = escala_comercial_4_nombre;
		this.escala_comercial_4_hora = escala_comercial_4_hora;
		this.escala_comercial_5_nombre = escala_comercial_5_nombre;
		this.escala_comercial_5_hora = escala_comercial_5_hora;
		this.escala_comercial_6_nombre = escala_comercial_6_nombre;
		this.escala_comercial_6_hora = escala_comercial_6_hora;
		this.escala_comercial_7_nombre = escala_comercial_7_nombre;
		this.escala_comercial_7_hora = escala_comercial_7_hora;
		this.escala_comercial_8_nombre = escala_comercial_8_nombre;
		this.escala_comercial_8_hora = escala_comercial_8_hora;
		this.desembarque_1_nombre = desembarque_1_nombre;
		this.desembarque_1_hora = desembarque_1_hora;
		this.desembarque_2_nombre = desembarque_2_nombre;
		this.desembarque_2_hora = desembarque_2_hora;
		this.desembarque_3_nombre = desembarque_3_nombre;
		this.desembarque_3_hora = desembarque_3_hora;
		this.desembarque_4_nombre = desembarque_4_nombre;
		this.desembarque_4_hora = desembarque_4_hora;
		this.desembarque_5_nombre = desembarque_5_nombre;
		this.desembarque_5_hora = desembarque_5_hora;
		this.desembarque_6_nombre = desembarque_6_nombre;
		this.desembarque_6_hora = desembarque_6_hora;
		this.desembarque_7_nombre = desembarque_7_nombre;
		this.desembarque_7_hora = desembarque_7_hora;
		this.desembarque_8_nombre = desembarque_8_nombre;
		this.desembarque_8_hora = desembarque_8_hora;
	}

	public BigDecimal getItinerario_id() {
		return itinerario_id;
	}

	public void setItinerario_id(BigDecimal itinerario_id) {
		this.itinerario_id = itinerario_id;
	}
	
	public String getServicio() {
		return servicio;
	}

	public void setServicio(String servicio) {
		this.servicio = servicio;
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

	public String getEmbarque_1_nombre() {
		return embarque_1_nombre;
	}

	public void setEmbarque_1_nombre(String embarque_1_nombre) {
		this.embarque_1_nombre = embarque_1_nombre;
	}

	public String getEmbarque_1_hora() {
		return embarque_1_hora;
	}

	public void setEmbarque_1_hora(String embarque_1_hora) {
		this.embarque_1_hora = embarque_1_hora;
	}

	public String getEmbarque_2_nombre() {
		return embarque_2_nombre;
	}

	public void setEmbarque_2_nombre(String embarque_2_nombre) {
		this.embarque_2_nombre = embarque_2_nombre;
	}

	public String getEmbarque_2_hora() {
		return embarque_2_hora;
	}

	public void setEmbarque_2_hora(String embarque_2_hora) {
		this.embarque_2_hora = embarque_2_hora;
	}

	public String getEscala_comercial_1_nombre() {
		return escala_comercial_1_nombre;
	}

	public void setEscala_comercial_1_nombre(String escala_comercial_1_nombre) {
		this.escala_comercial_1_nombre = escala_comercial_1_nombre;
	}

	public String getEscala_comercial_1_hora() {
		return escala_comercial_1_hora;
	}

	public void setEscala_comercial_1_hora(String escala_comercial_1_hora) {
		this.escala_comercial_1_hora = escala_comercial_1_hora;
	}

	public String getEscala_comercial_2_nombre() {
		return escala_comercial_2_nombre;
	}

	public void setEscala_comercial_2_nombre(String escala_comercial_2_nombre) {
		this.escala_comercial_2_nombre = escala_comercial_2_nombre;
	}

	public String getEscala_comercial_2_hora() {
		return escala_comercial_2_hora;
	}

	public void setEscala_comercial_2_hora(String escala_comercial_2_hora) {
		this.escala_comercial_2_hora = escala_comercial_2_hora;
	}

	public String getEscala_comercial_3_nombre() {
		return escala_comercial_3_nombre;
	}

	public void setEscala_comercial_3_nombre(String escala_comercial_3_nombre) {
		this.escala_comercial_3_nombre = escala_comercial_3_nombre;
	}

	public String getEscala_comercial_3_hora() {
		return escala_comercial_3_hora;
	}

	public void setEscala_comercial_3_hora(String escala_comercial_3_hora) {
		this.escala_comercial_3_hora = escala_comercial_3_hora;
	}

	public String getEscala_comercial_4_nombre() {
		return escala_comercial_4_nombre;
	}

	public void setEscala_comercial_4_nombre(String escala_comercial_4_nombre) {
		this.escala_comercial_4_nombre = escala_comercial_4_nombre;
	}

	public String getEscala_comercial_4_hora() {
		return escala_comercial_4_hora;
	}

	public void setEscala_comercial_4_hora(String escala_comercial_4_hora) {
		this.escala_comercial_4_hora = escala_comercial_4_hora;
	}

	public String getEscala_comercial_5_nombre() {
		return escala_comercial_5_nombre;
	}

	public void setEscala_comercial_5_nombre(String escala_comercial_5_nombre) {
		this.escala_comercial_5_nombre = escala_comercial_5_nombre;
	}

	public String getEscala_comercial_5_hora() {
		return escala_comercial_5_hora;
	}

	public void setEscala_comercial_5_hora(String escala_comercial_5_hora) {
		this.escala_comercial_5_hora = escala_comercial_5_hora;
	}

	public String getEscala_comercial_6_nombre() {
		return escala_comercial_6_nombre;
	}

	public void setEscala_comercial_6_nombre(String escala_comercial_6_nombre) {
		this.escala_comercial_6_nombre = escala_comercial_6_nombre;
	}

	public String getEscala_comercial_6_hora() {
		return escala_comercial_6_hora;
	}

	public void setEscala_comercial_6_hora(String escala_comercial_6_hora) {
		this.escala_comercial_6_hora = escala_comercial_6_hora;
	}

	public String getEscala_comercial_7_nombre() {
		return escala_comercial_7_nombre;
	}

	public void setEscala_comercial_7_nombre(String escala_comercial_7_nombre) {
		this.escala_comercial_7_nombre = escala_comercial_7_nombre;
	}

	public String getEscala_comercial_7_hora() {
		return escala_comercial_7_hora;
	}

	public void setEscala_comercial_7_hora(String escala_comercial_7_hora) {
		this.escala_comercial_7_hora = escala_comercial_7_hora;
	}

	public String getEscala_comercial_8_nombre() {
		return escala_comercial_8_nombre;
	}

	public void setEscala_comercial_8_nombre(String escala_comercial_8_nombre) {
		this.escala_comercial_8_nombre = escala_comercial_8_nombre;
	}

	public String getEscala_comercial_8_hora() {
		return escala_comercial_8_hora;
	}

	public void setEscala_comercial_8_hora(String escala_comercial_8_hora) {
		this.escala_comercial_8_hora = escala_comercial_8_hora;
	}

	public String getDesembarque_1_nombre() {
		return desembarque_1_nombre;
	}

	public void setDesembarque_1_nombre(String desembarque_1_nombre) {
		this.desembarque_1_nombre = desembarque_1_nombre;
	}

	public String getDesembarque_1_hora() {
		return desembarque_1_hora;
	}

	public void setDesembarque_1_hora(String desembarque_1_hora) {
		this.desembarque_1_hora = desembarque_1_hora;
	}

	public String getDesembarque_2_nombre() {
		return desembarque_2_nombre;
	}

	public void setDesembarque_2_nombre(String desembarque_2_nombre) {
		this.desembarque_2_nombre = desembarque_2_nombre;
	}

	public String getDesembarque_2_hora() {
		return desembarque_2_hora;
	}

	public void setDesembarque_2_hora(String desembarque_2_hora) {
		this.desembarque_2_hora = desembarque_2_hora;
	}

	public String getDesembarque_3_nombre() {
		return desembarque_3_nombre;
	}

	public void setDesembarque_3_nombre(String desembarque_3_nombre) {
		this.desembarque_3_nombre = desembarque_3_nombre;
	}

	public String getDesembarque_3_hora() {
		return desembarque_3_hora;
	}

	public void setDesembarque_3_hora(String desembarque_3_hora) {
		this.desembarque_3_hora = desembarque_3_hora;
	}

	public String getDesembarque_4_nombre() {
		return desembarque_4_nombre;
	}

	public void setDesembarque_4_nombre(String desembarque_4_nombre) {
		this.desembarque_4_nombre = desembarque_4_nombre;
	}

	public String getDesembarque_4_hora() {
		return desembarque_4_hora;
	}

	public void setDesembarque_4_hora(String desembarque_4_hora) {
		this.desembarque_4_hora = desembarque_4_hora;
	}

	public String getDesembarque_5_nombre() {
		return desembarque_5_nombre;
	}

	public void setDesembarque_5_nombre(String desembarque_5_nombre) {
		this.desembarque_5_nombre = desembarque_5_nombre;
	}

	public String getDesembarque_5_hora() {
		return desembarque_5_hora;
	}

	public void setDesembarque_5_hora(String desembarque_5_hora) {
		this.desembarque_5_hora = desembarque_5_hora;
	}

	public String getDesembarque_6_nombre() {
		return desembarque_6_nombre;
	}

	public void setDesembarque_6_nombre(String desembarque_6_nombre) {
		this.desembarque_6_nombre = desembarque_6_nombre;
	}

	public String getDesembarque_6_hora() {
		return desembarque_6_hora;
	}

	public void setDesembarque_6_hora(String desembarque_6_hora) {
		this.desembarque_6_hora = desembarque_6_hora;
	}

	public String getDesembarque_7_nombre() {
		return desembarque_7_nombre;
	}

	public void setDesembarque_7_nombre(String desembarque_7_nombre) {
		this.desembarque_7_nombre = desembarque_7_nombre;
	}

	public String getDesembarque_7_hora() {
		return desembarque_7_hora;
	}

	public void setDesembarque_7_hora(String desembarque_7_hora) {
		this.desembarque_7_hora = desembarque_7_hora;
	}

	public String getDesembarque_8_nombre() {
		return desembarque_8_nombre;
	}

	public void setDesembarque_8_nombre(String desembarque_8_nombre) {
		this.desembarque_8_nombre = desembarque_8_nombre;
	}

	public String getDesembarque_8_hora() {
		return desembarque_8_hora;
	}

	public void setDesembarque_8_hora(String desembarque_8_hora) {
		this.desembarque_8_hora = desembarque_8_hora;
	}
}