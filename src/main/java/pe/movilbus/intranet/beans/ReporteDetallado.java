package pe.movilbus.intranet.beans;

public class ReporteDetallado extends GenericBean implements java.io.Serializable, Cloneable{
	
	private static final long serialVersionUID = 1L;
	private int    venpas_id;
	private String c_apepat;
	private String c_apemat;
	private String c_nombre;
	private String c_login;
	private String nombre_pasajero;
	private String origen;
	private String destino;
	private String fecha_partida;
	private String hora_partida;
	private String n_numasiento;
	private String sistema;
	private String tipo_movimiento;
	private String c_numcontrol;
	private String c_numboleto;
	private Double n_tarifa;
	private Double n_recargo;
	private Double n_descuento;
	private Double n_acuenta;
	private Double n_penalidad;
	private Double n_imppag;
	private int    forpag_id;
	private String forpag;
	private int    tipforpag_id;
	private String tipforpag;
	private int    opetarcre_id;
	private String opetarcre;
	private int    tarcre_id;
	private String tarcre;
	private String audfecins;
	private int    tipmov_id;
	private String n_ididaret;
	private String fecha_exp_reserva;
	private String hora_exp_reserva;
	private String n_numopeban;
	private String correo_enviado;
	private String fecha_hora_pago;
	private String c_observaciones;
	private String url_pago;
	private String email_pasajero;
	private String sendmailpasajeros_id;
	private String numero_cip;
	private int    delay_correo;
	
	public ReporteDetallado(){
		super();
	}

	public ReporteDetallado(int venpas_id, String c_apepat, String c_apemat, String c_nombre, String c_login,
			String nombre_pasajero, String origen, String destino, String fecha_partida, String hora_partida,
			String n_numasiento, String sistema, String tipo_movimiento, String c_numcontrol, String c_numboleto,
			Double n_tarifa, Double n_recargo, Double n_descuento, Double n_acuenta, Double n_penalidad,
			Double n_imppag, int forpag_id, String forpag, int tipforpag_id, String tipforpag, int opetarcre_id,
			String opetarcre, int tarcre_id, String tarcre, String audfecins, int tipmov_id, String n_ididaret,
			String fecha_exp_reserva, String hora_exp_reserva, String n_numopeban, String correo_enviado, 
			String fecha_hora_pago, String c_observaciones, String url_pago, String email_pasajero, String sendmailpasajeros_id, 
			String numero_cip, int delay_correo) {
		super();
		this.venpas_id = venpas_id;
		this.c_apepat = c_apepat;
		this.c_apemat = c_apemat;
		this.c_nombre = c_nombre;
		this.c_login = c_login;
		this.nombre_pasajero = nombre_pasajero;
		this.origen = origen;
		this.destino = destino;
		this.fecha_partida = fecha_partida;
		this.hora_partida = hora_partida;
		this.n_numasiento = n_numasiento;
		this.sistema = sistema;
		this.tipo_movimiento = tipo_movimiento;
		this.c_numcontrol = c_numcontrol;
		this.c_numboleto = c_numboleto;
		this.n_tarifa = n_tarifa;
		this.n_recargo = n_recargo;
		this.n_descuento = n_descuento;
		this.n_acuenta = n_acuenta;
		this.n_penalidad = n_penalidad;
		this.n_imppag = n_imppag;
		this.forpag_id = forpag_id;
		this.forpag = forpag;
		this.tipforpag_id = tipforpag_id;
		this.tipforpag = tipforpag;
		this.opetarcre_id = opetarcre_id;
		this.opetarcre = opetarcre;
		this.tarcre_id = tarcre_id;
		this.tarcre = tarcre;
		this.audfecins = audfecins;
		this.tipmov_id = tipmov_id;
		this.n_ididaret = n_ididaret;
		this.fecha_exp_reserva = fecha_exp_reserva;
		this.hora_exp_reserva = hora_exp_reserva;
		this.n_numopeban = n_numopeban;
		this.correo_enviado = correo_enviado;
		this.fecha_hora_pago = fecha_hora_pago;
		this.c_observaciones = c_observaciones;
		this.url_pago = url_pago;
		this.email_pasajero = email_pasajero;
		this.sendmailpasajeros_id = sendmailpasajeros_id;
		this.numero_cip = numero_cip;
		this.delay_correo = delay_correo;
	}

	public int getVenpas_id() {
		return venpas_id;
	}

	public void setVenpas_id(int venpas_id) {
		this.venpas_id = venpas_id;
	}

	public String getC_apepat() {
		return c_apepat;
	}

	public void setC_apepat(String c_apepat) {
		this.c_apepat = c_apepat;
	}

	public String getC_apemat() {
		return c_apemat;
	}

	public void setC_apemat(String c_apemat) {
		this.c_apemat = c_apemat;
	}

	public String getC_nombre() {
		return c_nombre;
	}

	public void setC_nombre(String c_nombre) {
		this.c_nombre = c_nombre;
	}

	public String getC_login() {
		return c_login;
	}

	public void setC_login(String c_login) {
		this.c_login = c_login;
	}

	public String getNombre_pasajero() {
		return nombre_pasajero;
	}

