package pe.movilbus.intranet.daoImpl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import pe.movilbus.intranet.beans.DataPromocionesCuponesCyber;
import pe.movilbus.intranet.beans.GrupoCupones;
import pe.movilbus.intranet.beans.MaestroCupones;
import pe.movilbus.intranet.beans.PromocionSispas;
import pe.movilbus.intranet.beans.RptPromocion;
import pe.movilbus.intranet.dao.PromocionesDao;
import pe.movilbus.intranet.result.MensajeConfirmacionResult;
import pe.movilbus.intranet.util.Constantes;

@Repository
public class PromocionesDaoImpl implements PromocionesDao{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<PromocionSispas> getPromocionesVentasReservasRuc(int idRuc){
		List<PromocionSispas> lstPromocion = null;
		
		try{
			String sql = " select promocion_id, c_denominacion, c_rutas, c_servicios, c_punven, c_canven, c_pasnue, c_canviapas, c_asientos, c_edapas, c_cliente, c_idavue, "+
					  	 " n_valdes, c_tipdes, n_porimp, c_forpag, c_tippag, c_tarcre, c_entemp, c_paxfre, d_fecini, d_fecfin, c_expresion, c_beneficio, n_esacumulable, n_estarifa, "+
					  	 " c_horpar, c_tiptar, c_tipasi, grupo_cupones from vrmpromocion where (c_cliente like '%,"+idRuc+"%' or c_cliente like '%"+idRuc+",%' or c_cliente='"+idRuc+"') "+
					  	 " and (c_canven like '%1%' or c_canven like '%3%' or c_canven like '%*%') and d_fecini<=to_date(sysdate) and d_fecfin>=to_date(sysdate) "+
					  	 " and c_estreg='A' and ROWNUM = 1 order by promocion_id ";
			
			System.out.println(sql);
			lstPromocion = jdbcTemplate.query(sql, new PromocionSispasRowMapper());
			
			return lstPromocion;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return lstPromocion;
	}
	
