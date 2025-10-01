package pe.movilbus.intranet.daoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import pe.movilbus.intranet.util.Encriptar;
import pe.movilbus.intranet.beans.Agencia;
import pe.movilbus.intranet.beans.AuditoriaLoginSispas;
import pe.movilbus.intranet.beans.CanalVenta;
import pe.movilbus.intranet.beans.Concesionario;
import pe.movilbus.intranet.beans.Liquidacion;
import pe.movilbus.intranet.beans.Localidad;
import pe.movilbus.intranet.beans.LoginSispas;
import pe.movilbus.intranet.beans.Nacionalidad;
import pe.movilbus.intranet.beans.Personal;
import pe.movilbus.intranet.beans.Rol;
import pe.movilbus.intranet.beans.RptLogin;
import pe.movilbus.intranet.beans.TipoAgencia;
import pe.movilbus.intranet.beans.TipoComprobante;
import pe.movilbus.intranet.beans.TipoMoneda;
import pe.movilbus.intranet.beans.Ubigeo;
import pe.movilbus.intranet.beans.Usuario;
import pe.movilbus.intranet.beans.UsuarioAprobador;
import pe.movilbus.intranet.beans.UsuarioHardware;
import pe.movilbus.intranet.beans.UsuarioRol;
import pe.movilbus.intranet.dao.UsuariosDao;
import pe.movilbus.intranet.util.Constantes;
import org.springframework.jdbc.core.RowMapper;

@Repository
public class UsuariosDaoImpl implements UsuariosDao{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public RptLogin loginSispas(LoginSispas loginSispas){
		RptLogin rptLogin = new RptLogin();
		