	public void setNombre_pasajero(String nombre_pasajero) {
		this.nombre_pasajero = nombre_pasajero;
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

	public String getFecha_partida() {
		return fecha_partida;
	}

	public void setFecha_partida(String fecha_partida) {
		this.fecha_partida = fecha_partida;
	}

	public String getHora_partida() {
		return hora_partida;
	}

	public void setHora_partida(String hora_partida) {
		this.hora_partida = hora_partida;
	}

	public String getN_numasiento() {
		return n_numasiento;
	}

	public void setN_numasiento(String n_numasiento) {
		this.n_numasiento = n_numasiento;
	}

	public String getSistema() {
		return sistema;
	}

	public void setSistema(String sistema) {
		this.sistema = sistema;
	}

	public String getTipo_movimiento() {
		return tipo_movimiento;
	}

	public void setTipo_movimiento(String tipo_movimiento) {
		this.tipo_movimiento = tipo_movimiento;
	}

	public String getC_numcontrol() {
		return c_numcontrol;
	}

	public void setC_numcontrol(String c_numcontrol) {
		this.c_numcontrol = c_numcontrol;
	}

	public String getC_numboleto() {
		return c_numboleto;
	}

	public void setC_numboleto(String c_numboleto) {
		this.c_numboleto = c_numboleto;
	}

	public Double getN_tarifa() {
		return n_tarifa;
	}

	public void setN_tarifa(Double n_tarifa) {
		this.n_tarifa = n_tarifa;
	}

	public Double getN_recargo() {
		return n_recargo;
	}

	public void setN_recargo(Double n_recargo) {
		this.n_recargo = n_recargo;
	}

	public Double getN_descuento() {
		return n_descuento;
	}

	public void setN_descuento(Double n_descuento) {
		this.n_descuento = n_descuento;
	}

	public Double getN_acuenta() {
		return n_acuenta;
	}

	public void setN_acuenta(Double n_acuenta) {
		this.n_acuenta = n_acuenta;
	}

	public Double getN_penalidad() {
		return n_penalidad;
	}

	public void setN_penalidad(Double n_penalidad) {
		this.n_penalidad = n_penalidad;
	}

	public Double getN_imppag() {
		return n_imppag;
	}

	public void setN_imppag(Double n_imppag) {
		this.n_imppag = n_imppag;
	}

	public int getForpag_id() {
		return forpag_id;
	}

	public void setForpag_id(int forpag_id) {
		this.forpag_id = forpag_id;
	}

	public String getForpag() {
		return forpag;
	}

	public void setForpag(String forpag) {
		this.forpag = forpag;
	}

	public int getTipforpag_id() {
		return tipforpag_id;
	}

	public void setTipforpag_id(int tipforpag_id) {
		this.tipforpag_id = tipforpag_id;
	}

	public String getTipforpag() {
		return tipforpag;
	}

	public void setTipforpag(String tipforpag) {
		this.tipforpag = tipforpag;
	}

	public int getOpetarcre_id() {
		return opetarcre_id;
	}

	public void setOpetarcre_id(int opetarcre_id) {
		this.opetarcre_id = opetarcre_id;
	}

	public String getOpetarcre() {
		return opetarcre;
	}

	public void setOpetarcre(String opetarcre) {
		this.opetarcre = opetarcre;
	}

	public int getTarcre_id() {
		return tarcre_id;
	}

	public void setTarcre_id(int tarcre_id) {
		this.tarcre_id = tarcre_id;
	}

	public String getTarcre() {
		return tarcre;
	}

	public void setTarcre(String tarcre) {
		this.tarcre = tarcre;
	}

	public String getAudfecins() {
		return audfecins;
	}

	public void setAudfecins(String audfecins) {
		this.audfecins = audfecins;
	}

	public int getTipmov_id() {
		return tipmov_id;
	}

	public void setTipmov_id(int tipmov_id) {
		this.tipmov_id = tipmov_id;
	}

	public String getN_ididaret() {
		return n_ididaret;
	}

	public void setN_ididaret(String n_ididaret) {
		this.n_ididaret = n_ididaret;
	}

	public String getFecha_exp_reserva() {
		return fecha_exp_reserva;
	}

	public void setFecha_exp_reserva(String fecha_exp_reserva) {
		this.fecha_exp_reserva = fecha_exp_reserva;
	}

	public String getHora_exp_reserva() {
		return hora_exp_reserva;
	}

	public void setHora_exp_reserva(String hora_exp_reserva) {
		this.hora_exp_reserva = hora_exp_reserva;
	}

	public String getN_numopeban() {
		return n_numopeban;
	}

	public void setN_numopeban(String n_numopeban) {
		this.n_numopeban = n_numopeban;
	}

	public String getCorreo_enviado() {
		return correo_enviado;
	}

	public void setCorreo_enviado(String correo_enviado) {
		this.correo_enviado = correo_enviado;
	}

	public String getFecha_hora_pago() {
		return fecha_hora_pago;
	}

	public void setFecha_hora_pago(String fecha_hora_pago) {
		this.fecha_hora_pago = fecha_hora_pago;
	}

	public String getC_observaciones() {
		return c_observaciones;
	}

	public void setC_observaciones(String c_observaciones) {
		this.c_observaciones = c_observaciones;
	}

	public String getUrl_pago() {
		return url_pago;
	}

	public void setUrl_pago(String url_pago) {
		this.url_pago = url_pago;
	}

	public String getEmail_pasajero() {
		return email_pasajero;
	}

	public void setEmail_pasajero(String email_pasajero) {
		this.email_pasajero = email_pasajero;
	}

	public String getSendmailpasajeros_id() {
		return sendmailpasajeros_id;
	}

	public void setSendmailpasajeros_id(String sendmailpasajeros_id) {
		this.sendmailpasajeros_id = sendmailpasajeros_id;
	}

	public String getNumero_cip() {
		return numero_cip;
	}

	public void setNumero_cip(String numero_cip) {
		this.numero_cip = numero_cip;
	}

	public int getDelay_correo() {
		return delay_correo;
	}

	public void setDelay_correo(int delay_correo) {
		this.delay_correo = delay_correo;
	}
}
