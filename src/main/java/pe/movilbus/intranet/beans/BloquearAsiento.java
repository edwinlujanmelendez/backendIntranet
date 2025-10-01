package pe.movilbus.intranet.beans;

import java.math.BigDecimal;
import java.util.Arrays;

public class BloquearAsiento {
	private static final long serialVersionUID = 1L;
	private Integer rutaId;
	private BigDecimal  itinerarioId;
	private String  fechaPartida;
	private int[] asiento;
	private String  horaPartida;
	private int[] piso;
	private int tiempoBloqueo;
	private double tarifa;
	private int idUsuario;
	private int idHardware;

	public BloquearAsiento() {
		super();
	}

	public BloquearAsiento(Integer rutaId, BigDecimal itinerarioId, String fechaPartida, int[] asiento,
			String horaPartida, int[] piso,int tiempoBloqueo,double tarifa, int idUsuario, int idHardware){
		super();
		this.rutaId = rutaId;
		this.itinerarioId = itinerarioId;
		this.fechaPartida = fechaPartida;
		this.asiento = asiento;
		this.horaPartida = horaPartida;
		this.piso = piso;
		this.tiempoBloqueo = tiempoBloqueo;
		this.tarifa = tarifa;
		this.idUsuario = idUsuario;
		this.idHardware = idHardware;
	}

	public Integer getRutaId() {
		return rutaId;
	}

	public void setRutaId(Integer rutaId) {
		this.rutaId = rutaId;
	}

	public BigDecimal getItinerarioId() {
		return itinerarioId;
	}

	public void setItinerarioId(BigDecimal itinerarioId) {
		this.itinerarioId = itinerarioId;
	}

	public String getFechaPartida() {
		return fechaPartida;
	}

	public void setFechaPartida(String fechaPartida) {
		this.fechaPartida = fechaPartida;
	}

	public int[] getAsiento() {
		return asiento;
	}

	public void setAsiento(int[] asiento) {
		this.asiento = asiento;
	}

	public String getHoraPartida() {
		return horaPartida;
	}

	public void setHoraPartida(String horaPartida) {
		this.horaPartida = horaPartida;
	}

	public int[] getPiso() {
		return piso;
	}

	public void setPiso(int[] piso) {
		this.piso = piso;
	}

	public int getTiempoBloqueo() {
		return tiempoBloqueo;
	}

	public void setTiempoBloqueo(int tiempoBloqueo) {
		this.tiempoBloqueo = tiempoBloqueo;
	}

	public double getTarifa() {
		return tarifa;
	}

	public void setTarifa(double tarifa) {
		this.tarifa = tarifa;
	}
	
	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public int getIdHardware() {
		return idHardware;
	}

	public void setIdHardware(int idHardware) {
		this.idHardware = idHardware;
	}

	@Override
	public String toString() {
		return "BloquearAsiento [rutaId=" + rutaId + ", itinerarioId=" + itinerarioId + ", fechaPartida=" + fechaPartida
				+ ", asiento=" + Arrays.toString(asiento) + ", horaPartida=" + horaPartida + ", piso="
				+ Arrays.toString(piso) + ", tiempoBloqueo=" + tiempoBloqueo + ", tarifa=" + tarifa + ", idUsuario=" + idUsuario 
				+ ", idHardware=" + idHardware + "]";
	}

}
