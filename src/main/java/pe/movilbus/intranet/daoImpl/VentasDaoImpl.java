package pe.movilbus.intranet.daoImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import oracle.jdbc.pool.OracleDataSource;
import pe.movilbus.intranet.result.MensajeConfirmacionResult;
import pe.movilbus.intranet.result.MensajeFlagResult;
//import pe.movilbus.intranet.service.fe.WSFE;
import pe.movilbus.intranet.wspagoefectivo.ResponseGeneradorCip;
import pe.movilbus.intranet.beans.VentasGeneralPasajeros;
import pe.movilbus.intranet.beans.AgenciaSispas;
import pe.movilbus.intranet.beans.AsientoOcupado;
import pe.movilbus.intranet.beans.CanalVentaSispas;
import pe.movilbus.intranet.beans.CentroCostoSispas;
import pe.movilbus.intranet.beans.Cliente;
import pe.movilbus.intranet.beans.ClienteSispas;
import pe.movilbus.intranet.beans.CompaniaSispas;
import pe.movilbus.intranet.beans.ConcesionarioSispas;
import pe.movilbus.intranet.beans.DataTransbordos;
import pe.movilbus.intranet.beans.DatosIziPaySimularPago;
import pe.movilbus.intranet.beans.FormaPagoSispas;
import pe.movilbus.intranet.beans.ItinerarioAgenciaPartidaIDSispas;
import pe.movilbus.intranet.beans.ItinerarioSispas;
import pe.movilbus.intranet.beans.JsonRest;
import pe.movilbus.intranet.beans.LiquidacionSispas;
import pe.movilbus.intranet.beans.LocalidadSispas;
import pe.movilbus.intranet.beans.ManifiestoSispas;
import pe.movilbus.intranet.beans.NacionalidadSispas;
import pe.movilbus.intranet.beans.NuevoLinkNiubiz;
import pe.movilbus.intranet.beans.OperadorTarjetaCreditoSispas;
import pe.movilbus.intranet.beans.Pasajero;
import pe.movilbus.intranet.beans.PasajeroSispas;
import pe.movilbus.intranet.beans.PreferenciaAlimentariaSispas;
import pe.movilbus.intranet.beans.PromocionSispas;
import pe.movilbus.intranet.beans.RequestApiWS;
import pe.movilbus.intranet.beans.ResponseNiubiz;
import pe.movilbus.intranet.beans.ResultPagoLink;
import pe.movilbus.intranet.beans.RptVenpasIdOriginal;
import pe.movilbus.intranet.beans.Ruta;
import pe.movilbus.intranet.beans.RutaSispas;
import pe.movilbus.intranet.beans.Secuencia;
import pe.movilbus.intranet.beans.SecuenciaTramo;
import pe.movilbus.intranet.beans.ServicioSispas;
import pe.movilbus.intranet.beans.TarjetaCreditoSispas;
import pe.movilbus.intranet.beans.TipoAgenciaSispas;
import pe.movilbus.intranet.beans.TipoComprobanteSispas;
import pe.movilbus.intranet.beans.TipoDocumentoSispas;
import pe.movilbus.intranet.beans.TipoFormaPagoSispas;
import pe.movilbus.intranet.beans.TipoMonedaSispas;
import pe.movilbus.intranet.beans.TipoMovimientoSispas;
import pe.movilbus.intranet.beans.UbigeoSispas;
import pe.movilbus.intranet.beans.UpdateVrtVenpas;
import pe.movilbus.intranet.beans.UsuarioSispas;
import pe.movilbus.intranet.beans.VentaPasaje;
//import pe.movilbus.intranet.beans.VentaPasajeSispas;
import pe.movilbus.intranet.beans.VentaPasajeros;
import pe.movilbus.intranet.beans.VentasGeneral;
import pe.movilbus.intranet.beans.DescargarPdfPasajes;
import pe.movilbus.intranet.dao.MensajeFlagResultIziPay;
import pe.movilbus.intranet.dao.VentasDao;
import pe.movilbus.intranet.util.Constantes;
import pe.movilbus.intranet.util.Util;
import pe.movilbus.intranet.wsizipay.IziPay;
import pe.movilbus.intranet.wspagoefectivo.PagoEfectivo;
import pe.movilbus.intranet.wspagoefectivo.RequestPagoEfectivo;

@Repository
public class VentasDaoImpl implements VentasDao{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private static String url_api = "";
	private static String userpassBase64 = "";
	