		try{
			/**----------------------------- TOKEN -----------------------------**/
			String sql = " select usuario_id, personal_id, agencia_id, c_apepat, c_apemat, c_nombre, c_codigo, c_login, c_password, "+
						 " n_tippas, n_tipseg, usuhard_id, c_email from vrmusuario where c_login='"+loginSispas.getUsername()+"' and c_estreg='"+Constantes.ACTIVO+"'";
			Optional<Usuario> listUsuario = jdbcTemplate.query(sql, new UsuarioRowMapper()).stream().findFirst();
			
			if(!listUsuario.get().getPassword().equals("")){
				String desencrypt = Encriptar.decodifica(listUsuario.get().getPassword(), loginSispas.getUsername());
				if(desencrypt.equals(loginSispas.getPassword())){
					rptLogin.setToken(listUsuario.get().getPassword());
					
					if(!listUsuario.get().getPassword().equals("")){
						/**----------------------------- PERSONAL -----------------------------**/
						sql = " select personal_id, tipper_id, tipdoc_id, c_codigo, c_numdoc, c_nombre, c_apepat, c_apemat, d_fecnac, c_lugnac, estciv_id, sexo_id, nacionalidad_id, c_telefono, c_email, "+ 
							  " ubigeo_id, c_licencia, c_categoria, tipvia_id, c_nomvia, tipzon_id, c_nomzon, c_direccion, c_codcarsync from vrmpersonal where personal_id="+listUsuario.get().getPersonal_id()+" and c_estreg='"+Constantes.ACTIVO+"'";
						Optional<Personal> personal = jdbcTemplate.query(sql, new PersonalRowMapper()).stream().findFirst();
						
						if(personal.isPresent()){
							listUsuario.get().setPersonal(personal.get());
						}
						/**----------------------------- PERSONAL -----------------------------**/
						
						rptLogin.setUsuario(listUsuario.get());
						/**----------------------------- ROLES -----------------------------**/
						sql = " select vrol.* from vrmusuario vusu "+
							  " inner join vrtusuario_rol vusurol on vusurol.usuario_id=vusu.usuario_id "+
							  " inner join vrmrol vrol on vrol.rol_id=vusurol.rol_id "+
							  " where vusu.c_login='"+listUsuario.get().getLogin()+"' and vusu.c_password='"+listUsuario.get().getPassword()+"' ORDER BY vrol.c_denominacion ASC";
						
						List<UsuarioRol> listUsuarioRol = jdbcTemplate.query(sql, new UsuarioRolRowMapper());
						rptLogin.setUsuarioRol(listUsuarioRol);
						/**----------------------------- ROLES -----------------------------**/
						
						/**----------------------------- USUARIO APROBADOR -----------------------------**/
						sql = "select usuapro_id, n_nivapro from vrtusuapro where usuario_id="+listUsuario.get().getUsuario_id()+" and c_estreg='"+Constantes.ACTIVO+"'";
						Optional<UsuarioAprobador> listUsuarioAprobador = jdbcTemplate.query(sql, new UsuarioAprobadorRowMapper()).stream().findFirst();
						if(listUsuarioAprobador.isPresent()){
							rptLogin.setUsuarioAprobador(listUsuarioAprobador.get());
						}
						/**----------------------------- USUARIO APROBADOR -----------------------------**/
												
						/**----------------------------- USUARIO HARDWARE -----------------------------**/
						sql = "select usuhard_id, canven_id, agencia_id, c_codigo, c_dirmac, c_descripcion, n_priapp from vrtusuhard where usuhard_id="+listUsuario.get().getUsuarioHardware();
						Optional<UsuarioHardware> listUsuarioHardware = jdbcTemplate.query(sql, new UsuarioHardwareRowMapper()).stream().findFirst();
						if(listUsuarioHardware.isPresent()){
							// CANAL DE VENTA
							sql = "select canven_id, c_denominacion, c_nomcor, c_color from vrmcanven where canven_id="+listUsuarioHardware.get().getCanven_id();
							Optional<CanalVenta> listCanalVenta = jdbcTemplate.query(sql, new CanalVentaRowMapper()).stream().findFirst();
							if(listCanalVenta.isPresent()){
								listUsuarioHardware.get().setCanalVenta(listCanalVenta.get());
							}
							
							// AGENCIA
							sql = "select agencia_id, tipage_id, localidad_id, concesionario_id, ubigeo_id, zoncom_id, c_denominacion, c_nomcor, n_esterminal, c_codigo, c_direccion, nacionalidad_id, codigo_concar, c_nombre_concar from vrmagencia where agencia_id="+listUsuarioHardware.get().getAgencia_id();
							Optional<Agencia> agencia = jdbcTemplate.query(sql, new AgenciaRowMapper()).stream().findFirst();
							
							if(agencia.isPresent()){
								/**------ AGENCIA > TIPO AGENCIA ------**/
								sql = "select tipage_id, c_denominacion from vrmtipage where tipage_id="+agencia.get().getTipage_id();
								Optional<TipoAgencia> tipoAgencia = jdbcTemplate.query(sql, new TipoAgenciaRowMapper()).stream().findFirst();
								if(tipoAgencia.isPresent()){
									agencia.get().setTipoAgencia(tipoAgencia.get());
								}
								
								/**------ AGENCIA > CONCESIONARIO ------**/
								sql = "select concesionario_id, c_ruc, c_razsoc, c_direccion, d_fecact, d_feccad, d_fecsus, n_comision, n_tipcom, n_incigv, ns_internal_id from vrmconcesionario where concesionario_id="+agencia.get().getConcesionario_id();
								Optional<Concesionario> concesionario = jdbcTemplate.query(sql, new ConcesionarioRowMapper()).stream().findFirst();
								if(concesionario.isPresent()){
									agencia.get().setConcesionario(concesionario.get());
								}
								
								/**------ AGENCIA > LOCALIDAD ------**/
								sql = "select localidad_id, c_denominacion, uniagetit_id, c_est_web, c_iata from vrmlocalidad where localidad_id="+agencia.get().getLocalidad_id();
								Optional<Localidad> localidad = jdbcTemplate.query(sql, new LocalidadRowMapper()).stream().findFirst();
								if(localidad.isPresent()){
									agencia.get().setLocalidad(localidad.get());
								}
								
								/**------ AGENCIA > UBIGEO ------**/
								sql = "select ubigeo_id, c_coddpto, c_codprov, c_coddist, c_nombreubigeo from vrmubigeo where ubigeo_id="+agencia.get().getUbigeo_id();
								Optional<Ubigeo> ubigeo = jdbcTemplate.query(sql, new UbigeoRowMapper()).stream().findFirst();
								if(ubigeo.isPresent()){
									agencia.get().setUbigeo(ubigeo.get());
								}
								
								/**------ AGENCIA > NACIONALIDAD ------**/
								sql = "select nacionalidad_id, c_denominacion, tipmon_id from vrmnacionalidad where nacionalidad_id="+agencia.get().getNacionalidad_id();
								Optional<Nacionalidad> nacionalidad = jdbcTemplate.query(sql, new NacionalidadRowMapper()).stream().findFirst();
								
								/**------ AGENCIA > NACIONALIDAD > TIPO MONEDA ------**/								
								if(nacionalidad.isPresent()){
									sql = "select tipmon_id, c_denominacion, c_unimon, c_simmon from vrmtipmon where tipmon_id="+nacionalidad.get().getTipmond_id();
									Optional<TipoMoneda> tipoMoneda = jdbcTemplate.query(sql, new TipoMonedaRowMapper()).stream().findFirst();
									
									if(tipoMoneda.isPresent()){
										nacionalidad.get().setTipoMoneda(tipoMoneda.get());
									}
									
									agencia.get().setNacionalidad(nacionalidad.get());
								}
								
								/**----------------------------- TIPO COMPROBANTE -----------------------------**/
								int idTipoComprobante = 0;
								if(agencia.get().getTipoAgencia().getId() == Constantes.ID_TIPAGE_MOVIL){
									idTipoComprobante = Constantes.ID_TIPCOM_BOLETO_VIAJE;
								}else if(agencia.get().getTipoAgencia().getId() == Constantes.ID_TIPAGE_VIAJES){
									idTipoComprobante = Constantes.ID_TIPCOM_VOUCHER_AGENCIA_VIAJES;
								}else{
									idTipoComprobante = Constantes.ID_TIPCOM_VOUCHER_CORPORATIVO;
								}
								sql = "select tipcom_id, c_denominacion, c_abreviatura, n_rubro from vrmtipcom where tipcom_id="+idTipoComprobante;
								Optional<TipoComprobante> tipoComprobante = jdbcTemplate.query(sql, new TipoComprobanteRowMapper()).stream().findFirst();
								if(tipoComprobante.isPresent()){
									rptLogin.setTipoComprobante(tipoComprobante.get());
								}
								/**----------------------------- TIPO COMPROBANTE -----------------------------**/
								
								listUsuarioHardware.get().setAgencia(agencia.get());
								
								if(listUsuarioRol.size() == 1 && loginSispas.getRol_id() == 0){
									rptLogin.setRol(listUsuarioRol.get(0));
									rptLogin.setLogin(2);							//1=Elegir Rol, 2=Inicio de Sesión
									
									/**----------------------------- AUDITAR INGRESO -----------------------------**/
									AuditoriaLoginSispas objauditoria =  new AuditoriaLoginSispas();
									objauditoria.setAgencia(agencia.get());
									objauditoria.setUsuariohardware(listUsuarioHardware.get());
									objauditoria.setUsuario(listUsuario.get());
									objauditoria.setRol(listUsuarioRol.get(0));
									objauditoria.setN_tiposeguridad(Constantes.TRUE_VALUE);
									objauditoria.setDenominacion_seguridad("DIRECTO-WEB");
									
									controlLoginSispas(objauditoria);
									/**----------------------------- AUDITAR INGRESO -----------------------------**/
								}else if(listUsuarioRol.size() > 1 && loginSispas.getRol_id() != 0){
									/**----------------------------- ROL -----------------------------**/
									sql = " select * from vrmrol where rol_id="+loginSispas.getRol_id();
									Optional<UsuarioRol> rol = jdbcTemplate.query(sql, new UsuarioRolRowMapper()).stream().findFirst();
									rptLogin.setRol(rol.get());
									/**----------------------------- ROL -----------------------------**/
									
									rptLogin.setLogin(2);							//1=Elegir Rol, 2=Inicio de Sesión
									
									/**----------------------------- AUDITAR INGRESO -----------------------------**/
									AuditoriaLoginSispas objauditoria =  new AuditoriaLoginSispas();
									objauditoria.setAgencia(agencia.get());
									objauditoria.setUsuariohardware(listUsuarioHardware.get());
									objauditoria.setUsuario(listUsuario.get());
									objauditoria.setRol(rol.get());
									objauditoria.setN_tiposeguridad(Constantes.TRUE_VALUE);
									objauditoria.setDenominacion_seguridad("DIRECTO-WEB");
									
									controlLoginSispas(objauditoria);
									/**----------------------------- AUDITAR INGRESO -----------------------------**/
								}else if(listUsuarioRol.size() > 1 && loginSispas.getRol_id() == 0){
									rptLogin.setLogin(1);							//1=Elegir Rol, 2=Inicio de Sesión
								}
								
								/**----------------------------- LIQUIDACIÓN ABIERTA -----------------------------**/							
								sql = "select liquidacion_id, n_anio, agencia_id, usuario_id, c_nomusu, d_fecliq, n_moning, n_estliq, liqofi_id from vrtliquidacion where usuario_id="+listUsuario.get().getUsuario_id()+
									  " and agencia_id="+agencia.get().getAgencia_id()+" and n_estliq="+Constantes.LIQUI_ESTA_ABIERTO+" and c_estreg='"+Constantes.ACTIVO+"'";
								
								//System.out.println(sql);
								Optional<Liquidacion> liquidacion = jdbcTemplate.query(sql, new LiquidacionRowMapper()).stream().findFirst();
								if(liquidacion.isPresent()){
									rptLogin.setLiquidacion(liquidacion.get());
								}
								
								// Realiza el cierre y/o apertura de manera automatica para el caso de las agencias de viaje o corporativos - impl 31/03/2014
								/*Calendar calendarHoy = Calendar.getInstance();
								Date fechaHoy = calendarHoy.getTime();
								
								if(tipoAgencia.get().getId() != Constantes.ID_TIPAGE_MOVIL){									
									if(liquidacion.get()!= null && liquidacion.get().getD_fecliq().getTime()<fechaHoy.getTime()){
									
									}else if(liquidacion.get()== null){
										
									}
								}*/
								// Realiza el cierre y/o apertura de manera automatica para el caso de las agencias de viaje o corporativos - impl 31/03/2014
								
								/**----------------------------- LIQUIDACIÓN ABIERTA -----------------------------**/
							}
							
							rptLogin.setUsuarioHardware(listUsuarioHardware.get());
						}
						/**----------------------------- USUARIO HARDWARE -----------------------------**/
					}
				}
			}
			/**----------------------------- TOKEN -----------------------------**/
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return rptLogin;
	}
	
