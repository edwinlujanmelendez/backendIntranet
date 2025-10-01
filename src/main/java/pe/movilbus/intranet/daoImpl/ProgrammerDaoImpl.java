package pe.movilbus.intranet.daoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import pe.movilbus.intranet.dao.ProgrammerDao;
import pe.movilbus.intranet.util.Constantes;

@Repository
public class ProgrammerDaoImpl implements ProgrammerDao{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<Integer> cantidadPasajesPorCanjear(){
		List<Integer> cantidad = null;
		
		try{
			String sql = " select count(*) from ( "+
	                     " select vp.nro_operation_niubiz nro_operation, vp.venpas_id id "+
	                     " from vrtvenpas vp "+
	                     " where vp.tipmov_id="+Constantes.ID_TIPMOV_CREDITO+
	                     " and vp.usuario_id="+Constantes.ID_USUARIO_MOVIL_WEB+
	                     " and vp.RESULT_WS_ID in ("+Constantes.id_pago_autorizado+","+Constantes.id_pago_liquidado+" ) "+
	                     " and vp.c_numbolant is null "+
	                     " and vp.nro_operation_niubiz is not null "+
	                     " and vp.N_ESTADO_FE="+Constantes.ESTADO_FE_NO_REGISTRADO+
	                     " and vp.audfecins >= trunc(sysdate-1) "+
	                     " and vp.parentesco_id<>4 and vp.n_tipo_pasajero<>3 "+
	                     " order by vp.nro_operation_niubiz asc "+
	                     " ) resumen1 "+
	                     " union all "+
	                     " select count(*)  from ( "+
	                     " select vp.nro_operation_niubiz nro_operation "+
	                     " from vrtvenpas vp "+
	                     " where vp.usuario_id=1133 "+
	                     " and vp.RESULT_WS_ID in (3,7) "+
	                     " and vp.nro_operation_niubiz is not null "+
	                     " and vp.N_ESTADO_FE=1 "+
	                     " and vp.correo_enviado=0 "+
	                     " and vp.audfecins >= trunc(sysdate-1) "+
	                     " order by vp.nro_operation_niubiz asc "+
	                     " ) resumen2 "+
	                     " union all "+
	                     " select *  from ( "+
	                     " select COUNT(*) from v$session where status='ACTIVE' "+
	                     " GROUP BY STATUS "+
	                     " ) resumen3 "+
	                     " union all "+
	                     " select *  from ( "+
	                     " select COUNT(*) from v$session where status='INACTIVE' "+
	                     " GROUP BY STATUS "+
	                     " ) resumen4 ";
			
			cantidad = jdbcTemplate.query(sql, new ListIntegerRowMapper());
			
			return cantidad;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return cantidad;
	}
	
	private static final class ListIntegerRowMapper implements RowMapper<Integer> {
		
		@Override
		public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {			
			return rs.getInt(1);
		}
	}

}
