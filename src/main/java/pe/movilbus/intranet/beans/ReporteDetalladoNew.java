package pe.movilbus.intranet.beans;

public class ReporteDetalladoNew extends GenericBean implements java.io.Serializable, Cloneable{
	
	private static final long serialVersionUID = 1L;
	private int    venpas_id;
	private String nrocontrol;
	private String nroBoleto;
	private String nroBoletoRef;
	private String apepat;
	private String apemat;
	private String nombre;
	private String fechaActualizacion;
	private Double montoBase;
	private Double recargo;
	private Double descuento;
	private Double acuenta;
	private Double penalidad;
	private Double mixto_efectivo;
	private Double mixto_tarjeta;
	private Double netoPagado;
	private int    forpag_id;
	private String formapago;
	private String tipoFormaPago;
	private String tipoTarjeta;
	private String tipoMovimiento;
	private String tipoVenta;
	private String apepatUsu;
	private String nombreUsu;
	private String c_login;
	private String d_fecliq;
	private String agencia;
	private int    tipmov_id;
	private String liquidacion_id;
	private int    tipforpag_id;
	private int    tipcom_id;
	private int    empresa_id;
	private String nroos;
	private String c_stateos;
	private int    diferentar;
	private String observacion;
	private int    ruta_id;
	private String c_origen;
	private String c_destino;
	private String fecha_expiracion;
	private String hora_expiracion;
	private String nro_operation_niubiz;
	private String nro_cip_pagoefectivo;
	private String n_numopeban;
	private String email_pasajero1;
	private int    correoEnviado1;
	private int    correoEnviado2;
	private String fechaInsercionLog;
	private String url_pago;
	private String email_pasajero2;
	private int    delay_correo;
	
	public ReporteDetalladoNew(int venpas_id, String nrocontrol, String nroBoleto, String nroBoletoRef, String apepat,
			String apemat, String nombre, String fechaActualizacion, Double montoBase, Double recargo, Double descuento,
			Double acuenta, Double penalidad, Double mixto_efectivo, Double mixto_tarjeta, Double netoPagado,
			int forpag_id, String formapago, String tipoFormaPago, String tipoTarjeta, String tipoMovimiento,
			String tipoVenta, String apepatUsu, String nombreUsu, String c_login, String d_fecliq, String agencia,
			int tipmov_id, String liquidacion_id, int tipforpag_id, int tipcom_id, int empresa_id, String nroos,
			String c_stateos, int diferentar, String observacion, int ruta_id, String c_origen, String c_destino,
			String fecha_expiracion, String hora_expiracion, String nro_operation_niubiz, String nro_cip_pagoefectivo,
			String n_numopeban, String email_pasajero1, int correoEnviado1, int correoEnviado2,
			String fechaInsercionLog, String url_pago, String email_pasajero2, int delay_correo) {
		super();
		this.venpas_id = venpas_id;
		this.nrocontrol = nrocontrol;
		this.nroBoleto = nroBoleto;
		this.nroBoletoRef = nroBoletoRef;
		this.apepat = apepat;
		this.apemat = apemat;
		this.nombre = nombre;
		this.fechaActualizacion = fechaActualizacion;
		this.montoBase = montoBase;
		this.recargo = recargo;
		this.descuento = descuento;
		this.acuenta = acuenta;
		this.penalidad = penalidad;
		this.mixto_efectivo = mixto_efectivo;
		this.mixto_tarjeta = mixto_tarjeta;
		this.netoPagado = netoPagado;
		this.forpag_id = forpag_id;
		this.formapago = formapago;
		this.tipoFormaPago = tipoFormaPago;
		this.tipoTarjeta = tipoTarjeta;
		this.tipoMovimiento = tipoMovimiento;
		this.tipoVenta = tipoVenta;
		this.apepatUsu = apepatUsu;
		this.nombreUsu = nombreUsu;
		this.c_login = c_login;
		this.d_fecliq = d_fecliq;
		this.agencia = agencia;
		this.tipmov_id = tipmov_id;
		this.liquidacion_id = liquidacion_id;
		this.tipforpag_id = tipforpag_id;
		this.tipcom_id = tipcom_id;
		this.empresa_id = empresa_id;
		this.nroos = nroos;
		this.c_stateos = c_stateos;
		this.diferentar = diferentar;
		this.observacion = observacion;
		this.ruta_id = ruta_id;
		this.c_origen = c_origen;
		this.c_destino = c_destino;
		this.fecha_expiracion = fecha_expiracion;
		this.hora_expiracion = hora_expiracion;
		this.nro_operation_niubiz = nro_operation_niubiz;
		this.nro_cip_pagoefectivo = nro_cip_pagoefectivo;
		this.n_numopeban = n_numopeban;
		this.email_pasajero1 = email_pasajero1;
		this.correoEnviado1 = correoEnviado1;
		this.correoEnviado2 = correoEnviado2;
		this.fechaInsercionLog = fechaInsercionLog;
		this.url_pago = url_pago;
		this.email_pasajero2 = email_pasajero2;
		this.delay_correo = delay_correo;
	}