	@Override
	public Liquidacion getVerificarCajaAbierta(int idUsuario, int idAgencia){
		try{
			String sql = "select liquidacion_id, n_anio, agencia_id, usuario_id, c_nomusu, d_fecliq, n_moning, n_estliq, liqofi_id from vrtliquidacion where usuario_id="+idUsuario+
					  " and agencia_id="+idAgencia+" and n_estliq="+Constantes.LIQUI_ESTA_ABIERTO+" and c_estreg='"+Constantes.ACTIVO+"'";
			
			Optional<Liquidacion> liquidacion = jdbcTemplate.query(sql, new LiquidacionRowMapper()).stream().findFirst();
			if(liquidacion.isPresent()){
				return liquidacion.get();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public void controlLoginSispas(AuditoriaLoginSispas auditoria){
		
		String sqlinsert = " INSERT INTO VRTAUDIACCESO ( AUDIACCESO_ID, C_SISTEMA, C_AGENCIAS, "+
										                " C_USUARIOHARWARE, USUARIO_ID, C_LOGIN, "+
										                " AGENCIA_ID,USUHARD_ID, N_TIPSEG, "+
										                " C_TIPSEGURIDAD,ROL_ID	) "+
										      " VALUES ( SEQ_VRTAUDIACCESO_ID.NEXTVAL, 'INTRANET','"+auditoria.getAgencia().getC_denominacion().trim()+"',"+
										                 "'"+auditoria.getUsuariohardware().getC_descripcion().trim()+"',"+auditoria.getUsuario().getUsuario_id()+",'"+auditoria.getUsuario().getLogin().trim()+"',"+
										                     auditoria.getAgencia().getAgencia_id()+","+auditoria.getUsuariohardware().getUsuhard_id()+","+auditoria.getN_tiposeguridad()+",'"+
										                     auditoria.getDenominacion_seguridad()+"',"+auditoria.getRol().getRol_id()+")";
			
			jdbcTemplate.update(sqlinsert);
	}
	
	private static final class UsuarioRowMapper implements RowMapper<Usuario> {
		
		@Override
		public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Usuario(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), 
					rs.getInt(10), rs.getInt(11), rs.getInt(12), rs.getString(13));
		}
	}
	
	private static final class PersonalRowMapper implements RowMapper<Personal> {
		
		@Override
		public Personal mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Personal(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getInt(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getDate(9), 
					rs.getString(10), rs.getInt(11), rs.getInt(12), rs.getInt(13), rs.getString(14), rs.getString(15), rs.getInt(16), rs.getString(17), rs.getString(18), 
					rs.getInt(19), rs.getString(20), rs.getInt(21), rs.getString(22), rs.getString(23), rs.getString(24));
		}
	}
	
	private static final class UsuarioRolRowMapper implements RowMapper<UsuarioRol> {
		
		@Override
		public UsuarioRol mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new UsuarioRol(rs.getInt(1), rs.getString(2));
		}
	}
	