	@Override
	public MensajeConfirmacionResult simularPago(String numOperacion){
		try{
			String sql = "";
			
			if(numOperacion.contains("IZIPL")){
				sql = " select venpas_id, cliente_id, agencia_id, c_tiptra, c_estdoc, c_email_contacto, parentesco_id, n_tipo_pasajero from vrtvenpas "+
						 " where n_numopeban='"+numOperacion+"' and c_tiptra=2 and c_estdoc is null ";
			
				List<UpdateVrtVenpas> updateVrtVenpas = jdbcTemplate.query(sql, new UpdateVrtVenpasRowMapper());
				
				String email_contacto = "";
				String texto_ventas_concatenado = "";
				String texto_archivos_concatenado = "";
				
				if(updateVrtVenpas.size() > 0){
					for(UpdateVrtVenpas obj : updateVrtVenpas){
						actualizarVrtVenpasNiubizPagoEfectivo(obj.getVenpas_id());
						if(!numOperacion.contains("PE")){
							actualizarVrtVenpasNiubizPagoEfectivoTarCre(obj.getVenpas_id());
						}
						
						if(obj.getN_tipo_pasajero() != 3 && obj.getParentesco_id() != 4){
							/*************** GENERAR EL BOLETO *************/
							int cont_factura = 0;
							if(obj.getCliente_id() != null){
								cont_factura = 1;
							}
							
							int tipcom_val = 0;
							
							if(cont_factura == 1){
								tipcom_val = 2;
							}else{
								tipcom_val = 7;
							}
							
							sql = "select c_corseq, c_serie from vrmespval where agencia_id="+obj.getAgencia_id()+" and tipcom_id="+tipcom_val+" and empresa_id=1 and c_estreg='"+Constantes.ACTIVO+"'";
							List<Secuencia> lstSecuencia = jdbcTemplate.query(sql, new SecuenciaRowMapper());
							
							if(lstSecuencia.size() > 0){
								sql = "SELECT "+lstSecuencia.get(0).getC_corseq()+".NEXTVAL FROM DUAL";
								String correlativo = jdbcTemplate.queryForObject(sql, String.class);
								correlativo = String.format("%08d", Integer.valueOf(correlativo));
								
								String c_numboleto = lstSecuencia.get(0).getC_serie()+"-"+correlativo.trim();
								
								actualizarVrtVenpasNiubizPagoEfectivoBoletos(obj.getVenpas_id(), c_numboleto);
								actualizarVrtVenpasNiubizPagoEfectivoHijosBoletos(obj.getVenpas_id(), c_numboleto);
								
								texto_ventas_concatenado = obj.getVenpas_id() + ";" + texto_ventas_concatenado;
								texto_archivos_concatenado = c_numboleto + ";" + texto_archivos_concatenado;
								email_contacto = obj.getC_email_contacto();
							}
						}
					}
				}
				
				if(updateVrtVenpas.size() > 0){
					try{
						insertSendEmailPasajeros(email_contacto, texto_ventas_concatenado, texto_archivos_concatenado);
						
						return new MensajeConfirmacionResult(Constantes.RESULT_TRUE, "Se realizó el Pago Exitosamente.");
					}catch(Exception e){
						e.printStackTrace();
						return new MensajeConfirmacionResult(Constantes.RESULT_FALSE, "No se pudo realizar el Pago.");
					}
				}				
			}else if(numOperacion.contains("IZI")){
				sql = " SELECT "+
						 "   vp.nro_operation_niubiz, "+
						 "   vp.venpas_id, "+
						 "   vp.itinerario_id, "+
						 "   vp.ruta_id, "+
						 "   vp.n_numpiso, "+
						 "   vp.n_numasiento "+
						" FROM "+
						"    vrtvenpas vp "+
						" LEFT JOIN vrmlogecommerce vlog ON vlog.c_nropedido=vp.nro_operation_niubiz "+
						" WHERE "+
						"    vp.result_ws_id IN (0, 9, 13) "+
						"    AND vp.d_fecpar >= SYSDATE - 1 "+
						"    AND vp.audfecins <= SYSDATE - (5 / 1440) "+
						"    AND nro_operation_niubiz ='"+numOperacion+"'";
				
				List<DatosIziPaySimularPago> listaDatosIziPaySimularPago = jdbcTemplate.query(sql, new DatosIziPaySimularPagoRowMapper());
						
				if(listaDatosIziPaySimularPago.size() > 0) {
					for (DatosIziPaySimularPago datosIziPaySimularPago : listaDatosIziPaySimularPago) {
						//Busca si su asiento está ocupado o no.
						sql = " select case when MAX(venpas_id) is null then 0 else 1 end AsientoOcupadoVrtVenpas from vrtvenpas where itinerario_id="+datosIziPaySimularPago.getItinerario_id()+
							  " and n_numpiso="+datosIziPaySimularPago.getN_numpiso()+" and n_numasiento="+datosIziPaySimularPago.getN_numasiento()+" and tipmov_id not in (5,6,13,14) and tipcom_id in (2, 7) and audfecins >= trunc(sysdate-1)";
						
						Integer AsientoOcupadoVrtVenpas = jdbcTemplate.queryForObject(sql, Integer.class);
						
						if (AsientoOcupadoVrtVenpas == 0) {
						    sql = "SELECT CASE WHEN EXISTS ( " +
						          "    SELECT 1 FROM vrttmpocuasi " +
						          "    WHERE itinerario_id = " + datosIziPaySimularPago.getItinerario_id() +
						          "      AND ruta_id = " + datosIziPaySimularPago.getRuta_id() +
						          "      AND n_numpiso = " + datosIziPaySimularPago.getN_numpiso() +
						          "      AND n_asiento = " + datosIziPaySimularPago.getN_numasiento() +
						          "      AND audfecins >= TRUNC(SYSDATE - 1)" +
						          ") THEN 1 ELSE 0 END AS existe FROM dual";
						    
						    Integer AsientoOcupadoVrttmpocuasi = jdbcTemplate.queryForObject(sql, Integer.class);
						    
						    if(AsientoOcupadoVrttmpocuasi == 0){
						    	// Actualizacion de la ventas para darle tiempo
								sql = 	" SELECT VP.VENPAS_ID,vp.itinerario_id,vp.ruta_id,vp.pasajero_id,"+
										" vp.n_numasiento,vp.n_numpiso,vp.n_tarifa "+
										"  FROM PASAJES.VRTVENPAS VP "+
										" WHERE VP.NRO_OPERATION_NIUBIZ = '"+numOperacion+"'"+
										" AND VP.RESULT_WS_ID in (0,99,13)";
												
								List<VentaPasaje> listaActualizacion = jdbcTemplate.query(sql, new VentaPasaje7RowMapper());
							
								if(listaActualizacion.size() > 0) {
									for (VentaPasaje actualizar : listaActualizacion) {
										try{
											//Armando los correlativo
											sql = " SELECT SEQ_VRMESPVAL_CORRELATIVO_WEB_ID.NEXTVAL FROM DUAL";
											String correlativo = "0000000"+jdbcTemplate.queryForObject(sql, Long.class);
											String cserie = "000"+Constantes.ID_SERIE_WEB;
											
											sql = " UPDATE VRTVENPAS VP SET VP.c_Numboleto = '"+
													cserie.substring(cserie.length()-3)+ "-"+correlativo.substring(correlativo.length()-7)+"'"+
													" ,VP.Tipmov_Id ="+Constantes.ID_TIPMOV_CREDITO+
													" ,vp.c_tip_reg ='"+Constantes.TIPO_REGISTRO_VENPAS_PAGADO+"'"+
													" ,vp.RESULT_WS_ID ="+Constantes.RS_WS_AUTORIZADO+
												" WHERE VP.Venpas_Id ="+actualizar.getIdVenta()+" and vp.RESULT_WS_ID in (0,99,13)";
											jdbcTemplate.update(sql);
											
											sql = " DELETE FROM PASAJES.VRTTMPOCUASI "+ 
													 " WHERE ITINERARIO_ID ="+actualizar.getIdItinerario()+
													 "  and RUTA_ID ="+actualizar.getIdRuta()+
													 "  and USUHARD_ID="+Constantes.ID_HARDWARE_MOVIL_WEB+
													 "  and USUARIO_ID="+Constantes.ID_USUARIO_MOVIL_WEB+
													 "  and N_ASIENTO ="+actualizar.getNroAsiento();										
											jdbcTemplate.update(sql);
										}catch(Exception e){ e.printStackTrace(); }	
									}
									
									return new MensajeConfirmacionResult(Constantes.RESULT_TRUE, "Se realizó el Pago Exitosamente.");
								}
						    }else{
						    	return new MensajeConfirmacionResult(Constantes.RESULT_FALSE, "El asiento se encuentra ocupado en VRTTMPOCUASI.");
						    }
						}else{
							return new MensajeConfirmacionResult(Constantes.RESULT_FALSE, "El asiento se encuentra vendido en el VRTVENPAS.");
						}
					}
				}else{
					return new MensajeConfirmacionResult(Constantes.RESULT_FALSE, "No se encontraron registros.");
				}
			}else{
				sql = " select venpas_id, cliente_id, agencia_id, c_tiptra, c_estdoc, c_email_contacto, parentesco_id, n_tipo_pasajero from vrtvenpas "+
						 " where n_numopeban='"+numOperacion+"' and c_tiptra=2 and c_estdoc is null ";
			
				List<UpdateVrtVenpas> updateVrtVenpas = jdbcTemplate.query(sql, new UpdateVrtVenpasRowMapper());
				
				String email_contacto = "";
				String texto_ventas_concatenado = "";
				String texto_archivos_concatenado = "";
				
				if(updateVrtVenpas.size() > 0){
					for(UpdateVrtVenpas obj : updateVrtVenpas){
						actualizarVrtVenpasNiubizPagoEfectivo(obj.getVenpas_id());
						if(!numOperacion.contains("PE")){
							actualizarVrtVenpasNiubizPagoEfectivoTarCre(obj.getVenpas_id());
						}
						
						if(obj.getN_tipo_pasajero() != 3 && obj.getParentesco_id() != 4){
							/*************** GENERAR EL BOLETO *************/
							int cont_factura = 0;
							if(obj.getCliente_id() != null){
								cont_factura = 1;
							}
							
							int tipcom_val = 0;
							
							if(cont_factura == 1){
								tipcom_val = 2;
							}else{
								tipcom_val = 7;
							}
							
							sql = "select c_corseq, c_serie from vrmespval where agencia_id="+obj.getAgencia_id()+" and tipcom_id="+tipcom_val+" and empresa_id=1 and c_estreg='"+Constantes.ACTIVO+"'";
							List<Secuencia> lstSecuencia = jdbcTemplate.query(sql, new SecuenciaRowMapper());
							
							if(lstSecuencia.size() > 0){
								sql = "SELECT "+lstSecuencia.get(0).getC_corseq()+".NEXTVAL FROM DUAL";
								String correlativo = jdbcTemplate.queryForObject(sql, String.class);
								correlativo = String.format("%08d", Integer.valueOf(correlativo));
								
								String c_numboleto = lstSecuencia.get(0).getC_serie()+"-"+correlativo.trim();
								
								actualizarVrtVenpasNiubizPagoEfectivoBoletos(obj.getVenpas_id(), c_numboleto);
								actualizarVrtVenpasNiubizPagoEfectivoHijosBoletos(obj.getVenpas_id(), c_numboleto);
								
								texto_ventas_concatenado = obj.getVenpas_id() + ";" + texto_ventas_concatenado;
								texto_archivos_concatenado = c_numboleto + ";" + texto_archivos_concatenado;
								email_contacto = obj.getC_email_contacto();
							}
						}
					}
				}
				
				if(updateVrtVenpas.size() > 0){
					try{
						insertSendEmailPasajeros(email_contacto, texto_ventas_concatenado, texto_archivos_concatenado);
						
						return new MensajeConfirmacionResult(Constantes.RESULT_TRUE, "Se realizó el Pago Exitosamente.");
					}catch(Exception e){
						e.printStackTrace();
						return new MensajeConfirmacionResult(Constantes.RESULT_FALSE, "No se pudo realizar el Pago.");
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			return new MensajeConfirmacionResult(Constantes.RESULT_FALSE, "No se pudo realizar el Pago.");
		}
		
		return null;
	}
	
	private void actualizarVrtVenpasNiubizPagoEfectivo(BigDecimal venpas_id){
		String sql = "update vrtvenpas set c_estdoc='PAG', c_tiptra=1, d_fecexpres='', c_horexpres='' where venpas_id="+venpas_id;
		jdbcTemplate.update(sql);
	}
	
	private void actualizarVrtVenpasNiubizPagoEfectivoBoletos(BigDecimal venpas_id, String c_numboleto){
		String sql = "update vrtvenpas set c_numboleto='"+c_numboleto+"', tipmov_id=1 where venpas_id="+venpas_id;
		jdbcTemplate.update(sql);
	}
	
	private void actualizarVrtVenpasNiubizPagoEfectivoHijosBoletos(BigDecimal venpas_id, String c_numboleto){
		String sql = "update vrtvenpas set c_numboleto='"+c_numboleto+"', tipmov_id=16 where venpas_idparents="+venpas_id+" and parentesco_id=4 and n_tipo_pasajero=3";
		jdbcTemplate.update(sql);
	}
	
	private void actualizarVrtVenpasNiubizPagoEfectivoTarCre(BigDecimal venpas_id){
		String sql = "update vrtvenpas set tarcre_id='65', tipmov_id=1 where venpas_id="+venpas_id;
		jdbcTemplate.update(sql);
	}
	
	private void insertSendEmailPasajeros(String email, String texto_ventas_concatenado, String texto_archivos_concatenado){
		
		try{
			String sql = "SELECT seq_vrtsendmailpasajeros_id.NEXTVAL FROM DUAL";
			List<Long> idSendMailPasajeros = jdbcTemplate.query(sql, new LongRowMapper());
			
			if(idSendMailPasajeros.size() > 0){
				String sql_insert = " INSERT INTO PASAJES.vrtsendmailpasajeros (sendmailpasajeros_id, from_email, modulo_id, bodymailformat_id, id_venpas, archivos, estado, ult_correo_proc, c_estreg, tipo_sistema) "+
									" VALUES ("+idSendMailPasajeros.get(0)+", '"+email+"', 1, 1, '"+texto_ventas_concatenado+"', '"+texto_archivos_concatenado+"', 0, 0, 'A', 3)";
				
				jdbcTemplate.update(sql_insert);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void ConseguirConstantesApiFE(){
		try{
			String sql = " select t1.c_valor ||',,'|| t2.c_valor ||',,'|| t3.c_valor "+
					 	 " from "+
					 	 " (select vrm1.c_valor from vrmflag vrm1 where vrm1.c_codigo='FEAPI_URL') t1, "+
					 	 " (select vrm2.c_valor from vrmflag vrm2 where vrm2.c_codigo='FEAPI_USER') t2, "+
					 	 " (select vrm3.c_valor from vrmflag vrm3 where vrm3.c_codigo='FEAPI_PASSWD') t3 ";
			
			String datos_api_fe = jdbcTemplate.queryForObject(sql, String.class);
			String[] part = datos_api_fe.split(",,");
			url_api = part[0];
			userpassBase64 = new String(org.apache.commons.codec.binary.Base64.encodeBase64((part[1]+":"+part[2]).getBytes()));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public List<DescargarPdfPasajes> DescargarPdfPasajes(List<String> lstPasajes){
		try{
			ConseguirConstantesApiFE();
			
			/*List<DescargarPdfPasajes> archivos = new ArrayList<DescargarPdfPasajes>();
			
			for(String pasaje : lstPasajes){
				DescargarPdfPasajes dato = new DescargarPdfPasajes();
				
				String tipo_documento = "03";
				if(pasaje.contains("FB")){
					tipo_documento = "01";
				}
				
				String bodyWs="{"+
					  	  " \"rubro\": 1, "+
					  	  " \"tipoArchivo\": 1, "+
					  	  " \"tipoDocumento\": \""+tipo_documento+"\", "+
					  	  " \"numeroDocumento\": \""+pasaje+"\" "+
					  	  "}";
							
				RequestApiWS rqSession = new RequestApiWS(url_api+"descargarArchivo", bodyWs, "Basic "+userpassBase64, "POST");
				JsonRest jsBase = consumoWs(rqSession, 2);
				
				if(jsBase.getRspCode().equals("200")){
					JSONObject obj = new JSONObject(jsBase.getResponse().toString());
		          	if(obj.getString("archivo") != ""){
		          		dato.setBoleto(pasaje);
		          		dato.setPdf(obj.getString("archivo"));
		          		archivos.add(dato);
		          	}
				}
			}*/
			
			List<DescargarPdfPasajes> archivos = new ArrayList<>();

			for (String pasaje : lstPasajes) {
			    DescargarPdfPasajes dato = new DescargarPdfPasajes();

			    // Primer intento con tipo_documento
			    String tipo_documento = pasaje.contains("FB") ? "01" : "03";
			    
			    // Intentamos descargar
			    String archivoPdf = obtenerArchivoDesdeApi(pasaje, tipo_documento);

			    if ((archivoPdf == null || archivoPdf.isEmpty())) {
			        tipo_documento = "07";
			        archivoPdf = obtenerArchivoDesdeApi(pasaje, tipo_documento);
			        
			        if ((archivoPdf == null || archivoPdf.isEmpty())) {
				        tipo_documento = "03";
				        archivoPdf = obtenerArchivoDesdeApi(pasaje, tipo_documento);
				    }
			    }

			    // Si finalmente encontramos archivo, lo guardamos
			    if (archivoPdf != null && !archivoPdf.isEmpty()) {
			        dato.setBoleto(pasaje);
			        dato.setPdf(archivoPdf);
			        archivos.add(dato);
			    }
			}
			
			return archivos;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	private String obtenerArchivoDesdeApi(String pasaje, String tipo_documento) {
		try{
		    String bodyWs = "{"+
		        " \"rubro\": 1, "+
		        " \"tipoArchivo\": 1, "+
		        " \"tipoDocumento\": \"" + tipo_documento + "\", "+
		        " \"numeroDocumento\": \"" + pasaje + "\" "+
		    "}";
	
		    RequestApiWS rqSession = new RequestApiWS(url_api + "descargarArchivo", bodyWs, "Basic " + userpassBase64, "POST");
		    JsonRest jsBase = consumoWs(rqSession, 2);
	
		    if (jsBase.getRspCode().equals("200")) {
		        JSONObject obj = new JSONObject(jsBase.getResponse().toString());
		        return obj.optString("archivo", "");
		    }
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	@Override
	public List<DataTransbordos> getBuscarTransbordos(String boleto){
		try{
			String sql = " SELECT B.VENPAS_ID, "+
						 " RC.C_DENOMINACION AS TIPO_DE_PAGO, "+
						 " H.C_NOMBRE || ' ' || H.C_APEPAT || ' ' || H.C_APEMAT AS PASAJERO, "+
						 " B.C_NUMBOLETO AS NRO_BOLETO, "+
						 " F.C_ORIGEN || '-' || F.C_DESTINO AS RUTA, "+
						 " B.N_IMPPAG AS IMPORTE_TOTAL, "+
						 " Case "+
						 "   when G.N_NIVEL = 1 then "+
						 "    V.N_TARIFA1 "+
						 "   when G.N_NIVEL = 2 then "+
						 "    V.N_TARIFA2 "+
						 "   when G.N_NIVEL = 3 then "+
						 "    CASE "+
						 "      WHEN G.N_NUMPIS = 0 THEN "+
						 "       CASE "+
						 "         WHEN A.AUDFECMOD >= to_date('01/06/2024', 'DD/MM/YYYY') THEN "+
						 "          V.N_TARIFA1 + ROUND(V.N_TARIFA1 * VCONGF.N_AUMENTO / 100) "+
						 "         ELSE "+
						 "          V.N_TARIFA1 "+
						 "       END "+
						 "      WHEN G.N_NUMPIS = 1 THEN "+
						 "       CASE "+
						 "         WHEN A.AUDFECMOD >= to_date('01/06/2024', 'DD/MM/YYYY') THEN "+
						 "          V.N_TARIFA2 + ROUND(V.N_TARIFA2 * VCONGF.N_AUMENTO / 100) "+
						 "         ELSE "+
						 "          V.N_TARIFA2 "+
						 "       END "+
						 "      ELSE "+
						 "       0 "+
						 "    END "+
						 "   when G.N_NIVEL IS NULL AND GOPC.N_NIVEL = 1 then "+
						 "    V.N_TARIFA1 "+
						 "   when G.N_NIVEL IS NULL AND GOPC.N_NIVEL = 2 then "+
						 "    V.N_TARIFA2 "+
						 "   when G.N_NIVEL IS NULL AND GOPC.N_NIVEL = 3 then "+
						 "    CASE "+
						 "      WHEN G.N_NIVEL IS NULL AND GOPC.N_NUMPIS = 0 THEN "+
						 "       CASE "+
						 "         WHEN G.N_NIVEL IS NULL AND A.AUDFECMOD >= to_date('01/06/2024', 'DD/MM/YYYY') THEN "+
						 "          V.N_TARIFA1 + ROUND(V.N_TARIFA1 * VCONGF_OPC.N_AUMENTO / 100) "+
						 "         ELSE "+
						 "          V.N_TARIFA1 "+
						 "       END "+
						 "      WHEN G.N_NIVEL IS NULL AND GOPC.N_NUMPIS = 1 THEN "+
						 "       CASE "+
						 "         WHEN G.N_NIVEL IS NULL AND A.AUDFECMOD >= to_date('01/06/2024', 'DD/MM/YYYY') THEN "+
						 "                  V.N_TARIFA2 + ROUND(V.N_TARIFA2 * VCONGF_OPC.N_AUMENTO / 100) "+
						 "                 ELSE "+
						 "                  V.N_TARIFA2 "+
						 "               END "+
						 "              ELSE "+
						 "               0 "+
						 "            END "+
						 "           Else "+
						 "            0 "+
						 "         End as TARIFA_ASIENTO, "+
						 "         A.ITINERARIO_IDORIGEN, "+
						 "         C.C_DENOMINACION AS SERVICIO_DE_ORIGEN, "+
						 "         to_char(A.D_FECPARORI, 'dd/mm/yyyy') AS FECHA_ORIGEN, "+
						 "         A.C_HORPARORI AS HORA_PARTIDA, "+
						 "         A.N_NUMASIORI AS ASIENTO_ORIGEN, "+
						 "         A.ITINERARIO_IDDESTINO AS ITINERARIO_DESTINO, "+
						 "         D.C_DENOMINACION AS SERVICIO_DESTINO, "+
						 "         to_char(A.D_FECPARDES, 'dd/mm/yyyy') AS FECHA_PAR_DESPUES, "+
						 "         A.C_HORPARDES HORA_DESPUES, "+
						 "         A.N_NUMASIDES AS ASIENTO_DESPUES, "+
						 "         to_char(A.AUDFECMOD, 'dd/mm/yyyy') AS FECHA_MODIFICADA, "+
						 "         A.AUDUSUMOD AS USUARIO_MODIFICA, "+
						 "         E.C_DENOMINACION AS AGENCIA_VENTA "+
						          
						 "    FROM VRTTRANSBORDO A "+
						 "   INNER JOIN VRTVENPAS B "+
						 "      ON A.VENPAS_ID = B.VENPAS_ID "+
						 "   INNER JOIN VRMSERVICIO C "+
						 "      ON A.SERVICIO_IDORIGEN = C.SERVICIO_ID "+
						 "   INNER JOIN VRMSERVICIO D "+
						 "      ON A.SERVICIO_IDDESTINO = D.SERVICIO_ID "+
						 "   INNER JOIN VRMAGENCIA E "+
						 "      ON B.AGENCIA_ID = E.AGENCIA_ID "+
						 "   INNER JOIN VRMRUTA F "+
						 "      ON B.RUTA_ID = F.RUTA_ID "+
						 "   INNER JOIN VRMPASAJERO H "+
						 "      ON B.PASAJERO_ID = H.PASAJERO_ID "+
						 "   INNER JOIN VRMFORPAG RC "+
						 "      ON B.FORPAG_ID = RC.FORPAG_ID "+
						 "    LEFT JOIN VRTTARIFAXNIVEL V "+
						 "      ON A.ITINERARIO_IDDESTINO = V.ITINERARIO_ID "+
						 "     AND B.RUTA_ID = V.RUTA_ID "+
						 "      AND B.CANVEN_ID = V.CANVEN_ID "+
						 "    LEFT JOIN VRMCONFIGASIENTO VCONGF "+
						 "      ON VCONGF.N_GROUPSERVICIO = D.N_GROUPSERVICIO "+
						 "    LEFT JOIN VRTMAPABUS G "+
						 "      ON D.SERVICIO_ID = G.SERVICIO_ID "+
						 "     AND A.N_NUMASIDES = G.N_NUMASI "+
						 "    LEFT JOIN VRTITINERARIO ITOPC ON ITOPC.ITINERARIO_ID = B.ITINERARIO_ID "+
						 "    LEFT JOIN VRTMAPABUS GOPC "+
						 "      ON ITOPC.SERVICIO_ID = GOPC.SERVICIO_ID "+
						 "     AND B.n_numasiento = GOPC.N_NUMASI "+
						 "    LEFT JOIN VRMSERVICIO SOPC ON SOPC.SERVICIO_ID = ITOPC.SERVICIO_ID "+
						 "    LEFT JOIN VRMCONFIGASIENTO VCONGF_OPC "+
						 "    ON VCONGF_OPC.N_GROUPSERVICIO = SOPC.N_GROUPSERVICIO "+
						    
						 "    where b.c_numboleto in ( "+
						 "  '"+boleto+"') "+
						
						 " and b.tipmov_id not in (5, 13, 9)";
			
			return jdbcTemplate.query(sql, new DataTransbordosRowMapper());
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public List<VentasGeneralPasajeros> buscarVentasPasajes(String add_consulta, int cont){
		try{
			String sql = " select vp.venpas_id, vp.venpas_idref, vp.venpas_idoriginal, vp.itinerario_id, "+
						 " vp.ruta_id, vrut.c_origen, vrut.c_destino, vrut.c_origen||'-'||vrut.c_destino as detalle_ruta, "+
						 " vp.cliente_id, vcli.c_numdoc, vcli.c_razsoc, vcli.c_direccion, "+
						 " vp.pasajero_id, vpas.c_numdoc, vpas.c_nomape, vpas.c_email, vpas.c_telefono, "+
						 " vp.forpag_id, vfor.c_denominacion, "+
						 " vp.servicio_id, CASE WHEN SUBSTR(vser.c_denominacion,0,5) = 'EJECU' THEN 'Ejecutivo vip' WHEN SUBSTR(vser.c_denominacion,0,5) = 'PRESI' THEN 'Presidencial' WHEN SUBSTR(vser.c_denominacion,0,5) = 'PREMI' THEN 'Premier' WHEN SUBSTR(vser.c_denominacion,0,5) = 'ECONO' THEN 'Económico' END AS servicio, "+
						 " vp.tipcom_id, vtip.c_denominacion, "+
						 " vp.tipmov_id, vmov.c_denominacion, "+
						 " vp.tipforpag_id, vtipfor.c_denominacion, "+
						 " vp.tarcre_id, vtarcre.c_denominacion, "+
						 " vp.c_numboleto, vp.c_numbolant, vp.n_numasiento, vp.c_numcontrol, "+
						 " vp.agencia_idpartida, vagepar.c_direccion, "+
						 " vp.d_fecpar, vp.c_horpar, "+
						 " vp.agencia_idllegada, vagelle.c_direccion, "+
						 " vp.d_feclle, vp.c_horlle, "+
						 " vp.n_secuencial, "+
						 " vp.n_imppag, "+
						 " CASE WHEN vp.c_tiptra = 1 THEN 'VENTA' WHEN vp.c_tiptra = 2 THEN 'RESERVA' WHEN vp.c_tiptra = 3 THEN 'NOTA DE CRÉDITO' END AS tipo_tramite, "+
						 " vp.agencia_id, vagevent.c_denominacion, "+
						 " vp.usuario_id, vusu.c_login, "+
						 " vp.canven_id, vcan.c_denominacion, "+
						 " vp.n_numopeban, "+
						 " vp.d_fecexpres, vp.c_horexpres, "+
						 " vp.n_idaret, CASE WHEN vp.n_idaret = 0 THEN 'IDA' WHEN vp.n_idaret = 1 THEN 'IDA Y VUELTA' END AS detalle_idaret, "+
						 " vp.c_rucclicre, "+
						 " vp.n_esfecabi, CASE WHEN vp.n_esfecabi = 0 THEN 'VENTA NORMAL' WHEN vp.n_esfecabi = 1 THEN 'VENTA FECHA ABIERTA' END AS detalle_esfecabi, "+
						 " vp.c_observaciones, "+
						 " vp.promocion_id, vpro.c_denominacion, vpro.n_valdes, "+
						 " vp.n_ididaret, "+
						 " vp.audfecins, vp.audusuins, "+
						 " vp.audfecmod, vp.audusumod, "+
						 " vp.c_estdoc, "+
						 " vp.c_codref, "+
						 " vp.d_fecvent, "+
						 " vp.result_ws_id, CASE WHEN vp.result_ws_id = 0 THEN 'En espera de pago' WHEN vp.result_ws_id = 2 THEN 'Pago Denegado' WHEN vp.result_ws_id = 3 THEN 'Pagado' WHEN vp.result_ws_id = 9 THEN 'Pago Desaprobado' WHEN vp.result_ws_id = 13 THEN 'Pago Denegado' WHEN vp.result_ws_id = 17 THEN 'En espera de pago' WHEN vp.result_ws_id = 18 THEN 'Pago Expirado' WHEN vp.result_ws_id = 99 THEN 'No existe el Nro Operación en Niubiz' END AS detalle_result_ws_id, "+
						 " vp.n_esfe, vp.d_esfe, vp.n_estado_fe, "+
						 " vp.c_email_contacto, vp.c_telefono_opcional, "+
						 " vp.n_info, vp.correo_encuesta_enviado, "+
						 " vp.nro_operation_niubiz, vp.nro_cip_pagoefectivo, "+
						 " vp.name_promocion, vp.descuento_promocion,"+
						 " vsend.estado correo_enviado1, vp.correo_enviado correo_enviado2, "+
						 " vp.venpas_idtx, vp.parentesco_id, vp.venpas_idparents, vp.n_tipo_pasajero, "+
						 " (select vlogcanje.audfecins from VRTLOGCANJEVENPAS vlogcanje where vlogcanje.numoperacion=vp.nro_operation_niubiz and vlogcanje.audfecins is not null and vlogcanje.audfecins >= trunc(sysdate-32) and ROWNUM = 1) as fecha_hora_canje, "+
						 " vlogecom.audfecins fecha_hora_niubiz1, "+
						 " (select vrtcantapiniubiz.audfecins from VRTCANTAPINIUBIZ vrtcantapiniubiz where vrtcantapiniubiz.numoperacion=vp.nro_operation_niubiz and vrtcantapiniubiz.c_resultado='EXITO - PAGO AUTORIZADO' and vrtcantapiniubiz.audfecins >= trunc(sysdate-32) and ROWNUM = 1) as fecha_hora_niubiz2, "+
						 " vlogmail.audfecins fecha_hora_correo, "+
						 " '' as numboleto_numbolant, "+
						 " '' as anulado_por, "+
						 " 1 as estado_order, "+
						 " 0 as pdf_para_descargar "+
						 " from vrtvenpas vp "+
						 " left join vrmruta vrut on vrut.ruta_id=vp.ruta_id "+
						 " left join vrmcliente vcli on vcli.cliente_id=vp.cliente_id "+
						 " left join vrmpasajero vpas on vpas.pasajero_id=vp.pasajero_id "+
						 " left join vrmforpag vfor on vfor.forpag_id=vp.forpag_id "+
						 " left join vrmservicio vser on vser.servicio_id=vp.servicio_id "+
						 " left join vrmtipcom vtip on vtip.tipcom_id=vp.tipcom_id "+
						 " left join vrmtipmov vmov on vmov.tipmov_id=vp.tipmov_id "+
						 " left join vrmtipforpag vtipfor on vtipfor.tipforpag_id=vp.tipforpag_id "+
						 " left join vrmtarcre vtarcre on vtarcre.tarcre_id=vp.tarcre_id "+
						 " left join vrmagencia vagepar on vagepar.agencia_id=vp.agencia_idpartida "+
						 " left join vrmagencia vagelle on vagelle.agencia_id=vp.agencia_idllegada "+
						 " left join vrmagencia vagevent on vagevent.agencia_id=vp.agencia_id "+
						 " left join vrmusuario vusu on vusu.usuario_id=vp.usuario_id "+
						 " left join vrmcanven vcan on vcan.canven_id=vp.canven_id "+
						 " left join vrmpromocion vpro on vpro.promocion_id=vp.promocion_id "+
						 " left join vrmlogecommerce vlogecom on vlogecom.c_nropedido=vp.nro_operation_niubiz and vlogecom.audfecins >= trunc(sysdate-"+cont+") "+
						 " left join vrtlogsendmailvenpas vlogmail on vlogmail.numoperacion=vp.nro_operation_niubiz and vlogmail.audfecins >= trunc(sysdate-"+cont+") "+
						 //" left join vrtsendmailpasajeros vsend on vsend.archivos like '%'||vp.c_numboleto||'%' and vsend.audfecins >= trunc(sysdate-"+cont+") "+
						 " left join vrtsendmailpasajeros vsend on INSTR(vsend.archivos, vp.c_numboleto) > 0 and vsend.audfecins >= trunc(sysdate-"+cont+") "+
						 " where "+add_consulta+
						 " and vp.audfecins >= trunc(sysdate-"+cont+") order by vp.venpas_id asc";
			
			System.out.println(sql);
			
			return jdbcTemplate.query(sql, new VentasGeneralPasajerosRowMapper());
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<VentasGeneralPasajeros> getBuscarPasajes(String txt_input){
		try{
			txt_input = txt_input.trim();
			txt_input = txt_input.toUpperCase();
			List<Integer> idOriginal = new ArrayList<>();
			List<VentasGeneralPasajeros> lista = new ArrayList<>();
			int cont = 3650;		// 10 años

			if (txt_input.contains("BB") || txt_input.contains("FB") || txt_input.contains("-")) {				/********************** POR BOLETO o FACTURA **********************/
			    String sql;
			    
			    if(txt_input.contains("BB") || txt_input.contains("FB")){
			    	idOriginal = new ArrayList<>();
			    	
			        sql = "SELECT venpas_idoriginal FROM vrtvenpas WHERE C_NUMBOLETO = ? AND audfecins >= TRUNC(SYSDATE - ?)";
			        idOriginal = jdbcTemplate.queryForList(sql, Integer.class, txt_input, cont);
			    }else{
			    	cont = 0;
			    	idOriginal = new ArrayList<>();
			    	
			        sql = "SELECT venpas_idoriginal FROM vrtvenpas WHERE C_NUMBOLANT = ? AND audfecins >= TRUNC(SYSDATE - ?)";
			        idOriginal = jdbcTemplate.queryForList(sql, Integer.class, txt_input, cont);
			    }

			    if (idOriginal.isEmpty()) {
			        return lista; // Si no encontramos el idOriginal, devolvemos la lista vacía
			    }
			    
			    // Obtener los detalles del venpas_idoriginal encontrado
			    sql = "SELECT n_numopeban, nro_operation_niubiz, nro_cip_pagoefectivo FROM vrtvenpas " +
			          "WHERE VENPAS_IDORIGINAL = ? AND audfecins >= TRUNC(SYSDATE - ?) AND ROWNUM = 1";
			    List<RptVenpasIdOriginal> datVenpasIdOriginal = jdbcTemplate.query(sql, new RptVenpasIdOriginalRowMapper(), idOriginal.get(0), cont);
			    			    
			    // Verificamos si obtuvimos algún resultado
			    if (!datVenpasIdOriginal.isEmpty()) {
			        String n_numopeban = datVenpasIdOriginal.get(0).getN_numopeban();
			        String nro_operation_niubiz = datVenpasIdOriginal.get(0).getNro_operation_niubiz();
			        String nro_cip_pagoefectivo = datVenpasIdOriginal.get(0).getNro_cip_pagoefectivo();
			        String consulta = "";
			        
			        if (n_numopeban != null) {
			            // Consultar por n_numopeban
			            consulta = obtenerConsultaPorCampo("n_numopeban", n_numopeban, cont);
			            lista = buscarVentasPasajes("vp.venpas_idoriginal IN " + consulta, cont);
			        } else if (nro_operation_niubiz != null) {
			            // Consultar por nro_operation_niubiz
			            consulta = obtenerConsultaPorCampo("nro_operation_niubiz", nro_operation_niubiz, cont);
			            lista = buscarVentasPasajes("vp.venpas_idoriginal IN " + consulta, cont);
			        } else if (nro_cip_pagoefectivo != null) {
			            // Consultar por nro_cip_pagoefectivo
			            consulta = obtenerConsultaPorCampo("n_numopeban", nro_cip_pagoefectivo, cont);
			            lista = buscarVentasPasajes("vp.venpas_idoriginal IN " + consulta, cont);
			        } else {
			            // Caso en el que no encontramos otros valores, se consulta directamente por el idOriginal
			            lista = buscarVentasPasajes("vp.venpas_idoriginal = " + idOriginal.get(0), cont);
			        }
			    }
			    
			    return lista;
			}else{												/********************** POR NUMERO DE OPERACION **********************/				
				String sql;
				List<RptVenpasIdOriginal> datVenpasIdOriginal;
				
			    sql = "SELECT n_numopeban, nro_operation_niubiz, nro_cip_pagoefectivo " +
			          "FROM vrtvenpas " +
			          "WHERE (n_numopeban = ? OR nro_operation_niubiz = ?) " +
			          "AND audfecins >= TRUNC(SYSDATE - ?) AND ROWNUM = 1";
			    
			    datVenpasIdOriginal = jdbcTemplate.query(sql, new RptVenpasIdOriginalRowMapper(), txt_input, txt_input, cont);
				
				if (!datVenpasIdOriginal.isEmpty()) {
				    String n_numopeban = datVenpasIdOriginal.get(0).getN_numopeban();
				    String nro_operation_niubiz = datVenpasIdOriginal.get(0).getNro_operation_niubiz();
				    String nro_cip_pagoefectivo = datVenpasIdOriginal.get(0).getNro_cip_pagoefectivo();
				    String consulta = "";
				    				    
				    if (n_numopeban != null) {
			            // Consultar por n_numopeban
			            consulta = obtenerConsultaPorCampo("n_numopeban", n_numopeban, cont);
			            lista = buscarVentasPasajes("vp.venpas_idoriginal IN " + consulta, cont);
			        } else if (nro_operation_niubiz != null) {
			            // Consultar por nro_operation_niubiz
			            consulta = obtenerConsultaPorCampo("nro_operation_niubiz", nro_operation_niubiz, cont);
			            lista = buscarVentasPasajes("vp.venpas_idoriginal IN " + consulta, cont);
			        } else if (nro_cip_pagoefectivo != null) {
			            // Consultar por nro_cip_pagoefectivo
			            consulta = obtenerConsultaPorCampo("n_numopeban", nro_cip_pagoefectivo, cont);
			            lista = buscarVentasPasajes("vp.venpas_idoriginal IN " + consulta, cont);
			        }
				    
				    return lista;
				}

				return new ArrayList<>(); // Si no se encuentra ningún resultado
			}
			/*********************************************************************************************************/		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	private String obtenerConsultaPorCampo(String campo, String valor, int cont) {
	    String sql = "SELECT venpas_idoriginal FROM vrtvenpas WHERE " + campo + " = ? AND audfecins >= TRUNC(SYSDATE - ?)";
	    List<Integer> data = jdbcTemplate.queryForList(sql, Integer.class, valor, cont);
	    Set<Integer> set = new HashSet<>(data);  // Evitamos duplicados
	    data.clear();
	    data.addAll(set);

	    // Construimos la consulta con los valores únicos
	    StringBuilder consulta = new StringBuilder("(");
	    for (int dat : data) {
	        consulta.append(dat).append(",");
	    }
	    if (consulta.length() > 1) {
	        consulta.setLength(consulta.length() - 1); // Elimina la última coma
	    }
	    consulta.append(")");

	    return consulta.toString();
	}
	
	@Override
	public MensajeConfirmacionResult pagoLinkIziPay(VentasGeneral venta){
		try{
			String sql = "";
			
			/*------------------------ Evitar Duplicidad de Pasajeros -----------------------*/
			List<String> lstPasajero = new ArrayList<String>();
			String numDocPasajero = new String();
			
			for(VentaPasajeros obj : venta.getVentaPasajeros()){
				numDocPasajero = new String();
				numDocPasajero = obj.getVentaIda().getPasajero().getNumDocumento();
				lstPasajero.add(numDocPasajero);
				
				if(obj.getVentaVuelta() != null ) {
					numDocPasajero = new String();
					numDocPasajero = obj.getVentaVuelta().getPasajero().getNumDocumento();
					lstPasajero.add(numDocPasajero);
				}
			}
			
			Set<String> s= new HashSet<String>();
		    s.addAll(lstPasajero);
		    lstPasajero = new ArrayList<String>();
		    lstPasajero.addAll(s);
		    
		    for(VentaPasajeros obj : venta.getVentaPasajeros()){
				for(int a=0; a<lstPasajero.size(); a++){
					int cont = 0;
					
			    	if(obj.getVentaIda().getPasajero().getNumDocumento().equals(lstPasajero.get(a))){
			    		actualizarPasajeroNew(obj.getVentaIda().getPasajero());
			    		actualizarTelefonoPasajeroNew(obj.getVentaIda().getPasajero().getIdTipoDocumento(), obj.getVentaIda().getPasajero().getNumDocumento(), obj.getVentaIda().getTelefonoOpcional());
			    		cont = 1;
			    	}
			    	
			    	if(obj.getVentaVuelta() != null && cont==0){
						if(obj.getVentaVuelta().getPasajero().getNumDocumento().equals(lstPasajero.get(a))){
							actualizarPasajeroNew(obj.getVentaVuelta().getPasajero());
							actualizarTelefonoPasajeroNew(obj.getVentaVuelta().getPasajero().getIdTipoDocumento(), obj.getVentaVuelta().getPasajero().getNumDocumento(), obj.getVentaVuelta().getTelefonoOpcional());
				    	}
					}
			    }
			}
			/*------------------------ Evitar Duplicidad de Pasajeros -----------------------*/
			
			/*********************** ELIMINAR RESERVA DE ASIENTO ***********************/
			for(VentaPasajeros obj : venta.getVentaPasajeros()){
				if(obj.getVentaIda() != null){
					try{
						String sql_asientos_ida = " delete from pasajes.VRTTMPOCUASI TMP "+
					   			" where tmp.itinerario_id = "+obj.getVentaIda().getIdItinerario()+
					   			" and tmp.ruta_id = "+obj.getVentaIda().getIdRuta()+
					   			" and tmp.N_ASIENTO ="+obj.getVentaIda().getNroAsiento()+
					   			" and tmp.N_NUMPISO ="+obj.getVentaIda().getNroPiso()+
					   			" AND tmp.usuario_id="+venta.getIdUsuarioSispas()+
					   			" AND tmp.usuhard_id="+venta.getIdHardwareSispas();
						
						jdbcTemplate.update(sql_asientos_ida);
					}catch(Exception ex){ex.printStackTrace();}
				}
				
				if(obj.getVentaVuelta() != null){
					try{
					String sql_asientos_vuelta = " delete from pasajes.VRTTMPOCUASI TMP "+
				   			" where tmp.itinerario_id = "+obj.getVentaVuelta().getIdItinerario()+
				   			" and tmp.ruta_id = "+obj.getVentaVuelta().getIdRuta()+
				   			" and tmp.N_ASIENTO ="+obj.getVentaVuelta().getNroAsiento()+
				   			" and tmp.N_NUMPISO ="+obj.getVentaVuelta().getNroPiso()+
				   			" AND tmp.usuario_id="+venta.getIdUsuarioSispas()+
				   			" AND tmp.usuhard_id="+venta.getIdHardwareSispas();
					
					jdbcTemplate.update(sql_asientos_vuelta);
					}catch(Exception ex){ex.printStackTrace();}
				}
			}
			/*********************** ELIMINAR RESERVA DE ASIENTO ***********************/
			
			int cont_factura = 0;
			
			// Actualizar el cliente por cada Venta
			if(venta.getCliente() != null){
				actualizarCliente(venta.getCliente());
				cont_factura = 1;
			}
			
			int tipcom_val = 0;
			
			if(cont_factura == 1){
				tipcom_val = 2;
			}else{
				tipcom_val = 7;
			}
			
			double montoTotal = 0.0;
			
			LocalDateTime base = expirationDateUnaHoraRedondeadaBase();
			
			String formatoHoraCompleto = base.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
		    String formatoHoraOracle = base.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
		    
		    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		    String fechaFinal = base.format(formatter);
		    fechaFinal = resetHoraAInicioDia(fechaFinal);
		    
			// Realizar los registros de la venta
			// INTEGRACION NIUBIZ - NRO DE OPERACION
			sql = " SELECT LPAD(SEQ_NRO_OPERACION_NIUBIZ_ID.NEXTVAL,12, '0') FROM DUAL";
			//List<String> nroOperacion = jdbcTemplate.query(sql, new StringRowMapper());
			String nroOperacionIziPay = jdbcTemplate.queryForObject(sql, String.class);
						
			if(!nroOperacionIziPay.isEmpty()){
				
				nroOperacionIziPay = "IZIPL"+nroOperacionIziPay;
				
				for(VentaPasajeros obj : venta.getVentaPasajeros()){
					// Registro de Ventas de IDA
					sql = "SELECT SEQ_VRTVENPAS_ID.NEXTVAL FROM DUAL";
					List<Long> idVentaIda = jdbcTemplate.query(sql, new LongRowMapper());
					
					if(idVentaIda.size() > 0){
						String c_numcontrol = generateControlNumber(decimalToHexadecimal(idVentaIda.get(0)));
						BigDecimal idPasajero = buscarIdPasajero(obj.getVentaIda().getPasajero());
						
						//registraVentasVrtVenpasNiubizPagoEfectivo(idVentaIda.get(0), venta, c_numcontrol, obj.getVentaIda(), (obj.getVentaVuelta() != null), idVentaIda.get(0), nroOperacionIziPay, Constantes.ID_TIP_FORMA_PAGO_PAGO_LINK, 
						//										  "(TO_CHAR(SYSDATE+(60/1440), 'HH24:MI:SS'))", "", tipcom_val, idPasajero, obj.getVentaIda().getEmailContacto(), obj.getVentaIda().getTelefonoOpcional());
						
						registraVentasVrtVenpasNiubizPagoEfectivo(idVentaIda.get(0), venta, c_numcontrol, obj.getVentaIda(), (obj.getVentaVuelta() != null), idVentaIda.get(0), nroOperacionIziPay, Constantes.ID_TIP_FORMA_PAGO_PAGO_LINK, 
								fechaFinal, formatoHoraOracle, "", tipcom_val, idPasajero, obj.getVentaIda().getEmailContacto(), obj.getVentaIda().getTelefonoOpcional());
						
						//Colocando Venpas ID a cada pasajero
						obj.getVentaIda().setIdVenta(new BigDecimal(idVentaIda.get(0)));
					}else return new MensajeConfirmacionResult(Constantes.RESULT_FALSE, "PROBLEMAS CON NRO ID DE VENTAS IDA.");
					
					// Registro de Ventas de VUELTA
					if(obj.getVentaVuelta() != null){
						sql = "SELECT SEQ_VRTVENPAS_ID.NEXTVAL  FROM DUAL";
						List<Long> idVentaVuelta = jdbcTemplate.query(sql, new LongRowMapper());
						if(idVentaVuelta.size() > 0){
							String c_numcontrol = generateControlNumber(decimalToHexadecimal(idVentaVuelta.get(0)));
							BigDecimal idPasajero = buscarIdPasajero(obj.getVentaVuelta().getPasajero());
															
							//registraVentasVrtVenpasNiubizPagoEfectivo(idVentaVuelta.get(0), venta, c_numcontrol, obj.getVentaVuelta(), (obj.getVentaVuelta() != null), idVentaIda.get(0), nroOperacionIziPay, Constantes.ID_TIP_FORMA_PAGO_PAGO_LINK, 
							//										  "(TO_CHAR(SYSDATE+(60/1440), 'HH24:MI:SS'))", "", tipcom_val, idPasajero, obj.getVentaVuelta().getEmailContacto(), obj.getVentaVuelta().getTelefonoOpcional());
							
							registraVentasVrtVenpasNiubizPagoEfectivo(idVentaVuelta.get(0), venta, c_numcontrol, obj.getVentaVuelta(), (obj.getVentaVuelta() != null), idVentaIda.get(0), nroOperacionIziPay, Constantes.ID_TIP_FORMA_PAGO_PAGO_LINK, 
									fechaFinal, formatoHoraOracle, "", tipcom_val, idPasajero, obj.getVentaVuelta().getEmailContacto(), obj.getVentaVuelta().getTelefonoOpcional());
							
							//Colocando Venpas ID a cada pasajero
							obj.getVentaVuelta().setIdVenta(new BigDecimal(idVentaVuelta.get(0)));
						}else return new MensajeConfirmacionResult(Constantes.RESULT_FALSE, "PROBLEMAS CON NRO ID DE VENTAS VUELTA.");
					}
				}
				
				//ACTUALIZAR VENPAS_IDPARENTS | RELACION PADRE - HIJO
				List<Integer> list_idventas = new ArrayList<>();
				
				for(VentaPasajeros obj : venta.getVentaPasajeros()){
					if(obj.getVentaIda().getIdParentesco() == 4 || obj.getVentaIda().getIdParentesco() == 5){									// SI ES HIJO O CARTA APODERADO
						for(VentaPasajeros obj2 : venta.getVentaPasajeros()){
							if(obj2.getVentaIda().getPasajero().getNumDocumento().equals(obj.getVentaIda().getDniApoderado())){					// COMPARA CON EL DNI DE LOS PADRES-MADRES Y APODERADOS
								String sql_update = " UPDATE pasajes.VRTVENPAS set VENPAS_IDPARENTS = "+obj2.getVentaIda().getIdVenta()+" where VENPAS_ID = "+obj.getVentaIda().getIdVenta();
								jdbcTemplate.update(sql_update);
								
								list_idventas.add(Integer.valueOf(obj2.getVentaIda().getIdVenta().intValue()));
							}
						}
					}
					
					if(obj.getVentaVuelta() != null) {
						if(obj.getVentaVuelta().getIdParentesco() == 4 || obj.getVentaVuelta().getIdParentesco() == 5){							// SI ES HIJO O CARTA APODERADO
							for(VentaPasajeros obj2 : venta.getVentaPasajeros()){
								if(obj2.getVentaVuelta().getPasajero().getNumDocumento().equals(obj.getVentaVuelta().getDniApoderado())){		// COMPARA CON EL DNI DE LOS PADRES-MADRES Y APODERADOS
									String sql_update = " UPDATE pasajes.VRTVENPAS set VENPAS_IDPARENTS = "+obj2.getVentaVuelta().getIdVenta()+" where VENPAS_ID = "+obj.getVentaVuelta().getIdVenta();
									jdbcTemplate.update(sql_update);
								}
							}
						}
					}
				}
				
				if(list_idventas.size() > 0){
					//ACTUALIZAR VENPAS_IDTX | RELACION PADRE - HIJO
					Integer menorValor = list_idventas.stream().min(Comparator.comparing( v->v)).orElseThrow(NoSuchElementException::new);
					
					for(VentaPasajeros obj : venta.getVentaPasajeros()){
						if(obj.getVentaIda() != null) {
							String sql_update = " UPDATE pasajes.VRTVENPAS set VENPAS_IDTX = "+menorValor+" where VENPAS_ID = "+obj.getVentaIda().getIdVenta();
							jdbcTemplate.update(sql_update);
						}
						
						if(obj.getVentaVuelta() != null) {
							String sql_update = " UPDATE pasajes.VRTVENPAS set VENPAS_IDTX = "+menorValor+" where VENPAS_ID = "+obj.getVentaVuelta().getIdVenta();
							jdbcTemplate.update(sql_update);
						}
					}
				}
				
				montoTotal = venta.getMontoTotal();
				
				String montoFormateado = String.format(Locale.US, "%.2f", montoTotal);
				
				// INTEGRACIÓN PAGO LINK
				//String nombrePasajero = venta.getVentaPasajeros().get(0).getVentaIda().getPasajero().getNombre() + " " + venta.getVentaPasajeros().get(0).getVentaIda().getPasajero().getApePaterno() + " " + venta.getVentaPasajeros().get(0).getVentaIda().getPasajero().getApeMaterno();
				
				String textoViajeRuta = "";
				sql = " select c_origen || '-' || c_destino from vrmruta where ruta_id="+venta.getVentaPasajeros().get(0).getVentaIda().getIdRuta();
				List<String> textoRuta = jdbcTemplate.query(sql, new StringRowMapper());
				String[] parts = textoRuta.get(0).split("-");
				String texto_origen = parts[0];
				String texto_destino = parts[1];
				
				textoViajeRuta = texto_origen + " - " + texto_destino;
				if(venta.getVentaPasajeros().get(0).getVentaVuelta() != null){
					textoViajeRuta = texto_origen + " - " + texto_destino + " - " + texto_origen;
				}
				
				/*ResultPagoLink resultPagoLink = ObtenerInfoOperation(nroOperacion, textoViajeRuta + " | "+venta.getUsuarioSispas(), montoTotal, nombrePasajero, venta.getVentaPasajeros().get(0).getVentaIda().getEmailContacto());
				
				String urlPagoLink = resultPagoLink.getLink();*/
				
				//INTEGRACION - IZIPAY
				MensajeFlagResultIziPay result = IziPay.getTokenIziPay(nroOperacionIziPay);
				if(result.getResult() == true){
					//String urlPagoLink = IziPay.getLink(result.getTokenSession(), nroOperacionIziPay, montoFormateado, textoViajeRuta + " "+venta.getUsuarioSispas()+" "+nroOperacionIziPay, venta.getVentaPasajeros().get(0), formatoHoraCompleto);
					String urlPagoLink = IziPay.getLink(result.getTokenSession(), nroOperacionIziPay, montoFormateado, textoViajeRuta + " en Móvil Bus "+nroOperacionIziPay, venta.getVentaPasajeros().get(0), formatoHoraCompleto);
					
					if(urlPagoLink != null && !urlPagoLink.trim().isEmpty()){
						//System.out.println("urlPagoLink: "+urlPagoLink);
						//enviarCorreoPasajeroPagoLink(venta.getVentaPasajeros().get(0).getVentaIda().getEmailContacto(), urlPagoLink, nroOperacionIziPay);
						actualizarDatosHistorialVentas(venta.getVentaPasajeros().get(0).getVentaIda().getEmailContacto(), urlPagoLink, result.getTokenSession(), nroOperacionIziPay);
						System.out.println("SE ENVIA CORREO.");
						return new MensajeConfirmacionResult(Constantes.RESULT_TRUE, nroOperacionIziPay, urlPagoLink);
					}else{
						System.out.println("NO SE ENVIA CORREO.");
						return new MensajeConfirmacionResult(Constantes.RESULT_FALSE, nroOperacionIziPay, "Error al generar el link de IziPay PagoLink, generar nuevamente el Link.");
					}
				}else{
					return new MensajeConfirmacionResult(Constantes.RESULT_FALSE, "", "Error al generar el token con IziPay PagoLink.");
				}
				
			}else return new MensajeConfirmacionResult(Constantes.RESULT_FALSE, "", "Error al generar la venta, volver realizar la venta 1 .");					//new MensajeFlagResult(Constantes.RESULT_FALSE, "No tiene nro Operacion NIUBIZ.LLAMAR A SISTEMAS")
		}catch(Exception e){
			// TODO: handle exception
			e.printStackTrace();
			return new MensajeConfirmacionResult(Constantes.RESULT_FALSE, "", "Error al generar la venta, volver realizar la venta.");							//new MensajeFlagResult(Constantes.RESULT_FALSE, "LLAMAR A SISTEMAS")
		}
	}
	
	public static String resetHoraAInicioDia(String fechaHora) {
	    if (fechaHora == null || fechaHora.isEmpty()) {
	        return fechaHora;
	    }

	    String[] partes = fechaHora.split(" ");
	    return partes[0] + " 00:00:00";
	}
	
	public static LocalDateTime expirationDateUnaHoraRedondeadaBase() {
	    LocalDateTime ahora = LocalDateTime.now().plusHours(1);
	    int minutos = ahora.getMinute();

	    if (minutos == 0) {
	        // no cambia
	    } else if (minutos <= 30) {
	        ahora = ahora.withMinute(30);
	    } else {
	        ahora = ahora.plusHours(1).withMinute(0);
	    }

	    return ahora.withSecond(0).withNano(0);
	}
	
	@Override
	public ResponseEntity<String> actualizarVentasPagoLinkIziPay(String signature, String body){
		try{
			JSONObject obj = new JSONObject(body);
	        
            if (obj.has("code") && "00".equals(obj.getString("code"))) {
            	JSONObject response = obj.getJSONObject("response");

                // === ORDER NUMBER (IZIPAY INTERN) ===
                //JSONArray orderArray = response.getJSONArray("order");
                //JSONObject order = orderArray.getJSONObject(0);
                //String orderNumber = order.getString("orderNumber"); // LP0002412300001

                JSONArray customFields = response.getJSONArray("customFields");
                String numOperacion = "";

                for (int i = 0; i < customFields.length(); i++) {
                    JSONObject field = customFields.getJSONObject(i);
                    if ("field1".equals(field.getString("name"))) {
                    	numOperacion = field.getString("value");
                        break;
                    }
                }
                
    			String sql = " select venpas_id, cliente_id, agencia_id, c_tiptra, c_estdoc, c_email_contacto, parentesco_id, n_tipo_pasajero from vrtvenpas "+
				 " where n_numopeban='"+numOperacion+"' and c_tiptra=2 and c_estdoc is null ";
	
				List<UpdateVrtVenpas> updateVrtVenpas = jdbcTemplate.query(sql, new UpdateVrtVenpasRowMapper());
				
				String email_contacto = "";
				String texto_ventas_concatenado = "";
				String texto_archivos_concatenado = "";
				
				if(updateVrtVenpas.size() > 0){
					for(UpdateVrtVenpas obj2 : updateVrtVenpas){
						actualizarVrtVenpasNiubizPagoEfectivo(obj2.getVenpas_id());
						actualizarVrtVenpasNiubizPagoEfectivoTarCre(obj2.getVenpas_id());
						
						if(obj2.getN_tipo_pasajero() != 3 && obj2.getParentesco_id() != 4){
							/*************** GENERAR EL BOLETO *************/
							int cont_factura = 0;
							if(obj2.getCliente_id() != null){
								cont_factura = 1;
							}
							
							int tipcom_val = 0;
							
							if(cont_factura == 1){
								tipcom_val = 2;
							}else{
								tipcom_val = 7;
							}
							
							sql = "select c_corseq, c_serie from vrmespval where agencia_id="+obj2.getAgencia_id()+" and tipcom_id="+tipcom_val+" and empresa_id=1 and c_estreg='"+Constantes.ACTIVO+"'";
							List<Secuencia> lstSecuencia = jdbcTemplate.query(sql, new SecuenciaRowMapper());
							
							if(lstSecuencia.size() > 0){
								sql = "SELECT "+lstSecuencia.get(0).getC_corseq()+".NEXTVAL FROM DUAL";
								String correlativo = jdbcTemplate.queryForObject(sql, String.class);
								correlativo = String.format("%08d", Integer.valueOf(correlativo));
								
								String c_numboleto = lstSecuencia.get(0).getC_serie()+"-"+correlativo.trim();
								
								actualizarVrtVenpasNiubizPagoEfectivoBoletos(obj2.getVenpas_id(), c_numboleto);
								actualizarVrtVenpasNiubizPagoEfectivoHijosBoletos(obj2.getVenpas_id(), c_numboleto);
								
								texto_ventas_concatenado = obj2.getVenpas_id() + ";" + texto_ventas_concatenado;
								texto_archivos_concatenado = c_numboleto + ";" + texto_archivos_concatenado;
								email_contacto = obj2.getC_email_contacto();
							}
						}
					}
				}
				
				if(updateVrtVenpas.size() > 0){
					insertSendEmailPasajeros(email_contacto, texto_ventas_concatenado, texto_archivos_concatenado);
					
					ResponseEntity.ok("OK");
				}
            }
            
			return ResponseEntity.ok("OK");
		} catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno");
	    }
	}
	
	@Override
	public MensajeConfirmacionResult pagoLinkNiubiz(VentasGeneral venta){
//		try {
//			String sql = "";
//			
//			/*------------------------ Evitar Duplicidad de Pasajeros -----------------------*/
//			List<String> lstPasajero = new ArrayList<String>();
//			String numDocPasajero = new String();
//			
//			for(VentaPasajeros obj : venta.getVentaPasajeros()){
//				numDocPasajero = new String();
//				numDocPasajero = obj.getVentaIda().getPasajero().getNumDocumento();
//				lstPasajero.add(numDocPasajero);
//				
//				if(obj.getVentaVuelta() != null ) {
//					numDocPasajero = new String();
//					numDocPasajero = obj.getVentaVuelta().getPasajero().getNumDocumento();
//					lstPasajero.add(numDocPasajero);
//				}
//			}
//			
//			Set<String> s= new HashSet<String>();
//		    s.addAll(lstPasajero);
//		    lstPasajero = new ArrayList<String>();
//		    lstPasajero.addAll(s);
//		    
//		    for(VentaPasajeros obj : venta.getVentaPasajeros()){
//				for(int a=0; a<lstPasajero.size(); a++){
//					int cont = 0;
//					
//			    	if(obj.getVentaIda().getPasajero().getNumDocumento().equals(lstPasajero.get(a))){
//			    		actualizarPasajeroNew(obj.getVentaIda().getPasajero());
//			    		actualizarTelefonoPasajeroNew(obj.getVentaIda().getPasajero().getIdTipoDocumento(), obj.getVentaIda().getPasajero().getNumDocumento(), obj.getVentaIda().getTelefonoOpcional());
//			    		cont = 1;
//			    	}
//			    	
//			    	if(obj.getVentaVuelta() != null && cont==0){
//						if(obj.getVentaVuelta().getPasajero().getNumDocumento().equals(lstPasajero.get(a))){
//							actualizarPasajeroNew(obj.getVentaVuelta().getPasajero());
//							actualizarTelefonoPasajeroNew(obj.getVentaVuelta().getPasajero().getIdTipoDocumento(), obj.getVentaVuelta().getPasajero().getNumDocumento(), obj.getVentaVuelta().getTelefonoOpcional());
//				    	}
//					}
//			    }
//			}
//			/*------------------------ Evitar Duplicidad de Pasajeros -----------------------*/
//			
//			/*********************** ELIMINAR RESERVA DE ASIENTO ***********************/
//			for(VentaPasajeros obj : venta.getVentaPasajeros()){
//				if(obj.getVentaIda() != null){
//					try{
//						String sql_asientos_ida = " delete from pasajes.VRTTMPOCUASI TMP "+
//					   			" where tmp.itinerario_id = "+obj.getVentaIda().getIdItinerario()+
//					   			" and tmp.ruta_id = "+obj.getVentaIda().getIdRuta()+
//					   			" and tmp.N_ASIENTO ="+obj.getVentaIda().getNroAsiento()+
//					   			" and tmp.N_NUMPISO ="+obj.getVentaIda().getNroPiso()+
//					   			" AND tmp.usuario_id="+venta.getIdUsuarioSispas()+
//					   			" AND tmp.usuhard_id="+venta.getIdHardwareSispas();
//						
//						jdbcTemplate.update(sql_asientos_ida);
//					}catch(Exception ex){ex.printStackTrace();}
//				}
//				
//				if(obj.getVentaVuelta() != null){
//					try{
//					String sql_asientos_vuelta = " delete from pasajes.VRTTMPOCUASI TMP "+
//				   			" where tmp.itinerario_id = "+obj.getVentaVuelta().getIdItinerario()+
//				   			" and tmp.ruta_id = "+obj.getVentaVuelta().getIdRuta()+
//				   			" and tmp.N_ASIENTO ="+obj.getVentaVuelta().getNroAsiento()+
//				   			" and tmp.N_NUMPISO ="+obj.getVentaVuelta().getNroPiso()+
//				   			" AND tmp.usuario_id="+venta.getIdUsuarioSispas()+
//				   			" AND tmp.usuhard_id="+venta.getIdHardwareSispas();
//					
//					jdbcTemplate.update(sql_asientos_vuelta);
//					}catch(Exception ex){ex.printStackTrace();}
//				}
//			}
//			/*********************** ELIMINAR RESERVA DE ASIENTO ***********************/
//			
//			int cont_factura = 0;
//			
//			// Actualizar el cliente por cada Venta
//			if(venta.getCliente() != null){
//				actualizarCliente(venta.getCliente());
//				cont_factura = 1;
//			}
//			
//			int tipcom_val = 0;
//			
//			if(cont_factura == 1){
//				tipcom_val = 2;
//			}else{
//				tipcom_val = 7;
//			}
//			
//			double montoTotal = 0.0;
//			
//			// Realizar los registros de la venta
//			// INTEGRACION NIUBIZ - NRO DE OPERACION
//			sql = " SELECT LPAD(SEQ_NRO_OPERACION_NIUBIZ_ID.NEXTVAL,12, '0') FROM DUAL";
//			//List<String> nroOperacion = jdbcTemplate.query(sql, new StringRowMapper());
//			String nroOperacion = jdbcTemplate.queryForObject(sql, String.class);
//						
//			if(!nroOperacion.isEmpty()){
//				
//				for(VentaPasajeros obj : venta.getVentaPasajeros()){
//					// Registro de Ventas de IDA
//					sql = "SELECT SEQ_VRTVENPAS_ID.NEXTVAL FROM DUAL";
//					List<Long> idVentaIda = jdbcTemplate.query(sql, new LongRowMapper());
//					
//					if(idVentaIda.size() > 0){
//						String c_numcontrol = generateControlNumber(decimalToHexadecimal(idVentaIda.get(0)));
//						BigDecimal idPasajero = buscarIdPasajero(obj.getVentaIda().getPasajero());
//						
//						registraVentasVrtVenpasNiubizPagoEfectivo(idVentaIda.get(0), venta, c_numcontrol, obj.getVentaIda(), (obj.getVentaVuelta() != null), idVentaIda.get(0), nroOperacion, Constantes.ID_TIP_FORMA_PAGO_PAGO_LINK, 
//																  "(TO_CHAR(SYSDATE+(60/1440), 'HH24:MI:SS'))", "", tipcom_val, idPasajero, obj.getVentaIda().getEmailContacto(), obj.getVentaIda().getTelefonoOpcional());
//						
//						//Colocando Venpas ID a cada pasajero
//						obj.getVentaIda().setIdVenta(new BigDecimal(idVentaIda.get(0)));
//					}else return new MensajeConfirmacionResult(Constantes.RESULT_FALSE, "PROBLEMAS CON NRO ID DE VENTAS IDA.");
//					
//					// Registro de Ventas de VUELTA
//					if(obj.getVentaVuelta() != null){
//						sql = "SELECT SEQ_VRTVENPAS_ID.NEXTVAL  FROM DUAL";
//						List<Long> idVentaVuelta = jdbcTemplate.query(sql, new LongRowMapper());
//						if(idVentaVuelta.size() > 0){
//							String c_numcontrol = generateControlNumber(decimalToHexadecimal(idVentaVuelta.get(0)));
//							BigDecimal idPasajero = buscarIdPasajero(obj.getVentaVuelta().getPasajero());
//															
//							registraVentasVrtVenpasNiubizPagoEfectivo(idVentaVuelta.get(0), venta, c_numcontrol, obj.getVentaVuelta(), (obj.getVentaVuelta() != null), idVentaIda.get(0), nroOperacion, Constantes.ID_TIP_FORMA_PAGO_PAGO_LINK, 
//																	  "(TO_CHAR(SYSDATE+(60/1440), 'HH24:MI:SS'))", "", tipcom_val, idPasajero, obj.getVentaVuelta().getEmailContacto(), obj.getVentaVuelta().getTelefonoOpcional());
//							
//							//Colocando Venpas ID a cada pasajero
//							obj.getVentaVuelta().setIdVenta(new BigDecimal(idVentaVuelta.get(0)));
//						}else return new MensajeConfirmacionResult(Constantes.RESULT_FALSE, "PROBLEMAS CON NRO ID DE VENTAS VUELTA.");
//					}
//				}
//				
//				//ACTUALIZAR VENPAS_IDPARENTS | RELACION PADRE - HIJO
//				List<Integer> list_idventas = new ArrayList<>();
//				
//				for(VentaPasajeros obj : venta.getVentaPasajeros()){
//					if(obj.getVentaIda().getIdParentesco() == 4 || obj.getVentaIda().getIdParentesco() == 5){									// SI ES HIJO O CARTA APODERADO
//						for(VentaPasajeros obj2 : venta.getVentaPasajeros()){
//							if(obj2.getVentaIda().getPasajero().getNumDocumento().equals(obj.getVentaIda().getDniApoderado())){					// COMPARA CON EL DNI DE LOS PADRES-MADRES Y APODERADOS
//								String sql_update = " UPDATE pasajes.VRTVENPAS set VENPAS_IDPARENTS = "+obj2.getVentaIda().getIdVenta()+" where VENPAS_ID = "+obj.getVentaIda().getIdVenta();
//								jdbcTemplate.update(sql_update);
//								
//								list_idventas.add(Integer.valueOf(obj2.getVentaIda().getIdVenta().intValue()));
//							}
//						}
//					}
//					
//					if(obj.getVentaVuelta() != null) {
//						if(obj.getVentaVuelta().getIdParentesco() == 4 || obj.getVentaVuelta().getIdParentesco() == 5){							// SI ES HIJO O CARTA APODERADO
//							for(VentaPasajeros obj2 : venta.getVentaPasajeros()){
//								if(obj2.getVentaVuelta().getPasajero().getNumDocumento().equals(obj.getVentaVuelta().getDniApoderado())){		// COMPARA CON EL DNI DE LOS PADRES-MADRES Y APODERADOS
//									String sql_update = " UPDATE pasajes.VRTVENPAS set VENPAS_IDPARENTS = "+obj2.getVentaVuelta().getIdVenta()+" where VENPAS_ID = "+obj.getVentaVuelta().getIdVenta();
//									jdbcTemplate.update(sql_update);
//								}
//							}
//						}
//					}
//				}
//				
//				if(list_idventas.size() > 0){
//					//ACTUALIZAR VENPAS_IDTX | RELACION PADRE - HIJO
//					Integer menorValor = list_idventas.stream().min(Comparator.comparing( v->v)).orElseThrow(NoSuchElementException::new);
//					
//					for(VentaPasajeros obj : venta.getVentaPasajeros()){
//						if(obj.getVentaIda() != null) {
//							String sql_update = " UPDATE pasajes.VRTVENPAS set VENPAS_IDTX = "+menorValor+" where VENPAS_ID = "+obj.getVentaIda().getIdVenta();
//							jdbcTemplate.update(sql_update);
//						}
//						
//						if(obj.getVentaVuelta() != null) {
//							String sql_update = " UPDATE pasajes.VRTVENPAS set VENPAS_IDTX = "+menorValor+" where VENPAS_ID = "+obj.getVentaVuelta().getIdVenta();
//							jdbcTemplate.update(sql_update);
//						}
//					}
//				}
//				
//				montoTotal = venta.getMontoTotal();
//				
//				// INTEGRACIÓN PAGO LINK
//				String nombrePasajero = venta.getVentaPasajeros().get(0).getVentaIda().getPasajero().getNombre() + " " + venta.getVentaPasajeros().get(0).getVentaIda().getPasajero().getApePaterno() + " " + venta.getVentaPasajeros().get(0).getVentaIda().getPasajero().getApeMaterno();
//				
//				String textoViajeRuta = "";
//				sql = " select c_origen || '-' || c_destino from vrmruta where ruta_id="+venta.getVentaPasajeros().get(0).getVentaIda().getIdRuta();
//				List<String> textoRuta = jdbcTemplate.query(sql, new StringRowMapper());
//				String[] parts = textoRuta.get(0).split("-");
//				String texto_origen = parts[0];
//				String texto_destino = parts[1];
//				
//				textoViajeRuta = texto_origen + " - " + texto_destino;
//				if(venta.getVentaPasajeros().get(0).getVentaVuelta() != null){
//					textoViajeRuta = texto_origen + " - " + texto_destino + " - " + texto_origen;
//				}
//				
//				ResultPagoLink resultPagoLink = ObtenerInfoOperation(nroOperacion, textoViajeRuta + " | "+venta.getUsuarioSispas(), montoTotal, nombrePasajero, venta.getVentaPasajeros().get(0).getVentaIda().getEmailContacto());
//				
//				String urlPagoLink = resultPagoLink.getLink();
//				
//				if(urlPagoLink != "" || urlPagoLink != null){
//					enviarCorreoPasajeroPagoLink(venta.getVentaPasajeros().get(0).getVentaIda().getEmailContacto(), urlPagoLink, nroOperacion);
//					actualizarDatosHistorialVentas(venta.getVentaPasajeros().get(0).getVentaIda().getEmailContacto(), urlPagoLink, resultPagoLink.getOrderId(), nroOperacion);
//					System.out.println("SE ENVIA CORREO.");
//					return new MensajeConfirmacionResult(Constantes.RESULT_TRUE, nroOperacion, urlPagoLink);
//				}else{
//					System.out.println("NO SE ENVIA CORREO.");
//					return new MensajeConfirmacionResult(Constantes.RESULT_FALSE, nroOperacion, "Error al generar el link de PagoLink, generar nuevamente el Link.");
//				}
//			}else return new MensajeConfirmacionResult(Constantes.RESULT_FALSE, "", "Error al generar la venta, volver realizar la venta 1 .");					//new MensajeFlagResult(Constantes.RESULT_FALSE, "No tiene nro Operacion NIUBIZ.LLAMAR A SISTEMAS")
//		}catch (Exception e){
//			// TODO: handle exception
//			e.printStackTrace();
//			return new MensajeConfirmacionResult(Constantes.RESULT_FALSE, "", "Error al generar la venta, volver realizar la venta.");							//new MensajeFlagResult(Constantes.RESULT_FALSE, "LLAMAR A SISTEMAS")
//		}
		
		return null;
	}
	
	@Override
	public void actualizarDatosHistorialVentas(String email_contacto, String urlPago, String orderId, String numOperacion){
		try{
			//String sql_update = " UPDATE pasajes.vrthistorialventas set url_pago = '"+urlPago+"', order_id = '"+orderId+"', email_pasajero = '"+email_contacto+"' where numoperacion = '"+numOperacion+"' and audfecins>=trunc(sysdate-1)";
			//jdbcTemplate.update(sql_update);
			
			StringBuilder sql = new StringBuilder("UPDATE pasajes.vrthistorialventas SET ");
			List<Object> params = new ArrayList<>();
			List<String> updates = new ArrayList<>();

			if (urlPago != null && !urlPago.trim().isEmpty()) {
			    updates.add("url_pago = ?");
			    params.add(urlPago);
			}
			if (orderId != null && !orderId.trim().isEmpty()) {
			    updates.add("order_id = ?");
			    params.add(orderId);
			}
			if (email_contacto != null && !email_contacto.trim().isEmpty()) {
			    updates.add("email_pasajero = ?");
			    params.add(email_contacto);
			}

			if (!updates.isEmpty()) {
			    sql.append(String.join(", ", updates));
			    sql.append(" WHERE numoperacion = ? AND audfecins >= TRUNC(SYSDATE - 1)");
			    params.add(numOperacion);
			    
			    jdbcTemplate.update(sql.toString(), params.toArray());
			} else {
			    // Opcional: log o manejo de caso cuando no hay datos válidos para actualizar
			    System.out.println("No hay datos válidos para actualizar.");
			}			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public MensajeConfirmacionResult pagoLinkPagoEfectivo(VentasGeneral venta){
		
//		try {
//			String sql = "";
//			
//			/*------------------------ Evitar Duplicidad de Pasajeros -----------------------*/
//			List<String> lstPasajero = new ArrayList<String>();
//			String numDocPasajero = new String();
//			
//			for(VentaPasajeros obj : venta.getVentaPasajeros()){
//				numDocPasajero = new String();
//				numDocPasajero = obj.getVentaIda().getPasajero().getNumDocumento();
//				lstPasajero.add(numDocPasajero);
//				
//				if(obj.getVentaVuelta() != null ) {
//					numDocPasajero = new String();
//					numDocPasajero = obj.getVentaVuelta().getPasajero().getNumDocumento();
//					lstPasajero.add(numDocPasajero);
//				}
//			}
//			
//			Set<String> s= new HashSet<String>();
//		    s.addAll(lstPasajero);
//		    lstPasajero = new ArrayList<String>();
//		    lstPasajero.addAll(s);
//		    
//		    for(VentaPasajeros obj : venta.getVentaPasajeros()){
//				for(int a=0; a<lstPasajero.size(); a++){
//					int cont = 0;
//					
//			    	if(obj.getVentaIda().getPasajero().getNumDocumento().equals(lstPasajero.get(a))){
//			    		actualizarPasajeroNew(obj.getVentaIda().getPasajero());
//			    		actualizarTelefonoPasajeroNew(obj.getVentaIda().getPasajero().getIdTipoDocumento(), obj.getVentaIda().getPasajero().getNumDocumento(), obj.getVentaIda().getTelefonoOpcional());
//			    		cont = 1;
//			    	}
//			    	
//			    	if(obj.getVentaVuelta() != null && cont==0){
//						if(obj.getVentaVuelta().getPasajero().getNumDocumento().equals(lstPasajero.get(a))){
//							actualizarPasajeroNew(obj.getVentaVuelta().getPasajero());
//							actualizarTelefonoPasajeroNew(obj.getVentaVuelta().getPasajero().getIdTipoDocumento(), obj.getVentaVuelta().getPasajero().getNumDocumento(), obj.getVentaVuelta().getTelefonoOpcional());
//				    	}
//					}
//			    }
//			}
//			/*------------------------ Evitar Duplicidad de Pasajeros -----------------------*/
//			
//			/*********************** ELIMINAR RESERVA DE ASIENTO ***********************/
//			for(VentaPasajeros obj : venta.getVentaPasajeros()){
//				if(obj.getVentaIda() != null){
//					try{
//						String sql_asientos_ida = " delete from pasajes.VRTTMPOCUASI TMP "+
//					   			" where tmp.itinerario_id = "+obj.getVentaIda().getIdItinerario()+
//					   			" and tmp.ruta_id = "+obj.getVentaIda().getIdRuta()+
//					   			" and tmp.N_ASIENTO ="+obj.getVentaIda().getNroAsiento()+
//					   			" and tmp.N_NUMPISO ="+obj.getVentaIda().getNroPiso()+
//					   			" AND tmp.usuario_id="+venta.getIdUsuarioSispas()+
//					   			" AND tmp.usuhard_id="+venta.getIdHardwareSispas();
//						
//						jdbcTemplate.update(sql_asientos_ida);
//					}catch(Exception ex){ex.printStackTrace();}
//				}
//				
//				if(obj.getVentaVuelta() != null){
//					try{
//					String sql_asientos_vuelta = " delete from pasajes.VRTTMPOCUASI TMP "+
//				   			" where tmp.itinerario_id = "+obj.getVentaVuelta().getIdItinerario()+
//				   			" and tmp.ruta_id = "+obj.getVentaVuelta().getIdRuta()+
//				   			" and tmp.N_ASIENTO ="+obj.getVentaVuelta().getNroAsiento()+
//				   			" and tmp.N_NUMPISO ="+obj.getVentaVuelta().getNroPiso()+
//				   			" AND tmp.usuario_id="+venta.getIdUsuarioSispas()+
//				   			" AND tmp.usuhard_id="+venta.getIdHardwareSispas();
//					
//					jdbcTemplate.update(sql_asientos_vuelta);
//					}catch(Exception ex){ex.printStackTrace();}
//				}
//			}
//			/*********************** ELIMINAR RESERVA DE ASIENTO ***********************/
//			
//			int cont_factura = 0;
//			
//			// Actualizar el cliente por cada Venta
//			if(venta.getCliente() != null){
//				actualizarCliente(venta.getCliente());
//				cont_factura = 1;
//			}
//			
//			int tipcom_val = 0;
//			
//			if(cont_factura == 1){
//				tipcom_val = 2;
//			}else{
//				tipcom_val = 7;
//			}
//			
//			double montoTotal = 0.0;
//						
//			// Inicio -- actualizar temporal ocupacion de asiento ( 2 horas )
//			// Generador del CIP
//			// INTEGRACION PAGOEFECTIVO - NRO DE OPERACION		
//			sql = " SELECT 'PE'||LPAD(SEQ_NRO_OPE_PAGOEFECTIVO_ID.NEXTVAL,12, '0') FROM DUAL";
//			String nroOperacion = jdbcTemplate.queryForObject(sql, String.class);
//			if(!nroOperacion.isEmpty()) {
//				RequestPagoEfectivo rqPE = new RequestPagoEfectivo(nroOperacion, 
//												(venta.getVentaPasajeros().get(0).getVentaIda().getPasajero().getIdTipoDocumento()==Constantes.ID_TIPDOC_DNI?
//													"DNI":"PAS"), 
//												venta.getVentaPasajeros().get(0).getVentaIda().getPasajero().getNumDocumento(),
//												venta.getVentaPasajeros().get(0).getVentaIda().getTelefonoOpcional(), 
//												venta.getVentaPasajeros().get(0).getVentaIda().getPasajero().getNombre(), 
//												venta.getVentaPasajeros().get(0).getVentaIda().getPasajero().getApePaterno(), 
//												venta.getVentaPasajeros().get(0).getVentaIda().getEmailContacto(), 
//												venta.getMontoTotal(),venta.getCodePaisPhone());
//				JsonRest jscip= PagoEfectivo.ObtenerCip(rqPE);
//				if(jscip != null) {
//					JsonObject resultado = new JsonParser().parse(jscip.getResponse()).getAsJsonObject();
//					
//					String cipendiente = resultado.get("data").getAsJsonObject().get("cip").getAsString();
//					
//					for(VentaPasajeros obj : venta.getVentaPasajeros()){
//						// Registro de Ventas de IDA
//						sql = "SELECT SEQ_VRTVENPAS_ID.NEXTVAL FROM DUAL";
//						List<Long> idVentaIda = jdbcTemplate.query(sql, new LongRowMapper());
//						
//						if(idVentaIda.size() > 0){
//							String c_numcontrol = generateControlNumber(decimalToHexadecimal(idVentaIda.get(0)));
//							BigDecimal idPasajero = buscarIdPasajero(obj.getVentaIda().getPasajero());
//							
//							registraVentasVrtVenpasNiubizPagoEfectivo(idVentaIda.get(0), venta, c_numcontrol, obj.getVentaIda(), (obj.getVentaVuelta() != null), idVentaIda.get(0), nroOperacion, Constantes.ID_TIP_FORMA_PAGO_ORBIS, 
//																	  "(TO_CHAR(SYSDATE+(60/1440), 'HH24:MI:SS'))", cipendiente, tipcom_val, idPasajero, obj.getVentaIda().getEmailContacto(), obj.getVentaIda().getTelefonoOpcional());
//							
//							//Colocando Venpas ID a cada pasajero
//							obj.getVentaIda().setIdVenta(new BigDecimal(idVentaIda.get(0)));
//						}else return new MensajeConfirmacionResult(Constantes.RESULT_FALSE, "", "PROBLEMAS CON NRO ID DE VENTAS IDA.");
//						
//						// Registro de Ventas de VUELTA
//						if(obj.getVentaVuelta() != null){
//							sql = "SELECT SEQ_VRTVENPAS_ID.NEXTVAL  FROM DUAL";
//							List<Long> idVentaVuelta = jdbcTemplate.query(sql, new LongRowMapper());
//							if(idVentaVuelta.size() > 0){
//								String c_numcontrol = generateControlNumber(decimalToHexadecimal(idVentaVuelta.get(0)));
//								BigDecimal idPasajero = buscarIdPasajero(obj.getVentaVuelta().getPasajero());
//																
//								registraVentasVrtVenpasNiubizPagoEfectivo(idVentaVuelta.get(0), venta, c_numcontrol, obj.getVentaVuelta(), (obj.getVentaVuelta() != null), idVentaIda.get(0), nroOperacion, Constantes.ID_TIP_FORMA_PAGO_ORBIS, 
//																		  "(TO_CHAR(SYSDATE+(60/1440), 'HH24:MI:SS'))", cipendiente, tipcom_val, idPasajero, obj.getVentaVuelta().getEmailContacto(), obj.getVentaVuelta().getTelefonoOpcional());
//								
//								//Colocando Venpas ID a cada pasajero
//								obj.getVentaVuelta().setIdVenta(new BigDecimal(idVentaVuelta.get(0)));
//							}else return new MensajeConfirmacionResult(Constantes.RESULT_FALSE, "", "PROBLEMAS CON NRO ID DE VENTAS VUELTA.");
//						}
//					}
//										
//					//ACTUALIZAR VENPAS_IDPARENTS | RELACION PADRE - HIJO
//					List<Integer> list_idventas = new ArrayList<>();
//					
//					for(VentaPasajeros obj : venta.getVentaPasajeros()){
//						if(obj.getVentaIda().getIdParentesco() == 4 || obj.getVentaIda().getIdParentesco() == 5){									// SI ES HIJO O CARTA APODERADO
//							for(VentaPasajeros obj2 : venta.getVentaPasajeros()){
//								if(obj2.getVentaIda().getPasajero().getNumDocumento().equals(obj.getVentaIda().getDniApoderado())){					// COMPARA CON EL DNI DE LOS PADRES-MADRES Y APODERADOS
//									String sql_update = " UPDATE pasajes.VRTVENPAS set VENPAS_IDPARENTS = "+obj2.getVentaIda().getIdVenta()+" where VENPAS_ID = "+obj.getVentaIda().getIdVenta();
//									jdbcTemplate.update(sql_update);
//									
//									list_idventas.add(Integer.valueOf(obj2.getVentaIda().getIdVenta().intValue()));
//								}
//							}
//						}
//						
//						if(obj.getVentaVuelta() != null) {
//							if(obj.getVentaVuelta().getIdParentesco() == 4 || obj.getVentaVuelta().getIdParentesco() == 5){							// SI ES HIJO O CARTA APODERADO
//								for(VentaPasajeros obj2 : venta.getVentaPasajeros()){
//									if(obj2.getVentaVuelta().getPasajero().getNumDocumento().equals(obj.getVentaVuelta().getDniApoderado())){		// COMPARA CON EL DNI DE LOS PADRES-MADRES Y APODERADOS
//										String sql_update = " UPDATE pasajes.VRTVENPAS set VENPAS_IDPARENTS = "+obj2.getVentaVuelta().getIdVenta()+" where VENPAS_ID = "+obj.getVentaVuelta().getIdVenta();
//										jdbcTemplate.update(sql_update);
//									}
//								}
//							}
//						}
//					}
//					
//					if(list_idventas.size() > 0){
//						//ACTUALIZAR VENPAS_IDTX | RELACION PADRE - HIJO
//						Integer menorValor = list_idventas.stream().min(Comparator.comparing( v->v)).orElseThrow(NoSuchElementException::new);
//						
//						for(VentaPasajeros obj : venta.getVentaPasajeros()){
//							if(obj.getVentaIda() != null) {
//								String sql_update = " UPDATE pasajes.VRTVENPAS set VENPAS_IDTX = "+menorValor+" where VENPAS_ID = "+obj.getVentaIda().getIdVenta();
//								jdbcTemplate.update(sql_update);
//							}
//							
//							if(obj.getVentaVuelta() != null) {
//								String sql_update = " UPDATE pasajes.VRTVENPAS set VENPAS_IDTX = "+menorValor+" where VENPAS_ID = "+obj.getVentaVuelta().getIdVenta();
//								jdbcTemplate.update(sql_update);
//							}
//						}
//					}
//					
//					montoTotal = venta.getMontoTotal();
//					
//					actualizarDatosHistorialVentas(venta.getVentaPasajeros().get(0).getVentaIda().getEmailContacto(), "", cipendiente, nroOperacion);
//					
//					return new MensajeConfirmacionResult(Constantes.RESULT_TRUE, nroOperacion, new ResponseGeneradorCip(nroOperacion, resultado.get("data").getAsJsonObject().get("cipUrl").getAsString().replace(Constantes.URL_BASE_RESPUESTAPEF,"").replace(".html",""), resultado.get("data").getAsJsonObject().get("cip").getAsString(), ""));
//				}else return new MensajeConfirmacionResult(Constantes.RESULT_FALSE, nroOperacion, "Error al generar el CIP de PagoEfectivo.");
//			}else return new MensajeConfirmacionResult(Constantes.RESULT_FALSE, "", "Error al generar el Correlativo de PagoEfectivo.");
//		}catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//			return new MensajeConfirmacionResult(Constantes.RESULT_FALSE, "", "Error - pagoLinkPagoEfectivo.");
//		}
		
		return null;
	}
	
	private void registraVentasVrtVenpasNiubizPagoEfectivo(Long ventaId, VentasGeneral ventaGeneral, String c_numcontrol, VentaPasaje venta, boolean FlagVuelta, Long idMacthVenta,  String numOperacion, int FormaPago, 
														   String fechaFinal, String hora_bloqueo_venta, String nroCip, Integer tipcom_val, BigDecimal idPasajero, String email_contacto, String telefono_opcional){
				
		String insert = " INSERT INTO PASAJES.VRTVENPAS (venpas_id, venpas_idoriginal, itinerario_id, ruta_id, cliente_id, pasajero_id, "+	//1
						" forpag_id, servicio_id, tipcom_id, tipmov_id, tipforpag_id, "+	//2
						" c_numboleto, n_numasiento, n_numpiso, "+	//3
						" c_numcontrol, agencia_idpartida, d_fecpar, c_horpar, agencia_idllegada, d_feclle, c_horlle, n_secuencial, n_tarifa, "+	//4
						" n_recargo, n_descuento, n_penalidad, n_acuenta, n_imppag, n_imppagefe, n_imppagtar, c_tiptra, d_feccad, liquidacion_id, d_fecliq, "+	//5
						" agencia_id, usuario_id, canven_id, manifiesto_id, n_numopeban, d_fecexpres, c_horexpres, preali_id, promocion_id, n_idaret, "+	//6
						" c_rucclicre, n_esfecabi,c_observaciones, n_ididaret, c_estreg, audipinse, audipmodi, c_estdoc, tipmon_id, "+	//7
						" empresa_id, d_fecvent, n_diftar, nro_cip_pagoefectivo, n_migracion, c_email_contacto, c_telefono_opcional, correo_enviado, venpas_idtx, parentesco_id, n_tipo_pasajero) "+	//8
						
						" VALUES ("+ventaId+", "+ventaId+", "+venta.getIdItinerario()+", "+venta.getIdRuta()+", "+(ventaGeneral.getCliente()!=null?ventaGeneral.getCliente().getIdcliente():null)+", "+idPasajero+	//1
						" , "+Constantes.TIPO_COMPROBANTE_BOLETO_DE_VIAJE+", "+venta.getIdServicio()+", "+tipcom_val+", "+Constantes.ID_TIPMOV_RESERVA+", "+FormaPago+	//2
						" , '', "+venta.getNroAsiento()+", "+venta.getNroPiso()+	//3
						" , '"+c_numcontrol+"', "+venta.getIdAgenciaPartida()+", '"+venta.getFechaPartida()+"', '"+venta.getHoraPartida()+"', "+venta.getIdAgenciaLlegada()+", '"+venta.getFechaLlegada()+"', '"+venta.getHoraLlegada()+"', 0, "+venta.getTarifa()+	//4
						" , 0, "+venta.getDescuento()+", 0, 0, "+venta.getImpPagado()+", 0, 0, 2, sysdate+180, null, '"+ventaGeneral.getFechaLiquidacion()+"'"+	//5
						" , "+ventaGeneral.getIdAgencia()+", "+ventaGeneral.getIdUsuarioSispas()+", "+ventaGeneral.getCanalVenta()+", null, '"+numOperacion+"', TO_DATE('"+fechaFinal+"','DD/MM/YYYY HH24:MI:SS'), '"+hora_bloqueo_venta+"', 1, "+(venta.getPromocionIdSispas()!=0?venta.getPromocionIdSispas():null)+", "+(FlagVuelta?1:0)+	//6
						" , null, 0, '"+ventaGeneral.getObservaciones()+"', "+(FlagVuelta?idMacthVenta:null)+", '"+Constantes.ACTIVO+"', '"+ventaGeneral.getIpLocal()+"', '"+ventaGeneral.getIpLocal()+"', '', 1 "+	//7
						" , 1, sysdate, "+venta.getImpPagado()+", '"+nroCip+"', 0, '"+email_contacto+"', '"+telefono_opcional+"', 0, "+idMacthVenta+", "+venta.getIdParentesco()+", "+venta.getTipoPasajero()+	//8
						" )";
		
		insertarHistorialVentas(ventaId, ventaGeneral.getIdUsuarioSispas(), idPasajero, venta.getIdItinerario(), venta.getIdRuta(), venta.getNroAsiento(), Constantes.ID_TIPMOV_RESERVA, FormaPago, ventaGeneral.getDescripcionVenta(), insert, ventaGeneral.getFechaLiquidacion(), 3, numOperacion);
		
		jdbcTemplate.update(insert);
	}
	
	private void insertarHistorialVentas(Long venpas_id, int usuario_id, BigDecimal pasajero_id, BigDecimal itinerario_id, BigDecimal ruta_id, int n_numasiento, int tipmov_id, int tipforpag_id, String descripcion_venta, String query, String fechaLiquidacion, int tipo_sistema, String numOperacion){
		try{
			descripcion_venta = descripcion_venta.replace("'", "''");
			query = query.replace("'", "''");
			
			String insert = " INSERT INTO PASAJES.VRTHISTORIALVENTAS (venpas_id, usuario_id, pasajero_id, itinerario_id, ruta_id, n_numasiento, tipmov_id, tipforpag_id, descripcion_venta, query, d_fecliq, tipo_sistema, numoperacion ) "+
							" VALUES ("+venpas_id+", "+usuario_id+", "+pasajero_id+", "+itinerario_id+", "+ruta_id+", "+n_numasiento+", "+tipmov_id+", "+tipforpag_id+", '"+descripcion_venta+"', '"+query+"', '"+fechaLiquidacion+"', "+tipo_sistema+", '"+numOperacion+"' )";
			
			jdbcTemplate.update(insert);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public MensajeConfirmacionResult actualizarVentasPagoLink(String jsonPagoLink){
		
		try{
			if(jsonPagoLink.contains("Aprobado y completado con exito")){
				String[] split1 = jsonPagoLink.split("externalId");
				String[] split2 = split1[1].split("orderId");
				
				String orderId = split2[0].replaceAll("\"", "").replaceAll(":", "").replaceAll(",", "");
				
				String sql = "INSERT INTO PASAJES.VRMLOGECOMMERCE (C_REGISTRADO,C_NROPEDIDO,C_NROCIP,C_RESULTADO,C_ERRORCODE,RESPUESTA ) VALUES ('PAGOLINK','"+orderId+"','','200','','"+jsonPagoLink+"')";
	
				jdbcTemplate.update(sql);
				
				MensajeConfirmacionResult result = simularPago(orderId);
				
				if(result.getResult() == true){
					return new MensajeConfirmacionResult(true, Constantes.MSJ_OPERACION_AUTORIZADA);
				}else{
					return new MensajeConfirmacionResult(false, Constantes.MSJ_OPERACION_DENEGADA);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return new MensajeConfirmacionResult(Constantes.RESULT_TRUE, "");
	}
	
	public ResponseEntity<String> actualizarVentasPEfectivo(String  PESignature,String signatureBody) {
		try {			
			if(PESignature.equals(HMAC_SHA256(Constantes.SECRETKEY_PAGOEFECTIVO, signatureBody))) {
				JsonObject jsonObject = new JsonParser().parse(signatureBody).getAsJsonObject().get("data").getAsJsonObject();			
				// Consultar y registrar el log de Pago Efectivo 00-Autorizado 05-Rechazado
				String sql = "INSERT INTO PASAJES.VRMLOGECOMMERCE (C_REGISTRADO,C_NROPEDIDO,C_NROCIP, "+									
										" C_RESULTADO,C_ERRORCODE,RESPUESTA ) VALUES ('EPEFECTIVO','"+
							jsonObject.get("transactionCode").getAsString()+"','"+
							jsonObject.get("cip").getAsString()+"','00','','"+
							"Header: "+PESignature+" - Body: "+signatureBody+"')";
	
				jdbcTemplate.update(sql);
				
				MensajeConfirmacionResult result = simularPago(jsonObject.get("transactionCode").getAsString());
				
				if(result.getResult() == true){
					return new ResponseEntity<String>(Constantes.MSJ_OPERACION_AUTORIZADA, HttpStatus.OK);
				}else{
					return new ResponseEntity<String>("NO HAY VENTAS PARA NOTIFICAR EN PAGO EFECTIVO.", HttpStatus.BAD_REQUEST);
				}
			}else {
				JsonObject jsonObject = new JsonParser().parse(signatureBody).getAsJsonObject().get("data").getAsJsonObject();
				// Consultar y registrar el log de Pago Efectivo 00-Autorizado 05-Rechazado
				String sql = "INSERT INTO PASAJES.VRMLOGECOMMERCE (C_REGISTRADO,C_NROPEDIDO,C_NROCIP, "+
										" C_RESULTADO,C_ERRORCODE,RESPUESTA ) VALUES ('EPEFECTIVO','"+
							jsonObject.get("transactionCode").getAsString()+"','"+
							jsonObject.get("cip").getAsString()+"','05','','"+
							"Header: "+PESignature+" - Body: "+signatureBody+"')";
	
				jdbcTemplate.update(sql);
				return new ResponseEntity<String>("El PE-Signature es incorrecto.", HttpStatus.BAD_REQUEST);
			}
		
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("error-PEFECTIVO: "+e.getMessage());
			return new ResponseEntity<String>("ERROR-REPORTAR A MOVILBUS.", HttpStatus.BAD_REQUEST);
		}
	}
	
	//Generar firma de autorizacion
	private String HMAC_SHA256(String key, String data) {
		try {
		  Mac hmac = Mac.getInstance("HmacSHA256");
		  SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
		  hmac.init(secretKeySpec);
		  return String.format("%064x", new BigInteger(1, hmac.doFinal(data.getBytes(StandardCharsets.UTF_8)))) ;
		  
		}catch (Exception e) {
			return "";
		}
	}
	
	public static JsonRest consumoWsAPI(RequestApiWS request){
		try{
			System.setProperty("https.protocols", "TLSv1.2");
            // https 
            URL endpoint = new URL(request.getUrl());
            HttpURLConnection postConnection = (HttpURLConnection) endpoint.openConnection();               
            postConnection.setRequestProperty("Authorization",request.getAutorizacion());
            postConnection.setRequestMethod(request.getTipometodo());
            postConnection.setRequestProperty("Content-Type","application/json");                        
            postConnection.setDoOutput(true);

            // cuerpo
            if(!request.getBody().equals("")) {
                    OutputStream os = postConnection.getOutputStream();
                    os.write(request.getBody().getBytes());
                    os.flush();
                    os.close();
            }

            int code = postConnection.getResponseCode();
            String mensaje = postConnection.getResponseMessage();

            BufferedReader in =null;
            if( code == HttpURLConnection.HTTP_CREATED || code == HttpURLConnection.HTTP_OK ){ // 201 creado o 200 ok
                in = new BufferedReader(new InputStreamReader(postConnection.getInputStream()));
            }else
                in = new BufferedReader(new InputStreamReader(postConnection.getErrorStream()));

            String inputLn;
            StringBuffer response = new StringBuffer();
            while( (inputLn = in.readLine()) != null){
                response.append(inputLn);
            }
            in.close();

            JsonRest logJSON = new JsonRest(String.valueOf(code),mensaje,response.toString(),
                                        (( code == HttpURLConnection.HTTP_CREATED || code == HttpURLConnection.HTTP_OK )?code+" Response:"+mensaje:""),
                                        (( code != HttpURLConnection.HTTP_CREATED && code != HttpURLConnection.HTTP_OK)?code+" Response:"+mensaje:""),
                                        endpoint.toString(),request.getBody());	

                        return logJSON;

        }catch(Exception io){
            return null;
        }
	}
	
	public void registrarResponseFinalPagoEfectivo(MensajeConfirmacionResult resultado,String urlRespuesta) {
		try {
			// Registrar el log de Ecommerce -Respuesta Final
			String sql = "INSERT INTO PASAJES.VRMLOGRESPONSEECO (C_REGISTRADO,C_NROPEDIDO,C_NROCIP, "+									
									" C_RESULTADO,C_MENSAJE1,C_MENSAJE2,RESPUESTA ) VALUES ('EPEFECTIVO','"+
								(resultado.getRqPECip()==null?"N-A":resultado.getRqPECip().getTransactionCode())+"','','"+
						(resultado.getResult()?"CR":"ER")+"','"+resultado.getMensaje()+"','','"+urlRespuesta+"')";
			
				jdbcTemplate.update(sql);
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static ResultPagoLink ObtenerInfoOperation(String nroOperacion, String descripcion, Double monto, String nombre, String email){
		try{
			ResultPagoLink resultPagoLink = new ResultPagoLink();
        	// Generador de token
            RequestApiWS rqBase = new RequestApiWS(Constantes.url_token,"","Basic "+Constantes.userpassBase64,"POST");
            JsonRest jsBase = consumoWs(rqBase, 1);
            
            if(jsBase.getRspCode().equals("201")){
            	descripcion = descripcion.replace("  ", " ").trim();
            	nombre = nombre.replace("  ", " ").trim();
            	email = email.replace("  ", " ").trim();
            	
            	String bodyWs="{ \"externalId\": \""+nroOperacion+"\", "+
							  " \"orderType\": \"SINGLEPAY\", "+
							  " \"description\": \""+descripcion+"\", "+
							  //" \"expirationDate\": \"2024-04-13\", "+
							  " \"amount\": "+monto+", "+
							  " \"customer\":{ \"firstName\": \""+nombre+"\", "+
							  " \"email\": \""+email+"\" "+
							  "}}";
            	
                RequestApiWS rqSession = new RequestApiWS(Constantes.url_crear_orden, bodyWs, jsBase.getResponse().toString(),"POST");
                JsonRest jsBase2 = consumoWs(rqSession, 2);
                JSONObject obj2 = new JSONObject(jsBase2.getResponse().toString());
                
                resultPagoLink.setBatchId(obj2.getString("link"));
                resultPagoLink.setExternalId(obj2.getString("externalId"));
                resultPagoLink.setOrderId(obj2.getString("orderId"));
                resultPagoLink.setLink(obj2.getString("link"));
                resultPagoLink.setStatus(obj2.getString("status"));
                resultPagoLink.setMessage(obj2.getString("message"));
                
                return resultPagoLink;
            }else{
            	return resultPagoLink;
            }
        }catch(Exception io){
        	io.printStackTrace();
            return null;
        }
    }
	
	public static JsonRest consumoWs(RequestApiWS request, int consulta){
		try{
			System.setProperty("https.protocols", "TLSv1.2");
			// https
			URL endpoint = new URL(request.getUrl());
			HttpURLConnection postConnection = (HttpURLConnection) endpoint.openConnection();
			postConnection.setRequestProperty("Authorization", request.getAutorizacion());
			postConnection.setRequestMethod(request.getTipometodo());
			if(consulta == 1){
				postConnection.setRequestProperty("Content-Type","text/plain");
				
			}else{
				postConnection.setRequestProperty("Content-Type","application/json");
			}
			postConnection.setDoOutput(true);
			
			// cuerpo
			if(!request.getBody().equals("")) {
			        OutputStream os = postConnection.getOutputStream();
			        os.write(request.getBody().getBytes());
			        os.flush();
			        os.close();
			}
			
			int code = postConnection.getResponseCode();
			
			BufferedReader in =null;
			if( code == HttpURLConnection.HTTP_CREATED || code == HttpURLConnection.HTTP_OK ){ 			// 201 creado o 200 ok
			    in = new BufferedReader(new InputStreamReader(postConnection.getInputStream()));
			}else
			    in = new BufferedReader(new InputStreamReader(postConnection.getErrorStream()));
			
			String inputLn;
			StringBuffer response = new StringBuffer();
			while( (inputLn = in.readLine()) != null){
			    response.append(inputLn);
			}
			in.close();
			
			JsonRest logJSON = new JsonRest(String.valueOf(code),response.toString());
			
			return logJSON;
	    }catch(Exception io){
	    	io.printStackTrace();
	        return null;
	    }
	}
	
	private  String decimalToHexadecimal(long valor) {
		return Long.toHexString(valor).toUpperCase();
	}
	
	private String generateControlNumber(String valor) {
		String nControl = "000000000000000";
		nControl = nControl.concat(valor);
		nControl = "T" + nControl.substring(nControl.length() - 15);
		return nControl;
	}
	
	private void actualizarTelefonoPasajeroNew(int tipoDocumento, String numDocumento, String telefono){
		try{
			String sqlpasajero = " UPDATE VRMPASAJERO SET C_TELEFONO='"+telefono+"', AUDUSUMOD='"+Constantes.USUARIO_INSER_MODIFICACION+"'"+
					      		 " WHERE TIPDOC_ID="+tipoDocumento+" and TRIM(C_NUMDOC)='"+numDocumento.trim()+"' and C_ESTREG='"+Constantes.ACTIVO+"'";
			
			jdbcTemplate.update(sqlpasajero);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void actualizarPasajeroNew(Pasajero pasajero) {
		String sqlpasajero = "";
		
		if(pasajero.getIdpasajero() != null && pasajero.getIdpasajero().intValue() != 0){
			sqlpasajero = " SELECT PASAJERO_ID,tipdoc_id,c_numdoc,C_NOMBRE,C_APEPAT,c_apemat,c_fecnac,flag_ws FROM PASAJES.VRMPASAJERO "+
  				  		   " WHERE PASAJERO_ID="+pasajero.getIdpasajero();
		}else{
			sqlpasajero = " SELECT PASAJERO_ID,tipdoc_id,c_numdoc,C_NOMBRE,C_APEPAT,c_apemat,c_fecnac,flag_ws FROM PASAJES.VRMPASAJERO "+
            				" WHERE TRIM(C_NUMDOC) = '"+pasajero.getNumDocumento()+"' and C_ESTREG ='"+Constantes.ACTIVO+"' and TIPDOC_ID="+pasajero.getIdTipoDocumento();
		}
		
		List<Pasajero> listPasajero = jdbcTemplate.query(sqlpasajero, new PasajeroPr5RowMapper());
		
		if(listPasajero.size() > 0) {
			for(int a=0; a<listPasajero.size(); a++){
				// Actualizacion
				sqlpasajero = " UPDATE VRMPASAJERO VP SET C_APEPAT ='"+pasajero.getApePaterno()+"',c_apemat='"+pasajero.getApeMaterno()+"',"+
								     " C_NOMBRE = '"+pasajero.getNombre()+"',"+
								     " c_fecnac = '"+pasajero.getFechanacimiento()+"',"+
								     " sexo_id = "+pasajero.getGenero()+", "+
								     " c_nomape ='"+pasajero.getNombre()+" "+pasajero.getApePaterno()+" "+pasajero.getApeMaterno()+"',AUDUSUMOD ='"+Constantes.USUARIO_INSER_MODIFICACION+"'"+
						          " WHERE PASAJERO_ID="+listPasajero.get(a).getIdpasajero();
				
				jdbcTemplate.update(sqlpasajero);
			}
		}else {
			// Registrar El pasajero
			sqlpasajero = " select pasajes.SEQ_VRMPASAJERO_ID.NEXTVAL from dual";
			BigDecimal idpasajero = jdbcTemplate.queryForObject(sqlpasajero, BigDecimal.class);
			
			sqlpasajero = " INSERT INTO pasajes.VRMPASAJERO ( PASAJERO_ID, AGENCIA_ID, c_apepat, " + 
		                    " c_apemat, c_nombre, c_nomape, "+
		                    " TIPDOC_ID,c_numdoc,N_KILOMETROS, "+
		                    " SEXO_ID,C_FECNAC,UBIGEO_ID, "+
		                    " N_INDESEABLE,AUDUSUINS, "+
		                    " AUDUSUMOD ,FLAG_WS  ) VALUES ( "+
							idpasajero+","+Constantes.ID_AGENCIA_MOVIL_WEB+",'"+pasajero.getApePaterno()+"','"+
							pasajero.getApeMaterno()+"','"+pasajero.getNombre()+"','"+pasajero.getNombre()+" "+pasajero.getApePaterno()+" "+pasajero.getApeMaterno()+"',"+
							pasajero.getIdTipoDocumento()+",'"+pasajero.getNumDocumento()+"',"+BigDecimal.ZERO+","+
							BigDecimal.ONE+",'"+pasajero.getFechanacimiento()+"','"+Constantes.ID_UBIGEO_BUS_PORTAL+"',"+
							BigDecimal.ZERO+",'"+Constantes.USUARIO_INSER_MODIFICACION+"','"+
							Constantes.USUARIO_INSER_MODIFICACION+"',"+Constantes.N_FLAG_NO_ACTUALIZADO+")";
			
			jdbcTemplate.update(sqlpasajero);
		}
	}
	
	private void actualizarCliente(Cliente cliente) {
		
		String sqlcliente = "";
		
		if(cliente.getIdcliente() != null) 
			sqlcliente = " SELECT CLIENTE_ID,c_direccion FROM PASAJES.VRMCLIENTE "+
  				  		   " WHERE CLIENTE_ID="+cliente.getIdcliente();
			
		else sqlcliente = " SELECT CLIENTE_ID,c_direccion FROM PASAJES.VRMCLIENTE "+
            				" WHERE TRIM(C_NUMDOC) = TRIM('"+cliente.getNroDoc()+"') and C_ESTREG ='"+Constantes.ACTIVO+"'";
		          
		List<Cliente> listCliente = jdbcTemplate.query(sqlcliente, new Cliente2RowMapper());
		
		if(listCliente.size() > 0) {
			// Actualizar si es diferente de direccion
			if(!(cliente.getDireccion()).equals(listCliente.get(0).getDireccion())) {
				// Actualizacion
				sqlcliente = " UPDATE pasajes.VRMCLIENTE SET c_direccion ='"+cliente.getDireccion()+"',"+
											" AUDUSUMOD ='"+Constantes.USUARIO_INSER_MODIFICACION+"'"+ 
						          " WHERE CLIENTE_ID="+listCliente.get(0).getIdcliente();	
				
				jdbcTemplate.update(sqlcliente);
			}
			
		}else {
			// Registrar El cliente
			sqlcliente = " select pasajes.SEQ_VRMCLIENTE_ID.NEXTVAL from dual";
			BigDecimal idcliente = jdbcTemplate.queryForObject(sqlcliente, BigDecimal.class);
			
			sqlcliente =  " INSERT INTO pasajes.VRMCLIENTE ( CLIENTE_ID, AGENCIA_ID, UBIGEO_ID, " + 
                    " C_NUMDOC, C_RAZSOC, " + 
                    " N_CANTRAB, C_DIRECCION, N_KILOMETROS, "+
                    " AUDUSUINS,AUDUSUMOD ,FLAG_WS  ) VALUES ( "+
                    idcliente+","+Constantes.ID_AGENCIA_MOVIL_WEB+",'"+Constantes.ID_UBIGEO_BUS_PORTAL+"',"+
                    "'"+cliente.getNroDoc()+"','"+cliente.getRazonSocial()+"',"+BigDecimal.TEN+",'"+cliente.getDireccion()+"',"+
                    BigDecimal.TEN+",'"+Constantes.USUARIO_INSER_MODIFICACION+"','"+
                    Constantes.USUARIO_INSER_MODIFICACION+"',"+Constantes.N_FLAG_NO_ACTUALIZADO+")";
			
			jdbcTemplate.update(sqlcliente);
			cliente.setIdcliente(idcliente);
		}
	}
	
	private BigDecimal buscarIdPasajero(Pasajero pasajero){
		String sqlpasajero = "";
		
		if(pasajero.getIdpasajero() != null && pasajero.getIdpasajero().intValue() != 0){ 
			sqlpasajero = " SELECT PASAJERO_ID,tipdoc_id,c_numdoc,C_NOMBRE,C_APEPAT,c_apemat,c_fecnac,flag_ws FROM PASAJES.VRMPASAJERO "+
  				  		   " WHERE PASAJERO_ID="+pasajero.getIdpasajero();
		}else{ 
			sqlpasajero = " SELECT PASAJERO_ID,tipdoc_id,c_numdoc,C_NOMBRE,C_APEPAT,c_apemat,c_fecnac,flag_ws FROM PASAJES.VRMPASAJERO "+
            				" WHERE TRIM(C_NUMDOC) = '"+pasajero.getNumDocumento()+"' and C_ESTREG ='"+Constantes.ACTIVO+"' and TIPDOC_ID="+pasajero.getIdTipoDocumento();
		}
		
		List<Pasajero> listPasajero = jdbcTemplate.query(sqlpasajero, new PasajeroPr5RowMapper());
		
		return listPasajero.get(0).getIdpasajero();
	}
	
	public int enviarCorreoPasajeroPagoLink(String CorreoPasajero, String UrlPagoLink, String nroOperacion) throws IOException, Exception {    
           
        try{
             Properties props = new Properties();
             // Nombre del host de correo, es smtp.gmail.com
             props.setProperty("mail.smtp.host", Constantes.mail_stmp_host);                
             props.setProperty("mail.smtp.starttls.enable", Constantes.mail_stmp_starttls);                     
             props.setProperty("mail.smtp.port",Constantes.mail_stmp_port);     
             props.setProperty("mail.smtp.user",Constantes.correo_remitente);
             // Si requiere o no usuario y password para conectarse.
             props.setProperty("mail.smtp.auth", Constantes.mail_stmp_auth); 
             props.setProperty("mail.smtp.ssl.trust", Constantes.mail_stmp_host);
             
             Session session = Session.getDefaultInstance(props);
             
             //Se crea destino y origen del mensaje
              MimeMessage mimemessage = new MimeMessage(session);  

             // correo origen
             mimemessage.setFrom(new InternetAddress(Constantes.correo_remitente)); 
             //Validar correos destino
             if(CorreoPasajero==null){            	 
            	 //0=No enviado, 1=Enviado, 2=No hay PDF, 3=Pasado de Fecha, 4=Correo Invalido, 5=Error Desconocido, 6=No enviado PagoLink, 7=Enviado Correo PagoLink
            	 System.out.println("Error Email: "+nroOperacion);
 	        	 //actualizar_correo_erroneo(nroOperacion);
                 return 0;
             }
             
             if(!isValidEmailFinal(CorreoPasajero)){            	 
            	 //0=No enviado, 1=Enviado, 2=No hay PDF, 3=Pasado de Fecha, 4=Correo Invalido, 5=Error Desconocido, 6=No enviado PagoLink, 7=Enviado Correo PagoLink
 	        	 System.out.println("Error Email: "+nroOperacion);
 	        	//actualizar_correo_erroneo(nroOperacion);
                 return 0;
             }
              
             //destino                
             mimemessage.addRecipient(Message.RecipientType.TO, new InternetAddress(CorreoPasajero));

             // Motivo del mensaje                 
             mimemessage.setSubject("MOVIL BUS - URL PAGOLINK"); //Asunto real

            // para adjuntar el archivo
            Multipart multipart = new MimeMultipart();
            
            System.out.println("Preparando Archivos: "+nroOperacion);
            // Se crea el contenido del mensaje
            MimeBodyPart mimebodypart = new MimeBodyPart();
            mimebodypart.setContent(cuerpoHtmlMsj(UrlPagoLink),"text/html"); // Aqui el formato sera en html

            multipart.addBodyPart(mimebodypart);            

            mimemessage.setContent(multipart);            
            mimemessage.setSentDate(new Date());
            
            System.out.println("Enviando Correo: "+nroOperacion);

            // Para enviar el mensaje usamos la clase Transport
            Transport t = session.getTransport("smtp");
            t.connect(Constantes.correo_remitente, Constantes.password_remitente);
            t.sendMessage(mimemessage,mimemessage.getAllRecipients());
            t.close();
            
            //ACTUALIZACION DEL ESTADO DEL CORREO A ENVIADO
            //actualizarEstadoCorreo(nroOperacion);
            System.out.println("Correo enviado: "+nroOperacion);
            return 1;
	    }catch(SendFailedException sfex){
	    	System.out.println("Error correo invalido: "+nroOperacion);
	    	sfex.printStackTrace();
	    	
	    	//0=No enviado, 1=Enviado, 2=No hay PDF, 3=Pasado de Fecha, 4=Correo Invalido, 5=Error Desconocido, 6=No enviado PagoLink, 7=Enviado Correo PagoLink
        	//actualizar_correo_erroneo(nroOperacion);
	    	return 0;
	    }catch( MessagingException mex ){
        	System.out.println("Error no se envio en 5 intentos: "+nroOperacion);
        	mex.printStackTrace();
        	
        	//0=No enviado, 1=Enviado, 2=No hay PDF, 3=Pasado de Fecha, 4=Correo Invalido, 5=Error Desconocido, 6=No enviado PagoLink, 7=Enviado Correo PagoLink
        	//actualizar_correo_erroneo(nroOperacion);
        	return 0;
	    }catch(Exception ex){
	    	System.out.println("Error canjebus: "+nroOperacion);
	    	ex.printStackTrace();
	    	
	    	//0=No enviado, 1=Enviado, 2=No hay PDF, 3=Pasado de Fecha, 4=Correo Invalido, 5=Error Desconocido, 6=No enviado PagoLink, 7=Enviado Correo PagoLink
        	//actualizar_correo_erroneo(nroOperacion);
	    	return 0;
	    }
    }

	private static  String cuerpoHtmlMsj(String UrlPagoLink){
	    String mensaje = "<html> \r\n"+ 
	    					"<head> <style> li::marker { color: #ff6719; } #imagen_pagolink{width: auto;} #form_pagolink{border: 1.8px solid #ff6719; margin-left: auto; margin-right: auto; text-align: left !important; width: 36%; padding: 10px; font-size: 14px;} #button_pagolink{padding: 4px 0px; background-color: #ff6719; color: white; border: none; border-radius: 18px; cursor: pointer; font-size: 33px; font-weight: bold; width: 10%;} @media only screen and (max-width: 500px) { #button_pagolink{padding: 10px 0px; width: 18%;} #form_pagolink{width: 62%; font-size: 20px;} #imagen_pagolink{width: 35%;}}</style> </head> \r\n"+
	                        "<body> \r\n"+		
	                            "<div style=\"margin: 10px; font-family: 'Objective,sans-serif !important'; text-align: center;\">"+
	                                "<img id=\"imagen_pagolink\" src=\"https://www.movilbus.pe/images/logo.png\"/><br><br> &nbsp; <font style=\"color: #ff6719; font-weight: bold; font-size: 19.5px;\">&#161;GRACIAS POR PREFERIRNOS!</font>"+
	                                "<br><br>"+
	                                "<font style=\"color: black; font-weight: bold; font-size: 20px;\">Para continuar con tu compra <br> haz clic al bot&oacute;n pagar</font>"+
	                                "<br><br>"+
	                                "<a target=\"_blank\" href="+UrlPagoLink+"><button id=\"button_pagolink\">Pagar</button></a>"+
	                                "<br><br>"+
	                                "<div id=\"form_pagolink\">"+
	                                	"<font style=\"color: black; font-weight: bold; font-size: 20px; vertical-align: middle;\">Recuerda:</font>"+
	                                	"<br> \r\n"+
	                                	"<ul>"+
	                                		"<li style=\"margin-bottom: 10px;\">Coloca el correo donde desees recibir la confirmaci&oacute;n de compra.</li>\r\n" + 
	                                		"<li style=\"margin-bottom: 10px;\">Llena todos los datos de tu tarjeta.</li>	\r\n" + 
	                                		"<li style=\"margin-bottom: 10px;\">El CVV es el c&oacute;digo de seguridad de tu tarjeta que se encuentra en la parte de atr&aacute;s.</li>	\r\n" + 
	                                		"<li style=\"margin-bottom: 10px;\">Este formulario de pago es 100% seguro y protege tus datos sensibles.</li>	\r\n" + 
                                        "</ul>\r\n" + 
                                    "</div>"+
                                    "<br>"+
	                                "<font style=\"color: #ff6719; font-weight: bold; font-size: 19.5px;\">&#161;Hasta tu siguiente compra!</font>"+
	                                "</div>"+
	                            "</body>\r\n" + 
	                      "</html> ";
	     return mensaje;
	}
	
	public void actualizarEstadoCorreo(String numoperacion){
	    try{
	    	if(numoperacion.contains("PE")){
				String sql = " UPDATE VRTVENPAS SET correo_enviado=0 WHERE nro_ope_pagoefectivo = '"+numoperacion+"' and audfecins>=trunc(sysdate-1)";
				jdbcTemplate.update(sql);
	    	}else{
				String sql = " UPDATE VRTVENPAS SET correo_enviado=0 WHERE nro_operation_niubiz = '"+numoperacion+"' and audfecins>=trunc(sysdate-1)";
				jdbcTemplate.update(sql);
	    	}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void actualizar_correo_erroneo(String numoperacion){
	    try{
	    	if(numoperacion.contains("PE")){
				String sql = " UPDATE VRTVENPAS SET correo_enviado=6 WHERE nro_ope_pagoefectivo = '"+numoperacion+"' and audfecins>=trunc(sysdate-1)";
				jdbcTemplate.update(sql);
	    	}else{
				String sql = " UPDATE VRTVENPAS SET correo_enviado=6 WHERE nro_operation_niubiz = '"+numoperacion+"' and audfecins>=trunc(sysdate-1)";
				jdbcTemplate.update(sql);
	    	}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static boolean isValidEmailFinal(String email){
        boolean result = true;
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
                       "[a-zA-Z0-9_+&*-]+)*@" + 
                       "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
                       "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(regex);      
        if(email.substring(email.length()-3).equals(".om")){
            result = false;
        //}else if(email.substring(email.length()-3).equals(".co")){
        //    result = false;
        }else if(email.substring(email.length()-2).equals(".m")){
            result = false;
        }else if(email.substring(email.length()-2).equals(".e")){
            result = false;
        }else if(email.substring(email.length()-2).equals(".s")){
            result = false;
        }else if(email.substring(email.length()-4).equals(".cpm")){
            result = false;
        }else if(email.substring(email.length()-4).equals(".con")){
            result = false;
        }else if(email.substring(email.length()-5).equals(".ccom")){
            result = false;
        }else if(email.substring(email.length()-4).equals(".clm")){
            result = false;
        }else if(email.substring(email.length()-9).equals("hmail.com")){
            result = false;
        }else if(email.substring(email.length()-10).equals("hotmai.com")){
            result = false;
        }else if(email.substring(email.length()-8).equals("gmal.com")){
            result = false;
        }else if(email.substring(email.length()-9).equals("gamil.com")){
            result = false;
        }else if(!pat.matcher(email).matches()){
            result = false;
        }
        return result;
    }
	
	private static final class DatosIziPaySimularPagoRowMapper implements RowMapper<DatosIziPaySimularPago>{
		@Override
		public DatosIziPaySimularPago mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new DatosIziPaySimularPago(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
		}
	}
	
	private static final class VentaPasaje7RowMapper implements RowMapper<VentaPasaje> {
		
		@Override
		public VentaPasaje mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new VentaPasaje(rs.getBigDecimal(1), rs.getBigDecimal(2), rs.getBigDecimal(3),
									  new Pasajero(rs.getBigDecimal(4)), rs.getInt(5), rs.getInt(6), rs.getDouble(7));
		
			}
	}
	
	private static final class PasajeroPr9RowMapper implements RowMapper<Pasajero> {
		
		@Override
		public Pasajero mapRow(ResultSet rs, int rowNum) throws SQLException {			
			return new Pasajero(rs.getBigDecimal(1),rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5),rs.getString(6) ,rs.getString(7) , rs.getString(8), rs.getString(9), rs.getInt(10), rs.getInt(11));
			}	
	}

	private static final class PasajeroPr5RowMapper implements RowMapper<Pasajero> {
		
		@Override
		public Pasajero mapRow(ResultSet rs, int rowNum) throws SQLException {			
			return new Pasajero(rs.getBigDecimal(1),rs.getInt(2), rs.getString(5), rs.getString(4), rs.getString(7));
			}	
	}
	
	private static final class UpdateVrtVenpasRowMapper implements RowMapper<UpdateVrtVenpas> {
		
		@Override
		public UpdateVrtVenpas mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new UpdateVrtVenpas(rs.getBigDecimal(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getInt(8));
		}
	}
	
	private final class SecuenciaRowMapper implements RowMapper<Secuencia>{
		@Override
		public Secuencia mapRow(ResultSet rs, int rowNum) throws SQLException {			
			return new Secuencia(rs.getString(1),rs.getString(2));
		}
	}
	
	private final class RutaRowMapper implements RowMapper<Ruta> {
		
		@Override
		public Ruta mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Ruta(rs.getInt(1),rs.getInt(2));
		}
	}
		
	private static final class StringRowMapper implements RowMapper<String> {
		
		@Override
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {			
			return rs.getString(1);
		}
	}
	
	private static final class LongRowMapper implements RowMapper<Long> {
		
		@Override
		public Long mapRow(ResultSet rs, int rowNum) throws SQLException {			
			return rs.getLong(1);
		}	
	}
	
	private static final class Cliente2RowMapper implements RowMapper<Cliente> {
		
		@Override
		public Cliente mapRow(ResultSet rs, int rowNum) throws SQLException {			
			return new Cliente(rs.getBigDecimal(1), rs.getString(2));
		}	
	}
	
	private static final class VentasGeneralPasajerosRowMapper implements RowMapper<VentasGeneralPasajeros> {

		@Override
		public VentasGeneralPasajeros mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new VentasGeneralPasajeros(rs.getBigDecimal(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), 
					rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(14), rs.getString(15), rs.getString(16), rs.getString(17), rs.getString(18), rs.getString(19), 
					rs.getString(20), rs.getString(21), rs.getString(22), rs.getString(23), rs.getString(24), rs.getString(25), rs.getString(26), rs.getString(27), rs.getString(28), rs.getString(29), 
					rs.getString(30), rs.getString(31), rs.getInt(32), rs.getString(33), rs.getString(34), rs.getString(35), rs.getString(36), rs.getString(37), rs.getString(38), rs.getString(39), 
					rs.getString(40), rs.getString(41), rs.getInt(42), rs.getDouble(43), rs.getString(44), rs.getString(45), rs.getString(46), rs.getString(47), rs.getString(48), rs.getString(49), 
					rs.getString(50), rs.getString(51), rs.getString(52), rs.getString(53), rs.getInt(54), rs.getString(55), rs.getString(56), rs.getInt(57), rs.getString(58), rs.getString(59), 
					rs.getString(60), rs.getString(61), rs.getString(62), rs.getString(63), rs.getString(64), rs.getString(65), rs.getString(66), rs.getString(67), rs.getString(68), rs.getString(69), 
					rs.getString(70), rs.getString(71), rs.getString(72), rs.getString(73), rs.getString(74), rs.getString(75), rs.getString(76), rs.getString(77), rs.getString(78), rs.getInt(79), 
					rs.getString(80), rs.getString(81), rs.getString(82), rs.getString(83), rs.getInt(84), rs.getInt(85), rs.getString(86), rs.getString(87), rs.getString(88), rs.getString(89), 
					rs.getString(90), rs.getString(91), rs.getString(92), rs.getString(93), rs.getString(94), rs.getString(95), rs.getInt(96), rs.getInt(97));
		}
	}
	
	private static final class DataTransbordosRowMapper implements RowMapper<DataTransbordos> {

		@Override
		public DataTransbordos mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new DataTransbordos(rs.getBigDecimal(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDouble(6), rs.getDouble(7), rs.getInt(8), rs.getString(9), rs.getString(10), 
					rs.getString(11), rs.getInt(12), rs.getInt(13), rs.getString(14), rs.getString(15), rs.getString(16), rs.getInt(17), rs.getString(18), rs.getString(19), rs.getString(20));
		}
	}
	
	private static final class RptVenpasIdOriginalRowMapper implements RowMapper<RptVenpasIdOriginal> {

		@Override
		public RptVenpasIdOriginal mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new RptVenpasIdOriginal(rs.getString(1), rs.getString(2), rs.getString(3));
		}
	}
}
