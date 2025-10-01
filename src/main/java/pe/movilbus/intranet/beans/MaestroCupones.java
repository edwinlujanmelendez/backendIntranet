package pe.movilbus.intranet.beans;

import java.io.Serializable;

public class MaestroCupones implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int cupones_id;
	private String nombre;
	private double porcentaje_desc;
	private double preciofinal_desc;
	private int usos_restantes;
	private int stock;
	private String servicios;
	private String rutas_prohibidas;
	private String rutas_aceptadas;
	private String hora_partida;
	private String fecha_inicio;
	private String fecha_fin;
	private String compra_inicio;
	private String compra_fin;
	private int estado;
	private int grupoCuponesId;
	private String nombreGrupo_cupon;
	private int tipo_promocion;
	private int tipo_sistema;
		
	public MaestroCupones(){
		super();
	}
	
	public MaestroCupones(int cupones_id, String nombre, double porcentaje_desc, double preciofinal_desc, int usos_restantes, int stock, String servicios, String rutas_prohibidas, String rutas_aceptadas, String hora_partida, 
			String fecha_inicio, String fecha_fin, String compra_inicio, String compra_fin, int estado, int grupoCuponesId, String nombreGrupo_cupon, int tipo_promocion, int tipo_sistema){
		super();
		this.cupones_id = cupones_id;
		this.nombre = nombre;
		this.porcentaje_desc = porcentaje_desc;
		this.preciofinal_desc = preciofinal_desc;
		this.usos_restantes = usos_restantes;
		this.stock = stock;
		this.servicios = servicios;
		this.rutas_prohibidas = rutas_prohibidas;
		this.rutas_aceptadas = rutas_aceptadas;
		this.hora_partida = hora_partida;
		this.fecha_inicio = fecha_inicio;
		this.fecha_fin = fecha_fin;
		this.compra_inicio = compra_inicio;
		this.compra_fin = compra_fin;
		this.estado = estado;
		this.grupoCuponesId = grupoCuponesId;
		this.nombreGrupo_cupon = nombreGrupo_cupon;
		this.tipo_promocion = tipo_promocion;
		this.tipo_sistema = tipo_sistema;
	}

	public int getCupones_id() {
		return cupones_id;
	}

	public void setCupones_id(int cupones_id) {
		this.cupones_id = cupones_id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getPorcentaje_desc() {
		return porcentaje_desc;
	}

	public void setPorcentaje_desc(double porcentaje_desc) {
		this.porcentaje_desc = porcentaje_desc;
	}

	public double getPreciofinal_desc() {
		return preciofinal_desc;
	}

	public void setPreciofinal_desc(double preciofinal_desc) {
		this.preciofinal_desc = preciofinal_desc;
	}

	public int getUsos_restantes() {
		return usos_restantes;
	}

	public void setUsos_restantes(int usos_restantes) {
		this.usos_restantes = usos_restantes;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getServicios() {
		return servicios;
	}

	public void setServicios(String servicios) {
		this.servicios = servicios;
	}

	public String getRutas_prohibidas() {
		return rutas_prohibidas;
	}

	public void setRutas_prohibidas(String rutas_prohibidas) {
		this.rutas_prohibidas = rutas_prohibidas;
	}

	public String getRutas_aceptadas() {
		return rutas_aceptadas;
	}

	public void setRutas_aceptadas(String rutas_aceptadas) {
		this.rutas_aceptadas = rutas_aceptadas;
	}

	public String getHora_partida() {
		return hora_partida;
	}

	public void setHora_partida(String hora_partida) {
		this.hora_partida = hora_partida;
	}

	public String getFecha_inicio() {
		return fecha_inicio;
	}

	public void setFecha_inicio(String fecha_inicio) {
		this.fecha_inicio = fecha_inicio;
	}

	public String getFecha_fin() {
		return fecha_fin;
	}

	public void setFecha_fin(String fecha_fin) {
		this.fecha_fin = fecha_fin;
	}

	public String getCompra_inicio() {
		return compra_inicio;
	}

	public void setCompra_inicio(String compra_inicio) {
		this.compra_inicio = compra_inicio;
	}

	public String getCompra_fin() {
		return compra_fin;
	}

	public void setCompra_fin(String compra_fin) {
		this.compra_fin = compra_fin;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public int getGrupoCuponesId() {
		return grupoCuponesId;
	}

	public void setGrupoCuponesId(int grupoCuponesId) {
		this.grupoCuponesId = grupoCuponesId;
	}

	public String getNombreGrupo_cupon() {
		return nombreGrupo_cupon;
	}

	public void setNombreGrupo_cupon(String nombreGrupo_cupon) {
		this.nombreGrupo_cupon = nombreGrupo_cupon;
	}

	public int getTipo_promocion() {
		return tipo_promocion;
	}

	public void setTipo_promocion(int tipo_promocion) {
		this.tipo_promocion = tipo_promocion;
	}

	public int getTipo_sistema() {
		return tipo_sistema;
	}

	public void setTipo_sistema(int tipo_sistema) {
		this.tipo_sistema = tipo_sistema;
	}
	
}
