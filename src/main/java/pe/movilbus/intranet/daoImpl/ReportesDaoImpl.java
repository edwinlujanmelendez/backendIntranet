package pe.movilbus.intranet.daoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import pe.movilbus.intranet.beans.Agencia;
import pe.movilbus.intranet.beans.BodyUrlPago;
import pe.movilbus.intranet.beans.ReporteDetallado;
import pe.movilbus.intranet.beans.ReporteSeguimientoFrotcom;
import pe.movilbus.intranet.beans.UsuariosCounter;
import pe.movilbus.intranet.dao.ReportesDao;

@Repository
public class ReportesDaoImpl implements ReportesDao{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<Agencia> getAgencias(){
		List<Agencia> lstReportes = new ArrayList<Agencia>();
		
		try{
			String sql = " select agencia_id, tipage_id, localidad_id, concesionario_id, ubigeo_id, zoncom_id, c_denominacion, c_nomcor, n_esterminal, c_codigo, "+
						 " c_direccion, nacionalidad_id, codigo_concar, c_nombre_concar from vrmagencia where c_estreg='A' and concesionario_id=1 and agencia_id=345 order by c_denominacion";
			
			/*String sql = " select agencia_id, tipage_id, localidad_id, concesionario_id, ubigeo_id, zoncom_id, c_denominacion, c_nomcor, n_esterminal, c_codigo, "+
					 	 " c_direccion, nacionalidad_id, codigo_concar, c_nombre_concar from vrmagencia where c_estreg='A' and concesionario_id=1 order by c_denominacion";*/
			
			lstReportes = jdbcTemplate.query(sql, new AgenciaRowMapper());
			
			return lstReportes;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public List<ReporteSeguimientoFrotcom> getSeguimientoFrotcom(int localidadOrigen, int localidadDestino, String fechaInicio, String fechaFin){
		List<ReporteSeguimientoFrotcom> lstReporteSeguimientoFrotcom = new ArrayList<ReporteSeguimientoFrotcom>();
		
		String query_localidadOrigen = "";
		String query_localidadDestino = "";
		
		if(localidadOrigen != 0){
			query_localidadOrigen = " and vrma.localidad_idorigen = "+localidadOrigen;
		}
		
		if(localidadDestino != 0){
			query_localidadDestino = " and vrma.localidad_iddestino = "+localidadDestino;
		}
		
		try{
			String sql = " with itinerarios_filtrados as ( "+
						 "   select viti.itinerario_id, viti.c_sectra "+
						 "   from vrtitinerario viti "+
						 "   join vrmruta vrma on vrma.ruta_id = viti.ruta_idmayor "+
						 "   where viti.d_fecpar between to_date('"+fechaInicio+"','yyyy-mm-dd') and to_date('"+fechaFin+"','yyyy-mm-dd') "+
						 query_localidadOrigen+
						 query_localidadDestino+
						 "     and viti.c_estreg='A' "+
						 "     and viti.bus_id is not null "+
						 " ), "+
						 " detalle as ( "+
						 "   select diti.itinerario_id, "+
						 "          vrut.c_origen, "+
						 "          vrut.c_destino, "+
						 "          diti.c_horpar, "+
						 "          diti.c_horlle, "+
						 "          diti.d_fecpar, "+
				         "          diti.d_feclle, "+
				         "          row_number() over(partition by diti.itinerario_id order by diti.d_fecpar, diti.c_horpar) as rn, "+
				         "          count(*) over(partition by diti.itinerario_id) as cnt "+
						 "   from vrtdetiti diti "+
						 "   join vrmruta vrut on vrut.ruta_id = diti.ruta_id "+
						 "   join itinerarios_filtrados ifil on ifil.itinerario_id = diti.itinerario_id "+
						 "   where instr(ifil.c_sectra, vrut.localidad_idorigen || '-' || vrut.localidad_iddestino) > 0 "+
						 " ), "+
						 " embarques as ( "+
						 "   select vagepar.itinerario_id, "+
						 "          vage.c_denominacion as agencia, "+
						 "          vagepar.c_horpar as hora, "+
						 "          row_number() over(partition by vagepar.itinerario_id order by vagepar.c_horpar) as rn "+
						 "   from vrtitiagepar vagepar "+
						 "   join vrmagencia vage on vage.agencia_id = vagepar.agencia_id "+
						 " ), "+
						 " desembarques as ( "+
						 "   select vagelle.itinerario_id, "+
						 "          vage.c_denominacion as agencia, "+
						 "          vagelle.c_horlle as hora, "+
						 "          row_number() over(partition by vagelle.itinerario_id order by vagelle.c_horlle) as rn "+
						 "   from vrtitiagelle vagelle "+
						 "   join vrmagencia vage on vage.agencia_id = vagelle.agencia_id "+
						 " ) "+
						 " select t.itinerario_id, "+
						 "      coalesce(max(case when t.rn = 1 then t.c_origen end), '-') as origen, "+
						 "      coalesce(max(case when t.rn = t.cnt then t.c_destino end), '-') as destino, "+
						 
						 "      case when max(case when t.rn = 1 then t.c_origen end) = 'LIMA' "+
						 "           then coalesce(max(case when e.rn = 1 then e.agencia end), '-') "+
						 "           else coalesce(max(case when t.rn = 1 then t.c_origen end), '-') "+
						 "      end as embarque_1_nombre, "+
						 "      case when max(case when t.rn = 1 then t.c_origen end) = 'LIMA' "+
						 "           then coalesce(max(case when e.rn = 1 then e.hora end), '-') "+
						 "           else coalesce(max(case when t.rn = 1 then t.c_horpar end), '-') "+
						 "      end as embarque_1_hora, "+
						 
						 "      case when max(case when t.rn = 1 then t.c_origen end) = 'LIMA' "+
						 "           then coalesce(max(case when e.rn = 2 then e.agencia end), '-') else '-' end as embarque_2_nombre, "+
						 "      case when max(case when t.rn = 1 then t.c_origen end) = 'LIMA' "+
						 "           then coalesce(max(case when e.rn = 2 then e.hora end), '-') else '-' end as embarque_2_hora, "+
					     
						 "      coalesce(max(case when t.rn = 2 then t.c_origen end), '-') as escala_comercial_1_nombre, "+
						 "      coalesce(max(case when t.rn = 2 then t.c_horpar end), '-') as escala_comercial_1_hora, "+
					     
						 "      coalesce(max(case when t.rn = 3 then t.c_origen end), '-') as escala_comercial_2_nombre, "+
						 "      coalesce(max(case when t.rn = 3 then t.c_horpar end), '-') as escala_comercial_2_hora, "+
					     
						 "      coalesce(max(case when t.rn = 4 then t.c_origen end), '-') as escala_comercial_3_nombre, "+
						 "      coalesce(max(case when t.rn = 4 then t.c_horpar end), '-') as escala_comercial_3_hora, "+
						 
						 "      coalesce(max(case when t.rn = 5 then t.c_origen end), '-') as escala_comercial_4_nombre, "+
						 "      coalesce(max(case when t.rn = 5 then t.c_horpar end), '-') as escala_comercial_4_hora, "+
						       
						 "      coalesce(max(case when t.rn = 6 then t.c_origen end), '-') as escala_comercial_5_nombre, "+
						 "      coalesce(max(case when t.rn = 6 then t.c_horpar end), '-') as escala_comercial_5_hora, "+
						 
						 "      coalesce(max(case when t.rn = 7 then t.c_origen end), '-') as escala_comercial_6_nombre, "+
						 "      coalesce(max(case when t.rn = 7 then t.c_horpar end), '-') as escala_comercial_6_hora, "+
						
						 "      coalesce(max(case when t.rn = 8 then t.c_origen end), '-') as escala_comercial_7_nombre, "+
						 "      coalesce(max(case when t.rn = 8 then t.c_horpar end), '-') as escala_comercial_7_hora, "+
						
						 "      coalesce(max(case when t.rn = 9 then t.c_origen end), '-') as escala_comercial_8_nombre, "+
						 "      coalesce(max(case when t.rn = 9 then t.c_horpar end), '-') as escala_comercial_8_hora, "+
						 
						 "      case when max(case when t.rn = t.cnt then t.c_destino end) = 'LIMA' "+
						 "           then coalesce(max(case when d.rn = 1 then d.agencia end), '-') "+
						 "           else coalesce(max(case when t.rn = t.cnt then t.c_destino end), '-') "+
						 "      end as desembarque_1_nombre, "+
						 "      case when max(case when t.rn = t.cnt then t.c_destino end) = 'LIMA' "+
						 "           then coalesce(max(case when d.rn = 1 then d.hora end), '-') "+
						 "           else coalesce(max(case when t.rn = t.cnt then t.c_horlle end), '-') "+
						 "      end as desembarque_1_hora, "+
					     
						 "      case when max(case when t.rn = t.cnt then t.c_destino end) = 'LIMA' "+
						 "           then coalesce(max(case when d.rn = 2 then d.agencia end), '-') else '-' end as desembarque_2_nombre, "+
						 "      case when max(case when t.rn = t.cnt then t.c_destino end) = 'LIMA' "+
						 "           then coalesce(max(case when d.rn = 2 then d.hora end), '-') else '-' end as desembarque_2_hora, "+
					     	
						 "      case when max(case when t.rn = t.cnt then t.c_destino end) = 'LIMA' "+
						 "           then coalesce(max(case when d.rn = 3 then d.agencia end), '-') else '-' end as desembarque_3_nombre, "+
						 "      case when max(case when t.rn = t.cnt then t.c_destino end) = 'LIMA' "+
						 "           then coalesce(max(case when d.rn = 3 then d.hora end), '-') else '-' end as desembarque_3_hora, "+
					     
						 "      case when max(case when t.rn = t.cnt then t.c_destino end) = 'LIMA' "+
						 "           then coalesce(max(case when d.rn = 4 then d.agencia end), '-') else '-' end as desembarque_4_nombre, "+
						 "      case when max(case when t.rn = t.cnt then t.c_destino end) = 'LIMA' "+
						 "           then coalesce(max(case when d.rn = 4 then d.hora end), '-') else '-' end as desembarque_4_hora, "+
						 
						 "      case when max(case when t.rn = t.cnt then t.c_destino end) = 'LIMA' "+
						 "           then coalesce(max(case when d.rn = 5 then d.agencia end), '-') else '-' end as desembarque_5_nombre, "+
						 "      case when max(case when t.rn = t.cnt then t.c_destino end) = 'LIMA' "+
						 "           then coalesce(max(case when d.rn = 5 then d.hora end), '-') else '-' end as desembarque_5_hora, "+
						 
						 "      case when max(case when t.rn = t.cnt then t.c_destino end) = 'LIMA' "+
						 "           then coalesce(max(case when d.rn = 6 then d.agencia end), '-') else '-' end as desembarque_6_nombre, "+
						 "      case when max(case when t.rn = t.cnt then t.c_destino end) = 'LIMA' "+
						 "           then coalesce(max(case when d.rn = 6 then d.hora end), '-') else '-' end as desembarque_6_hora, "+
						
						 "      case when max(case when t.rn = t.cnt then t.c_destino end) = 'LIMA' "+
						 "           then coalesce(max(case when d.rn = 7 then d.agencia end), '-') else '-' end as desembarque_7_nombre, "+
						 "      case when max(case when t.rn = t.cnt then t.c_destino end) = 'LIMA' "+
						 "           then coalesce(max(case when d.rn = 7 then d.hora end), '-') else '-' end as desembarque_7_hora, "+
						
						 "      case when max(case when t.rn = t.cnt then t.c_destino end) = 'LIMA' "+
						 "           then coalesce(max(case when d.rn = 8 then d.agencia end), '-') else '-' end as desembarque_8_nombre, "+
						 "      case when max(case when t.rn = t.cnt then t.c_destino end) = 'LIMA' "+
						 "           then coalesce(max(case when d.rn = 8 then d.hora end), '-') else '-' end as desembarque_8_hora "+
						 
						 " from detalle t "+
						 " left join embarques e on e.itinerario_id = t.itinerario_id "+
						 " left join desembarques d on d.itinerario_id = t.itinerario_id "+
						 " group by t.itinerario_id "+
						 " order by coalesce(max(case when t.rn = 1 then t.c_horpar end), '-'), t.itinerario_id ";
			
			//System.out.println(sql);
			
			lstReporteSeguimientoFrotcom = jdbcTemplate.query(sql, new ReporteSeguimientoFrotcomRowMapper());
			
			return lstReporteSeguimientoFrotcom;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public List<UsuariosCounter> getUsuariosCounter(int agencia_id, String fechaInicio, String fechaFin){
		
		List<UsuariosCounter> lstUsuariosCounter = new ArrayList<UsuariosCounter>();
		
		try{
			String sql = " SELECT DISTINCT(vp.usuario_id), u.c_apepat, u.c_apemat, u.c_nombre, u.c_login "+
						 " FROM vrtvenpas vp "+
						 " INNER JOIN vrmusuario u ON u.usuario_id=vp.usuario_id "+
						 " WHERE vp.agencia_id="+agencia_id+" AND "+
						 " vp.d_fecliq BETWEEN to_date('"+fechaInicio+"', 'yyyy-mm-dd') AND to_date('"+fechaFin+"', 'yyyy-mm-dd') AND "+
						 " vp.c_estreg='A' "+
						 " ORDER BY u.c_apepat, u.c_apemat, u.c_nombre";
			
			lstUsuariosCounter = jdbcTemplate.query(sql, new UsuariosCounterMapper());
			
			return lstUsuariosCounter;
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return lstUsuariosCounter;
	}
	
	@Override
	public int postEnviarUrlPago(BodyUrlPago data){
		try{
			String sql1 = "update vrthistorialventas set email_pasajero='"+data.getEmail()+"' where numoperacion='"+data.getNumoperacion()+"'";
			jdbcTemplate.update(sql1);
			
			String sql2 = "update vrtvenpas set c_email_contacto='"+data.getEmail()+"' where n_numopeban='"+data.getNumoperacion()+"'";
			jdbcTemplate.update(sql2);
			
			VentasDaoImpl ventasDaoImpl = new VentasDaoImpl();
			return ventasDaoImpl.enviarCorreoPasajeroPagoLink(data.getEmail(), data.getUrl_pago(), data.getNumoperacion());
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}
	
	public String getQueryTipoVenta(){
		String sql = " DECODE( "+
					 "    tm.c_denominacion, "+
					 "    'FECHA ABIERTA', "+
					 "        DECODE( "+
					 "            tfp.c_denominacion, "+
					 "            'EFECTIVO', 'FA.(EF)', "+
					 "            DECODE(tfp.tipforpag_id, "+
					 "                13, 'FA.(PGLINK)', "+
					 "                 6, 'FA.(DEPOSITO)', "+
					 "                12, 'FA.(ORBIS)', "+
					 "                14, 'FA.(BETTEREZ)', "+
					 "                15, 'FA.(BETTEREZ)', "+
					 "                    'FA.(TC)' "+
					 "            ) "+
					 "        ), "+
					 "    'CONFIRMACION FA', "+
					 "        DECODE( "+
					 "            tfp.c_denominacion, "+
					 "            'EFECTIVO', 'CFA.(EF)', "+
					 "            DECODE(tfp.tipforpag_id, "+
					 "                13, 'CFA.(PGLINK)', "+
					 "                 6, 'CFA.(DEPOSITO)', "+
					 "                12, 'CFA.(ORBIS)', "+
					 "                14, 'CFA.(BETTEREZ)', "+
					 "                15, 'CFA.(BETTEREZ)', "+
					 "                    'CFA.(TC)' "+
					 "            ) "+
					 "        ), "+
					 "    'REIMPRESION', "+
					 "        DECODE( "+
					 "            tfp.c_denominacion, "+
					 "            'EFECTIVO', 'REIMP.(EF)', "+
					 "            DECODE(tfp.tipforpag_id, "+
					 "                13, 'REIMP.(PGLINK)', "+
					 "                 6, 'REIMP.(DEPOSITO)', "+
					 "                12, 'REIMP.(ORBIS)', "+
					 "                14, 'REIMP.(BETTEREZ)', "+
					 "                15, 'REIMP.(BETTEREZ)', "+
					 "                    'REIMP.(TC)' "+
					 "            ) "+
					 "        ), "+
					 "    'POSTERGACION', "+
					 "        DECODE( "+
					 "            tfp.c_denominacion, "+
					 "            'EFECTIVO', 'POST.(EF)', "+
					 "            DECODE(tfp.tipforpag_id, "+
					 "                13, 'POST.(PGLINK)', "+
					 "                 6, 'POST.(DEPOSITO)', "+
					 "                12, 'POST.(ORBIS)', "+
					 "                14, 'POST.(BETTEREZ)', "+
					 "                15, 'POST.(BETTEREZ)', "+
					 "                    'POST.(TC)' "+
					 "            ) "+
					 "        ), "+
					 "    'POSTERGACION FA', "+
					 "        DECODE( "+
					 "            tfp.c_denominacion, "+
					 "            'EFECTIVO', 'POST.FA.(EF)', "+
					 "            DECODE(tfp.tipforpag_id, "+
					 "                13, 'POST.FA.(PGLINK)', "+
					 "                 6, 'POST.FA.(DEPOSITO)', "+
					 "                12, 'POST.FA.(ORBIS)', "+
					 "                14, 'POST.FA.(BETTEREZ)', "+
					 "                15, 'POST.FA.(BETTEREZ)', "+
					 "                    'POST.FA.(TC)' "+
					 "            ) "+
					 "        ), "+
					 "    DECODE( "+
					 "        tm.tipmov_id, "+
					 "        15, "+
					 "            DECODE( "+
					 "                tfp.c_denominacion, "+
					 "                'EFECTIVO', 'GAS.ADM(EFE)', "+
					 "                DECODE(tfp.tipforpag_id, "+
					 "                    13, 'GAS.ADM(PGLINK)', "+
					 "                     6, 'GAS.ADM(DEPOSITO)', "+
					 "                    12, 'GAS.ADM(ORBIS)', "+
					 "                        'GAS.ADM(TC)' "+
					 "                ) "+
					 "            ), "+
					 "        DECODE( "+
					 "            vp.tipcom_id, "+
					 "            8, 'NOTA CREDITO', "+
					 "            DECODE( "+
					 "                tm.c_denominacion, "+
					 "                'EFECTIVO', "+
					 "                    DECODE( "+
					 "                        tfp.c_denominacion, "+
					 "                        'EFECTIVO', 'V.(EF)', "+
					 "                        DECODE(tfp.tipforpag_id, "+
					 "                            13, 'V.(PGLINK)', "+
					 "                             6, 'V.(DEPOSITO)', "+
					 "                            12, 'V.(ORBIS)', "+
					 "                            14, 'V.(BETTEREZ)', "+
					 "                            15, 'V.(BETTEREZ)', "+
					 "                                'V.(TC)' "+
					 "                        ) "+
					 "                    ) "+
					 "            ) "+
					 "        ) "+
					 "    ) "+
					 " ) TIPOVENTA, ";
		
		return sql;
	}
	
	public String getQueryLiquidacionesChachaJaen(int agencia_id, Integer usuario_id, String fechaInicio, String fechaFin){
		String sql = " SELECT "+
		             " vp.venpas_id, "+
		             " vp.c_numcontrol NroControl, "+
		             " vp.c_numboleto NroBoleto, "+
		             " vp.c_numbolant NroBoletoRef, "+
		             " p.c_apepat ApePat, "+
		             " p.c_apemat ApeMat, "+
		             " p.c_nombre Nombre, "+
		             " vp.audfecins FechaActualizacion, "+
		             " vp.n_tarifa MontoBase, "+
		             " vp.n_recargo Recargo, "+
		             " vp.n_descuento Descuento, "+
		             " vp.n_acuenta ACuenta, "+
		             " vp.n_penalidad Penalidad, "+
		             " vp.n_imppagefe MIXTO_EFECTIVO, "+
		             " vp.n_imppagtar MIXTO_TARJETA, "+
		             " vp.n_imppag NetoPagado, "+
		             " fp.forpag_id, "+
		             " fp.c_denominacion FormaPago, "+
		             " tfp.c_denominacion TipoFormaPago, "+
		             " otc.c_denominacion TipoTarjeta, "+
		             " tm.c_denominacion TipoMovimiento, "+
		               getQueryTipoVenta()+
		             " u.c_apepat apepatusu, "+
		             " u.c_nombre nombreusu, "+
		             " u.c_login, "+
		             " vp.d_fecliq, "+
		             " ag.c_denominacion as agencia, "+
		             " vp.tipmov_id, "+
		             " vp.liquidacion_id, "+
		             " vp.tipforpag_id, "+
		             " vp.tipcom_id, "+
		             " vp.empresa_id, "+
		             " os.C_NROOR || '-' || os.C_NROSE nroOS, "+
		             " vp.C_STATEOS, "+
		             " nvl(vp.N_DIFTAR, 0.0) DIFERENTAR, "+
		             " nvl(vp.C_OBSERVACIONES, 'NO HAY REGISTROS') OBSERVACION, "+
		             " rt.ruta_id, "+
		             " rt.c_origen, "+
		             " rt.c_destino, "+
		             // AGREGADO
				     " to_char(vp.d_fecexpres, 'dd/mm/yyyy') D_FECEXPRESS, vp.c_horexpres, vp.nro_operation_niubiz, vp.nro_cip_pagoefectivo, vp.n_numopeban, vp.c_email_contacto, vp.correo_enviado correoEnviado1 "+
				     // AGREGADO
		          " FROM "+
		             " vrtvenpas vp "+
		             " INNER JOIN vrmpasajero p ON p.pasajero_id = vp.pasajero_id "+
		             " INNER JOIN vrmforpag fp ON fp.forpag_id = vp.forpag_id "+
		             " INNER JOIN vrmtipforpag tfp ON tfp.tipforpag_id = vp.tipforpag_id "+
		             " INNER JOIN vrmtipmov tm ON tm.tipmov_id = vp.tipmov_id "+
		             " INNER JOIN vrmusuario u ON u.usuario_id = vp.usuario_id "+
		             " LEFT JOIN vrmtarcre tc ON tc.tarcre_id = vp.tarcre_id "+
		             " LEFT JOIN vrmopetarcre otc ON otc.opetarcre_id = tc.opetarcre_id "+
		             " INNER JOIN vrmagencia ag ON (ag.agencia_id = vp.agencia_id) "+
		             " LEFT JOIN vrtordenser os on os.ORDENSER_ID = vp.ORDENSER_ID "+
		             " INNER JOIN VRMRUTA rt on rt.ruta_id = vp.ruta_id "+
		          " WHERE "+
		             " vp.agencia_id = "+agencia_id+
		             " AND vp.usuario_id = NVL("+usuario_id+", vp.usuario_id) "+
		             " AND vp.d_fecliq BETWEEN to_date('"+fechaInicio+"', 'yyyy-mm-dd') AND to_date('"+fechaFin+"', 'yyyy-mm-dd') "+
		             " AND vp.ruta_id in (630,629,602,594) "+
		             " AND vp.forpag_id = 1 "+
		             " AND vp.tipcom_id = 5 "+
		             " AND vp.n_tarifa > 0 "+
		             " AND vp.tipmov_id NOT IN (4,5,6,10,11,12,13) ";
		
		return sql;
	}
	
	public String getQueryVentas(int agencia_id, Integer usuario_id, String fechaInicio, String fechaFin){
		String sql = " SELECT "+
		             " vp.venpas_id, "+
		             " vp.c_numcontrol NroControl, "+
		             " vp.c_numboleto NroBoleto, "+
		             " vp.c_numbolant NroBoletoRef, "+
		             " p.c_apepat ApePat, "+
		             " p.c_apemat ApeMat, "+
		             " p.c_nombre Nombre, "+
		             " vp.audfecins FechaActualizacion, "+
		             " vp.n_tarifa MontoBase, "+
		             " vp.n_recargo Recargo, "+
		             " vp.n_descuento Descuento, "+
		             " vp.n_acuenta ACuenta, "+
		             " vp.n_penalidad Penalidad, "+
		             " vp.n_imppagefe MIXTO_EFECTIVO, "+
		             " vp.n_imppagtar MIXTO_TARJETA, "+
		             " vp.n_imppag NetoPagado, "+
		             " fp.forpag_id, "+
		             " fp.c_denominacion FormaPago, "+
		             " tfp.c_denominacion TipoFormaPago, "+
		             " otc.c_denominacion TipoTarjeta, "+
		             " tm.c_denominacion TipoMovimiento, "+
		               getQueryTipoVenta()+
		             " u.c_apepat apepatusu, "+
		             " u.c_nombre nombreusu, "+
		             " u.c_login, "+
		             " vp.d_fecliq, "+
		             " ag.c_denominacion as agencia, "+
		             " vp.tipmov_id, "+
		             " vp.liquidacion_id, "+
		             " vp.tipforpag_id, "+
		             " vp.tipcom_id, "+
		             " vp.empresa_id, "+
		             " os.C_NROOR || '-' || os.C_NROSE nroOS, "+
		             " vp.C_STATEOS, "+
		             " nvl(vp.N_DIFTAR, 0.0) DIFERENTAR, "+
		             " nvl(vp.C_OBSERVACIONES, 'NO HAY REGISTROS') OBSERVACION, "+
		             " rt.ruta_id, "+
		             " rt.c_origen, "+
		             " rt.c_destino, "+
		             // AGREGADO
				     " to_char(vp.d_fecexpres, 'dd/mm/yyyy') D_FECEXPRESS, vp.c_horexpres, vp.nro_operation_niubiz, vp.nro_cip_pagoefectivo, vp.n_numopeban, vp.c_email_contacto, "+ 
		             " CASE WHEN vp.correo_enviado = 0 THEN vsend.estado ELSE vp.correo_enviado END AS correoEnviado1 "+
				     // AGREGADO
		          " FROM "+
		             " vrtvenpas vp "+
		             " INNER JOIN vrmpasajero p ON p.pasajero_id = vp.pasajero_id "+
		             " INNER JOIN vrmforpag fp ON fp.forpag_id = vp.forpag_id "+
		             " INNER JOIN vrmtipforpag tfp ON tfp.tipforpag_id = vp.tipforpag_id "+
		             " INNER JOIN vrmtipmov tm ON tm.tipmov_id = vp.tipmov_id "+
		             " INNER JOIN vrmusuario u ON u.usuario_id = vp.usuario_id "+
		             " LEFT JOIN vrmtarcre tc ON tc.tarcre_id = vp.tarcre_id "+
		             " LEFT JOIN vrmopetarcre otc ON otc.opetarcre_id = tc.opetarcre_id "+
		             " INNER JOIN vrmagencia ag ON (ag.agencia_id = vp.agencia_id) "+
		             " LEFT JOIN vrtordenser os on os.ORDENSER_ID = vp.ORDENSER_ID "+
		             " INNER JOIN VRMRUTA rt on rt.ruta_id = vp.ruta_id "+
		             " LEFT JOIN vrtsendmailpasajeros vsend ON vp.correo_enviado = 0 AND tfp.tipforpag_id = 13 AND vsend.id_venpas LIKE '%' || vp.venpas_id || '%' AND vsend.audfecins >= TO_DATE('"+fechaInicio+"', 'yyyy-mm-dd') "+
		          " WHERE "+
		             " vp.agencia_id = "+agencia_id+
		             " AND vp.usuario_id = NVL("+usuario_id+", vp.usuario_id) "+
		             " AND vp.d_fecliq BETWEEN to_date('"+fechaInicio+"', 'yyyy-mm-dd') AND to_date('"+fechaFin+"', 'yyyy-mm-dd') "+
		             " AND vp.forpag_id = 1 "+
		             " AND vp.tipcom_id in (1,7,2,8,9) "+
		             " AND vp.n_tarifa > 0 "+
		             " AND vp.tipmov_id NOT IN (4,5,6,10,11,12,13) ";
		
		return sql;
	}
	
	public String getQueryReciboCaja(int agencia_id, Integer usuario_id, String fechaInicio, String fechaFin){
		String sql = " SELECT "+
		             " vp.venpas_id, "+
		             " vp.c_numcontrol NroControl, "+
		             " vp.c_numboleto NroBoleto, "+
		             " vp.c_numbolant NroBoletoRef, "+
		             " p.c_apepat ApePat, "+
		             " p.c_apemat ApeMat, "+
		             " p.c_nombre Nombre, "+
		             " vp.audfecins FechaActualizacion, "+
		             " vp.n_tarifa MontoBase, "+
		             " vp.n_recargo Recargo, "+
		             " vp.n_descuento Descuento, "+
		             " vp.n_acuenta ACuenta, "+
		             " vp.n_penalidad Penalidad, "+
		             " vp.n_imppagefe MIXTO_EFECTIVO, "+
		             " vp.n_imppagtar MIXTO_TARJETA, "+
		             " vp.n_imppag NetoPagado, "+
		             " fp.forpag_id, "+
		             " fp.c_denominacion FormaPago, "+
		             " tfp.c_denominacion TipoFormaPago, "+
		             " otc.c_denominacion TipoTarjeta, "+
		             " tm.c_denominacion TipoMovimiento, "+
		             " DECODE(tfp.c_denominacion, 'EFECTIVO', 'RC.(EF)', DECODE(tfp.c_Denominacion, 'TARJETA', 'RC.(TC)', 'RC.(DEP)')) TIPOVENTA, "+
		             " u.c_apepat apepatusu, "+
		             " u.c_nombre nombreusu, "+
		             " u.c_login, "+
		             " vp.d_fecliq, "+
		             " ag.c_denominacion as agencia, "+
		             " vp.tipmov_id, "+
		             " vp.liquidacion_id, "+
		             " vp.tipforpag_id, "+
		             " vp.tipcom_id, "+
		             " vp.empresa_id, "+
		             " os.C_NROOR || '-' || os.C_NROSE nroOS, "+
		             " vp.C_STATEOS, "+
		             " nvl(vp.N_DIFTAR, 0.0) DIFERENTAR, "+
		             " nvl(vp.C_OBSERVACIONES, 'NO HAY REGISTROS') OBSERVACION, "+
		             " rt.ruta_id, "+
		             " rt.c_origen, "+
		             " rt.c_destino, "+
		             // AGREGADO
				     " to_char(vp.d_fecexpres, 'dd/mm/yyyy') D_FECEXPRESS, vp.c_horexpres, vp.nro_operation_niubiz, vp.nro_cip_pagoefectivo, vp.n_numopeban, vp.c_email_contacto, vp.correo_enviado correoEnviado1 "+
				     // AGREGADO
		          " FROM "+
		             " vrtvenpas vp "+
		             " INNER JOIN vrmpasajero p ON p.pasajero_id = vp.pasajero_id "+
		             " INNER JOIN vrmforpag fp ON fp.forpag_id = vp.forpag_id "+
		             " INNER JOIN vrmtipforpag tfp ON tfp.tipforpag_id = vp.tipforpag_id "+
		             " INNER JOIN vrmtipmov tm ON tm.tipmov_id = vp.tipmov_id "+
		             " INNER JOIN vrmusuario u ON u.usuario_id = vp.usuario_id "+
		             " LEFT JOIN vrmtarcre tc ON tc.tarcre_id = vp.tarcre_id "+
		             " LEFT JOIN vrmopetarcre otc ON otc.opetarcre_id = tc.opetarcre_id "+
		             " INNER JOIN vrmagencia ag ON (ag.agencia_id = vp.agencia_id) "+
		             " LEFT JOIN vrtordenser os on os.ORDENSER_ID = vp.ORDENSER_ID "+
		             " INNER JOIN VRMRUTA rt on rt.ruta_id = vp.ruta_id "+
		          " WHERE "+
		             " vp.agencia_id = "+agencia_id+
		             " AND vp.usuario_id = NVL("+usuario_id+", vp.usuario_id) "+
		             " AND vp.d_fecliq BETWEEN to_date('"+fechaInicio+"', 'yyyy-mm-dd') AND to_date('"+fechaFin+"', 'yyyy-mm-dd') "+
		             " AND vp.tipcom_id = 3 "+
		             " AND vp.forpag_id = 1 "+
		             " AND vp.tipmov_id NOT IN (5,6,13,14) ";
		
		return sql;
	}
	
	public String getQueryPrepagado(int agencia_id, Integer usuario_id, String fechaInicio, String fechaFin){
		String sql = " SELECT "+
		             " vp.venpas_id, "+
		             " vp.c_numcontrol NroControl, "+
		             " vp.c_numboleto NroBoleto, "+
		             " vp.c_numbolant NroBoletoRef, "+
		             " p.c_apepat ApePat, "+
		             " p.c_apemat ApeMat, "+
		             " p.c_nombre Nombre, "+
		             " vp.audfecins FechaActualizacion, "+
		             " vp.n_tarifa MontoBase, "+
		             " vp.n_recargo Recargo, "+
		             " vp.n_descuento Descuento, "+
		             " vp.n_acuenta ACuenta, "+
		             " vp.n_penalidad Penalidad, "+
		             " vp.n_imppagefe MIXTO_EFECTIVO, "+
		             " vp.n_imppagtar MIXTO_TARJETA, "+
		             " vp.n_imppag NetoPagado, "+
		             " fp.forpag_id, "+
		             " fp.c_denominacion FormaPago, "+
		             " tfp.c_denominacion TipoFormaPago, "+
		             " otc.c_denominacion TipoTarjeta, "+
		             " tm.c_denominacion TipoMovimiento, "+
		             " DECODE(tfp.c_denominacion, 'EFECTIVO', 'PREP.(EF)', 'PREP.(TC)') TIPOVENTA, "+
		             " u.c_apepat apepatusu, "+
		             " u.c_nombre nombreusu, "+
		             " u.c_login, "+
		             " vp.d_fecliq, "+
		             " ag.c_denominacion as agencia, "+
		             " vp.tipmov_id, "+
		             " vp.liquidacion_id, "+
		             " vp.tipforpag_id, "+
		             " vp.tipcom_id, "+
		             " vp.empresa_id, "+
		             " os.C_NROOR || '-' || os.C_NROSE nroOS, "+
		             " vp.C_STATEOS, "+
		             " nvl(vp.N_DIFTAR, 0.0) DIFERENTAR, "+
		             " nvl(vp.C_OBSERVACIONES, 'NO HAY REGISTROS') OBSERVACION, "+
		             " rt.ruta_id, "+
		             " rt.c_origen, "+
		             " rt.c_destino, "+
		             // AGREGADO
				     " to_char(vp.d_fecexpres, 'dd/mm/yyyy') D_FECEXPRESS, vp.c_horexpres, vp.nro_operation_niubiz, vp.nro_cip_pagoefectivo, vp.n_numopeban, vp.c_email_contacto, vp.correo_enviado correoEnviado1 "+
				     // AGREGADO
		          " FROM "+
		             " vrtvenpas vp "+
		             " INNER JOIN vrmpasajero p ON p.pasajero_id = vp.pasajero_id "+
		             " INNER JOIN vrmforpag fp ON fp.forpag_id = vp.forpag_id "+
		             " INNER JOIN vrmtipforpag tfp ON tfp.tipforpag_id = vp.tipforpag_id "+
		             " INNER JOIN vrmtipmov tm ON tm.tipmov_id = vp.tipmov_id "+
		             " INNER JOIN vrmusuario u ON u.usuario_id = vp.usuario_id "+
		             " LEFT JOIN vrmtarcre tc ON tc.tarcre_id = vp.tarcre_id "+
		             " LEFT JOIN vrmopetarcre otc ON otc.opetarcre_id = tc.opetarcre_id "+
		             " INNER JOIN vrmagencia ag ON (ag.agencia_id = vp.agencia_id) "+
		             " LEFT JOIN vrtordenser os ON os.ORDENSER_ID = vp.ORDENSER_ID "+
		             " INNER JOIN VRMRUTA rt ON rt.ruta_id = vp.ruta_id "+
		          " WHERE "+
		             " vp.agencia_id = "+agencia_id+
		             " AND vp.usuario_id = NVL("+usuario_id+", vp.usuario_id) "+
		             " AND vp.d_fecliq BETWEEN to_date('"+fechaInicio+"', 'yyyy-mm-dd') AND to_date('"+fechaFin+"', 'yyyy-mm-dd') "+
		             " AND vp.tipmov_id = 12 "+
		             " AND vp.tipmov_id NOT IN (5,6,13,14) ";
		
		return sql;
	}
	
	public String getQueryCredito(int agencia_id, Integer usuario_id, String fechaInicio, String fechaFin){
		String sql = " SELECT "+
		             " vp.venpas_id, "+
		             " vp.c_numcontrol NroControl, "+
		             " vp.c_numboleto NroBoleto, "+
		             " vp.c_numbolant NroBoletoRef, "+
		             " p.c_apepat ApePat, "+
		             " p.c_apemat ApeMat, "+
		             " p.c_nombre Nombre, "+
		             " vp.audfecins FechaActualizacion, "+
		             " vp.n_tarifa MontoBase, "+
		             " vp.n_recargo Recargo, "+
		             " vp.n_descuento Descuento, "+
		             " vp.n_acuenta ACuenta, "+
		             " vp.n_penalidad Penalidad, "+
		             " vp.n_imppagefe MIXTO_EFECTIVO, "+
		             " vp.n_imppagtar MIXTO_TARJETA, "+
		             " vp.n_imppag NetoPagado, "+
		             " fp.forpag_id, "+
		             " fp.c_denominacion FormaPago, "+
		             " tfp.c_denominacion TipoFormaPago, "+
		             " otc.c_denominacion TipoTarjeta, "+
		             " tm.c_denominacion TipoMovimiento, "+
		             " 'CREDITO' TIPOVENTA, "+
		             " u.c_apepat apepatusu, "+
		             " u.c_nombre nombreusu, "+
		             " u.c_login, "+
		             " vp.d_fecliq, "+
		             " ag.c_denominacion as agencia, "+
		             " vp.tipmov_id, "+
		             " vp.liquidacion_id, "+
		             " vp.tipforpag_id, "+
		             " vp.tipcom_id, "+
		             " vp.empresa_id, "+
		             " os.C_NROOR || '-' || os.C_NROSE nroOS, "+
		             " vp.C_STATEOS, "+
		             " nvl(vp.N_DIFTAR, 0.0) DIFERENTAR, "+
		             " nvl(vp.C_OBSERVACIONES, 'NO HAY REGISTROS') OBSERVACION, "+
		             " rt.ruta_id, "+
		             " rt.c_origen, "+
		             " rt.c_destino, "+
		             // AGREGADO
				     " to_char(vp.d_fecexpres, 'dd/mm/yyyy') D_FECEXPRESS, vp.c_horexpres, vp.nro_operation_niubiz, vp.nro_cip_pagoefectivo, vp.n_numopeban, vp.c_email_contacto, "+
		             " CASE WHEN vp.correo_enviado = 0 THEN vsend.estado ELSE vp.correo_enviado END AS correoEnviado1 "+
				     // AGREGADO
		          " FROM "+
		             " vrtvenpas vp "+
		             " INNER JOIN vrmpasajero p ON p.pasajero_id = vp.pasajero_id "+
		             " INNER JOIN vrmforpag fp ON fp.forpag_id = vp.forpag_id "+
		             " INNER JOIN vrmtipforpag tfp ON tfp.tipforpag_id = vp.tipforpag_id "+
		             " INNER JOIN vrmtipmov tm ON tm.tipmov_id = vp.tipmov_id "+
		             " INNER JOIN vrmusuario u ON u.usuario_id = vp.usuario_id "+
		             " LEFT JOIN vrmtarcre tc ON tc.tarcre_id = vp.tarcre_id "+
		             " LEFT JOIN vrmopetarcre otc ON otc.opetarcre_id = tc.opetarcre_id "+
		             " INNER JOIN vrmagencia ag ON (ag.agencia_id = vp.agencia_id) "+
		             " LEFT JOIN vrtordenser os on os.ORDENSER_ID = vp.ORDENSER_ID "+
		             " INNER JOIN VRMRUTA rt on rt.ruta_id = vp.ruta_id "+
		             " LEFT JOIN vrtsendmailpasajeros vsend ON vp.correo_enviado = 0 AND vsend.archivos LIKE '%' || vp.c_numboleto || '%' AND vsend.audfecins >= TO_DATE('"+fechaInicio+"', 'yyyy-mm-dd') "+
		          " WHERE "+
		             " vp.agencia_id = "+agencia_id+
		             " AND vp.usuario_id = NVL("+usuario_id+", vp.usuario_id) "+
		             " AND vp.d_fecliq BETWEEN to_date('"+fechaInicio+"', 'yyyy-mm-dd') AND to_date('"+fechaFin+"', 'yyyy-mm-dd') "+
		             " AND vp.forpag_id = 2 "+
		             " AND vp.tipcom_id in (1,7,2) "+
		             " AND vp.tipmov_id NOT IN (5,6,13,14) "+
		             " AND vp.c_numboleto IS NOT NULL ";
		
		//System.out.println(sql);
		
		return sql;
	}
	
	public String getQueryCortesias(int agencia_id, Integer usuario_id, String fechaInicio, String fechaFin){
		String sql = " SELECT "+
			         " vp.venpas_id, "+
			         " vp.c_numcontrol NroControl, "+
			         " vp.c_numboleto NroBoleto, "+
			         " vp.c_numbolant NroBoletoRef, "+
			         " p.c_apepat ApePat, "+
			         " p.c_apemat ApeMat, "+
			         " p.c_nombre Nombre, "+
			         " vp.audfecins FechaActualizacion, "+
			         " vp.n_tarifa MontoBase, "+
			         " vp.n_recargo Recargo, "+
			         " vp.n_descuento Descuento, "+
			         " vp.n_acuenta ACuenta, "+
			         " vp.n_penalidad Penalidad, "+
			         " vp.n_imppagefe MIXTO_EFECTIVO, "+
			         " vp.n_imppagtar MIXTO_TARJETA, "+
			         " vp.n_imppag NetoPagado, "+
			         " fp.forpag_id, "+
			         " fp.c_denominacion FormaPago, "+
			         " tfp.c_denominacion TipoFormaPago, "+
			         " otc.c_denominacion TipoTarjeta, "+
			         " tm.c_denominacion TipoMovimiento, "+
			         " DECODE(tfp.tipforpag_id, 3, 'CORTXCUMP', 5, 'CORTXPUNT', 10, 'CORTXPASE', 9, 'CORTXORDT', 'CORTESIA') TIPOVENTA, "+
			         " u.c_apepat apepatusu, "+
			         " u.c_nombre nombreusu, "+
			         " u.c_login, "+
			         " vp.d_fecliq, "+
			         " ag.c_denominacion as agencia, "+
			         " vp.tipmov_id, "+
			         " vp.liquidacion_id, "+
			         " vp.tipforpag_id, "+
			         " vp.tipcom_id, "+
			         " vp.empresa_id, "+
			         " os.C_NROOR || '-' || os.C_NROSE nroOS, "+
			         " vp.C_STATEOS, "+
			         " nvl(vp.N_DIFTAR, 0.0) DIFERENTAR, "+
			         " nvl(vp.C_OBSERVACIONES, 'NO HAY REGISTROS') OBSERVACION, "+
			         " rt.ruta_id, "+
			         " rt.c_origen, "+
			         " rt.c_destino, "+
			         // AGREGADO
				     " to_char(vp.d_fecexpres, 'dd/mm/yyyy') D_FECEXPRESS, vp.c_horexpres, vp.nro_operation_niubiz, vp.nro_cip_pagoefectivo, vp.n_numopeban, vp.c_email_contacto, vp.correo_enviado correoEnviado1 "+
				     // AGREGADO
			      " FROM "+
			         " vrtvenpas vp "+
			         " INNER JOIN vrmpasajero p ON p.pasajero_id = vp.pasajero_id "+
			         " INNER JOIN vrmforpag fp ON fp.forpag_id = vp.forpag_id "+
			         " INNER JOIN vrmtipforpag tfp ON tfp.tipforpag_id = vp.tipforpag_id "+
			         " INNER JOIN vrmtipmov tm ON tm.tipmov_id = vp.tipmov_id "+
			         " INNER JOIN vrmusuario u ON u.usuario_id = vp.usuario_id "+
			         " LEFT JOIN vrmtarcre tc ON tc.tarcre_id = vp.tarcre_id "+
			         " LEFT JOIN vrmopetarcre otc ON otc.opetarcre_id = tc.opetarcre_id "+
			         " INNER JOIN vrmagencia ag ON (ag.agencia_id = vp.agencia_id) "+
			         " LEFT JOIN vrtordenser os on os.ORDENSER_ID = vp.ORDENSER_ID "+
			         " INNER JOIN VRMRUTA rt on rt.ruta_id = vp.ruta_id "+
			      " WHERE "+
			         " vp.agencia_id = "+agencia_id+
			         " AND vp.usuario_id = NVL("+usuario_id+", vp.usuario_id) "+
			         " AND vp.d_fecliq BETWEEN to_date('"+fechaInicio+"', 'yyyy-mm-dd') AND to_date('"+fechaFin+"', 'yyyy-mm-dd') "+
			         " AND fp.forpag_id = 3 "+
			         " AND vp.c_numboleto IS NOT NULL "+
			         " AND vp.tipmov_id NOT IN (5,6,13,14) "+
			         " AND vp.venpas_id = any(SELECT MAX(vrt2.venpas_id) venpas_id FROM vrtvenpas vrt2 where vrt2.c_numcontrol = vp.c_numcontrol) ";
		
		return sql;
	}
	
	public String getQueryDevoluciones(int agencia_id, Integer usuario_id, String fechaInicio, String fechaFin){
		String sql = " SELECT "+
			         " vp.venpas_id, "+
			         " vp.c_numcontrol NroControl, "+
			         " vp.c_numboleto NroBoleto, "+
			         " vp.c_numbolant NroBoletoRef, "+
			         " p.c_apepat ApePat, "+
			         " p.c_apemat ApeMat, "+
			         " p.c_nombre Nombre, "+
			         " vp.audfecins FechaActualizacion, "+
			         " vp.n_tarifa MontoBase, "+
			         " vp.n_recargo Recargo, "+
			         " vp.n_descuento Descuento, "+
			         " vp.n_acuenta ACuenta, "+
			         " vp.n_penalidad Penalidad, "+
			         " vp.n_imppagefe MIXTO_EFECTIVO, "+
			         " vp.n_imppagtar MIXTO_TARJETA, "+
			         " vp.n_imppag NetoPagado, "+
			         " fp.forpag_id, "+
			         " fp.c_denominacion FormaPago, "+
			         " tfp.c_denominacion TipoFormaPago, "+
			         " otc.c_denominacion TipoTarjeta, "+
			         " tm.c_denominacion TipoMovimiento, "+
			         " DECODE(vp.n_penalidad, 0, 'DEV.100%', '<DEV.100%') TIPOVENTA, "+
			         " u.c_apepat apepatusu, "+
			         " u.c_nombre nombreusu, "+
			         " u.c_login, "+
			         " vp.d_fecliq, "+
			         " ag.c_denominacion as agencia, "+
			         " vp.tipmov_id, "+
			         " vp.liquidacion_id, "+
			         " vp.tipforpag_id, "+
			         " vp.tipcom_id, "+
			         " vp.empresa_id, "+
			         " os.C_NROOR || '-' || os.C_NROSE nroOS, "+
			         " vp.C_STATEOS, "+
			         " nvl(vp.N_DIFTAR, 0.0) DIFERENTAR, "+
			         " nvl(vp.C_OBSERVACIONES, 'NO HAY REGISTROS') OBSERVACION, "+
			         " rt.ruta_id, "+
			         " rt.c_origen, "+
			         " rt.c_destino, "+
			         // AGREGADO
				     " to_char(vp.d_fecexpres, 'dd/mm/yyyy') D_FECEXPRESS, vp.c_horexpres, vp.nro_operation_niubiz, vp.nro_cip_pagoefectivo, vp.n_numopeban, vp.c_email_contacto, vp.correo_enviado correoEnviado1 "+
				     // AGREGADO
			      " FROM "+
			         " vrtvenpas vp "+
			         " INNER JOIN vrmpasajero p ON p.pasajero_id = vp.pasajero_id "+
			         " INNER JOIN vrmforpag fp ON fp.forpag_id = vp.forpag_id "+
			         " INNER JOIN vrmtipforpag tfp ON tfp.tipforpag_id = vp.tipforpag_id "+
			         " INNER JOIN vrmtipmov tm ON tm.tipmov_id = vp.tipmov_id "+
			         " INNER JOIN vrmusuario u ON u.usuario_id = vp.usuario_id "+
			         " LEFT JOIN vrmtarcre tc ON tc.tarcre_id = vp.tarcre_id "+
			         " LEFT JOIN vrmopetarcre otc ON otc.opetarcre_id = tc.opetarcre_id "+
			         " INNER JOIN vrmagencia ag ON (ag.agencia_id = vp.agencia_id) "+
			         " LEFT JOIN vrtordenser os on os.ORDENSER_ID = vp.ORDENSER_ID "+
			         " INNER JOIN VRMRUTA rt on rt.ruta_id = vp.ruta_id "+
			      " WHERE "+
			         " vp.agencia_id = "+agencia_id+
			         " AND vp.usuario_id = NVL("+usuario_id+", vp.usuario_id) "+
			         " AND vp.d_fecliq BETWEEN to_date('"+fechaInicio+"', 'yyyy-mm-dd') AND to_date('"+fechaFin+"', 'yyyy-mm-dd') "+
			         " AND tm.tipmov_id = 6 ";
		
		return sql;
	}
	
	public String getQueryAnulados(int agencia_id, Integer usuario_id, String fechaInicio, String fechaFin){
		
		String sql = " SELECT "+
			         " vp.venpas_id, "+
			         " vp.c_numcontrol NroControl, "+
			         " vp.c_numboleto NroBoleto, "+
			         " vp.c_numbolant NroBoletoRef, "+
			         " p.c_apepat ApePat, "+
			         " p.c_apemat ApeMat, "+
			         " p.c_nombre Nombre, "+
			         " vp.audfecins FechaActualizacion, "+
			         " vp.n_tarifa MontoBase, "+
			         " vp.n_recargo Recargo, "+
			         " vp.n_descuento Descuento, "+
			         " vp.n_acuenta ACuenta, "+
			         " vp.n_penalidad Penalidad, "+
			         " vp.n_imppagefe MIXTO_EFECTIVO, "+
			         " vp.n_imppagtar MIXTO_TARJETA, "+
			         " vp.n_imppag NetoPagado, "+
			         " fp.forpag_id, "+
			         " fp.c_denominacion FormaPago, "+
			         " tfp.c_denominacion TipoFormaPago, "+
			         " otc.c_denominacion TipoTarjeta, "+
			         " tm.c_denominacion TipoMovimiento, "+
			         " 'ANULADO' TIPOVENTA, "+
			         " u.c_apepat apepatusu, "+
			         " u.c_nombre nombreusu, "+
			         " u.c_login, "+
			         " vp.d_fecliq, "+
			         " ag.c_denominacion as agencia, "+
			         " vp.tipmov_id, "+
			         " vp.liquidacion_id, "+
			         " vp.tipforpag_id, "+
			         " vp.tipcom_id, "+
			         " vp.empresa_id, "+
			         " os.C_NROOR || '-' || os.C_NROSE nroOS, "+
			         " vp.C_STATEOS, "+
			         " nvl(vp.N_DIFTAR, 0.0) DIFERENTAR, "+
			         " nvl(vp.C_OBSERVACIONES, 'NO HAY REGISTROS') OBSERVACION, "+
			         " rt.ruta_id, "+
			         " rt.c_origen, "+
			         " rt.c_destino, "+
				     // AGREGADO
				     " to_char(vp.d_fecexpres, 'dd/mm/yyyy') D_FECEXPRESS, vp.c_horexpres, vp.nro_operation_niubiz, vp.nro_cip_pagoefectivo, vp.n_numopeban, vp.c_email_contacto, vp.correo_enviado correoEnviado1 "+
				     // AGREGADO
				     " FROM "+
				     " vrtvenpas vp "+
				     " INNER JOIN vrmpasajero p ON p.pasajero_id = vp.pasajero_id "+
				     " INNER JOIN vrmforpag fp ON fp.forpag_id = vp.forpag_id "+
				     " INNER JOIN vrmtipforpag tfp ON tfp.tipforpag_id = vp.tipforpag_id "+
				     " INNER JOIN vrmtipmov tm ON tm.tipmov_id = vp.tipmov_id "+
				     " INNER JOIN vrmusuario u ON u.usuario_id = vp.usuario_id "+
				     " LEFT JOIN vrmtarcre tc ON tc.tarcre_id = vp.tarcre_id "+
				     " LEFT JOIN vrmopetarcre otc ON otc.opetarcre_id = tc.opetarcre_id "+
				     " INNER JOIN vrmagencia ag ON (ag.agencia_id = vp.agencia_id) "+
				     " LEFT JOIN vrtordenser os on os.ORDENSER_ID = vp.ORDENSER_ID "+
				     " INNER JOIN VRMRUTA rt on rt.ruta_id = vp.ruta_id "+
				     " WHERE "+
				     " vp.agencia_id = "+agencia_id+
				     " AND vp.usuario_id = NVL("+usuario_id+", vp.usuario_id) "+
				     " AND vp.d_fecliq BETWEEN to_date('"+fechaInicio+"', 'yyyy-mm-dd') AND to_date('"+fechaFin+"', 'yyyy-mm-dd') "+
				     " AND vp.tipmov_id = 13 "+
				     " AND vp.n_imppag = 0 ";
		
		return sql;
	}
	
//	@Override
//	public List<ReporteDetallado> getReporteDetallado(int agencia_id, Integer usuario_id, String fechaInicio, String fechaFin){
//		List<ReporteDetallado> lstReporteDetallado = new ArrayList<ReporteDetallado>();
//		
//		try{
//			
//			if(usuario_id == 0){
//				usuario_id = null;
//			}
//			
//			//NORMAL
//			/*String sql = " SELECT * FROM ( "+
//						   getQueryAnulados(agencia_id, usuario_id, fechaInicio, fechaFin) + " UNION ALL "+
//						   getQueryDevoluciones(agencia_id, usuario_id, fechaInicio, fechaFin) + " UNION ALL "+
//						   getQueryCortesias(agencia_id, usuario_id, fechaInicio, fechaFin) + " UNION ALL "+
//						   getQueryCredito(agencia_id, usuario_id, fechaInicio, fechaFin) + " UNION ALL "+
//						   getQueryPrepagado(agencia_id, usuario_id, fechaInicio, fechaFin) + " UNION ALL "+
//						   getQueryReciboCaja(agencia_id, usuario_id, fechaInicio, fechaFin) + " UNION ALL "+
//						   getQueryVentas(agencia_id, usuario_id, fechaInicio, fechaFin) + " UNION ALL "+
//						   getQueryLiquidacionesChachaJaen(agencia_id, usuario_id, fechaInicio, fechaFin) + 
//						   " ) GROUP BY VENPAS_ID, NROCONTROL, NROBOLETO, NROBOLETOREF, APEPAT, APEMAT, NOMBRE, FECHAACTUALIZACION, MONTOBASE, RECARGO, DESCUENTO, ACUENTA, PENALIDAD, "+
//						   " MIXTO_EFECTIVO, MIXTO_TARJETA, NETOPAGADO, FORPAG_ID, FORMAPAGO, TIPOFORMAPAGO, TIPOTARJETA, TIPOMOVIMIENTO, TIPOVENTA, APEPATUSU, NOMBREUSU, C_LOGIN, D_FECLIQ, "+
//						   " AGENCIA, TIPMOV_ID, LIQUIDACION_ID, TIPFORPAG_ID, TIPCOM_ID, EMPRESA_ID, NROOS, C_STATEOS, DIFERENTAR, OBSERVACION, RUTA_ID, C_ORIGEN, C_DESTINO, D_FECEXPRESS, C_HOREXPRES, "+
//						   " NRO_OPERATION_NIUBIZ, NRO_CIP_PAGOEFECTIVO, N_NUMOPEBAN, CORREOENVIADO1, CORREOENVIADO2, FECHAINSERCIONLOG, URL_PAGO, EMAIL_PASAJERO, C_EMAIL_CONTACTO, DELAY_CORREO "+
//						   " ORDER BY forpag_id, tipcom_id, tipforpag_id, NroBoleto ";*/
//			
//			//DATOS VACIOS
//			String sql = "SELECT * FROM ( "+
//						 //" SELECT resumen.*, 0 correoEnviado2, '' fechaInsercionLog, '' URL_PAGO, '' EMAIL_PASAJERO, 0 DELAY_CORREO " + 
//						 " SELECT resumen.*, 0 correoEnviado2, vlog.audfecins fechaInsercionLog, vh.url_pago, vh.email_pasajero, " +
//					     " CASE WHEN sysdate >= (vlog.audfecins + (15/1440)) and resumen.correoEnviado1 = 0 THEN 1 ELSE 0 END AS DELAY_CORREO " + 
//					     " FROM ( "+
//						 getQueryAnulados(agencia_id, usuario_id, fechaInicio, fechaFin) + " UNION ALL "+
//						 getQueryDevoluciones(agencia_id, usuario_id, fechaInicio, fechaFin) + " UNION ALL "+
//						 getQueryCortesias(agencia_id, usuario_id, fechaInicio, fechaFin) + " UNION ALL "+
//						 getQueryCredito(agencia_id, usuario_id, fechaInicio, fechaFin) + " UNION ALL "+
//						 getQueryPrepagado(agencia_id, usuario_id, fechaInicio, fechaFin) + " UNION ALL "+
//						 getQueryReciboCaja(agencia_id, usuario_id, fechaInicio, fechaFin) + " UNION ALL "+
//						 getQueryVentas(agencia_id, usuario_id, fechaInicio, fechaFin) + " UNION ALL "+
//						 getQueryLiquidacionesChachaJaen(agencia_id, usuario_id, fechaInicio, fechaFin) + 
//						 " ) resumen "+
//						 " LEFT JOIN vrthistorialventas vh ON vh.numoperacion=resumen.n_numopeban and resumen.tipoventa = 'V.(PGLINK)'"+
//			             " LEFT JOIN vrmlogecommerce vlog ON vlog.c_nropedido=resumen.n_numopeban and (resumen.tipoventa = 'V.(PGLINK)' OR resumen.tipoventa = 'V.(CREDITO)') and ROWNUM=1 "+
//						 " ) GROUP BY VENPAS_ID, NROCONTROL, NROBOLETO, NROBOLETOREF, APEPAT, APEMAT, NOMBRE, FECHAACTUALIZACION, MONTOBASE, RECARGO, DESCUENTO, ACUENTA, PENALIDAD, "+
//						 " MIXTO_EFECTIVO, MIXTO_TARJETA, NETOPAGADO, FORPAG_ID, FORMAPAGO, TIPOFORMAPAGO, TIPOTARJETA, TIPOMOVIMIENTO, TIPOVENTA, APEPATUSU, NOMBREUSU, C_LOGIN, D_FECLIQ, "+
//						 " AGENCIA, TIPMOV_ID, LIQUIDACION_ID, TIPFORPAG_ID, TIPCOM_ID, EMPRESA_ID, NROOS, C_STATEOS, DIFERENTAR, OBSERVACION, RUTA_ID, C_ORIGEN, C_DESTINO, D_FECEXPRESS, C_HOREXPRES, "+
//						 " NRO_OPERATION_NIUBIZ, NRO_CIP_PAGOEFECTIVO, N_NUMOPEBAN, CORREOENVIADO1, CORREOENVIADO2, FECHAINSERCIONLOG, URL_PAGO, EMAIL_PASAJERO, C_EMAIL_CONTACTO, DELAY_CORREO "+
//						 " ORDER BY fechaActualizacion DESC ";
//			
//			/*String sql = "SELECT * FROM ( "+
//						 " SELECT resumen.*, vsend.estado correoEnviado2, vlog.audfecins fechaInsercionLog, vh.url_pago, vh.email_pasajero, " +
//					     " CASE WHEN sysdate >= (vlog.audfecins + (15/1440)) and vsend.estado = 0 THEN 1 ELSE 0 END AS DELAY_CORREO " + 
//					     " FROM ( "+
//						 getQueryAnulados(agencia_id, usuario_id, fechaInicio, fechaFin) + " UNION ALL "+
//						 getQueryDevoluciones(agencia_id, usuario_id, fechaInicio, fechaFin) + " UNION ALL "+
//						 getQueryCortesias(agencia_id, usuario_id, fechaInicio, fechaFin) + " UNION ALL "+
//						 getQueryCredito(agencia_id, usuario_id, fechaInicio, fechaFin) + " UNION ALL "+
//						 getQueryPrepagado(agencia_id, usuario_id, fechaInicio, fechaFin) + " UNION ALL "+
//						 getQueryReciboCaja(agencia_id, usuario_id, fechaInicio, fechaFin) + " UNION ALL "+
//						 getQueryVentas(agencia_id, usuario_id, fechaInicio, fechaFin) + " UNION ALL "+
//						 getQueryLiquidacionesChachaJaen(agencia_id, usuario_id, fechaInicio, fechaFin) + 
//						 " ) resumen "+
//						 " LEFT JOIN vrthistorialventas vh ON vh.numoperacion=resumen.n_numopeban and resumen.tipoventa = 'V.(PGLINK)'"+
//			             " LEFT JOIN vrmlogecommerce vlog ON vlog.c_nropedido=resumen.n_numopeban and (resumen.tipoventa = 'V.(PGLINK)' OR resumen.tipoventa = 'V.(CREDITO)') and ROWNUM=1 "+
//						 " LEFT JOIN vrtsendmailpasajeros vsend ON vsend.id_venpas like '%'|| resumen.venpas_id || '%' and vsend.audfecins >= to_date('"+fechaInicio+"', 'yyyy-mm-dd') "+
//			             " ) GROUP BY VENPAS_ID, NROCONTROL, NROBOLETO, NROBOLETOREF, APEPAT, APEMAT, NOMBRE, FECHAACTUALIZACION, MONTOBASE, RECARGO, DESCUENTO, ACUENTA, PENALIDAD, "+
//						 " MIXTO_EFECTIVO, MIXTO_TARJETA, NETOPAGADO, FORPAG_ID, FORMAPAGO, TIPOFORMAPAGO, TIPOTARJETA, TIPOMOVIMIENTO, TIPOVENTA, APEPATUSU, NOMBREUSU, C_LOGIN, D_FECLIQ, "+
//						 " AGENCIA, TIPMOV_ID, LIQUIDACION_ID, TIPFORPAG_ID, TIPCOM_ID, EMPRESA_ID, NROOS, C_STATEOS, DIFERENTAR, OBSERVACION, RUTA_ID, C_ORIGEN, C_DESTINO, D_FECEXPRESS, C_HOREXPRES, "+
//						 " NRO_OPERATION_NIUBIZ, NRO_CIP_PAGOEFECTIVO, N_NUMOPEBAN, CORREOENVIADO1, CORREOENVIADO2, FECHAINSERCIONLOG, URL_PAGO, EMAIL_PASAJERO, C_EMAIL_CONTACTO, DELAY_CORREO "+
//						 " ORDER BY fechaActualizacion DESC ";*/
//			
//			/*String sql = " SELECT * "+
//						 " FROM ( "+
//						 "   SELECT resumen.*, "+
//						 "          vsend.estado AS correoEnviado2, "+
//						 "          vlog.audfecins AS fechaInsercionLog, "+
//						 "          vh.url_pago, "+
//						 "          vh.email_pasajero, "+
//						 "          CASE "+
//						 "              WHEN SYSDATE >= (vlog.audfecins + (15 / 1440)) "+
//						 "                   AND vsend.estado = 0 THEN 1 "+
//						 "              ELSE 0 "+
//						 "          END AS DELAY_CORREO "+
//						 "   FROM ( "+
//						 getQueryAnulados(agencia_id, usuario_id, fechaInicio, fechaFin) + " UNION ALL "+
//						 getQueryDevoluciones(agencia_id, usuario_id, fechaInicio, fechaFin) + " UNION ALL "+
//						 getQueryCortesias(agencia_id, usuario_id, fechaInicio, fechaFin) + " UNION ALL "+
//						 getQueryCredito(agencia_id, usuario_id, fechaInicio, fechaFin) + " UNION ALL "+
//						 getQueryPrepagado(agencia_id, usuario_id, fechaInicio, fechaFin) + " UNION ALL "+
//						 getQueryReciboCaja(agencia_id, usuario_id, fechaInicio, fechaFin) + " UNION ALL "+
//						 getQueryVentas(agencia_id, usuario_id, fechaInicio, fechaFin) + " UNION ALL "+
//						 getQueryLiquidacionesChachaJaen(agencia_id, usuario_id, fechaInicio, fechaFin) + 
//						 "   ) resumen "+
//						 "   LEFT JOIN vrthistorialventas vh "+
//						 "          ON vh.numoperacion = resumen.n_numopeban "+
//						 "         AND resumen.tipoventa = 'V.(PGLINK)' "+
//						 "   LEFT JOIN ( "+
//						 "       SELECT * "+
//						 "       FROM vrmlogecommerce "+
//						 "       WHERE ROWNUM = 1 "+
//						 "   ) vlog "+
//						 "          ON vlog.c_nropedido = resumen.n_numopeban "+
//						 "         AND (resumen.tipoventa = 'V.(PGLINK)' OR resumen.tipoventa = 'V.(CREDITO)') "+
//						 "   LEFT JOIN vrtsendmailpasajeros vsend "+
//						 "          ON vsend.id_venpas LIKE '%' || resumen.venpas_id || '%' "+
//						 "         AND vsend.audfecins >= TO_DATE('"+fechaInicio+"', 'yyyy-mm-dd') "+
//						" ) final "+
//						" GROUP BY VENPAS_ID, NROCONTROL, NROBOLETO, NROBOLETOREF, APEPAT, APEMAT, NOMBRE, "+
//						 "        FECHAACTUALIZACION, MONTOBASE, RECARGO, DESCUENTO, ACUENTA, PENALIDAD, "+
//						 "        MIXTO_EFECTIVO, MIXTO_TARJETA, NETOPAGADO, FORPAG_ID, FORMAPAGO, TIPOFORMAPAGO, "+
//						 "        TIPOTARJETA, TIPOMOVIMIENTO, TIPOVENTA, APEPATUSU, NOMBREUSU, C_LOGIN, D_FECLIQ, "+
//						 "        AGENCIA, TIPMOV_ID, LIQUIDACION_ID, TIPFORPAG_ID, TIPCOM_ID, EMPRESA_ID, NROOS, "+
//						 "        C_STATEOS, DIFERENTAR, OBSERVACION, RUTA_ID, C_ORIGEN, C_DESTINO, D_FECEXPRESS, "+
//						 "        C_HOREXPRES, NRO_OPERATION_NIUBIZ, NRO_CIP_PAGOEFECTIVO, N_NUMOPEBAN, "+
//						 "        CORREOENVIADO1, CORREOENVIADO2, FECHAINSERCIONLOG, URL_PAGO, EMAIL_PASAJERO, "+
//						 "        C_EMAIL_CONTACTO, DELAY_CORREO "+
//						" ORDER BY fechaActualizacion DESC ";*/
//			
//			//System.out.println(sql);
//			
//			lstReporteDetallado = jdbcTemplate.query(sql, new ReporteDetalladoMapper());
//			
//			System.out.println("SE RECIBIO RESPUESTA.....");
//			
//			return lstReporteDetallado;
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		
//		return lstReporteDetallado;
//	}
	
	@Override
	public List<ReporteDetallado> getReporteDetallado(int agencia_id, int usuario_id, String fechaInicio, String fechaFin){
		
		List<ReporteDetallado> lstReporteDetallado = new ArrayList<ReporteDetallado>();
		
		String idTipoMovimiento = "%", idUsuario = "%";
		if(usuario_id != 0){
			idUsuario = String.valueOf(usuario_id);
		}
		
		try{			
			String sql = " select vh.venpas_id, u.c_apepat, u.c_apemat, u.c_nombre, u.c_login, vpa.c_nomape, vrut.c_origen, vrut.c_destino,"+
						 " to_char(viti.d_fecpar, 'dd/mm/yyyy'), viti.c_horpar, vh.n_numasiento, "+
						 " CASE "+
						 "   WHEN vh.tipo_sistema = 1 "+
						 "   THEN 'ECOMMERCE' "+
						 "   WHEN vh.tipo_sistema = 2 "+
						 "   THEN 'AUTOSERVICIO' "+
						 "   WHEN vh.tipo_sistema = 3 "+
						 "   THEN 'INTRANET' "+
						 "   WHEN vh.tipo_sistema = 4 "+
						 "   THEN 'SISPAS' "+
						 " END AS SISTEMA, "+
						 " tm.c_denominacion, vp.c_numcontrol, vp.c_numboleto, vp.n_tarifa, vp.n_recargo, vp.n_descuento, vp.n_acuenta,  vp.n_penalidad, vp.n_imppag, "+
						 " fp.forpag_id, fp.c_denominacion forpag, tfp.tipforpag_id,  tfp.c_denominacion tipforpag, otc.opetarcre_id, "+
						 " otc.c_denominacion opetarcre, tc.tarcre_id, tc.c_denominacion tarcre, to_char(vp.audfecins, 'dd/mm/yyyy HH24:MI:SS'), tm.tipmov_id, vp.n_ididaret, "+
						 " to_char(vp.d_fecexpres, 'dd/mm/yyyy'), vp.c_horexpres, vp.n_numopeban, vsend.estado, vlog.audfecins, vp.c_observaciones, vh.url_pago, vh.email_pasajero, vsend.sendmailpasajeros_id, nro_cip_pagoefectivo, "+
						 " CASE WHEN sysdate >= (vlog.audfecins + (15/1440)) and vsend.estado = 0 THEN 1 ELSE 0 END AS DELAY_CORREO "+
						 " from vrthistorialventas vh "+
						 " left join vrmusuario u on u.usuario_id=vh.usuario_id "+
						 " left join vrmpasajero vpa on vpa.pasajero_id=vh.pasajero_id "+
						 " left join vrtitinerario viti on viti.itinerario_id=vh.itinerario_id "+
						 " left join vrmruta vrut on vrut.ruta_id=vh.ruta_id "+
						 " left join vrtvenpas vp on vp.venpas_id=vh.venpas_id and vp.agencia_id LIKE '"+agencia_id+"' and vp.tipmov_id LIKE '"+idTipoMovimiento+"' "+
						 " LEFT JOIN vrmtipmov tm ON tm.tipmov_id=vp.tipmov_id  "+
						 " LEFT JOIN vrmusuario u ON u.usuario_id=vp.usuario_id  "+
						 " LEFT JOIN vrmforpag fp ON fp.forpag_id=vp.forpag_id  "+
						 " LEFT JOIN vrmtipforpag tfp ON tfp.tipforpag_id=vp.tipforpag_id  "+
						 " LEFT JOIN vrmtarcre tc ON tc.tarcre_id=vp.tarcre_id  "+
						 " LEFT JOIN vrmopetarcre otc ON otc.opetarcre_id=tc.opetarcre_id "+
						 " LEFT JOIN vrmlogecommerce vlog ON vlog.c_nropedido=vp.n_numopeban and ROWNUM=1 "+
						 " LEFT JOIN vrtsendmailpasajeros vsend ON vsend.id_venpas like '%'|| vh.venpas_id || '%' and vsend.audfecins >= to_date('"+fechaInicio+"', 'yyyy-mm-dd') and vsend.tipo_sistema=vh.tipo_sistema "+
						 " where vh.usuario_id LIKE '"+idUsuario+"' and vp.n_imppag<>0 and "+
						 " vh.d_fecliq BETWEEN to_date('"+fechaInicio+"', 'yyyy-mm-dd') AND to_date('"+fechaFin+"', 'yyyy-mm-dd') "+
						 " ORDER BY vp.audfecins DESC";
			
			//System.out.println(sql);
			lstReporteDetallado = jdbcTemplate.query(sql, new ReporteDetalladoMapper());
			
			return lstReporteDetallado;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return lstReporteDetallado;
	}
	
	private final class ReporteDetalladoMapper implements RowMapper<ReporteDetallado> {
		
		@Override
		public ReporteDetallado mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new ReporteDetallado(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10),
					rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(14), rs.getString(15), rs.getDouble(16), rs.getDouble(17), rs.getDouble(18), rs.getDouble(19), rs.getDouble(20), rs.getDouble(21), 
					rs.getInt(22), rs.getString(23), rs.getInt(24), rs.getString(25), rs.getInt(26), rs.getString(27), rs.getInt(28), rs.getString(29), rs.getString(30), rs.getInt(31), rs.getString(32), rs.getString(33), 
					rs.getString(34), rs.getString(35), rs.getString(36), rs.getString(37), rs.getString(38), rs.getString(39), rs.getString(40), rs.getString(41), rs.getString(42), rs.getInt(43));
		}
	}
	
	/*private final class ReporteDetalladoMapper implements RowMapper<ReporteDetallado> {
		
		@Override
		public ReporteDetallado mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new ReporteDetallado(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getDouble(9), rs.getDouble(10),
				    rs.getDouble(11), rs.getDouble(12), rs.getDouble(13), rs.getDouble(14), rs.getDouble(15), rs.getDouble(16), rs.getInt(17), rs.getString(18), rs.getString(19), rs.getString(20),
				    rs.getString(21), rs.getString(22), rs.getString(23), rs.getString(24), rs.getString(25), rs.getString(26), rs.getString(27), rs.getInt(28), rs.getString(29), rs.getInt(30),
				    rs.getInt(31), rs.getInt(32), rs.getString(33), rs.getString(34), rs.getInt(35), rs.getString(36), rs.getInt(37), rs.getString(38), rs.getString(39), rs.getString(40),
				    rs.getString(41), rs.getString(42), rs.getString(43), rs.getString(44), rs.getString(45), rs.getInt(46), rs.getInt(47), rs.getString(48), rs.getString(49), rs.getString(50), rs.getInt(51));
		}
	}*/
	
	private final class ReporteSeguimientoFrotcomRowMapper implements RowMapper<ReporteSeguimientoFrotcom> {
		
		@Override
		public ReporteSeguimientoFrotcom mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new ReporteSeguimientoFrotcom(rs.getBigDecimal(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), 
					rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(14), rs.getString(15), rs.getString(16), rs.getString(17), rs.getString(18), rs.getString(19), rs.getString(20), 
					rs.getString(21), rs.getString(22), rs.getString(23), rs.getString(24), rs.getString(25), rs.getString(26), rs.getString(27), rs.getString(28), rs.getString(29), rs.getString(30), rs.getString(31), 
					rs.getString(32), rs.getString(33), rs.getString(34), rs.getString(35), rs.getString(36), rs.getString(37), rs.getString(38), rs.getString(39));
		}
	}
	
	private final class AgenciaRowMapper implements RowMapper<Agencia> {
		
		@Override
		public Agencia mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Agencia(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getString(7), rs.getString(8), rs.getInt(9), rs.getInt(10), rs.getString(11),
					rs.getInt(12), rs.getInt(13), rs.getString(14));
		}
	}
	
	private final class UsuariosCounterMapper implements RowMapper<UsuariosCounter> {
		
		@Override
		public UsuariosCounter mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new UsuariosCounter(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
		}
	}
}