	@Override
	public List<RptPromocion> getPromocionesSispas(int itinerarioIda, int rutaIda, int idServicioIda, String fechaRutaIda, int itinerarioVuelta, int rutaVuelta, int idServicioVuelta, String fechaRutaVuelta){
		List<RptPromocion> rptPromocion = new ArrayList<RptPromocion>();
		
		try{
			String sql = "";
			
			sql = " select promocion_id, c_denominacion, c_rutas, c_servicios, c_punven, c_canven, c_pasnue, c_canviapas, c_asientos, c_edapas, c_cliente, c_idavue, "+
				  " n_valdes, c_tipdes, n_porimp, c_forpag, c_tippag, c_tarcre, c_entemp, c_paxfre, d_fecini, d_fecfin, c_expresion, c_beneficio, n_esacumulable, n_estarifa, "+
				  " c_horpar, c_tiptar, c_tipasi, grupo_cupones from vrmpromocion "+
				  " where (c_rutas like '%,"+rutaIda+"%' or c_rutas like '%"+rutaIda+",%' or c_rutas='"+rutaIda+"' or c_rutas like '%*%') "+
				  " and (c_servicios like '%,"+idServicioIda+"%' or c_servicios like '%"+idServicioIda+",%' or c_servicios='"+idServicioIda+"' or c_servicios like '%*%') "+
				  " and d_fecini<=to_date('"+fechaRutaIda+"','yyyy-mm-dd') and d_fecfin>=to_date('"+fechaRutaIda+"','yyyy-mm-dd') and (c_canven like '%1%' or c_canven like '%3%' or c_canven like '%*%') and c_cliente='*' "+
				  " and c_estreg='A' and tipo_sistema in (3,4) order by c_denominacion asc";
			
			List<PromocionSispas> promocionSispas1 = jdbcTemplate.query(sql, new PromocionSispasRowMapper());
			
			if(promocionSispas1.size() > 0){
				for(int a=0; a<promocionSispas1.size(); a++){
					if(promocionSispas1.get(a).getGrupoCupones() != 0){
						int cantidadPasajesComprados = buscarCantidadPasajesComprados(promocionSispas1.get(a).getId().intValue(), itinerarioIda);
						int stockPorBus = buscarStockPorBus(promocionSispas1.get(a).getId().intValue());
						
						int stockDisponible = 0;
						if(cantidadPasajesComprados >= stockPorBus){
							stockDisponible = stockPorBus;
						}else{
							stockDisponible = stockPorBus - cantidadPasajesComprados;
						}
						
						RptPromocion subRptPromocion = new RptPromocion();
						subRptPromocion.setIdaVuelta("IDA");
						subRptPromocion.setPromocion_id(new BigDecimal(promocionSispas1.get(a).getId()));
						subRptPromocion.setC_denominacion(promocionSispas1.get(a).getDenominacion());
						subRptPromocion.setC_tarcre(promocionSispas1.get(a).getTarjetaCredito());
						subRptPromocion.setN_valdes(promocionSispas1.get(a).getValorDescuento());
						subRptPromocion.setStock(stockDisponible);
						subRptPromocion.setC_tipdes(promocionSispas1.get(a).getTipoDescuento());
						rptPromocion.add(subRptPromocion);
					}else{
						RptPromocion subRptPromocion = new RptPromocion();
						subRptPromocion.setIdaVuelta("IDA");
						subRptPromocion.setPromocion_id(new BigDecimal(promocionSispas1.get(a).getId()));
						subRptPromocion.setC_denominacion(promocionSispas1.get(a).getDenominacion());
						subRptPromocion.setC_tarcre(promocionSispas1.get(a).getTarjetaCredito());
						subRptPromocion.setN_valdes(promocionSispas1.get(a).getValorDescuento());
						subRptPromocion.setStock(99);
						subRptPromocion.setC_tipdes(promocionSispas1.get(a).getTipoDescuento());
						rptPromocion.add(subRptPromocion);
					}
				}
			}
			
			if(itinerarioVuelta != 0){
				sql = " select promocion_id, c_denominacion, c_rutas, c_servicios, c_punven, c_canven, c_pasnue, c_canviapas, c_asientos, c_edapas, c_cliente, c_idavue, "+
						  " n_valdes, c_tipdes, n_porimp, c_forpag, c_tippag, c_tarcre, c_entemp, c_paxfre, d_fecini, d_fecfin, c_expresion, c_beneficio, n_esacumulable, n_estarifa, "+
						  " c_horpar, c_tiptar, c_tipasi, grupo_cupones from vrmpromocion "+
						  " where (c_rutas like '%,"+rutaVuelta+"%' or c_rutas like '%"+rutaVuelta+",%' or c_rutas='"+rutaVuelta+"' or c_rutas like '%*%') "+
						  " and (c_servicios like '%,"+idServicioVuelta+"%' or c_servicios like '%"+idServicioVuelta+",%' or c_servicios='"+idServicioVuelta+"' or c_servicios like '%*%') "+
						  " and d_fecini<=to_date('"+fechaRutaVuelta+"','yyyy-mm-dd') and d_fecfin>=to_date('"+fechaRutaVuelta+"','yyyy-mm-dd') and (c_canven like '%1%' or c_canven like '%3%' or c_canven like '%*%') and c_cliente='*' "+
						  " and c_estreg='A' and tipo_sistema in (3,4) order by c_denominacion asc";
				
				List<PromocionSispas> promocionSispas2 = jdbcTemplate.query(sql, new PromocionSispasRowMapper());
				
				if(promocionSispas2.size() > 0){
					for(int a=0; a<promocionSispas2.size(); a++){
						if(promocionSispas2.get(a).getGrupoCupones() != 0){
							int cantidadPasajesComprados = buscarCantidadPasajesComprados(promocionSispas2.get(a).getId().intValue(), itinerarioVuelta);
							int stockPorBus = buscarStockPorBus(promocionSispas2.get(a).getId().intValue());
							
							int stockDisponible = 0;
							if(cantidadPasajesComprados >= stockPorBus){
								stockDisponible = stockPorBus;
							}else{
								stockDisponible = stockPorBus - cantidadPasajesComprados;
							}
							
							RptPromocion subRptPromocion = new RptPromocion();
							subRptPromocion.setIdaVuelta("VUELTA");
							subRptPromocion.setPromocion_id(new BigDecimal(promocionSispas2.get(a).getId()));
							subRptPromocion.setC_denominacion(promocionSispas2.get(a).getDenominacion());
							subRptPromocion.setC_tarcre(promocionSispas2.get(a).getTarjetaCredito());
							subRptPromocion.setN_valdes(promocionSispas2.get(a).getValorDescuento());
							subRptPromocion.setStock(stockDisponible);
							subRptPromocion.setC_tipdes(promocionSispas2.get(a).getTipoDescuento());
							rptPromocion.add(subRptPromocion);
						}else{
							RptPromocion subRptPromocion = new RptPromocion();
							subRptPromocion.setIdaVuelta("VUELTA");
							subRptPromocion.setPromocion_id(new BigDecimal(promocionSispas2.get(a).getId()));
							subRptPromocion.setC_denominacion(promocionSispas2.get(a).getDenominacion());
							subRptPromocion.setC_tarcre(promocionSispas2.get(a).getTarjetaCredito());
							subRptPromocion.setN_valdes(promocionSispas2.get(a).getValorDescuento());
							subRptPromocion.setStock(99);
							subRptPromocion.setC_tipdes(promocionSispas2.get(a).getTipoDescuento());
							rptPromocion.add(subRptPromocion);
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return rptPromocion;
	}
	
	@Override
	public List<PromocionSispas> getPromocionesVentasReservas(int canalVenta){
		List<PromocionSispas> lstPromocion = null;
		
		try{
			String sql = " select promocion_id, c_denominacion, c_rutas, c_servicios, c_punven, c_canven, c_pasnue, c_canviapas, c_asientos, c_edapas, c_cliente, c_idavue, c_forpag, c_tippag, c_tarcre, "+
		                 " c_entemp, c_paxfre, n_valdes, c_tipdes, n_porimp, d_fecini, d_fecfin, c_expresion, c_beneficio, n_esacumulable, n_estarifa, c_horpar, c_tiptar, c_tipasi, grupo_cupones "+
						 " from vrmpromocion where c_denominacion like '%GESTIÓN%' and (c_canven like '%"+canalVenta+"%' or c_canven like '%*%') and d_fecini<=to_date(sysdate) and d_fecfin>=to_date(sysdate) and c_estreg='A' order by promocion_id";
			
			lstPromocion = jdbcTemplate.query(sql, new PromocionSispasRowMapper());
			
			return lstPromocion;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return lstPromocion;
	}
	
	@Override
	public List<MaestroCupones> getPromocionesCupones(){
		List<MaestroCupones> listaCupones = jdbcTemplate.query(" SELECT VCUP.CUPONES_ID, VCUP.NOMBRE, VCUP.PORCENTAJE_DESC, VCUP.PRECIOFINAL_DESC, VCUP.USOSRESTANTES, VGRUP.STOCK, VCUP.SERVICIOS, VCUP.RUTAS_PROHIBIDAS, VCUP.RUTAS_ACEPTADAS, VCUP.HORA_PARTIDA, "+
				" VCUP.FECHA_INICIO, VCUP.FECHA_FIN, VCUP.COMPRA_INICIO, VCUP.COMPRA_FIN, VCUP.ESTADO, VCUP.GRUPO_CUPONES, VGRUP.NOMBRE NOMBRE_GRUPO, VCUP.TIPO_PROMOCION, VCUP.TIPO_SISTEMA FROM VRMPROMOCIONES VCUP "+
				" LEFT JOIN VRMGRUPOCUPONES VGRUP ON VGRUP.GRUPOCUPONES_ID = VCUP.GRUPO_CUPONES "+
			    " WHERE VCUP.C_ESTREG = 'A' ORDER BY VCUP.CUPONES_ID DESC", new MaestroCuponesRowMapper());
		
		return listaCupones;
	}
	
	@Override
	public List<GrupoCupones> getGrupoCupones(){
		List<GrupoCupones> listaGrupoCupones = jdbcTemplate.query(" select grupocupones_id, nombre, stock from vrmgrupocupones where c_estreg='A' order by grupocupones_id asc", new GrupoCuponesRowMapper());
		
		return listaGrupoCupones;
	}
	
	@Override
	public MensajeConfirmacionResult updateInsertPromocion(DataPromocionesCuponesCyber data){
		//CREATE SEQUENCE  "PASAJES"."SEQ_VRMCUPONES_ID"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 25 NOCACHE  ORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL ;
		String sql = "";
		try{
			LocalDateTime myDateObj = LocalDateTime.now();
			DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			
			Date fechaHoy = formato.parse(myDateObj.format(myFormatObj));
			Date fechaPromocionInicio = formato.parse(data.getFecha_inicio());
			Date fechaPromocionFin = formato.parse(data.getFecha_fin());
			
			int estado = 0;
			if(fechaPromocionInicio.equals(fechaHoy) || fechaPromocionFin.equals(fechaHoy)){
				estado = 1;
			}else if(fechaHoy.after(fechaPromocionInicio) && fechaHoy.before(fechaPromocionFin)){
				estado = 1;
			}else{
				estado = 0;
			}
			
			if(data.getCupones_id() == 0){										//NUEVA PROMOCION
				sql = " select pasajes.SEQ_VRMCUPONES_ID.NEXTVAL from dual";
				BigDecimal cupones_id = jdbcTemplate.queryForObject(sql, BigDecimal.class);
				
				sql = " insert into pasajes.vrmpromociones (cupones_id, nombre, porcentaje_desc, usosrestantes, servicios, rutas_prohibidas, fecha_inicio, fecha_fin, compra_inicio, compra_fin, estado, grupo_cupones, rutas_aceptadas, tipo_promocion, tipo_sistema) "+
					  " values ("+cupones_id+", '"+data.getNombre()+"', "+data.getPorcentaje_desc()+", '"+data.getUsosrestantes()+"', '"+data.getServicios()+"', '"+data.getRutas_prohibidas()+"', '"+data.getFecha_inicio()+"', '"+data.getFecha_fin()+"', '"+data.getCompra_inicio()+"',"+
					  " '"+data.getCompra_fin()+"', "+estado+", "+data.getGrupo_cupones()+", '"+data.getRutas_aceptadas()+"', "+data.getTipo_promocion()+", "+data.getTipo_sistema()+")";
				
				jdbcTemplate.update(sql);
			}else{																//ACTUALIZAR PROMOCION
				sql = " update vrmpromociones set nombre='"+data.getNombre()+"', porcentaje_desc="+data.getPorcentaje_desc()+", usosrestantes='"+data.getUsosrestantes()+"', servicios='"+data.getServicios()+"', rutas_prohibidas='"+data.getRutas_prohibidas()+"', fecha_inicio='"+data.getFecha_inicio()+"', "+
					  " fecha_fin='"+data.getFecha_fin()+"', compra_inicio='"+data.getCompra_inicio()+"', compra_fin='"+data.getCompra_fin()+"', estado="+estado+", grupo_cupones="+data.getGrupo_cupones()+", rutas_aceptadas='"+data.getRutas_aceptadas()+"', tipo_promocion="+data.getTipo_promocion()+", tipo_sistema="+data.getTipo_sistema()+
					  " where cupones_id="+data.getCupones_id();
				
				jdbcTemplate.update(sql);
			}
			
			return new MensajeConfirmacionResult(Constantes.RESULT_TRUE, "Se insertó o actualizó con éxito la promoción: "+data.getCupones_id());
		}catch(Exception e){
			e.printStackTrace();
			
			return new MensajeConfirmacionResult(Constantes.RESULT_FALSE, "Error al insertar o actualizar la promoción: "+data.getCupones_id());
		}
	}
	
	public MensajeConfirmacionResult eliminarPromocion(int cupon_id){
		try{
			String sql = " delete from pasajes.vrmpromociones where cupones_id = "+cupon_id;
			
			jdbcTemplate.update(sql);
			
			return new MensajeConfirmacionResult(Constantes.RESULT_TRUE, "Se eliminó con éxito la promoción: "+cupon_id);
		}catch(Exception e){
			e.printStackTrace();
			
			return new MensajeConfirmacionResult(Constantes.RESULT_FALSE, "Error al eliminar la promoción: "+cupon_id);
		}
	}
	
	public int buscarCantidadPasajesComprados(int idPromocion, int idItinerario){
		int val1 = 0;
		int val2 = 0;
		
		String sql = " select count(*) from vrtvenpas vp "+
			" where vp.itinerario_id="+idItinerario+" AND vp.tipcom_id in (2,7) AND vp.c_tiptra=1 "+                    
				     " and vp.venpas_id = any(SELECT MAX(vrt2.venpas_id) venpas_id FROM "+ 
				                                  " vrtvenpas vrt2 where vrt2.c_numcontrol=vp.c_numcontrol) "+
				    "  and vp.tipmov_id not in (5,6,13,14) "+
					 " and vp.promocion_id in ("+
					 				" select promocion_id from vrmpromocion where grupo_cupones=(select grupo_cupones from vrmpromocion where promocion_id="+idPromocion+")) ";
				
		val1 = jdbcTemplate.queryForObject(sql, Integer.class);
		
		/********************************* SISPAS *********************************/
		
		/********************************* ECOMMERCE *********************************/
		sql = " select count(vp.venpas_id) from vrtvenpas vp "+
			  " where vp.itinerario_id="+idItinerario+" and vp.result_ws_id=3 and vp.name_promocion is not null "+
			  " and vp.name_promocion in (select nombre from vrmpromociones WHERE grupo_cupones=(select grupo_cupones from vrmpromocion where promocion_id="+idPromocion+") group by nombre)";
		//System.out.println(sql);
		val2 = jdbcTemplate.queryForObject(sql, Integer.class);
		/********************************* ECOMMERCE *********************************/
		
		return val1 + val2;
	}
	
	public int buscarStockPorBus(int idPromocion){
		int val = 0;
		
		String sql = " select CASE WHEN vg.stock is null "+
				     " THEN 99 "+
				     " ELSE vg.stock END AS stockMaximo from vrmpromocion vp "+
				     " left join vrmgrupocupones vg on vg.grupocupones_id=vp.grupo_cupones where vp.promocion_id="+idPromocion;
		
		val = jdbcTemplate.queryForObject(sql, Integer.class);
		
		return val;
	}
	
	private final class GrupoCuponesRowMapper implements RowMapper<GrupoCupones> {
		
		@Override
		public GrupoCupones mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new GrupoCupones(rs.getInt(1), rs.getString(2), rs.getInt(3));
		}
	}
	
	private final class PromocionSispasRowMapper implements RowMapper<PromocionSispas> {
		
		@Override
		public PromocionSispas mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new PromocionSispas(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11),
					rs.getString(12), rs.getDouble(13), rs.getString(14), rs.getDouble(15), rs.getString(16), rs.getString(17), rs.getString(18), rs.getString(19), rs.getString(20), rs.getDate(21), rs.getDate(22), rs.getString(23), 
					rs.getString(24), rs.getInt(25), rs.getInt(26), rs.getString(27), rs.getString(28), rs.getString(29), rs.getInt(30));
		}
	}
	
	private static final class MaestroCuponesRowMapper implements RowMapper<MaestroCupones> {

		@Override
		public MaestroCupones mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new MaestroCupones(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getDouble(4), rs.getInt(5), rs.getInt(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), 
					rs.getString(13), rs.getString(14), rs.getInt(15), rs.getInt(16), rs.getString(17), rs.getInt(18), rs.getInt(19));
		}
	}
}
