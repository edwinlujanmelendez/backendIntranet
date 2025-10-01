package pe.movilbus.intranet.daoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import pe.movilbus.intranet.beans.AsientoOcupado;
import pe.movilbus.intranet.beans.BloquearAsiento;
import pe.movilbus.intranet.beans.Ruta;
import pe.movilbus.intranet.beans.SecuenciaTramo;
import pe.movilbus.intranet.beans.Error;
import pe.movilbus.intranet.dao.AsientosDao;
import pe.movilbus.intranet.result.GenericoResult;
import pe.movilbus.intranet.util.Constantes;

@Repository
public class AsientosDaoImpl implements AsientosDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public GenericoResult SetBloquearAsiento(BloquearAsiento bloq) {
				
		try {
			
			// Validar que el itinerario este disponible
			String sql = " SELECT  c_sectra FROM pasajes.VRTITINERARIO "+
						  " WHERE ITINERARIO_ID="+bloq.getItinerarioId()+
						    " AND N_ESANULADO="+Constantes.ACTIVO_ITINERARIO;
			List<String> ItiActivoTramo = jdbcTemplate.query(sql, new ListTramosRowMapper());
			
			if ( ItiActivoTramo.size() > 0 ) {
				// si la ruta seleccionado esta en dos horas antes puede bloquearlo o sino no se vende
				sql="SELECT COUNT(1) "+
					 " FROM pasajes.VRTDETITI di "+
					" WHERE di.itinerario_id="+bloq.getItinerarioId()+
					  " AND di.ruta_id="+bloq.getRutaId()+
					  " AND (to_date(to_char(DI.D_FECPAR,'dd/mm/yyyy')||' '||trim(di.C_HORPAR),'dd/mm/yyyy hh24:mi:ss') >= sysdate+1/12) "+ 
					  " AND di.c_estreg='"+Constantes.ACTIVO+"'"+
					  " AND di.N_ESTADO="+Constantes.ACTIVO_DETITI_RUTA;
					  
				if ( jdbcTemplate.queryForObject(sql, Integer.class) > 0 ) {
					 // Validando que el asiento no esta utilizado
					// Listado de bloqueos y Ventas
					
					int n_cantidad = bloq.getAsiento().length;
					
					for(int i=0; i<n_cantidad; i++){
						sql = " select to_char(NVL(tmp.n_asiento,0),'99') asiento, tmp.n_numpiso piso, "+ 
								   "      r.LOCALIDAD_IDORIGEN, r.LOCALIDAD_IDDESTINO    "+     
								   " 		    FROM pasajes.VRTTMPOCUASI TMP     "+    				   		  
								   " 	    inner join pasajes.vrmruta r on r.ruta_id=tmp.ruta_id   "+      
								   " 	    where tmp.itinerario_id = "+bloq.getItinerarioId()+
								   			" and tmp.ruta_id = "+bloq.getRutaId()+
								   			" and tmp.N_ASIENTO ="+bloq.getAsiento()[i]+
								   			" and tmp.N_NUMPISO ="+bloq.getPiso()[i]+
								   " union all "+ 
								   " select to_char(nvl(vp.n_numasiento,0),'99') asiento, vp.n_numpiso piso, "+ 
								   "     r.LOCALIDAD_IDORIGEN, r.LOCALIDAD_IDDESTINO "+ 
								   "  FROM pasajes.VRTVENPAS VP    				 "+ 
								   "   INNER JOIN ( SELECT MAX(VENPAS_ID) VENPAS_ID,C_NUMCONTROL  FROM pasajes.VRTVENPAS    "+      				  
								   "   WHERE itinerario_id = "+bloq.getItinerarioId()+" GROUP BY C_NUMCONTROL) VENTAPJ ON VENTAPJ.VENPAS_ID=VP.VENPAS_ID    "+  
								   "  inner join pasajes.vrmruta r on r.ruta_id=vp.ruta_id      "+ 
								   "   WHERE VP.ITINERARIO_ID = "+bloq.getItinerarioId()+
								   	   " AND VP.TIPMOV_ID NOT IN ("+Constantes.ID_TIPMOV_ANULACION_SISTEMA+","+
											   	+Constantes.ID_TIPMOV_DEVOLUCION+","+
											   	 Constantes.ID_TIPMOV_ANULACION+","+Constantes.ID_TIPMOV_DEV_EMPRESA+") "+
									   " AND VP.N_NUMASIENTO = "+bloq.getAsiento()[i]+" AND VP.N_NUMPISO = "+bloq.getPiso()[i];
						
						
						
						List<AsientoOcupado> lstAsientoOcupado = jdbcTemplate.query(sql, new AsientoOcupadoRowMapper());
						
						if ( lstAsientoOcupado.size() > 0 ) {
								
								sql="SELECT r.localidad_idorigen, localidad_iddestino FROM pasajes.VRMRUTA r "+
									" WHERE r.ruta_id="+bloq.getRutaId();
								List<Ruta> lstRuta = jdbcTemplate.query(sql, new RutaRowMapper());
								
								if(lstRuta.size() > 0) {
									List<SecuenciaTramo> listaTramo = obtenerSecuencia(ItiActivoTramo.get(0));
									// Obtenemos el subconjunto que queremos buscar segun la ruta seleccionada				
									List<Integer> subConjuntoBuscar = obtenerSubconjunto(listaTramo, lstRuta.get(0).getIdLocOrigen(),lstRuta.get(0).getIdLocDestino());						
									lstAsientoOcupado = obtenerConjuntos(lstAsientoOcupado, listaTramo);
									
									if (validacionAsientoBloqueado(bloq.getAsiento()[i]+"-"+bloq.getPiso()[i], lstAsientoOcupado,subConjuntoBuscar)){ 
										return new GenericoResult(new Error("AS01","EL ASIENTO NO ESTA DISPONIBLE."));
									}
								} else return new GenericoResult(new Error("AS02","LA RUTA SELECCIONADA NO EXISTE."));
						}
					}
					
					int resultado = 0;
					
					for(int i=0; i<n_cantidad; i++){
						sql = " INSERT INTO pasajes.VRTTMPOCUASI "
								+ " (ITINERARIO_ID, RUTA_ID, USUHARD_ID, USUARIO_ID, N_ASIENTO, N_NUMPISO, C_FECPAR, C_HORPAR,"
								+ " C_ESTREG, D_FECEXPBLO) " +
							" VALUES ("+bloq.getItinerarioId()+","+
										bloq.getRutaId()+","+
										bloq.getIdHardware()+","+
										bloq.getIdUsuario()+","+
										bloq.getAsiento()[i]+","+
										bloq.getPiso()[i]+","+
										"'"+bloq.getFechaPartida()+"',"+
										"'"+bloq.getHoraPartida()+"',"+
										"'"+Constantes.ACTIVO+"',"+
										" sysdate + (1/1440)*"+bloq.getTiempoBloqueo()+" )";
					
						resultado = jdbcTemplate.update(sql);
					}
					
					if (resultado != 0 )						
						return  new GenericoResult( Constantes.RESULT_TRUE, null);						
					else return  new GenericoResult( new Error("AS03","No se pudo registrar el asiento del bloqueo."));
				
				}else return new GenericoResult(new Error("IT02","NO SE PUEDE REALIZAR ALGUNA VENTA DE LA RUTA SELECCIONADO SE BLOQUEO."));
				
			} else return new GenericoResult(new Error("IT01","EL ITINERARIO NO ESTA DISPONIBLE"));
				
		}catch (Exception e) {
			// TODO: handle exception			
			return new GenericoResult(new Error("EX01","LLAMAR A SISTEMAS"));
		}
	}
	
	
	
	@Override
	public GenericoResult eliminarLiberarAsiento(BloquearAsiento bloq) {
		
		int n_cantidad = bloq.getAsiento().length;
		
		try {
			int resultado = 0;
			for(int i=0; i<n_cantidad; i++){
				String sql = " delete from pasajes.VRTTMPOCUASI TMP     "+    				      
						   	" 	    where tmp.itinerario_id = "+bloq.getItinerarioId()+
						   			" and tmp.ruta_id = "+bloq.getRutaId()+
						   			" and tmp.N_ASIENTO ="+bloq.getAsiento()[i]+
						   			" and tmp.N_NUMPISO ="+bloq.getPiso()[i]+
						   			" AND tmp.usuario_id="+bloq.getIdUsuario()+
						   			" AND tmp.usuhard_id="+bloq.getIdHardware();
				
				resultado = jdbcTemplate.update(sql);
			}
			if (resultado != 0 )
				return  new GenericoResult( Constantes.RESULT_TRUE, null);
			else return  new GenericoResult( new Error("AS03","No se encontró algun registro para eliminar."));
		}catch (Exception e) {
			return new GenericoResult(new Error("EX01","LLAMAR A SISTEMAS"));
		}
	}
	
	
	@Override
	public GenericoResult actualizarLiberarAsiento(BloquearAsiento bloq) {
		
		int n_cantidad = bloq.getAsiento().length;
		
		try {
			int resultado = 0;
			for(int i=0; i<n_cantidad; i++){
				String sql = " UPDATE pasajes.VRTTMPOCUASI TMP  set  tmp.D_FECEXPBLO = sysdate + (1/1440)*"+bloq.getTiempoBloqueo()+
									" , tmp.N_TARIFA= "+bloq.getTarifa()+
						   	" 	    where tmp.itinerario_id = "+bloq.getItinerarioId()+
						   			" and tmp.ruta_id = "+bloq.getRutaId()+					   							   			
						   			" and tmp.N_ASIENTO ="+bloq.getAsiento()[i]+
						   			" and tmp.N_NUMPISO ="+bloq.getPiso()[i]+
						   			" AND tmp.usuario_id="+bloq.getIdUsuario()+	
						   			" AND tmp.usuhard_id="+bloq.getIdHardware();
				
				resultado = jdbcTemplate.update(sql);
			}
			
			if (resultado != 0 )
				return  new GenericoResult( Constantes.RESULT_TRUE, null);
			else return  new GenericoResult( new Error("AS03","No se encontró algun registro para actualizar."));
			
		}catch (Exception e) {
			return new GenericoResult(new Error("EX01","LLAMAR A SISTEMAS"));
		}
	}
	
	private List<SecuenciaTramo> obtenerSecuencia(String secuencia){
		String[] sArray = secuencia.split(";");
		List<SecuenciaTramo> lstResult = new ArrayList<SecuenciaTramo>();
		for(String obj : sArray){
			SecuenciaTramo secuenciaTramo = new SecuenciaTramo();
			String[] buffer = obj.split("-");
			secuenciaTramo.setOrigen(Integer.valueOf(buffer[0]));
			secuenciaTramo.setDestino(Integer.valueOf(buffer[1]));
			secuenciaTramo.setOrden(Integer.valueOf(buffer[2]));
			lstResult.add(secuenciaTramo);
		}
		return lstResult;
	}
	
	public List<Integer> obtenerSubconjunto(List<SecuenciaTramo> lstSecuencias, int idOrigen, int idDestino){
		List<Integer> lstSubconjunto = new ArrayList<Integer>();
		//	Recorremos la secuencia de tramos del itinerario
		for(int j=0; j<lstSecuencias.size(); j++){
			SecuenciaTramo secuencia = lstSecuencias.get(j);
			//	Validamos si el origen de la secuencia coincide con el origen de la ruta	
			if(secuencia.getOrigen().intValue()==idOrigen){
				//	Recorremos la secuencia de tramos desde la posicion j	
				for(int k=j; k<lstSecuencias.size(); k++){
					secuencia = lstSecuencias.get(k);
					lstSubconjunto.add(secuencia.getOrden());
					//	Validamos si el destino de la secuencia coincide con el destino de la ruta	
					if(secuencia.getDestino().intValue()==idDestino)
						break;
				}
				break;
			}
		}
		return lstSubconjunto;
	}
	
	public boolean validacionAsientoBloqueado(String claveAsiento,List<AsientoOcupado> listado,List<Integer> subConjunto){
		boolean resultado = false;
		
		try {
			if (listado.size() > 0) {
				for (AsientoOcupado asientoOcupado : listado) {
					for(Integer orden : subConjunto){	
							if(claveAsiento.equals(asientoOcupado.getAsiento() + "-" + asientoOcupado.getPiso())
									&& asientoOcupado.getSubConjunto().contains(orden) ) { 											
								resultado = true;
								break;
							}
					}
				}
			}
		}catch (Exception e) {
			resultado = false;
		}
		
		return resultado;
	}
	
	public List<AsientoOcupado> obtenerConjuntos(List<AsientoOcupado> lista, List<SecuenciaTramo> lstSecuencias){
		for(AsientoOcupado obj : lista){
			
			List<Integer> subConjunto = obtenerSubconjunto(lstSecuencias, obj.getIdorigen().intValue() , obj.getIddestino().intValue() );
			obj.setSubConjunto(subConjunto);								
			
		}
		
		return lista;
	}
	
	private static final class ListTramosRowMapper implements RowMapper<String> {
		
		@Override
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {			
			return rs.getString(1);
		}
	}
	
	private static final class AsientoOcupadoRowMapper implements RowMapper<AsientoOcupado> {
		
		@Override
		public AsientoOcupado mapRow(ResultSet rs, int rowNum) throws SQLException {	
			// int estadoAsiento, int bloqueado
			return new AsientoOcupado(rs.getInt(1),rs.getInt(2), rs.getInt(3),rs.getInt(4));
		}
	}
	
	private static final class RutaRowMapper implements RowMapper<Ruta> {
		
		@Override
		public Ruta mapRow(ResultSet rs, int rowNum) throws SQLException {	
			// int estadoAsiento, int bloqueado
			return new Ruta(rs.getInt(1),rs.getInt(2));
		}
	}
	
}
