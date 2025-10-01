package pe.movilbus.intranet.beans;

public class AuditoriaLoginSispas implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private Agencia agencia;
	private UsuarioHardware usuariohardware;
	private Usuario usuario;
	private Integer n_tiposeguridad;
	private String  denominacion_seguridad;
	private UsuarioRol rol;
	/**
	 * 
	 */
	public AuditoriaLoginSispas() {
		super();
	}
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the agencia
	 */
	public Agencia getAgencia() {
		return agencia;
	}
	/**
	 * @param agencia the agencia to set
	 */
	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}
	/**
	 * @return the usuariohardware
	 */
	public UsuarioHardware getUsuariohardware() {
		return usuariohardware;
	}
	/**
	 * @param usuariohardware the usuariohardware to set
	 */
	public void setUsuariohardware(UsuarioHardware usuariohardware) {
		this.usuariohardware = usuariohardware;
	}
	/**
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}
	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	/**
	 * @return the n_tiposeguridad
	 */
	public Integer getN_tiposeguridad() {
		return n_tiposeguridad;
	}
	/**
	 * @param n_tiposeguridad the n_tiposeguridad to set
	 */
	public void setN_tiposeguridad(Integer n_tiposeguridad) {
		this.n_tiposeguridad = n_tiposeguridad;
	}
	/**
	 * @return the denominacion_seguridad
	 */
	public String getDenominacion_seguridad() {
		return denominacion_seguridad;
	}
	/**
	 * @param denominacion_seguridad the denominacion_seguridad to set
	 */
	public void setDenominacion_seguridad(String denominacion_seguridad) {
		this.denominacion_seguridad = denominacion_seguridad;
	}
	/**
	 * @return the rol
	 */
	public UsuarioRol getRol() {
		return rol;
	}
	/**
	 * @param rol the rol to set
	 */
	public void setRol(UsuarioRol rol) {
		this.rol = rol;
	}
	

	
}
