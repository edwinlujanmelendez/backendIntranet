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
import pe.movilbus.intranet.beans.Personal;
import pe.movilbus.intranet.dao.ConductoresDao;

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
			e.printStackTrace();
		}
		
		return null;
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