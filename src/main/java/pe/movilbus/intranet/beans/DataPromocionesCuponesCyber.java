package pe.movilbus.intranet.beans;

import java.io.Serializable;
import java.math.BigDecimal;

public class DataPromocionesCuponesCyber implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int cupones_id;
	private String nombre;
	private int porcentaje_desc;
	private int usosrestantes;
	private String servicios;
	private String rutas_prohibidas;
	private String fecha_inicio;
	private String fecha_fin;
	private String compra_inicio;
	private String compra_fin;
	private int estado;
	private int grupo_cupones;
	private String rutas_aceptadas;
	private int tipo_promocion;
	private int tipo_sistema;
	
	public DataPromocionesCuponesCyber(){
		super();
	}

	public DataPromocionesCuponesCyber(int cupones_id, String nombre, int porcentaje_desc, int usosrestantes,
			String servicios, String rutas_prohibidas, String fecha_inicio, String fecha_fin, String compra_inicio,
			String compra_fin, int estado, int grupo_cupones, String rutas_aceptadas, int tipo_promocion,
			int tipo_sistema) {
		super();
		this.cupones_id = cupones_id;
		this.nombre = nombre;
		this.porcentaje_desc = porcentaje_desc;
		this.usosrestantes = usosrestantes;
		this.servicios = servicios;
		this.rutas_prohibidas = rutas_prohibidas;
		this.fecha_inicio = fecha_inicio;
		this.fecha_fin = fecha_fin;
		this.compra_inicio = compra_inicio;
		this.compra_fin = compra_fin;
		this.estado = estado;
		this.grupo_cupones = grupo_cupones;
		this.rutas_aceptadas = rutas_aceptadas;
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

	public int getPorcentaje_desc() {
		return porcentaje_desc;
	}

	public void setPorcentaje_desc(int porcentaje_desc) {
		this.porcentaje_desc = porcentaje_desc;
	}

	public int getUsosrestantes() {
		return usosrestantes;
	}

	public void setUsosrestantes(int usosrestantes) {
		this.usosrestantes = usosrestantes;
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

	public int getGrupo_cupones() {
		return grupo_cupones;
	}

	public void setGrupo_cupones(int grupo_cupones) {
		this.grupo_cupones = grupo_cupones;
	}

	public String getRutas_aceptadas() {
		return rutas_aceptadas;
	}

	public void setRutas_aceptadas(String rutas_aceptadas) {
		this.rutas_aceptadas = rutas_aceptadas;
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