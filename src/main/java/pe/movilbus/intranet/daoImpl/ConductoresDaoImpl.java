package pe.movilbus.intranet.daoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import pe.movilbus.intranet.beans.Bus;
import pe.movilbus.intranet.beans.FormularioReten;
import pe.movilbus.intranet.beans.MantenimientoRuta;
import pe.movilbus.intranet.beans.Personal;
import pe.movilbus.intranet.beans.ReporteTareoConductor;
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
			String sql = " select vreten.fecha_partida, vreten.id_conductor, vper.c_apepat || ' ' || vper.c_apemat || ', ' || vper.c_nombre as nombreConductor, vreten.agencia_id, vage.c_denominacion, vreten.tipo_conductor, vreten.unidad, vreten.placa, vreten.servicio, vreten.tipo, vreten.observaciones "+ 
						 " from VRTFORMULARIORETEN vreten "+
						 " inner join vrmpersonal vper on vper.personal_id = vreten.id_conductor "+
						 " inner join vrmagencia vage on vage.agencia_id = vreten.agencia_id WHERE vreten.fecha_partida BETWEEN TO_DATE(?, 'YYYY-MM-DD') AND TO_DATE(?, 'YYYY-MM-DD')";
		
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
			String sql_insert = " INSERT INTO pasajes.VRTFORMULARIORETEN (FECHA_PARTIDA, ID_CONDUCTOR, AGENCIA_ID, TIPO_CONDUCTOR, UNIDAD, PLACA, SERVICIO, TIPO, OBSERVACIONES) "+
						 " VALUES ('"+data.getFecha_partida()+"', '"+data.getId_conductor()+"', '"+data.getAgencia_id()+"', '"+data.getTipo_conductor()+"', '"+data.getUnidad()+"', '"+data.getPlaca()+"', '"+data.getServicio()+"', '"+data.getTipo()+"', '"+data.getObservaciones()+"')";
			
			jdbcTemplate.update(sql_insert);
			
			return new MensajeResult(Constantes.RESULT_TRUE, "");
		}catch(Exception e){
			e.printStackTrace();
			return new MensajeResult(Constantes.RESULT_FALSE, "");
		}
	}
	
	@Override
	public List<MantenimientoRuta> getReporteMantenimientoRuta(int localidadOrigen, int localidadDestino){
		List<MantenimientoRuta> lstMantenimientoRuta = new ArrayList<MantenimientoRuta>();
		
		String query_localidadOrigen = "";
		String query_localidadDestino = "";
		
		if(localidadOrigen != 0){
			query_localidadOrigen = " and localidad_idorigen = "+localidadOrigen;
		}
		
		if(localidadDestino != 0){
			query_localidadDestino = " and localidad_iddestino = "+localidadDestino;
		}
		
		try{
			String sql = " select ruta_id, c_origen, c_destino, n_kilometros, n_horvia, precio_base, precio_economico, precio_ejecutivo, precio_presidencial, precio_premier from vrmruta where c_estreg='A' "+
						 query_localidadOrigen +
						 query_localidadDestino +
						 " order by c_origen, c_destino";
			
			lstMantenimientoRuta = jdbcTemplate.query(sql, new MantenimientoRutaRowMapper());
			
			return lstMantenimientoRuta;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<ReporteTareoConductor> getReporteTareoConductor(String fechaInicio, String fechaFin, int conductor_id){
		List<ReporteTareoConductor> lstReporteTareoConductor = new ArrayList<ReporteTareoConductor>();
		
		String query_conductor_id = "";
		String query_conductor_id_temporal_piloto = "";
		String query_conductor_id_temporal_copiloto = "";
		
		if(conductor_id != 0){
			query_conductor_id = " and PE.personal_id = "+conductor_id;
			query_conductor_id_temporal_piloto = " INNER JOIN ( select (vp.c_apepat || ' ' || vp.c_apemat || ' ' || vp.c_nombre) nombreConductor from VRMPERSONAL vp where vp.personal_id = " + conductor_id +" ) vconsult ON NVL(vs.PILOTO, '') = vconsult.nombreConductor ";
			query_conductor_id_temporal_copiloto = " INNER JOIN ( select (vp.c_apepat || ' ' || vp.c_apemat || ' ' || vp.c_nombre) nombreConductor from VRMPERSONAL vp where vp.personal_id = " + conductor_id +" ) vconsult ON NVL('', vs.COPILOTO) = vconsult.nombreConductor ";
		}
		
		try{
			String sql         = ""; // sentencia sql principal.
			String sqltemporal = "";
			String sqlventas   = "";
					
			// Fecha Actual:: 
			// Es para validar y consultar ventas de igual o mayor de la fecha actual
			//Date fechaActual   = Constantes.FORMAT_DATE.parse(new MyTime().dateServer());
			Date fechaActual = java.sql.Date.valueOf(java.time.LocalDate.now());
			
			LocalDate ld1 = LocalDate.parse(fechaInicio);
			String fechaInicioBarra = ld1.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			
			LocalDate ld2 = LocalDate.parse(fechaFin);
			String fechaFinBarra = ld2.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			
			// Los parametros de la Fecha_Inicio y Fecha_Fin.
			Date D_fechaInicio = Constantes.FORMAT_DATE.parse(fechaInicioBarra);
			Date D_fechafin    = Constantes.FORMAT_DATE.parse(fechaFinBarra);
			
			// Fecha Actual y Fecha Anterior del Actual. (Formato String)
			String FechaActual        ="";
			String AnteriorFechaActual="";
			
			if( D_fechafin.after(fechaActual)){
				if(D_fechaInicio.after(fechaActual)){
					// El Rango de la Fecha es Mayor a la Fecha_Actual
					// ventas :: Fecha_Inicial y Fecha_Fin

					sqlventas =
						// =========================
						// FILA 1: PILOTO
						// =========================
						" select distinct ptmy.EMPRESA, " +
						" ptmy.FECHA_PARTIDA, " +
						" ptmy.TURNO, " +
						" ptmy.ORIGEN, " +
						" ptmy.DESTINO, " +
						" ptmy.SERVICIO, " +
						" ptmy.NRO_BUS, " +
						" ptmy.PLACA_BUS, " +
						" ptmy.TERRAMOZA, " +
						" TO_CHAR(ptmy.NRO_PROGRAMACION) NRO_PROGRAMACION, " +
						//" ptmy.PILOTO as PILOTO, " +
						//" '' as COPILOTO, " +
						" NVL(ptmy.PILOTO, '') as CONDUCTOR, "+
						" ptmy.AUXPILOTO, " +
						" ptmy.ADICIONAL_FIJO, " +
						" TO_CHAR(ptmy.CAPACIDAD_BUS) CAPACIDAD_BUS, " +
						" TO_CHAR(count(1)) T_BOLETO, " +
						" sum(ptmy.T_MONTO) T_MONTO " +
						" from (SELECT  E.C_NOMCOR EMPRESA, " +
						" TO_CHAR(I.D_FECPAR,'DD/MM/YYYY') FECHA_PARTIDA, " +
						" I.C_HORPAR TURNO, " +
						" R.C_ORIGEN ORIGEN, " +
						" R.C_DESTINO DESTINO, " +
						" S.C_NOMCOR AS SERVICIO, " +
						" B.C_CODIGO AS NRO_BUS, " +
						" B.C_NUMPLACA AS PLACA_BUS, " +
						" (TR.C_APEPAT||' '||TR.C_APEMAT||' '||TR.C_NOMBRE) AS TERRAMOZA, " +
						" I.ITINERARIO_ID AS NRO_PROGRAMACION, " +
						" (PE.C_APEPAT||' '||PE.C_APEMAT||' '||PE.C_NOMBRE) AS PILOTO, " +
						" (CO.C_APEPAT||' '||CO.C_APEMAT||' '||CO.C_NOMBRE) AS COPILOTO, " +
						" (CAUX.C_APEPAT||' '||CAUX.C_APEMAT||' '||CAUX.C_NOMBRE) AS AUXPILOTO, " +
						" DECODE(I.C_ADICIONAL,0,'FIJO',1,'ADICIONAL') AS ADICIONAL_FIJO, " +
						" (S.N_NUMASIPIS1 + NVL(S.N_NUMASIPIS2,0)) AS CAPACIDAD_BUS, " +
						" v.N_TARIFA+v.N_RECARGO+v.N_PENALIDAD-v.N_DESCUENTO as T_MONTO " +
						" FROM VRTVENPAS v " +
						" left join ( select max(proser_id) proser_id, itinerario_id from VRTPROSER group by itinerario_id ) PROS0 on PROS0.itinerario_id=v.itinerario_id " +
						" left join VRTPROSER PROS on PROS.proser_id = PROS0.proser_id " +
						" inner join ( select max(venpas_id)venpas_id, c_numcontrol from vrtvenpas group by c_numcontrol ) max_venta on max_venta.venpas_id=v.venpas_id " +
						" inner join vrtitinerario i on i.itinerario_id  = V.itinerario_id " +
						" inner join vrmservicio   s on s.servicio_id    = i.servicio_id " +
						" inner join vrmempresa    e on e.empresa_id     = i.empresa_id " +
						" inner join vrmruta       r on r.ruta_id        = I.RUTA_IDMAYOR " +
						" LEFT JOIN VRMBUS        B  ON B.BUS_ID        = PROS.BUS_ID " +
						" LEFT JOIN  VRMPERSONAL   TR ON TR.PERSONAL_ID  = PROS.personal_idterramoza " +
						" LEFT JOIN VRMPERSONAL   PE ON PE.PERSONAL_ID  = PROS.PERSONAL_IDPILOTO " +
						" LEFT JOIN  VRMPERSONAL   CO ON CO.PERSONAL_ID  = PROS.PERSONAL_IDCOPILOTO " +
						" LEFT JOIN  VRMPERSONAL   CAUX ON CAUX.PERSONAL_ID  = PROS.personal_idcopilotoaux " +
						" where v.tipmov_id not in( 5,6,13,14) " +
						" AND v.c_estreg='A' " +
						" and i.d_fecpar between to_date('"+fechaInicioBarra+"','dd/mm/yyyy') and to_date('"+fechaFinBarra+"','dd/mm/yyyy') " +
						" and v.agencia_id <> 69 "+query_conductor_id+") ptmy " +
						" group by ptmy.EMPRESA, ptmy.FECHA_PARTIDA, ptmy.TURNO, ptmy.ORIGEN, ptmy.DESTINO, ptmy.SERVICIO, ptmy.NRO_BUS, ptmy.PLACA_BUS, ptmy.TERRAMOZA, ptmy.NRO_PROGRAMACION, " +
						" ptmy.PILOTO, ptmy.AUXPILOTO, ptmy.ADICIONAL_FIJO, ptmy.CAPACIDAD_BUS " +

						// =========================
						// FILA 2: COPILOTO
						// =========================
						" UNION ALL " +
						" select distinct ptmy.EMPRESA, " +
						" ptmy.FECHA_PARTIDA, " +
						" ptmy.TURNO, " +
						" ptmy.ORIGEN, " +
						" ptmy.DESTINO, " +
						" ptmy.SERVICIO, " +
						" ptmy.NRO_BUS, " +
						" ptmy.PLACA_BUS, " +
						" ptmy.TERRAMOZA, " +
						" TO_CHAR(ptmy.NRO_PROGRAMACION) NRO_PROGRAMACION, " +
						//" '' as PILOTO, " +
						//" ptmy.COPILOTO as COPILOTO, " +
						" NVL('', ptmy.COPILOTO) as CONDUCTOR, "+
						" ptmy.AUXPILOTO, " +
						" ptmy.ADICIONAL_FIJO, " +
						" TO_CHAR(ptmy.CAPACIDAD_BUS) CAPACIDAD_BUS, " +
						" TO_CHAR(count(1)) T_BOLETO, " +
						" sum(ptmy.T_MONTO) T_MONTO " +
						" from (SELECT  E.C_NOMCOR EMPRESA, " +
						" TO_CHAR(I.D_FECPAR,'DD/MM/YYYY') FECHA_PARTIDA, " +
						" I.C_HORPAR TURNO, " +
						" R.C_ORIGEN ORIGEN, " +
						" R.C_DESTINO DESTINO, " +
						" S.C_NOMCOR AS SERVICIO, " +
						" B.C_CODIGO AS NRO_BUS, " +
						" B.C_NUMPLACA AS PLACA_BUS, " +
						" (TR.C_APEPAT||' '||TR.C_APEMAT||' '||TR.C_NOMBRE) AS TERRAMOZA, " +
						" I.ITINERARIO_ID AS NRO_PROGRAMACION, " +
						" (PE.C_APEPAT||' '||PE.C_APEMAT||' '||PE.C_NOMBRE) AS PILOTO, " +
						" (CO.C_APEPAT||' '||CO.C_APEMAT||' '||CO.C_NOMBRE) AS COPILOTO, " +
						" (CAUX.C_APEPAT||' '||CAUX.C_APEMAT||' '||CAUX.C_NOMBRE) AS AUXPILOTO, " +
						" DECODE(I.C_ADICIONAL,0,'FIJO',1,'ADICIONAL') AS ADICIONAL_FIJO, " +
						" (S.N_NUMASIPIS1 + NVL(S.N_NUMASIPIS2,0)) AS CAPACIDAD_BUS, " +
						" v.N_TARIFA+v.N_RECARGO+v.N_PENALIDAD-v.N_DESCUENTO as T_MONTO " +
						" FROM VRTVENPAS v " +
						" left join ( select max(proser_id) proser_id, itinerario_id from VRTPROSER group by itinerario_id ) PROS0 on PROS0.itinerario_id=v.itinerario_id " +
						" left join VRTPROSER PROS on PROS.proser_id = PROS0.proser_id " +
						" inner join ( select max(venpas_id)venpas_id, c_numcontrol from vrtvenpas group by c_numcontrol ) max_venta on max_venta.venpas_id=v.venpas_id " +
						" inner join vrtitinerario i on i.itinerario_id  = V.itinerario_id " +
						" inner join vrmservicio   s on s.servicio_id    = i.servicio_id " +
						" inner join vrmempresa    e on e.empresa_id     = i.empresa_id " +
						" inner join vrmruta       r on r.ruta_id        = I.RUTA_IDMAYOR " +
						" LEFT JOIN VRMBUS        B  ON B.BUS_ID        = PROS.BUS_ID " +
						" LEFT JOIN  VRMPERSONAL   TR ON TR.PERSONAL_ID  = PROS.personal_idterramoza " +
						" LEFT JOIN VRMPERSONAL   PE ON PE.PERSONAL_ID  = PROS.PERSONAL_IDPILOTO " +
						" LEFT JOIN  VRMPERSONAL   CO ON CO.PERSONAL_ID  = PROS.PERSONAL_IDCOPILOTO " +
						" LEFT JOIN  VRMPERSONAL   CAUX ON CAUX.PERSONAL_ID  = PROS.personal_idcopilotoaux " +
						" where v.tipmov_id not in( 5,6,13,14) " +
						" AND v.c_estreg='A' " +
						" and i.d_fecpar between to_date('"+fechaInicioBarra+"','dd/mm/yyyy') and to_date('"+fechaFinBarra+"','dd/mm/yyyy') " +
						" and v.agencia_id <> 69 "+query_conductor_id+") ptmy " +
						" group by ptmy.EMPRESA, ptmy.FECHA_PARTIDA, ptmy.TURNO, ptmy.ORIGEN, ptmy.DESTINO, ptmy.SERVICIO, ptmy.NRO_BUS, ptmy.PLACA_BUS, ptmy.TERRAMOZA, ptmy.NRO_PROGRAMACION, " +
						" ptmy.COPILOTO, ptmy.AUXPILOTO, ptmy.ADICIONAL_FIJO, ptmy.CAPACIDAD_BUS " +
						" HAVING ptmy.COPILOTO is not null ";

					sql = sqlventas;										
				}else{
					if(fechaActual.after(D_fechaInicio)){
						// El Rango de la Fecha esta adentro del rango la Fecha_Actual.
						// temporal: Fecha_Inicio y (Fecha_Actual -1)
						// ventas  : Fecha_Actual y Fecha_Fin
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(fechaActual);
						calendar.add(Calendar.DAY_OF_YEAR,-1);// Restar un dia menos			

						FechaActual = Constantes.FORMAT_DATE.format(fechaActual);
						AnteriorFechaActual = Constantes.FORMAT_DATE.format(calendar.getTime());

						// sqltemporal (2 filas)
						sqltemporal =
							" select vs.EMPRESA, " +
							" vs.FECHA_PARTIDA, " +
							" vs.TURNO, " +
							" vs.ORIGEN, " +
							" vs.DESTINO, " +
							" vs.SERVICIO, " +
							" vs.NRO_BUS, " +
							" vs.PLACA_BUS, " +
							" vs.TERRAMOZA, " +
							" vs.NRO_PROGRAMACION, " +
							//" vs.PILOTO as PILOTO, " +
							//" '' as COPILOTO, " +
							" NVL(vs.PILOTO, '') as CONDUCTOR, "+
							" vs.AUXPILOTO, " +
							" vs.ADICIONAL_FIJO, " +
							" vs.CAPACIDAD_BUS, " +
							" vs.T_BOLETO, " +
							" vs.T_MONTO " +
							" FROM tmpventasxservicios vs " +
							query_conductor_id_temporal_piloto+
							" where vs.FECHA_PARTIDA between to_date('"+fechaInicioBarra+"','dd/mm/yyyy') and to_date('"+AnteriorFechaActual+"','dd/mm/yyyy') " +

							" UNION ALL " +

							" select vs.EMPRESA, " +
							" vs.FECHA_PARTIDA, " +
							" vs.TURNO, " +
							" vs.ORIGEN, " +
							" vs.DESTINO, " +
							" vs.SERVICIO, " +
							" vs.NRO_BUS, " +
							" vs.PLACA_BUS, " +
							" vs.TERRAMOZA, " +
							" vs.NRO_PROGRAMACION, " +
							//" '' as PILOTO, " +
							//" vs.COPILOTO as COPILOTO, " +
							" NVL('', vs.COPILOTO) as CONDUCTOR, "+
							" vs.AUXPILOTO, " +
							" vs.ADICIONAL_FIJO, " +
							" vs.CAPACIDAD_BUS, " +
							" vs.T_BOLETO, " +
							" vs.T_MONTO " +
							" FROM tmpventasxservicios vs " +
							query_conductor_id_temporal_copiloto+
							" where vs.FECHA_PARTIDA between to_date('"+fechaInicioBarra+"','dd/mm/yyyy') and to_date('"+AnteriorFechaActual+"','dd/mm/yyyy') " +
							" and vs.COPILOTO is not null ";

						// sqlventas (2 filas)
						sqlventas =
							// FILA 1 PILOTO
							" select distinct ptmy.EMPRESA, ptmy.FECHA_PARTIDA, ptmy.TURNO, ptmy.ORIGEN, ptmy.DESTINO, ptmy.SERVICIO, ptmy.NRO_BUS, ptmy.PLACA_BUS, ptmy.TERRAMOZA, " +
							" TO_CHAR(ptmy.NRO_PROGRAMACION) NRO_PROGRAMACION, "+
							//" ptmy.PILOTO as PILOTO, '' as COPILOTO, "+
							" NVL(ptmy.PILOTO, '') as CONDUCTOR, "+
							" ptmy.AUXPILOTO, ptmy.ADICIONAL_FIJO, TO_CHAR(ptmy.CAPACIDAD_BUS) CAPACIDAD_BUS, " +
							" TO_CHAR(count(1)) T_BOLETO, sum(ptmy.T_MONTO) T_MONTO " +
							" from (SELECT E.C_NOMCOR EMPRESA, TO_CHAR(I.D_FECPAR,'DD/MM/YYYY') FECHA_PARTIDA, I.C_HORPAR TURNO, R.C_ORIGEN ORIGEN, R.C_DESTINO DESTINO, S.C_NOMCOR AS SERVICIO, " +
							" B.C_CODIGO AS NRO_BUS, B.C_NUMPLACA AS PLACA_BUS, (TR.C_APEPAT||' '||TR.C_APEMAT||' '||TR.C_NOMBRE) AS TERRAMOZA, I.ITINERARIO_ID AS NRO_PROGRAMACION, " +
							" (PE.C_APEPAT||' '||PE.C_APEMAT||' '||PE.C_NOMBRE) AS PILOTO, (CO.C_APEPAT||' '||CO.C_APEMAT||' '||CO.C_NOMBRE) AS COPILOTO, " +
							" (CAUX.C_APEPAT||' '||CAUX.C_APEMAT||' '||CAUX.C_NOMBRE) AS AUXPILOTO, DECODE(I.C_ADICIONAL,0,'FIJO',1,'ADICIONAL') AS ADICIONAL_FIJO, " +
							" (S.N_NUMASIPIS1 + NVL(S.N_NUMASIPIS2,0)) AS CAPACIDAD_BUS, v.N_TARIFA+v.N_RECARGO+v.N_PENALIDAD-v.N_DESCUENTO as T_MONTO " +
							" FROM VRTVENPAS v left join ( select max(proser_id) proser_id, itinerario_id from VRTPROSER group by itinerario_id ) PROS0 on PROS0.itinerario_id=v.itinerario_id " +
							" left join VRTPROSER PROS on PROS.proser_id = PROS0.proser_id inner join ( select max(venpas_id)venpas_id, c_numcontrol from vrtvenpas group by c_numcontrol ) max_venta " +
							" on max_venta.venpas_id=v.venpas_id inner join vrtitinerario i on i.itinerario_id = V.itinerario_id inner join vrmservicio s on s.servicio_id = i.servicio_id " +
							" inner join vrmempresa e on e.empresa_id = i.empresa_id inner join vrmruta r on r.ruta_id = I.RUTA_IDMAYOR LEFT JOIN VRMBUS B ON B.BUS_ID = PROS.BUS_ID " +
							" LEFT JOIN VRMPERSONAL TR ON TR.PERSONAL_ID = PROS.personal_idterramoza LEFT JOIN VRMPERSONAL PE ON PE.PERSONAL_ID = PROS.PERSONAL_IDPILOTO " +
							" LEFT JOIN VRMPERSONAL CO ON CO.PERSONAL_ID = PROS.PERSONAL_IDCOPILOTO LEFT JOIN VRMPERSONAL CAUX ON CAUX.PERSONAL_ID = PROS.personal_idcopilotoaux " +
							" where v.tipmov_id not in(5,6,13,14) AND v.c_estreg='A' and i.d_fecpar between to_date('"+FechaActual+"','dd/mm/yyyy') and to_date('"+fechaFinBarra+"','dd/mm/yyyy') " +
							" and v.agencia_id <> 69 "+query_conductor_id+") ptmy " +
							" group by ptmy.EMPRESA, ptmy.FECHA_PARTIDA, ptmy.TURNO, ptmy.ORIGEN, ptmy.DESTINO, ptmy.SERVICIO, ptmy.NRO_BUS, ptmy.PLACA_BUS, ptmy.TERRAMOZA, ptmy.NRO_PROGRAMACION, " +
							" ptmy.PILOTO, ptmy.AUXPILOTO, ptmy.ADICIONAL_FIJO, ptmy.CAPACIDAD_BUS " +

							// FILA 2 COPILOTO
							" UNION ALL " +
							" select distinct ptmy.EMPRESA, ptmy.FECHA_PARTIDA, ptmy.TURNO, ptmy.ORIGEN, ptmy.DESTINO, ptmy.SERVICIO, ptmy.NRO_BUS, ptmy.PLACA_BUS, ptmy.TERRAMOZA, " +
							" TO_CHAR(ptmy.NRO_PROGRAMACION) NRO_PROGRAMACION, "+
							//" '' as PILOTO, ptmy.COPILOTO as COPILOTO, "+
							" NVL('', ptmy.COPILOTO) as CONDUCTOR, "+
							" ptmy.AUXPILOTO, ptmy.ADICIONAL_FIJO, TO_CHAR(ptmy.CAPACIDAD_BUS) CAPACIDAD_BUS, " +
							" TO_CHAR(count(1)) T_BOLETO, sum(ptmy.T_MONTO) T_MONTO " +
							" from (SELECT E.C_NOMCOR EMPRESA, TO_CHAR(I.D_FECPAR,'DD/MM/YYYY') FECHA_PARTIDA, I.C_HORPAR TURNO, R.C_ORIGEN ORIGEN, R.C_DESTINO DESTINO, S.C_NOMCOR AS SERVICIO, " +
							" B.C_CODIGO AS NRO_BUS, B.C_NUMPLACA AS PLACA_BUS, (TR.C_APEPAT||' '||TR.C_APEMAT||' '||TR.C_NOMBRE) AS TERRAMOZA, I.ITINERARIO_ID AS NRO_PROGRAMACION, " +
							" (PE.C_APEPAT||' '||PE.C_APEMAT||' '||PE.C_NOMBRE) AS PILOTO, (CO.C_APEPAT||' '||CO.C_APEMAT||' '||CO.C_NOMBRE) AS COPILOTO, " +
							" (CAUX.C_APEPAT||' '||CAUX.C_APEMAT||' '||CAUX.C_NOMBRE) AS AUXPILOTO, DECODE(I.C_ADICIONAL,0,'FIJO',1,'ADICIONAL') AS ADICIONAL_FIJO, " +
							" (S.N_NUMASIPIS1 + NVL(S.N_NUMASIPIS2,0)) AS CAPACIDAD_BUS, v.N_TARIFA+v.N_RECARGO+v.N_PENALIDAD-v.N_DESCUENTO as T_MONTO " +
							" FROM VRTVENPAS v left join ( select max(proser_id) proser_id, itinerario_id from VRTPROSER group by itinerario_id ) PROS0 on PROS0.itinerario_id=v.itinerario_id " +
							" left join VRTPROSER PROS on PROS.proser_id = PROS0.proser_id inner join ( select max(venpas_id)venpas_id, c_numcontrol from vrtvenpas group by c_numcontrol ) max_venta " +
							" on max_venta.venpas_id=v.venpas_id inner join vrtitinerario i on i.itinerario_id = V.itinerario_id inner join vrmservicio s on s.servicio_id = i.servicio_id " +
							" inner join vrmempresa e on e.empresa_id = i.empresa_id inner join vrmruta r on r.ruta_id = I.RUTA_IDMAYOR LEFT JOIN VRMBUS B ON B.BUS_ID = PROS.BUS_ID " +
							" LEFT JOIN VRMPERSONAL TR ON TR.PERSONAL_ID = PROS.personal_idterramoza LEFT JOIN VRMPERSONAL PE ON PE.PERSONAL_ID = PROS.PERSONAL_IDPILOTO " +
							" LEFT JOIN VRMPERSONAL CO ON CO.PERSONAL_ID = PROS.PERSONAL_IDCOPILOTO LEFT JOIN VRMPERSONAL CAUX ON CAUX.PERSONAL_ID = PROS.personal_idcopilotoaux " +
							" where v.tipmov_id not in(5,6,13,14) AND v.c_estreg='A' and i.d_fecpar between to_date('"+FechaActual+"','dd/mm/yyyy') and to_date('"+fechaFinBarra+"','dd/mm/yyyy') " +
							" and v.agencia_id <> 69 "+query_conductor_id+") ptmy " +
							" group by ptmy.EMPRESA, ptmy.FECHA_PARTIDA, ptmy.TURNO, ptmy.ORIGEN, ptmy.DESTINO, ptmy.SERVICIO, ptmy.NRO_BUS, ptmy.PLACA_BUS, ptmy.TERRAMOZA, ptmy.NRO_PROGRAMACION, " +
							" ptmy.COPILOTO, ptmy.AUXPILOTO, ptmy.ADICIONAL_FIJO, ptmy.CAPACIDAD_BUS " +
							" HAVING ptmy.COPILOTO is not null ";

						sql = sqltemporal+" UNION ALL "+sqlventas;
					}else{
						// Fecha de Inicio es la Fecha_Actual.
						// ventas :: Fecha_Actual y Fecha_Fin
						FechaActual = Constantes.FORMAT_DATE.format(fechaActual);

						// sqlventas (2 filas)
						sqlventas =
							" select distinct ptmy.EMPRESA, ptmy.FECHA_PARTIDA, ptmy.TURNO, ptmy.ORIGEN, ptmy.DESTINO, ptmy.SERVICIO, ptmy.NRO_BUS, ptmy.PLACA_BUS, ptmy.TERRAMOZA, " +
							" TO_CHAR(ptmy.NRO_PROGRAMACION) NRO_PROGRAMACION, "+
							//" ptmy.PILOTO as PILOTO, '' as COPILOTO, "+
							" NVL(ptmy.PILOTO, '') as CONDUCTOR, "+
							" ptmy.AUXPILOTO, ptmy.ADICIONAL_FIJO, TO_CHAR(ptmy.CAPACIDAD_BUS) CAPACIDAD_BUS, " +
							" TO_CHAR(count(1)) T_BOLETO, sum(ptmy.T_MONTO) T_MONTO " +
							" from (SELECT E.C_NOMCOR EMPRESA, TO_CHAR(I.D_FECPAR,'DD/MM/YYYY') FECHA_PARTIDA, I.C_HORPAR TURNO, R.C_ORIGEN ORIGEN, R.C_DESTINO DESTINO, S.C_NOMCOR AS SERVICIO, " +
							" B.C_CODIGO AS NRO_BUS, B.C_NUMPLACA AS PLACA_BUS, (TR.C_APEPAT||' '||TR.C_APEMAT||' '||TR.C_NOMBRE) AS TERRAMOZA, I.ITINERARIO_ID AS NRO_PROGRAMACION, " +
							" (PE.C_APEPAT||' '||PE.C_APEMAT||' '||PE.C_NOMBRE) AS PILOTO, (CO.C_APEPAT||' '||CO.C_APEMAT||' '||CO.C_NOMBRE) AS COPILOTO, " +
							" (CAUX.C_APEPAT||' '||CAUX.C_APEMAT||' '||CAUX.C_NOMBRE) AS AUXPILOTO, DECODE(I.C_ADICIONAL,0,'FIJO',1,'ADICIONAL') AS ADICIONAL_FIJO, " +
							" (S.N_NUMASIPIS1 + NVL(S.N_NUMASIPIS2,0)) AS CAPACIDAD_BUS, v.N_TARIFA+v.N_RECARGO+v.N_PENALIDAD-v.N_DESCUENTO as T_MONTO " +
							" FROM VRTVENPAS v left join ( select max(proser_id) proser_id, itinerario_id from VRTPROSER group by itinerario_id ) PROS0 on PROS0.itinerario_id=v.itinerario_id " +
							" left join VRTPROSER PROS on PROS.proser_id = PROS0.proser_id inner join ( select max(venpas_id)venpas_id, c_numcontrol from vrtvenpas group by c_numcontrol ) max_venta " +
							" on max_venta.venpas_id=v.venpas_id inner join vrtitinerario i on i.itinerario_id = V.itinerario_id inner join vrmservicio s on s.servicio_id = i.servicio_id " +
							" inner join vrmempresa e on e.empresa_id = i.empresa_id inner join vrmruta r on r.ruta_id = I.RUTA_IDMAYOR LEFT JOIN VRMBUS B ON B.BUS_ID = PROS.BUS_ID " +
							" LEFT JOIN VRMPERSONAL TR ON TR.PERSONAL_ID = PROS.personal_idterramoza LEFT JOIN VRMPERSONAL PE ON PE.PERSONAL_ID = PROS.PERSONAL_IDPILOTO " +
							" LEFT JOIN VRMPERSONAL CO ON CO.PERSONAL_ID = PROS.PERSONAL_IDCOPILOTO LEFT JOIN VRMPERSONAL CAUX ON CAUX.PERSONAL_ID = PROS.personal_idcopilotoaux " +
							" where v.tipmov_id not in(5,6,13,14) AND v.c_estreg='A' and i.d_fecpar between to_date('"+FechaActual+"','dd/mm/yyyy') and to_date('"+fechaFinBarra+"','dd/mm/yyyy') " +
							" and v.agencia_id <> 69 "+query_conductor_id+") ptmy " +
							" group by ptmy.EMPRESA, ptmy.FECHA_PARTIDA, ptmy.TURNO, ptmy.ORIGEN, ptmy.DESTINO, ptmy.SERVICIO, ptmy.NRO_BUS, ptmy.PLACA_BUS, ptmy.TERRAMOZA, ptmy.NRO_PROGRAMACION, " +
							" ptmy.PILOTO, ptmy.AUXPILOTO, ptmy.ADICIONAL_FIJO, ptmy.CAPACIDAD_BUS " +
							" UNION ALL " +
							" select distinct ptmy.EMPRESA, ptmy.FECHA_PARTIDA, ptmy.TURNO, ptmy.ORIGEN, ptmy.DESTINO, ptmy.SERVICIO, ptmy.NRO_BUS, ptmy.PLACA_BUS, ptmy.TERRAMOZA, " +
							" TO_CHAR(ptmy.NRO_PROGRAMACION) NRO_PROGRAMACION, "+
							//" '' as PILOTO, ptmy.COPILOTO as COPILOTO, "+
							" NVL('', ptmy.COPILOTO) as CONDUCTOR, "+
							" ptmy.AUXPILOTO, ptmy.ADICIONAL_FIJO, TO_CHAR(ptmy.CAPACIDAD_BUS) CAPACIDAD_BUS, " +
							" TO_CHAR(count(1)) T_BOLETO, sum(ptmy.T_MONTO) T_MONTO " +
							" from (SELECT E.C_NOMCOR EMPRESA, TO_CHAR(I.D_FECPAR,'DD/MM/YYYY') FECHA_PARTIDA, I.C_HORPAR TURNO, R.C_ORIGEN ORIGEN, R.C_DESTINO DESTINO, S.C_NOMCOR AS SERVICIO, " +
							" B.C_CODIGO AS NRO_BUS, B.C_NUMPLACA AS PLACA_BUS, (TR.C_APEPAT||' '||TR.C_APEMAT||' '||TR.C_NOMBRE) AS TERRAMOZA, I.ITINERARIO_ID AS NRO_PROGRAMACION, " +
							" (PE.C_APEPAT||' '||PE.C_APEMAT||' '||PE.C_NOMBRE) AS PILOTO, (CO.C_APEPAT||' '||CO.C_APEMAT||' '||CO.C_NOMBRE) AS COPILOTO, " +
							" (CAUX.C_APEPAT||' '||CAUX.C_APEMAT||' '||CAUX.C_NOMBRE) AS AUXPILOTO, DECODE(I.C_ADICIONAL,0,'FIJO',1,'ADICIONAL') AS ADICIONAL_FIJO, " +
							" (S.N_NUMASIPIS1 + NVL(S.N_NUMASIPIS2,0)) AS CAPACIDAD_BUS, v.N_TARIFA+v.N_RECARGO+v.N_PENALIDAD-v.N_DESCUENTO as T_MONTO " +
							" FROM VRTVENPAS v left join ( select max(proser_id) proser_id, itinerario_id from VRTPROSER group by itinerario_id ) PROS0 on PROS0.itinerario_id=v.itinerario_id " +
							" left join VRTPROSER PROS on PROS.proser_id = PROS0.proser_id inner join ( select max(venpas_id)venpas_id, c_numcontrol from vrtvenpas group by c_numcontrol ) max_venta " +
							" on max_venta.venpas_id=v.venpas_id inner join vrtitinerario i on i.itinerario_id = V.itinerario_id inner join vrmservicio s on s.servicio_id = i.servicio_id " +
							" inner join vrmempresa e on e.empresa_id = i.empresa_id inner join vrmruta r on r.ruta_id = I.RUTA_IDMAYOR LEFT JOIN VRMBUS B ON B.BUS_ID = PROS.BUS_ID " +
							" LEFT JOIN VRMPERSONAL TR ON TR.PERSONAL_ID = PROS.personal_idterramoza LEFT JOIN VRMPERSONAL PE ON PE.PERSONAL_ID = PROS.PERSONAL_IDPILOTO " +
							" LEFT JOIN VRMPERSONAL CO ON CO.PERSONAL_ID = PROS.PERSONAL_IDCOPILOTO LEFT JOIN VRMPERSONAL CAUX ON CAUX.PERSONAL_ID = PROS.personal_idcopilotoaux " +
							" where v.tipmov_id not in(5,6,13,14) AND v.c_estreg='A' and i.d_fecpar between to_date('"+FechaActual+"','dd/mm/yyyy') and to_date('"+fechaFinBarra+"','dd/mm/yyyy') " +
							" and v.agencia_id <> 69 "+query_conductor_id+") ptmy " +
							" group by ptmy.EMPRESA, ptmy.FECHA_PARTIDA, ptmy.TURNO, ptmy.ORIGEN, ptmy.DESTINO, ptmy.SERVICIO, ptmy.NRO_BUS, ptmy.PLACA_BUS, ptmy.TERRAMOZA, ptmy.NRO_PROGRAMACION, " +
							" ptmy.COPILOTO, ptmy.AUXPILOTO, ptmy.ADICIONAL_FIJO, ptmy.CAPACIDAD_BUS " +
							" HAVING ptmy.COPILOTO is not null ";

						sql = sqlventas;
					}			
				}
			}else{
				if( fechaActual.after(D_fechafin)){
					// Aqui solo consultara a las tablas temporales.
					// Temporal :: Fecha_Inicio y Fecha_Fin

					sqltemporal =
						" select vs.EMPRESA, vs.FECHA_PARTIDA, vs.TURNO, vs.ORIGEN, vs.DESTINO, vs.SERVICIO, vs.NRO_BUS, vs.PLACA_BUS, vs.TERRAMOZA, vs.NRO_PROGRAMACION, " +
						//" vs.PILOTO as PILOTO, '' as COPILOTO, "+
						" NVL(vs.PILOTO, '') as CONDUCTOR, "+
						" vs.AUXPILOTO, vs.ADICIONAL_FIJO, vs.CAPACIDAD_BUS, vs.T_BOLETO, vs.T_MONTO " +
						" FROM tmpventasxservicios vs " +
						" where vs.FECHA_PARTIDA between to_date('"+fechaInicioBarra+"','dd/mm/yyyy') and to_date('"+fechaFinBarra+"','dd/mm/yyyy') " +
						" UNION ALL " +
						" select vs.EMPRESA, vs.FECHA_PARTIDA, vs.TURNO, vs.ORIGEN, vs.DESTINO, vs.SERVICIO, vs.NRO_BUS, vs.PLACA_BUS, vs.TERRAMOZA, vs.NRO_PROGRAMACION, " +
						//" '' as PILOTO, vs.COPILOTO as COPILOTO, "+
						" NVL('', vs.COPILOTO) as CONDUCTOR, "+
						" vs.AUXPILOTO, vs.ADICIONAL_FIJO, vs.CAPACIDAD_BUS, vs.T_BOLETO, vs.T_MONTO " +
						" FROM tmpventasxservicios vs " +
						query_conductor_id_temporal_piloto+
						" where vs.FECHA_PARTIDA between to_date('"+fechaInicioBarra+"','dd/mm/yyyy') and to_date('"+fechaFinBarra+"','dd/mm/yyyy') " +
						" and vs.COPILOTO is not null ";

					sql = sqltemporal;
				}else{
					//Del Rango: la fecha final,Es igual a la Fecha Actual.
					// Temporal :: Fecha_Inicio y (Fecha_Actual - 1)
					// Ventas   :: Fecha_Actual.

					Calendar calendar = Calendar.getInstance();
					calendar.setTime(fechaActual);
					calendar.add(Calendar.DAY_OF_YEAR,-1);// Restar un dia menos			

					FechaActual = Constantes.FORMAT_DATE.format(fechaActual);
					AnteriorFechaActual = Constantes.FORMAT_DATE.format(calendar.getTime());

					sqltemporal =
						" select vs.EMPRESA, vs.FECHA_PARTIDA, vs.TURNO, vs.ORIGEN, vs.DESTINO, vs.SERVICIO, vs.NRO_BUS, vs.PLACA_BUS, vs.TERRAMOZA, vs.NRO_PROGRAMACION, " +
						//" vs.PILOTO as PILOTO, '' as COPILOTO, "+
						" NVL(vs.PILOTO, '') as CONDUCTOR, "+
						" vs.AUXPILOTO, vs.ADICIONAL_FIJO, vs.CAPACIDAD_BUS, vs.T_BOLETO, vs.T_MONTO " +
						" FROM tmpventasxservicios vs " +
						query_conductor_id_temporal_piloto+
						" where vs.FECHA_PARTIDA between to_date('"+fechaInicioBarra+"','dd/mm/yyyy') and to_date('"+AnteriorFechaActual+"','dd/mm/yyyy') " +
						" UNION ALL " +
						" select vs.EMPRESA, vs.FECHA_PARTIDA, vs.TURNO, vs.ORIGEN, vs.DESTINO, vs.SERVICIO, vs.NRO_BUS, vs.PLACA_BUS, vs.TERRAMOZA, vs.NRO_PROGRAMACION, " +
						//" '' as PILOTO, vs.COPILOTO as COPILOTO, "+
						" NVL('', vs.COPILOTO) as CONDUCTOR, "+
						" vs.AUXPILOTO, vs.ADICIONAL_FIJO, vs.CAPACIDAD_BUS, vs.T_BOLETO, vs.T_MONTO " +
						" FROM tmpventasxservicios vs " +
						query_conductor_id_temporal_copiloto+
						" where vs.FECHA_PARTIDA between to_date('"+fechaInicioBarra+"','dd/mm/yyyy') and to_date('"+AnteriorFechaActual+"','dd/mm/yyyy') " +
						" and vs.COPILOTO is not null ";

					// aquí tu sqlventas ya está en la parte superior (2 filas)
					if (sqlventas != null && sqlventas.trim().length() > 0) {
					    sql = sqltemporal + " UNION ALL " + sqlventas;
					} else {
					    sql = sqltemporal;
					}
				}
			}
						
			lstReporteTareoConductor = jdbcTemplate.query(sql, new ReporteTareoConductorRowMapper());
			
			return lstReporteTareoConductor;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	private final class FormularioRetenRowMapper implements RowMapper<FormularioReten>{
		
		@Override
		public FormularioReten mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new FormularioReten(rs.getString(1), rs.getInt(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11));
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
	
	private final class MantenimientoRutaRowMapper implements RowMapper<MantenimientoRuta>{
		
		@Override
		public MantenimientoRuta mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new MantenimientoRuta(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getDouble(5), rs.getDouble(6), rs.getDouble(7), rs.getDouble(8), rs.getDouble(9), rs.getDouble(10));
		}
	}
	
	private final class ReporteTareoConductorRowMapper implements RowMapper<ReporteTareoConductor>{
		
		@Override
		public ReporteTareoConductor mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new ReporteTareoConductor(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), 
					rs.getString(11), rs.getString(12), rs.getString(13), rs.getInt(14), rs.getInt(15), rs.getDouble(16));
		}
	}
}