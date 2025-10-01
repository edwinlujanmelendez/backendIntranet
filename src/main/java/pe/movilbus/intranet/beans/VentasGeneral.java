package pe.movilbus.intranet.beans;

import java.io.Serializable;
import java.util.List;

public class VentasGeneral implements Serializable{

	private static final long serialVersionUID = 1L;
	private List<VentaPasajeros> ventaPasajeros;
	private int tiempoPasarelaPago;
	private String ipLocal;
	private Cliente cliente;
	private int idTipForPago;
	private double montoTotal;
	private String codePaisPhone;
	private int idAgencia;
	private int idUsuarioSispas;
	private String usuarioSispas;
	private int idHardwareSispas;
	private int canalVenta;
	private String fechaLiquidacion;
	private String descripcionVenta;
	private String observaciones;
	
	public VentasGeneral() {
		super();
	}
	
	public VentasGeneral(List<VentaPasajeros> ventaPasajeros, int tiempoPasarelaPago, String ipLocal, Cliente cliente,
			int idTipForPago, double montoTotal, String codePaisPhone, int idAgencia, int idUsuarioSispas, String usuarioSispas, int idHardwareSispas, 
			int canalVenta, String fechaLiquidacion, String descripcionVenta, String observaciones) {
		super();
		this.ventaPasajeros = ventaPasajeros;
		this.tiempoPasarelaPago = tiempoPasarelaPago;
		this.ipLocal = ipLocal;
		this.cliente = cliente;
		this.idTipForPago = idTipForPago;
		this.montoTotal = montoTotal;
		this.codePaisPhone = codePaisPhone;
		this.idAgencia = idAgencia;
		this.idUsuarioSispas = idUsuarioSispas;
		this.usuarioSispas = usuarioSispas;
		this.idHardwareSispas = idHardwareSispas;
		this.canalVenta = canalVenta;
		this.fechaLiquidacion = fechaLiquidacion;
		this.descripcionVenta = descripcionVenta;
		this.observaciones = observaciones;
	}
	
	public List<VentaPasajeros> getVentaPasajeros() {
		return ventaPasajeros;
	}
	
	public void setVentaPasajeros(List<VentaPasajeros> ventaPasajeros) {
		this.ventaPasajeros = ventaPasajeros;
	}
	
	public int getTiempoPasarelaPago() {
		return tiempoPasarelaPago;
	}
	
	public void setTiempoPasarelaPago(int tiempoPasarelaPago) {
		this.tiempoPasarelaPago = tiempoPasarelaPago;
	}
	
	public String getIpLocal() {
		return ipLocal;
	}
	
	public void setIpLocal(String ipLocal) {
		this.ipLocal = ipLocal;
	}
	
	public Cliente getCliente() {
		return cliente;
	}
	
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public int getIdTipForPago() {
		return idTipForPago;
	}

	public void setIdTipForPago(int idTipForPago) {
		this.idTipForPago = idTipForPago;
	}

	public double getMontoTotal() {
		return montoTotal;
	}

	public void setMontoTotal(double montoTotal) {
		this.montoTotal = montoTotal;
	}

	public String getCodePaisPhone() {
		return codePaisPhone;
	}

	public void setCodePaisPhone(String codePaisPhone) {
		this.codePaisPhone = codePaisPhone;
	}
	
	public int getIdAgencia() {
		return idAgencia;
	}

	public void setIdAgencia(int idAgencia) {
		this.idAgencia = idAgencia;
	}

	public int getIdUsuarioSispas() {
		return idUsuarioSispas;
	}

	public void setIdUsuarioSispas(int idUsuarioSispas) {
		this.idUsuarioSispas = idUsuarioSispas;
	}
	
	public String getUsuarioSispas() {
		return usuarioSispas;
	}

	public void setUsuarioSispas(String usuarioSispas) {
		this.usuarioSispas = usuarioSispas;
	}

	public int getIdHardwareSispas() {
		return idHardwareSispas;
	}

	public void setIdHardwareSispas(int idHardwareSispas) {
		this.idHardwareSispas = idHardwareSispas;
	}

	public int getCanalVenta() {
		return canalVenta;
	}

	public void setCanalVenta(int canalVenta) {
		this.canalVenta = canalVenta;
	}
	
	public String getFechaLiquidacion() {
		return fechaLiquidacion;
	}

	public void setFechaLiquidacion(String fechaLiquidacion) {
		this.fechaLiquidacion = fechaLiquidacion;
	}

	public String getDescripcionVenta() {
		return descripcionVenta;
	}

	public void setDescripcionVenta(String descripcionVenta) {
		this.descripcionVenta = descripcionVenta;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	@Override
	public String toString() {
		return "VentasGeneral [ventaPasajeros=" + ventaPasajeros + ", tiempoPasarelaPago=" + tiempoPasarelaPago
				+ ", ipLocal=" + ipLocal + ", cliente=" + cliente + ", idTipForPago=" + idTipForPago + ", montoTotal=" + montoTotal 
				+ ", codePaisPhone=" + codePaisPhone + ", idAgencia=" + idAgencia + ", idUsuarioSispas=" + idUsuarioSispas 
				+ ", idHardwareSispas=" + idHardwareSispas + ", canalVenta=" + canalVenta + ", fechaLiquidacion=" + fechaLiquidacion 
				+ ", descripcionVenta=" + descripcionVenta + ", observaciones=" + observaciones + "]";
	}
	
}