	public int getVenpas_id() {
		return venpas_id;
	}

	public void setVenpas_id(int venpas_id) {
		this.venpas_id = venpas_id;
	}

	public String getNrocontrol() {
		return nrocontrol;
	}

	public void setNrocontrol(String nrocontrol) {
		this.nrocontrol = nrocontrol;
	}

	public String getNroBoleto() {
		return nroBoleto;
	}

	public void setNroBoleto(String nroBoleto) {
		this.nroBoleto = nroBoleto;
	}

	public String getNroBoletoRef() {
		return nroBoletoRef;
	}

	public void setNroBoletoRef(String nroBoletoRef) {
		this.nroBoletoRef = nroBoletoRef;
	}

	public String getApepat() {
		return apepat;
	}

	public void setApepat(String apepat) {
		this.apepat = apepat;
	}

	public String getApemat() {
		return apemat;
	}

	public void setApemat(String apemat) {
		this.apemat = apemat;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(String fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public Double getMontoBase() {
		return montoBase;
	}

	public void setMontoBase(Double montoBase) {
		this.montoBase = montoBase;
	}

	public Double getRecargo() {
		return recargo;
	}

	public void setRecargo(Double recargo) {
		this.recargo = recargo;
	}

	public Double getDescuento() {
		return descuento;
	}

	public void setDescuento(Double descuento) {
		this.descuento = descuento;
	}

	public Double getAcuenta() {
		return acuenta;
	}

	public void setAcuenta(Double acuenta) {
		this.acuenta = acuenta;
	}

	public Double getPenalidad() {
		return penalidad;
	}

	public void setPenalidad(Double penalidad) {
		this.penalidad = penalidad;
	}

	public Double getMixto_efectivo() {
		return mixto_efectivo;
	}

	public void setMixto_efectivo(Double mixto_efectivo) {
		this.mixto_efectivo = mixto_efectivo;
	}

	public Double getMixto_tarjeta() {
		return mixto_tarjeta;
	}

	public void setMixto_tarjeta(Double mixto_tarjeta) {
		this.mixto_tarjeta = mixto_tarjeta;
	}

	public Double getNetoPagado() {
		return netoPagado;
	}

	public void setNetoPagado(Double netoPagado) {
		this.netoPagado = netoPagado;
	}

	public int getForpag_id() {
		return forpag_id;
	}

	public void setForpag_id(int forpag_id) {
		this.forpag_id = forpag_id;
	}

	public String getFormapago() {
		return formapago;
	}

	public void setFormapago(String formapago) {
		this.formapago = formapago;
	}

	public String getTipoFormaPago() {
		return tipoFormaPago;
	}

	public void setTipoFormaPago(String tipoFormaPago) {
		this.tipoFormaPago = tipoFormaPago;
	}

	public String getTipoTarjeta() {
		return tipoTarjeta;
	}

	public void setTipoTarjeta(String tipoTarjeta) {
		this.tipoTarjeta = tipoTarjeta;
	}

	public String getTipoMovimiento() {
		return tipoMovimiento;
	}

	public void setTipoMovimiento(String tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}

	public String getTipoVenta() {
		return tipoVenta;
	}

	public void setTipoVenta(String tipoVenta) {
		this.tipoVenta = tipoVenta;
	}

	public String getApepatUsu() {
		return apepatUsu;
	}

	public void setApepatUsu(String apepatUsu) {
		this.apepatUsu = apepatUsu;
	}

	public String getNombreUsu() {
		return nombreUsu;
	}

	public void setNombreUsu(String nombreUsu) {
		this.nombreUsu = nombreUsu;
	}

	public String getC_login() {
		return c_login;
	}

	public void setC_login(String c_login) {
		this.c_login = c_login;
	}

	public String getD_fecliq() {
		return d_fecliq;
	}

	public void setD_fecliq(String d_fecliq) {
		this.d_fecliq = d_fecliq;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public int getTipmov_id() {
		return tipmov_id;
	}

	public void setTipmov_id(int tipmov_id) {
		this.tipmov_id = tipmov_id;
	}

	public String getLiquidacion_id() {
		return liquidacion_id;
	}

	public void setLiquidacion_id(String liquidacion_id) {
		this.liquidacion_id = liquidacion_id;
	}

	public int getTipforpag_id() {
		return tipforpag_id;
	}

	public void setTipforpag_id(int tipforpag_id) {
		this.tipforpag_id = tipforpag_id;
	}

	public int getTipcom_id() {
		return tipcom_id;
	}

	public void setTipcom_id(int tipcom_id) {
		this.tipcom_id = tipcom_id;
	}

	public int getEmpresa_id() {
		return empresa_id;
	}

	public void setEmpresa_id(int empresa_id) {
		this.empresa_id = empresa_id;
	}

	public String getNroos() {
		return nroos;
	}

	public void setNroos(String nroos) {
		this.nroos = nroos;
	}

	public String getC_stateos() {
		return c_stateos;
	}

	public void setC_stateos(String c_stateos) {
		this.c_stateos = c_stateos;
	}

	public int getDiferentar() {
		return diferentar;
	}

	public void setDiferentar(int diferentar) {
		this.diferentar = diferentar;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
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

	public String getFecha_expiracion() {
		return fecha_expiracion;
	}

	public void setFecha_expiracion(String fecha_expiracion) {
		this.fecha_expiracion = fecha_expiracion;
	}

	public String getHora_expiracion() {
		return hora_expiracion;
	}

	public void setHora_expiracion(String hora_expiracion) {
		this.hora_expiracion = hora_expiracion;
	}

	public String getNro_operation_niubiz() {
		return nro_operation_niubiz;
	}

	public void setNro_operation_niubiz(String nro_operation_niubiz) {
		this.nro_operation_niubiz = nro_operation_niubiz;
	}

	public String getNro_cip_pagoefectivo() {
		return nro_cip_pagoefectivo;
	}

	public void setNro_cip_pagoefectivo(String nro_cip_pagoefectivo) {
		this.nro_cip_pagoefectivo = nro_cip_pagoefectivo;
	}

	public String getN_numopeban() {
		return n_numopeban;
	}

	public void setN_numopeban(String n_numopeban) {
		this.n_numopeban = n_numopeban;
	}

	public String getEmail_pasajero1() {
		return email_pasajero1;
	}

	public void setEmail_pasajero1(String email_pasajero1) {
		this.email_pasajero1 = email_pasajero1;
	}

	public int getCorreoEnviado1() {
		return correoEnviado1;
	}

	public void setCorreoEnviado1(int correoEnviado1) {
		this.correoEnviado1 = correoEnviado1;
	}

	public int getCorreoEnviado2() {
		return correoEnviado2;
	}

	public void setCorreoEnviado2(int correoEnviado2) {
		this.correoEnviado2 = correoEnviado2;
	}

	public String getFechaInsercionLog() {
		return fechaInsercionLog;
	}

	public void setFechaInsercionLog(String fechaInsercionLog) {
		this.fechaInsercionLog = fechaInsercionLog;
	}

	public String getUrl_pago() {
		return url_pago;
	}

	public void setUrl_pago(String url_pago) {
		this.url_pago = url_pago;
	}

	public String getEmail_pasajero2() {
		return email_pasajero2;
	}

	public void setEmail_pasajero2(String email_pasajero2) {
		this.email_pasajero2 = email_pasajero2;
	}

	public int getDelay_correo() {
		return delay_correo;
	}

	public void setDelay_correo(int delay_correo) {
		this.delay_correo = delay_correo;
	}
}