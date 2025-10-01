package pe.movilbus.intranet.beans;

import java.io.Serializable;
import java.util.Date;

public class Personal implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int id;
	private int tipper_id;
	private int tipdoc_id;
	private String c_codigo;
	private int c_numdoc;
	private String c_nombre;
	private String c_apepat;
	private String c_apemat;
	private Date d_fecnac;
	private String c_lugnac;
	private int estciv_id;
	private int sexo_id;
	private int nacionalidad_id;
	private String c_telefono;
	private String c_email;
	private int ubigeo_id;
	private String c_licencia;
	private String c_categoria;
	private int tipvia_id;
	private String c_nomvia;
	private int tipzon_id;
	private String c_nomzon;
	private String c_direccion;
	private String c_codcarsync;
	
	public Personal(){
		super();
	}

	public Personal(int id, int tipper_id, int tipdoc_id, String c_codigo, int c_numdoc, String c_nombre, String c_apepat,
			String c_apemat, Date d_fecnac, String c_lugnac, int estciv_id, int sexo_id, int nacionalidad_id,
			String c_telefono, String c_email, int ubigeo_id, String c_licencia, String c_categoria, int tipvia_id,
			String c_nomvia, int tipzon_id, String c_nomzon, String c_direccion, String c_codcarsync) {
		super();
		this.id = id;
		this.tipper_id = tipper_id;
		this.tipdoc_id = tipdoc_id;
		this.c_codigo = c_codigo;
		this.c_numdoc = c_numdoc;
		this.c_nombre = c_nombre;
		this.c_apepat = c_apepat;
		this.c_apemat = c_apemat;
		this.d_fecnac = d_fecnac;
		this.c_lugnac = c_lugnac;
		this.estciv_id = estciv_id;
		this.sexo_id = sexo_id;
		this.nacionalidad_id = nacionalidad_id;
		this.c_telefono = c_telefono;
		this.c_email = c_email;
		this.ubigeo_id = ubigeo_id;
		this.c_licencia = c_licencia;
		this.c_categoria = c_categoria;
		this.tipvia_id = tipvia_id;
		this.c_nomvia = c_nomvia;
		this.tipzon_id = tipzon_id;
		this.c_nomzon = c_nomzon;
		this.c_direccion = c_direccion;
		this.c_codcarsync = c_codcarsync;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTipper_id() {
		return tipper_id;
	}

	public void setTipper_id(int tipper_id) {
		this.tipper_id = tipper_id;
	}

	public int getTipdoc_id() {
		return tipdoc_id;
	}

	public void setTipdoc_id(int tipdoc_id) {
		this.tipdoc_id = tipdoc_id;
	}

	public String getC_codigo() {
		return c_codigo;
	}

	public void setC_codigo(String c_codigo) {
		this.c_codigo = c_codigo;
	}

	public int getC_numdoc() {
		return c_numdoc;
	}

	public void setC_numdoc(int c_numdoc) {
		this.c_numdoc = c_numdoc;
	}

	public String getC_nombre() {
		return c_nombre;
	}

	public void setC_nombre(String c_nombre) {
		this.c_nombre = c_nombre;
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

	public Date getD_fecnac() {
		return d_fecnac;
	}

	public void setD_fecnac(Date d_fecnac) {
		this.d_fecnac = d_fecnac;
	}

	public String getC_lugnac() {
		return c_lugnac;
	}

	public void setC_lugnac(String c_lugnac) {
		this.c_lugnac = c_lugnac;
	}

	public int getEstciv_id() {
		return estciv_id;
	}

	public void setEstciv_id(int estciv_id) {
		this.estciv_id = estciv_id;
	}

	public int getSexo_id() {
		return sexo_id;
	}

	public void setSexo_id(int sexo_id) {
		this.sexo_id = sexo_id;
	}

	public int getNacionalidad_id() {
		return nacionalidad_id;
	}

	public void setNacionalidad_id(int nacionalidad_id) {
		this.nacionalidad_id = nacionalidad_id;
	}

	public String getC_telefono() {
		return c_telefono;
	}

	public void setC_telefono(String c_telefono) {
		this.c_telefono = c_telefono;
	}

	public String getC_email() {
		return c_email;
	}

	public void setC_email(String c_email) {
		this.c_email = c_email;
	}

	public int getUbigeo_id() {
		return ubigeo_id;
	}

	public void setUbigeo_id(int ubigeo_id) {
		this.ubigeo_id = ubigeo_id;
	}

	public String getC_licencia() {
		return c_licencia;
	}

	public void setC_licencia(String c_licencia) {
		this.c_licencia = c_licencia;
	}

	public String getC_categoria() {
		return c_categoria;
	}

	public void setC_categoria(String c_categoria) {
		this.c_categoria = c_categoria;
	}

	public int getTipvia_id() {
		return tipvia_id;
	}

	public void setTipvia_id(int tipvia_id) {
		this.tipvia_id = tipvia_id;
	}

	public String getC_nomvia() {
		return c_nomvia;
	}

	public void setC_nomvia(String c_nomvia) {
		this.c_nomvia = c_nomvia;
	}

	public int getTipzon_id() {
		return tipzon_id;
	}

	public void setTipzon_id(int tipzon_id) {
		this.tipzon_id = tipzon_id;
	}

	public String getC_nomzon() {
		return c_nomzon;
	}

	public void setC_nomzon(String c_nomzon) {
		this.c_nomzon = c_nomzon;
	}

	public String getC_direccion() {
		return c_direccion;
	}

	public void setC_direccion(String c_direccion) {
		this.c_direccion = c_direccion;
	}

	public String getC_codcarsync() {
		return c_codcarsync;
	}

	public void setC_codcarsync(String c_codcarsync) {
		this.c_codcarsync = c_codcarsync;
	}
}
