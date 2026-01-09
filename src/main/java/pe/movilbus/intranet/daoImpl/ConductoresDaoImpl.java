package pe.movilbus.intranet.daoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import pe.movilbus.intranet.beans.Bus;
import pe.movilbus.intranet.beans.FormularioReten;
import pe.movilbus.intranet.beans.Personal;
import pe.movilbus.intranet.dao.ConductoresDao;
import pe.movilbus.intranet.result.MensajeResult;
import pe.movilbus.intranet.util.Constantes;

@Repository
public class ConductoresDaoImpl implements ConductoresDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<Personal> getPilotos(){
		List<Personal> lstPilotos = new ArrayList<Personal>();
		
		try{
			String sql = "select * from vrmpersonal where tipper_id=3 and c_estreg='A' order by c_apepat asc";
			
			lstPilotos = jdbcTemplate.query(sql, new PersonalRowMapper());
			
			return lstPilotos;
		}catch(Exception e){
			//e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<FormularioReten> getReporteFormularioReten(String fecha_inicio, String fecha_fin){		
		try{
			String sql = " select vreten.fecha_partida, vreten.id_conductor, vper.c_apepat || ' ' || vper.c_apemat || ', ' || vper.c_nombre as nombreConductor, vreten.tipo_conductor, vreten.unidad, vreten.placa, vreten.servicio, vreten.tipo, vreten.observaciones  from VRTFORMULARIORETEN vreten "+
						 " inner join vrmpersonal vper on vper.personal_id = vreten.id_conductor WHERE vreten.fecha_partida BETWEEN TO_DATE(?, 'YYYY-MM-DD') AND TO_DATE(?, 'YYYY-MM-DD')";

		    return jdbcTemplate.query(sql, new Object[]{fecha_inicio, fecha_fin}, new FormularioRetenRowMapper());
		}catch(Exception e){
			//e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public Bus getDatosBus(String unidad){		
		try{
			String sql = "select bus.bus_id, bus.c_codigo, bus.c_numplaca, bus.n_capacidad, vser.c_nomcor  from vrmbus bus left join vrmservicio vser on vser.servicio_id=bus.servicio_id where c_codigo=?";
			
			return jdbcTemplate.queryForObject(sql, new BusRowMapper(), unidad);
		}catch(EmptyResultDataAccessException e){
			//e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public MensajeResult insertFormularioReten(FormularioReten data){
		try{
			String sql_insert = " INSERT INTO pasajes.VRTFORMULARIORETEN (FECHA_PARTIDA, ID_CONDUCTOR, TIPO_CONDUCTOR, UNIDAD, PLACA, SERVICIO, TIPO, OBSERVACIONES) "+
						 " VALUES ('"+data.getFecha_partida()+"', '"+data.getIdConductor()+"', '"+data.getTipo_conductor()+"', '"+data.getUnidad()+"', '"+data.getPlaca()+"', '"+data.getServicio()+"', '"+data.getTipo()+"', '"+data.getObservaciones()+"')";
			
			jdbcTemplate.update(sql_insert);
			
			return new MensajeResult(Constantes.RESULT_TRUE, "");
		}catch(Exception e){
			e.printStackTrace();
			return new MensajeResult(Constantes.RESULT_FALSE, "");
		}
	}
	
	private final class FormularioRetenRowMapper implements RowMapper<FormularioReten>{
		
		@Override
		public FormularioReten mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new FormularioReten(rs.getString(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9));
		}
	}
	
	private final class PersonalRowMapper implements RowMapper<Personal>{
		
		@Override
		public Personal mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Personal(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getInt(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getDate(9), rs.getString(10), rs.getInt(11), rs.getInt(12), rs.getInt(13), 
					rs.getString(14), rs.getString(15), rs.getInt(16), rs.getString(17), rs.getString(18), rs.getInt(19), rs.getString(20), rs.getInt(21), rs.getString(22), rs.getString(23), rs.getString(24));
		}
	}
	
	private final class BusRowMapper implements RowMapper<Bus>{
		
		@Override
		public Bus mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Bus(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5));
		}
	}
}