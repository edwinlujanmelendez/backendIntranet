package pe.movilbus.intranet.beans;

import java.io.Serializable;
import java.math.BigDecimal;

public class VentasGeneralPasajeros implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private BigDecimal venpas_id;
	private String venpas_idref;
	private String venpas_idoriginal;
	private String itinerario_id;
	private String ruta_id;
	private String c_origen;
	private String c_destino;
	private String detalle_ruta;
	private String cliente_id;
	private String ruc_cliente;
	private String razon_social;
	private String direccion_cliente;
	private String pasajero_id;
	private String documento_pasajero;
	private String nombres_apellidos;
	private String email_pasajero;
	private String telefono_pasajero;
	private String forpag_id;
	private String detalle_forpag;
	private String servicio_id;
	private String detalle_servicio;
	private String tipcom_id;
	private String detalle_tipcom;
	private String tipmov_id;
	private String detalle_tipmov;
	private String tipforpag_id;
	private String detalle_tipforpag;
	private String tarcre_id;
	private String detalle_tarcre;
	private String c_numboleto;
	private String c_numbolant;
	private int    n_numasiento;
	private String c_numcontrol;
	private String id_agencia_partida;
	private String direccion_agencia_partida;
	private String fecha_partida;
	private String hora_partida;
	private String id_agencia_llegada;
	private String direccion_agencia_llegada;
	private String fecha_llegada;
	private String hora_llegada;
	private int    n_secuencial;
	private Double n_imppag;
	private String tipo_tramite;
	private String agencia_id;
	private String nombre_agencia;
	private String usuario_id;
	private String c_login;
	private String canven_id;
	private String detalle_canven;
	private String n_numopeban;
	private String d_fecexpres;
	private String c_horexpres;
	private int    n_idaret;
	private String detalle_idaret;
	private String c_rucclicre;
	private int    n_esfecabi;
	private String detalle_esfecabi;
	private String c_observaciones;
	private String promocion_id;
	private String nombre_promocion1;
	private String descuento_promocion1;
	private String n_ididaret;
	private String audfecins;
	private String audusuins;
	private String audfecmod;
	private String audusumod;
	private String c_estdoc;
	private String c_codref;
	private String d_fecvent;
	private String result_ws_id;
	private String detalle_result_ws_id;
	private String n_esfe;
	private String d_esfe;
	private String n_estado_fe;
	private String c_email_contacto;
	private String c_telefono_opcional;
	private String n_info;
	private int    correo_encuesta_enviado;
	private String nro_operation_niubiz;
	private String nro_cip_pagoefectivo;
	private String nombre_promocion2;
	private String descuento_promocion2;
	private int    correo_enviado1;
	private int    correo_enviado2;
	private String venpas_idtx;
	private String parentesco_id;
	private String venpas_idparents;
	private String n_tipo_pasajero;
	private String fecha_hora_canje;
	private String fecha_hora_niubiz1;
	private String fecha_hora_niubiz2;
	private String fecha_hora_correo;
	private String numboleto_numbolant;
	private String anulado_por;
	private int    estado_order;
	private int    pdf_para_descargar;
	
	public VentasGeneralPasajeros(){
		super();
	}

	public VentasGeneralPasajeros(BigDecimal venpas_id, String venpas_idref, String venpas_idoriginal,
			String itinerario_id, String ruta_id, String c_origen, String c_destino, String detalle_ruta,
			String cliente_id, String ruc_cliente, String razon_social, String direccion_cliente, String pasajero_id,
			String documento_pasajero, String nombres_apellidos, String email_pasajero, String telefono_pasajero,
			String forpag_id, String detalle_forpag, String servicio_id, String detalle_servicio, String tipcom_id,
			String detalle_tipcom, String tipmov_id, String detalle_tipmov, String tipforpag_id,
			String detalle_tipforpag, String tarcre_id, String detalle_tarcre, String c_numboleto, String c_numbolant,
			int n_numasiento, String c_numcontrol, String id_agencia_partida, String direccion_agencia_partida,
			String fecha_partida, String hora_partida, String id_agencia_llegada, String direccion_agencia_llegada,
			String fecha_llegada, String hora_llegada, int n_secuencial, Double n_imppag, String tipo_tramite,
			String agencia_id, String nombre_agencia, String usuario_id, String c_login, String canven_id,
			String detalle_canven, String n_numopeban, String d_fecexpres, String c_horexpres, int n_idaret,
			String detalle_idaret, String c_rucclicre, int n_esfecabi, String detalle_esfecabi, String c_observaciones,
			String promocion_id, String nombre_promocion1, String descuento_promocion1, String n_ididaret,
			String audfecins, String audusuins, String audfecmod, String audusumod, String c_estdoc, String c_codref,
			String d_fecvent, String result_ws_id, String detalle_result_ws_id, String n_esfe, String d_esfe,
			String n_estado_fe, String c_email_contacto, String c_telefono_opcional, String n_info,
			int correo_encuesta_enviado, String nro_operation_niubiz, String nro_cip_pagoefectivo,
			String nombre_promocion2, String descuento_promocion2, int correo_enviado1, int correo_enviado2,
			String venpas_idtx, String parentesco_id, String venpas_idparents, String n_tipo_pasajero,
			String fecha_hora_canje, String fecha_hora_niubiz1, String fecha_hora_niubiz2, String fecha_hora_correo,
			String numboleto_numbolant, String anulado_por, int estado_order, int pdf_para_descargar) {
		super();
		this.venpas_id = venpas_id;
		this.venpas_idref = venpas_idref;
		this.venpas_idoriginal = venpas_idoriginal;
		this.itinerario_id = itinerario_id;
		this.ruta_id = ruta_id;
		this.c_origen = c_origen;
		this.c_destino = c_destino;
		this.detalle_ruta = detalle_ruta;
		this.cliente_id = cliente_id;
		this.ruc_cliente = ruc_cliente;
		this.razon_social = razon_social;
		this.direccion_cliente = direccion_cliente;
		this.pasajero_id = pasajero_id;
		this.documento_pasajero = documento_pasajero;
		this.nombres_apellidos = nombres_apellidos;
		this.email_pasajero = email_pasajero;
		this.telefono_pasajero = telefono_pasajero;
		this.forpag_id = forpag_id;
		this.detalle_forpag = detalle_forpag;
		this.servicio_id = servicio_id;
		this.detalle_servicio = detalle_servicio;
		this.tipcom_id = tipcom_id;
		this.detalle_tipcom = detalle_tipcom;
		this.tipmov_id = tipmov_id;
		this.detalle_tipmov = detalle_tipmov;
		this.tipforpag_id = tipforpag_id;
		this.detalle_tipforpag = detalle_tipforpag;
		this.tarcre_id = tarcre_id;
		this.detalle_tarcre = detalle_tarcre;
		this.c_numboleto = c_numboleto;
		this.c_numbolant = c_numbolant;
		this.n_numasiento = n_numasiento;
		this.c_numcontrol = c_numcontrol;
		this.id_agencia_partida = id_agencia_partida;
		this.direccion_agencia_partida = direccion_agencia_partida;
		this.fecha_partida = fecha_partida;
		this.hora_partida = hora_partida;
		this.id_agencia_llegada = id_agencia_llegada;
		this.direccion_agencia_llegada = direccion_agencia_llegada;
		this.fecha_llegada = fecha_llegada;
		this.hora_llegada = hora_llegada;
		this.n_secuencial = n_secuencial;
		this.n_imppag = n_imppag;
		this.tipo_tramite = tipo_tramite;
		this.agencia_id = agencia_id;
		this.nombre_agencia = nombre_agencia;
		this.usuario_id = usuario_id;
		this.c_login = c_login;
		this.canven_id = canven_id;
		this.detalle_canven = detalle_canven;
		this.n_numopeban = n_numopeban;
		this.d_fecexpres = d_fecexpres;
		this.c_horexpres = c_horexpres;
		this.n_idaret = n_idaret;
		this.detalle_idaret = detalle_idaret;
		this.c_rucclicre = c_rucclicre;
		this.n_esfecabi = n_esfecabi;
		this.detalle_esfecabi = detalle_esfecabi;
		this.c_observaciones = c_observaciones;
		this.promocion_id = promocion_id;
		this.nombre_promocion1 = nombre_promocion1;
		this.descuento_promocion1 = descuento_promocion1;
		this.n_ididaret = n_ididaret;
		this.audfecins = audfecins;
		this.audusuins = audusuins;
		this.audfecmod = audfecmod;
		this.audusumod = audusumod;
		this.c_estdoc = c_estdoc;
		this.c_codref = c_codref;
		this.d_fecvent = d_fecvent;
		this.result_ws_id = result_ws_id;
		this.detalle_result_ws_id = detalle_result_ws_id;
		this.n_esfe = n_esfe;
		this.d_esfe = d_esfe;
		this.n_estado_fe = n_estado_fe;
		this.c_email_contacto = c_email_contacto;
		this.c_telefono_opcional = c_telefono_opcional;
		this.n_info = n_info;
		this.correo_encuesta_enviado = correo_encuesta_enviado;
		this.nro_operation_niubiz = nro_operation_niubiz;
		this.nro_cip_pagoefectivo = nro_cip_pagoefectivo;
		this.nombre_promocion2 = nombre_promocion2;
		this.descuento_promocion2 = descuento_promocion2;
		this.correo_enviado1 = correo_enviado1;
		this.correo_enviado2 = correo_enviado2;
		this.venpas_idtx = venpas_idtx;
		this.parentesco_id = parentesco_id;
		this.venpas_idparents = venpas_idparents;
		this.n_tipo_pasajero = n_tipo_pasajero;
		this.fecha_hora_canje = fecha_hora_canje;
		this.fecha_hora_niubiz1 = fecha_hora_niubiz1;
		this.fecha_hora_niubiz2 = fecha_hora_niubiz2;
		this.fecha_hora_correo = fecha_hora_correo;
		this.numboleto_numbolant = numboleto_numbolant;
		this.anulado_por = anulado_por;
		this.estado_order = estado_order;
		this.pdf_para_descargar = pdf_para_descargar;
	}

	public BigDecimal getVenpas_id() {
		return venpas_id;
	}

	public void setVenpas_id(BigDecimal venpas_id) {
		this.venpas_id = venpas_id;
	}

	public String getVenpas_idref() {
		return venpas_idref;
	}

	public void setVenpas_idref(String venpas_idref) {
		this.venpas_idref = venpas_idref;
	}

	public String getVenpas_idoriginal() {
		return venpas_idoriginal;
	}

	public void setVenpas_idoriginal(String venpas_idoriginal) {
		this.venpas_idoriginal = venpas_idoriginal;
	}

	public String getItinerario_id() {
		return itinerario_id;
	}

	public void setItinerario_id(String itinerario_id) {
		this.itinerario_id = itinerario_id;
	}

	public String getRuta_id() {
		return ruta_id;
	}

	public void setRuta_id(String ruta_id) {
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

	public String getDetalle_ruta() {
		return detalle_ruta;
	}

	public void setDetalle_ruta(String detalle_ruta) {
		this.detalle_ruta = detalle_ruta;
	}

	public String getCliente_id() {
		return cliente_id;
	}

	public void setCliente_id(String cliente_id) {
		this.cliente_id = cliente_id;
	}

	public String getRuc_cliente() {
		return ruc_cliente;
	}

	public void setRuc_cliente(String ruc_cliente) {
		this.ruc_cliente = ruc_cliente;
	}

	public String getRazon_social() {
		return razon_social;
	}

	public void setRazon_social(String razon_social) {
		this.razon_social = razon_social;
	}

	public String getDireccion_cliente() {
		return direccion_cliente;
	}

	public void setDireccion_cliente(String direccion_cliente) {
		this.direccion_cliente = direccion_cliente;
	}

	public String getPasajero_id() {
		return pasajero_id;
	}

	public void setPasajero_id(String pasajero_id) {
		this.pasajero_id = pasajero_id;
	}

	public String getDocumento_pasajero() {
		return documento_pasajero;
	}

	public void setDocumento_pasajero(String documento_pasajero) {
		this.documento_pasajero = documento_pasajero;
	}

	public String getNombres_apellidos() {
		return nombres_apellidos;
	}

	public void setNombres_apellidos(String nombres_apellidos) {
		this.nombres_apellidos = nombres_apellidos;
	}

	public String getEmail_pasajero() {
		return email_pasajero;
	}

	public void setEmail_pasajero(String email_pasajero) {
		this.email_pasajero = email_pasajero;
	}

	public String getTelefono_pasajero() {
		return telefono_pasajero;
	}

	public void setTelefono_pasajero(String telefono_pasajero) {
		this.telefono_pasajero = telefono_pasajero;
	}

	public String getForpag_id() {
		return forpag_id;
	}

	public void setForpag_id(String forpag_id) {
		this.forpag_id = forpag_id;
	}

	public String getDetalle_forpag() {
		return detalle_forpag;
	}

	public void setDetalle_forpag(String detalle_forpag) {
		this.detalle_forpag = detalle_forpag;
	}

	public String getServicio_id() {
		return servicio_id;
	}

	public void setServicio_id(String servicio_id) {
		this.servicio_id = servicio_id;
	}

	public String getDetalle_servicio() {
		return detalle_servicio;
	}

	public void setDetalle_servicio(String detalle_servicio) {
		this.detalle_servicio = detalle_servicio;
	}

	public String getTipcom_id() {
		return tipcom_id;
	}

	public void setTipcom_id(String tipcom_id) {
		this.tipcom_id = tipcom_id;
	}

	public String getDetalle_tipcom() {
		return detalle_tipcom;
	}

	public void setDetalle_tipcom(String detalle_tipcom) {
		this.detalle_tipcom = detalle_tipcom;
	}

	public String getTipmov_id() {
		return tipmov_id;
	}

	public void setTipmov_id(String tipmov_id) {
		this.tipmov_id = tipmov_id;
	}

	public String getDetalle_tipmov() {
		return detalle_tipmov;
	}

	public void setDetalle_tipmov(String detalle_tipmov) {
		this.detalle_tipmov = detalle_tipmov;
	}

	public String getTipforpag_id() {
		return tipforpag_id;
	}

	public void setTipforpag_id(String tipforpag_id) {
		this.tipforpag_id = tipforpag_id;
	}

	public String getDetalle_tipforpag() {
		return detalle_tipforpag;
	}

	public void setDetalle_tipforpag(String detalle_tipforpag) {
		this.detalle_tipforpag = detalle_tipforpag;
	}

	public String getTarcre_id() {
		return tarcre_id;
	}

	public void setTarcre_id(String tarcre_id) {
		this.tarcre_id = tarcre_id;
	}

	public String getDetalle_tarcre() {
		return detalle_tarcre;
	}

	public void setDetalle_tarcre(String detalle_tarcre) {
		this.detalle_tarcre = detalle_tarcre;
	}

	public String getC_numboleto() {
		return c_numboleto;
	}

	public void setC_numboleto(String c_numboleto) {
		this.c_numboleto = c_numboleto;
	}

	public String getC_numbolant() {
		return c_numbolant;
	}

	public void setC_numbolant(String c_numbolant) {
		this.c_numbolant = c_numbolant;
	}

	public int getN_numasiento() {
		return n_numasiento;
	}

	public void setN_numasiento(int n_numasiento) {
		this.n_numasiento = n_numasiento;
	}

	public String getC_numcontrol() {
		return c_numcontrol;
	}

	public void setC_numcontrol(String c_numcontrol) {
		this.c_numcontrol = c_numcontrol;
	}

	public String getId_agencia_partida() {
		return id_agencia_partida;
	}

	public void setId_agencia_partida(String id_agencia_partida) {
		this.id_agencia_partida = id_agencia_partida;
	}

	public String getDireccion_agencia_partida() {
		return direccion_agencia_partida;
	}

	public void setDireccion_agencia_partida(String direccion_agencia_partida) {
		this.direccion_agencia_partida = direccion_agencia_partida;
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

	public String getId_agencia_llegada() {
		return id_agencia_llegada;
	}

	public void setId_agencia_llegada(String id_agencia_llegada) {
		this.id_agencia_llegada = id_agencia_llegada;
	}

	public String getDireccion_agencia_llegada() {
		return direccion_agencia_llegada;
	}

	public void setDireccion_agencia_llegada(String direccion_agencia_llegada) {
		this.direccion_agencia_llegada = direccion_agencia_llegada;
	}

	public String getFecha_llegada() {
		return fecha_llegada;
	}

	public void setFecha_llegada(String fecha_llegada) {
		this.fecha_llegada = fecha_llegada;
	}

	public String getHora_llegada() {
		return hora_llegada;
	}

	public void setHora_llegada(String hora_llegada) {
		this.hora_llegada = hora_llegada;
	}

	public int getN_secuencial() {
		return n_secuencial;
	}

	public void setN_secuencial(int n_secuencial) {
		this.n_secuencial = n_secuencial;
	}

	public Double getN_imppag() {
		return n_imppag;
	}

	public void setN_imppag(Double n_imppag) {
		this.n_imppag = n_imppag;
	}

	public String getTipo_tramite() {
		return tipo_tramite;
	}

	public void setTipo_tramite(String tipo_tramite) {
		this.tipo_tramite = tipo_tramite;
	}

	public String getAgencia_id() {
		return agencia_id;
	}

	public void setAgencia_id(String agencia_id) {
		this.agencia_id = agencia_id;
	}

	public String getNombre_agencia() {
		return nombre_agencia;
	}

	public void setNombre_agencia(String nombre_agencia) {
		this.nombre_agencia = nombre_agencia;
	}

	public String getUsuario_id() {
		return usuario_id;
	}

	public void setUsuario_id(String usuario_id) {
		this.usuario_id = usuario_id;
	}

	public String getC_login() {
		return c_login;
	}

	public void setC_login(String c_login) {
		this.c_login = c_login;
	}

	public String getCanven_id() {
		return canven_id;
	}

	public void setCanven_id(String canven_id) {
		this.canven_id = canven_id;
	}

	public String getDetalle_canven() {
		return detalle_canven;
	}

	public void setDetalle_canven(String detalle_canven) {
		this.detalle_canven = detalle_canven;
	}

	public String getN_numopeban() {
		return n_numopeban;
	}

	public void setN_numopeban(String n_numopeban) {
		this.n_numopeban = n_numopeban;
	}

	public String getD_fecexpres() {
		return d_fecexpres;
	}

	public void setD_fecexpres(String d_fecexpres) {
		this.d_fecexpres = d_fecexpres;
	}

	public String getC_horexpres() {
		return c_horexpres;
	}

	public void setC_horexpres(String c_horexpres) {
		this.c_horexpres = c_horexpres;
	}

	public int getN_idaret() {
		return n_idaret;
	}

	public void setN_idaret(int n_idaret) {
		this.n_idaret = n_idaret;
	}

	public String getDetalle_idaret() {
		return detalle_idaret;
	}

	public void setDetalle_idaret(String detalle_idaret) {
		this.detalle_idaret = detalle_idaret;
	}

	public String getC_rucclicre() {
		return c_rucclicre;
	}

	public void setC_rucclicre(String c_rucclicre) {
		this.c_rucclicre = c_rucclicre;
	}

	public int getN_esfecabi() {
		return n_esfecabi;
	}

	public void setN_esfecabi(int n_esfecabi) {
		this.n_esfecabi = n_esfecabi;
	}

	public String getDetalle_esfecabi() {
		return detalle_esfecabi;
	}

	public void setDetalle_esfecabi(String detalle_esfecabi) {
		this.detalle_esfecabi = detalle_esfecabi;
	}

	public String getC_observaciones() {
		return c_observaciones;
	}

	public void setC_observaciones(String c_observaciones) {
		this.c_observaciones = c_observaciones;
	}

	public String getPromocion_id() {
		return promocion_id;
	}

	public void setPromocion_id(String promocion_id) {
		this.promocion_id = promocion_id;
	}

	public String getNombre_promocion1() {
		return nombre_promocion1;
	}

	public void setNombre_promocion1(String nombre_promocion1) {
		this.nombre_promocion1 = nombre_promocion1;
	}

	public String getDescuento_promocion1() {
		return descuento_promocion1;
	}

	public void setDescuento_promocion1(String descuento_promocion1) {
		this.descuento_promocion1 = descuento_promocion1;
	}

	public String getN_ididaret() {
		return n_ididaret;
	}

	public void setN_ididaret(String n_ididaret) {
		this.n_ididaret = n_ididaret;
	}

	public String getAudfecins() {
		return audfecins;
	}

	public void setAudfecins(String audfecins) {
		this.audfecins = audfecins;
	}

	public String getAudusuins() {
		return audusuins;
	}

	public void setAudusuins(String audusuins) {
		this.audusuins = audusuins;
	}

	public String getAudfecmod() {
		return audfecmod;
	}

	public void setAudfecmod(String audfecmod) {
		this.audfecmod = audfecmod;
	}

	public String getAudusumod() {
		return audusumod;
	}

	public void setAudusumod(String audusumod) {
		this.audusumod = audusumod;
	}

	public String getC_estdoc() {
		return c_estdoc;
	}

	public void setC_estdoc(String c_estdoc) {
		this.c_estdoc = c_estdoc;
	}

	public String getC_codref() {
		return c_codref;
	}

	public void setC_codref(String c_codref) {
		this.c_codref = c_codref;
	}

	public String getD_fecvent() {
		return d_fecvent;
	}

	public void setD_fecvent(String d_fecvent) {
		this.d_fecvent = d_fecvent;
	}

	public String getResult_ws_id() {
		return result_ws_id;
	}

	public void setResult_ws_id(String result_ws_id) {
		this.result_ws_id = result_ws_id;
	}

	public String getDetalle_result_ws_id() {
		return detalle_result_ws_id;
	}

	public void setDetalle_result_ws_id(String detalle_result_ws_id) {
		this.detalle_result_ws_id = detalle_result_ws_id;
	}

	public String getN_esfe() {
		return n_esfe;
	}

	public void setN_esfe(String n_esfe) {
		this.n_esfe = n_esfe;
	}

	public String getD_esfe() {
		return d_esfe;
	}

	public void setD_esfe(String d_esfe) {
		this.d_esfe = d_esfe;
	}

	public String getN_estado_fe() {
		return n_estado_fe;
	}

	public void setN_estado_fe(String n_estado_fe) {
		this.n_estado_fe = n_estado_fe;
	}

	public String getC_email_contacto() {
		return c_email_contacto;
	}

	public void setC_email_contacto(String c_email_contacto) {
		this.c_email_contacto = c_email_contacto;
	}

	public String getC_telefono_opcional() {
		return c_telefono_opcional;
	}

	public void setC_telefono_opcional(String c_telefono_opcional) {
		this.c_telefono_opcional = c_telefono_opcional;
	}

	public String getN_info() {
		return n_info;
	}

	public void setN_info(String n_info) {
		this.n_info = n_info;
	}

	public int getCorreo_encuesta_enviado() {
		return correo_encuesta_enviado;
	}

	public void setCorreo_encuesta_enviado(int correo_encuesta_enviado) {
		this.correo_encuesta_enviado = correo_encuesta_enviado;
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

	public String getNombre_promocion2() {
		return nombre_promocion2;
	}

	public void setNombre_promocion2(String nombre_promocion2) {
		this.nombre_promocion2 = nombre_promocion2;
	}

	public String getDescuento_promocion2() {
		return descuento_promocion2;
	}

	public void setDescuento_promocion2(String descuento_promocion2) {
		this.descuento_promocion2 = descuento_promocion2;
	}

	public int getCorreo_enviado1() {
		return correo_enviado1;
	}

	public void setCorreo_enviado1(int correo_enviado1) {
		this.correo_enviado1 = correo_enviado1;
	}

	public int getCorreo_enviado2() {
		return correo_enviado2;
	}

	public void setCorreo_enviado2(int correo_enviado2) {
		this.correo_enviado2 = correo_enviado2;
	}

	public String getVenpas_idtx() {
		return venpas_idtx;
	}

	public void setVenpas_idtx(String venpas_idtx) {
		this.venpas_idtx = venpas_idtx;
	}

	public String getParentesco_id() {
		return parentesco_id;
	}

	public void setParentesco_id(String parentesco_id) {
		this.parentesco_id = parentesco_id;
	}

	public String getVenpas_idparents() {
		return venpas_idparents;
	}

	public void setVenpas_idparents(String venpas_idparents) {
		this.venpas_idparents = venpas_idparents;
	}

	public String getN_tipo_pasajero() {
		return n_tipo_pasajero;
	}

	public void setN_tipo_pasajero(String n_tipo_pasajero) {
		this.n_tipo_pasajero = n_tipo_pasajero;
	}

	public String getFecha_hora_canje() {
		return fecha_hora_canje;
	}

	public void setFecha_hora_canje(String fecha_hora_canje) {
		this.fecha_hora_canje = fecha_hora_canje;
	}

	public String getFecha_hora_niubiz1() {
		return fecha_hora_niubiz1;
	}

	public void setFecha_hora_niubiz1(String fecha_hora_niubiz1) {
		this.fecha_hora_niubiz1 = fecha_hora_niubiz1;
	}

	public String getFecha_hora_niubiz2() {
		return fecha_hora_niubiz2;
	}

	public void setFecha_hora_niubiz2(String fecha_hora_niubiz2) {
		this.fecha_hora_niubiz2 = fecha_hora_niubiz2;
	}

	public String getFecha_hora_correo() {
		return fecha_hora_correo;
	}

	public void setFecha_hora_correo(String fecha_hora_correo) {
		this.fecha_hora_correo = fecha_hora_correo;
	}

	public String getNumboleto_numbolant() {
		return numboleto_numbolant;
	}

	public void setNumboleto_numbolant(String numboleto_numbolant) {
		this.numboleto_numbolant = numboleto_numbolant;
	}

	public String getAnulado_por() {
		return anulado_por;
	}

	public void setAnulado_por(String anulado_por) {
		this.anulado_por = anulado_por;
	}

	public int getEstado_order() {
		return estado_order;
	}

	public void setEstado_order(int estado_order) {
		this.estado_order = estado_order;
	}

	public int getPdf_para_descargar() {
		return pdf_para_descargar;
	}

	public void setPdf_para_descargar(int pdf_para_descargar) {
		this.pdf_para_descargar = pdf_para_descargar;
	}
}