	private static final class UsuarioAprobadorRowMapper implements RowMapper<UsuarioAprobador> {
		
		@Override
		public UsuarioAprobador mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new UsuarioAprobador(rs.getInt(1), rs.getInt(2));
		}
	}
	
	private static final class UsuarioHardwareRowMapper implements RowMapper<UsuarioHardware> {
		
		@Override
		public UsuarioHardware mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new UsuarioHardware(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7));
		}
	}
	
	private static final class CanalVentaRowMapper implements RowMapper<CanalVenta> {
		
		@Override
		public CanalVenta mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new CanalVenta(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
		}
	}
	
	private static final class AgenciaRowMapper implements RowMapper<Agencia> {
		
		@Override
		public Agencia mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Agencia(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getString(7), rs.getString(8), rs.getInt(9), rs.getInt(10), rs.getString(11), 
					rs.getInt(12), rs.getInt(13), rs.getString(14));
		}
	}
	
	private static final class TipoAgenciaRowMapper implements RowMapper<TipoAgencia> {
		
		@Override
		public TipoAgencia mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new TipoAgencia(rs.getInt(1), rs.getString(2));
		}
	}
	
	private static final class ConcesionarioRowMapper implements RowMapper<Concesionario> {
		
		@Override
		public Concesionario mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Concesionario(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDate(5), rs.getDate(6), rs.getDate(7), rs.getInt(8), rs.getInt(9), rs.getInt(10));
		}
	}
	
	private static final class LocalidadRowMapper implements RowMapper<Localidad> {
		
		@Override
		public Localidad mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Localidad(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5));
		}
	}
	
	private static final class UbigeoRowMapper implements RowMapper<Ubigeo> {
		
		@Override
		public Ubigeo mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Ubigeo(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getString(5));
		}
	}
	
	private static final class NacionalidadRowMapper implements RowMapper<Nacionalidad> {
		
		@Override
		public Nacionalidad mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Nacionalidad(rs.getInt(1), rs.getString(2), rs.getInt(3));
		}
	}
	
	private static final class TipoMonedaRowMapper implements RowMapper<TipoMoneda> {
		
		@Override
		public TipoMoneda mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new TipoMoneda(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
		}
	}
	
	private static final class TipoComprobanteRowMapper implements RowMapper<TipoComprobante> {
		
		@Override
		public TipoComprobante mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new TipoComprobante(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4));
		}
	}
	
	private static final class LiquidacionRowMapper implements RowMapper<Liquidacion> {
		
		@Override
		public Liquidacion mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Liquidacion(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getString(5), rs.getDate(6), rs.getString(7), rs.getInt(8), rs.getInt(9));
		}
	}
}
