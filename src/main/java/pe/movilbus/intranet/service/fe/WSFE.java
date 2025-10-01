//package pe.movilbus.intranet.service.fe;
//
//import java.awt.Color;
//import java.awt.Graphics2D;
//import java.awt.image.BufferedImage;
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.Serializable;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.sql.Connection;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Base64;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.Hashtable;
//import java.util.List;
//
//import javax.imageio.ImageIO;
//import javax.xml.bind.JAXBContext;
//import javax.xml.bind.JAXBElement;
//import javax.xml.bind.Marshaller;
//import javax.xml.namespace.QName;
//
//import org.apache.tomcat.util.http.fileupload.FileUtils;
//import org.datacontract.schemas._2004._07.feservice.ArrayOfDetalleCuota;
//import org.datacontract.schemas._2004._07.feservice.ArrayOfDetalleVenta;
//import org.datacontract.schemas._2004._07.feservice.ArrayOfInformacionAdicionalPropiedadAdicional;
//import org.datacontract.schemas._2004._07.feservice.ArrayOfInformacionAdicionalTotalMonedaAdicional;
//import org.datacontract.schemas._2004._07.feservice.Cliente;
//import org.datacontract.schemas._2004._07.feservice.DetalleCuota;
//import org.datacontract.schemas._2004._07.feservice.DetalleVenta;
//import org.datacontract.schemas._2004._07.feservice.DocumentoReferencia;
//import org.datacontract.schemas._2004._07.feservice.InformacionAdicional;
//import org.datacontract.schemas._2004._07.feservice.InformacionAdicionalPropiedadAdicional;
//import org.datacontract.schemas._2004._07.feservice.InformacionAdicionalTotalMonedaAdicional;
//import org.datacontract.schemas._2004._07.feservice.Result;
//import org.datacontract.schemas._2004._07.feservice.Venta;
//import org.springframework.transaction.annotation.Transactional;
//import org.tempuri.IMEFEService;
//import org.tempuri.MEFEService;
//
//import pe.movilbus.intranet.beans.AgenciaSispas;
//import pe.movilbus.intranet.beans.CompaniaSispas;
//import pe.movilbus.intranet.beans.ItinerarioAgenciaPartidaIDSispas;
//import pe.movilbus.intranet.beans.VentaPasajeSispas;
//import pe.movilbus.intranet.daoImpl.VentasDaoImpl;
//import pe.movilbus.intranet.util.Constantes;
//import pe.movilbus.intranet.util.ConvertirNumeroString;
//import pe.movilbus.intranet.util.Util;
//import sun.misc.BASE64Encoder;
//
//public class WSFE implements Serializable{
//    /**
// * 
// */
//private static final long serialVersionUID = 1L;
//private static String TOKEN="#MAOE13579ZCBMKHFAQETUIP12W4R6Y8U9O#...";
//private static String NAMESPACE="http://schemas.datacontract.org/2004/07/FEService.Input";
//private static final DateFormat FORMAT_DATE = new SimpleDateFormat ("yyyy-MM-dd");
//private static String FE_TIPCOM_FACTURA="01";
//private static String FE_TIPCOM_BOLETA="03";
//private static String FE_TIPCOM_NOTA_CREDITO="07";
//private static String FE_TIPCOM_NOTA_DEBITO="08";
//public static String FE_TIPDOC_RUC="6";
//public static String FE_TIPDOC_DNI="1";
//public static String FE_TIPDOC_CARNET_EXTRANEJERIA="4";
//public static String FE_TIPDOC_PASAPORTE="7";
//public static String FE_TIPDOC_CEDULA_DIPLOMATICA_IDENTIDAD="A";
//public static Integer FE_TIPO_VENTA_CONTADO=20;
//public static Integer FE_TIPO_VENTA_CREDITO=21;
//public static Integer FE_TIPO_VENTA_CORTESIA=22;
//
//public static IMEFEService iMEFEService;
//	
//private static IMEFEService getSoap()throws Exception{
//	try {
//			//DEV
//            System.setProperty("http.proxyHost", "192.168.10.24");
//	        System.setProperty("http.proxyPort", "8086");
//			//PRD
//			//System.setProperty("http.proxyHost", "192.168.10.25");
//        	//System.setProperty("http.proxyPort", "8085");
//		
//		if(iMEFEService==null){
//            MEFEService mefeservice= new MEFEService();
//            iMEFEService = mefeservice.getBasicHttpBindingIMEFEService();
//		}
//	} catch (Exception e) {
//		e.printStackTrace();
//	}
//	return iMEFEService;
//}
//
///**
// * Realiza le envio de la venta al WebService FE.
// * @param ventaPasaje : Instancia del Object VentaPasaje
// * @throws Exception
// */
//public void sendVenta(Connection conx, List<VentaPasajeSispas> listVentaPasaje)throws Exception{
//	
//	try {
//		List<VentaPasajeSispas> ventasEnviadas = new ArrayList<VentaPasajeSispas>();
//		
//		Result result=null;
//		for(VentaPasajeSispas oVentaPasajeFE: listVentaPasaje){
//				// crea el objeto venta
//	            Venta oventa = createVenta(oVentaPasajeFE);
//	            
//	            if(oventa==null) System.out.println(" metodo SendVenta : No genero el objeto VENTA ");
//		        
//	            // Envia la venta a nuestro ws
//	            result= getSoap().setVenta(TOKEN, oventa);
//	            
//	            // Agrega a la lista para la impresion
//	            if(result!=null && result.getBarcode().getValue()!=null){
//	            	new VentasDaoImpl().actualizarVentaPasaje(oVentaPasajeFE);
//	            }
//	            
//				if(result!=null){
//					oVentaPasajeFE.setResult(result);
//					ventasEnviadas.add(oVentaPasajeFE);
//				}
//			}
//	} catch (Exception e) {
//		e.printStackTrace();
//	}
//}
//
///**
// * Crea el objeto Venta para enviarla al WebService FE.
// * @param ventaPasaje	: Instancia del Object VentaPasaje
// * @return
// * @throws Exception
// */
//private static Venta createVenta(VentaPasajeSispas ventaPasaje)throws Exception{
//	try {
//		String serie=ventaPasaje.getNumeroBoleto().split("-")[0].toString();
//		String correlativo=ventaPasaje.getNumeroBoleto().split("-")[1].toString();
//		String fechaEmision="";
//		
//		Date date=new Date();
//		fechaEmision=FORMAT_DATE.format(date);	
//		
//		Boolean isCortesia=ventaPasaje.getFormaPago().getId().intValue()==Constantes.ID_FORPAG_CORTESIA;
//		// Datos del cliente pasajero
//		String cliente_tipoDocumentoID = null;
//		String cliente_nombres = null;
//		String cliente_nroDocumento = null;
//		String cliente_direccion = null;
//		if(ventaPasaje.getCliente() != null){
//			cliente_tipoDocumentoID=FE_TIPDOC_RUC;
//			cliente_nombres=ventaPasaje.getCliente().getRazonSocial();
//			cliente_nroDocumento=ventaPasaje.getCliente().getNumeroDocumento();
//			cliente_direccion=ventaPasaje.getCliente().getDireccion();
//		}else{
//			switch (ventaPasaje.getPasajero().getTipoDocumento().getId().intValue()) {
//			case Constantes.ID_TIPDOC_DNI:
//				cliente_tipoDocumentoID=FE_TIPDOC_DNI;
//				break;
//			case Constantes.ID_TIPDOC_CARNET_EXTRANJERIA:
//				cliente_tipoDocumentoID=FE_TIPDOC_CARNET_EXTRANEJERIA;
//				break;
//			case Constantes.ID_TIPDOC_PASAPORTE:
//				cliente_tipoDocumentoID=FE_TIPDOC_PASAPORTE;
//				break;
//			case Constantes.ID_TIPDOC_CEDULA_IDENTIDAD:
//				cliente_tipoDocumentoID=FE_TIPDOC_CEDULA_DIPLOMATICA_IDENTIDAD;
//				break;
//			default:
//				break;
//			}
//			cliente_nombres=ventaPasaje.getPasajero().toString();
//			cliente_nroDocumento=ventaPasaje.getPasajero().getNumeroDocumento();
//		}
//		// Tipo de comprobante
//		String tipoComprobanteID=null;
//		switch (ventaPasaje.getTipoComprobante().getId().intValue()) {
//		case Constantes.ID_TIPCOM_BOLETA_VENTA:
//			tipoComprobanteID=FE_TIPCOM_BOLETA;
//			break;
//		case Constantes.ID_TIPCOM_FACTURA:
//			tipoComprobanteID=FE_TIPCOM_FACTURA;
//			break;
//		default:
//			break;
//		}
//			
//		Cliente cliente= new Cliente();
//		cliente.setNombres(new JAXBElement<String>(new QName(NAMESPACE,"nombres"), String.class, cliente_nombres));
//		cliente.setTipoDocumentoID(new JAXBElement<String>(new QName(NAMESPACE,"tipoDocumentoID"), String.class, cliente_tipoDocumentoID));
//		cliente.setNumeroDocumento(new JAXBElement<String>(new QName(NAMESPACE,"numeroDocumento"), String.class, cliente_nroDocumento));
//		cliente.setDireccion(new JAXBElement<String>(new QName(NAMESPACE,"direccion"), String.class, cliente_direccion));
//		
//		CompaniaSispas empresa = new VentasDaoImpl().buscarCompaniaById(ventaPasaje.getEmpresa().getId().longValue());
//		Venta venta= new Venta();
//		venta.setRucEmpresa(new JAXBElement<String>(new QName(NAMESPACE, "rucEmpresa"), String.class, empresa.getNumeroDocumento()));
//		venta.setTipoComprobanteID(new JAXBElement<String>(new QName(NAMESPACE,"tipoComprobanteID"), String.class, tipoComprobanteID));
//		venta.setNumeroSerie(new JAXBElement<String>(new QName(NAMESPACE,"numeroSerie"), String.class, serie));
//		venta.setNumeroCorrelativo(new JAXBElement<String>(new QName(NAMESPACE,"numeroCorrelativo"), String.class, autoCompletCorrelativo(correlativo)));
//		venta.setTipoMonedaSoles(true);// dolares
//		venta.setFechaEmision(new JAXBElement<String>(new QName(NAMESPACE,"fechaEmision"), String.class, fechaEmision));		
//		// Validando si es una cortesia
//		if(!(isCortesia)){
//			if(ventaPasaje.getIgv() != null && ventaPasaje.getIgv().doubleValue()>0.00){
//				venta.setIgv(ventaPasaje.getIgv());
//				venta.setMontoSubTotal(ventaPasaje.getImportePagado()-ventaPasaje.getIgv());
//				venta.setMontoTotal(ventaPasaje.getImportePagado());
//			}else{
//				venta.setMontoTotal(ventaPasaje.getImportePagado());
//				venta.setMontoSubTotal(ventaPasaje.getImportePagado());	
//			}
//		}else{
//			venta.setMontoTotal(0.00);
//			venta.setIgv(0.00);
//			venta.setMontoSubTotal(0.00);				
//		}
//		venta.setMontoTotalDescuento(0.00);
//		venta.setCliente(new JAXBElement<Cliente>(new QName(NAMESPACE,"cliente"), Cliente.class, cliente));
//		venta.setAgenciaID(ventaPasaje.getAgencia().getId().longValue());
//		venta.setUsuarioID(ventaPasaje.getUsuario().getId().longValue());
//		venta.setUsuarioInsercion(new JAXBElement<String>(new QName(NAMESPACE,"usuarioInsercion"), String.class, ventaPasaje.getUsuarioInsercion()));
//		venta.setUsuarioModificacion(new JAXBElement<String>(new QName(NAMESPACE,"usuarioModificacion"), String.class, ventaPasaje.getUsuarioInsercion()));
//		switch (ventaPasaje.getFormaPago().getId().intValue()) {
//		case Constantes.ID_FORPAG_CONTADO:
//			venta.setTipoVenta(FE_TIPO_VENTA_CONTADO);
//			break;
//		case Constantes.ID_FORPAG_CREDITO:
//			if(ventaPasaje.getRucClienteCredito().equals("20547391501")
//			|| ventaPasaje.getRucClienteCredito().equals("20555893052"))
//				venta.setTipoVenta(FE_TIPO_VENTA_CONTADO);
//			else {
//				venta.setTipoVenta(FE_TIPO_VENTA_CREDITO);
//				
//				DetalleCuota detalleCuota = new DetalleCuota();
//				detalleCuota.setNumeroCuota(1);
//				detalleCuota.setAliasCuota(new JAXBElement<String>(new QName(NAMESPACE,"aliasCuota"), String.class, "Cuota001"));
//				detalleCuota.setImporteCuota(ventaPasaje.getImportePagado());
//				Calendar cal30days = Calendar.getInstance();
//				cal30days.setTime(ventaPasaje.getFechaInsercion()); 
//				cal30days.add(Calendar.DAY_OF_YEAR, 30); 
//				detalleCuota.setFechaVencimiento(new JAXBElement<String>(new QName(NAMESPACE,"fechaVencimiento"), String.class,  new SimpleDateFormat ("yyyy-MM-dd").format(cal30days.getTime())));
//			
//				ArrayOfDetalleCuota arrayOfDetalleCuota = new ArrayOfDetalleCuota();
//				arrayOfDetalleCuota.getDetalleCuota().add(detalleCuota);
//				venta.setListDetalleCuota(new JAXBElement<ArrayOfDetalleCuota>(new QName(NAMESPACE,"listDetalleCuota"), ArrayOfDetalleCuota.class, arrayOfDetalleCuota));
//			}
//			
//			break;
//		case Constantes.ID_FORPAG_CORTESIA:
//			venta.setTipoVenta(FE_TIPO_VENTA_CORTESIA);
//			String observaciones="***CORTESIA POR "+ventaPasaje.getTipoFormaPago().getDenominacion()+"***";
//			venta.setObservaciones(new JAXBElement<String>(new QName(NAMESPACE,"observaciones"), String.class, observaciones));
//			break;
//		default:
//			venta.setTipoVenta(99);
//		}
//		
//		// Valida si tiene centro de costo
//		if(ventaPasaje.getCentroCosto()!=null){
//			String centroCosto=ventaPasaje.getCentroCosto().getCodigo()+" - "+ventaPasaje.getCentroCosto().getDenominacion();
//			venta.setCentroCosto(new JAXBElement<String>(new QName(NAMESPACE,"centroCosto"), String.class, centroCosto));
//		}
//		// Comprobante referencial
//		if(ventaPasaje.getNumeroBoletoAnterior()!=null){
//			DocumentoReferencia documentoReferencia= new DocumentoReferencia();
//			documentoReferencia.setNumeroDocumento(new JAXBElement<String>(new QName(NAMESPACE,"NumeroDocumento"), String.class, ventaPasaje.getNumeroBoletoAnterior()));
//			venta.setDocumentoReferencia(new JAXBElement<DocumentoReferencia>(new QName(NAMESPACE,"documentoReferencia"), DocumentoReferencia.class, documentoReferencia));
//		}
//		// Direccion de embarque			
//		if(ventaPasaje.getAgenciaPartida()!=null){
//			AgenciaSispas agenciaPartida=ventaPasaje.getAgenciaPartida();
//			if(agenciaPartida.getDireccion()==null)
//				agenciaPartida = new VentasDaoImpl().buscarAgenciaById(agenciaPartida.getId().longValue());
//			if(agenciaPartida.getDireccion()!=null)
//				venta.setDireccionEmbarque(new JAXBElement<String>(new QName(NAMESPACE,"direccionEmbarque"), String.class, agenciaPartida.getDireccion()+" - "+(agenciaPartida.getUbigeo()!=null?agenciaPartida.getUbigeo().getNombreUbigeo():" ")));
//			else
//				venta.setDireccionEmbarque(new JAXBElement<String>(new QName(NAMESPACE,"direccionEmbarque"), String.class, "--"));
//		}
//		
//		/*=======================================================*/
//		/*DETALLE DE LA VENTA*/
//		/*=======================================================*/
////		Double totalOpGratuitas=.00;
//		// acespedes 20/06/2019 autorizado por valeria darle mas detalles en el pdf FE
//		DetalleVenta detalleVenta = createDetalleVenta(ventaPasaje, isCortesia,true);
//		
//		ArrayOfDetalleVenta arrayOfDetalleVenta= new ArrayOfDetalleVenta();
//		arrayOfDetalleVenta.getDetalleVenta().add(detalleVenta);
//		venta.setListDetalleVenta(new JAXBElement<ArrayOfDetalleVenta>(new QName(NAMESPACE,"listDetalleVenta"), ArrayOfDetalleVenta.class,arrayOfDetalleVenta));
//		
//		/*======================================================*/
//        /*-->Otros conceptos tributarios. (Cat. 14)*/
//        /*======================================================*/
//		//Operaciones inafectas
//		ArrayOfInformacionAdicionalTotalMonedaAdicional arrayTotalMonedaAdicional= new ArrayOfInformacionAdicionalTotalMonedaAdicional();
//		InformacionAdicionalTotalMonedaAdicional totalMonedaAdicional= new InformacionAdicionalTotalMonedaAdicional();
//		if(venta.getIgv()!=null && venta.getIgv().doubleValue()>0.00){
//			totalMonedaAdicional.setCodigo(new JAXBElement<String>(new QName(NAMESPACE,"codigo"), String.class, "1001")); /*Segun catalogo 14*/
//			totalMonedaAdicional.setNombre(new JAXBElement<String>(new QName(NAMESPACE,"nombre"), String.class, "TOTAL VALOR DE VENTA - OPERACIONES GRAVADAS"));
//		}else{
//			totalMonedaAdicional.setCodigo(new JAXBElement<String>(new QName(NAMESPACE,"codigo"), String.class, "1003")); /*Segun catalogo 14*/
//			totalMonedaAdicional.setNombre(new JAXBElement<String>(new QName(NAMESPACE,"nombre"), String.class, "TOTAL VALOR DE VENTA - OPERACIONES EXONERADAS"));
//		}
//		totalMonedaAdicional.setValor(new JAXBElement<String>(new QName(NAMESPACE,"valor"), String.class, Util.toNumberFormat(venta.getMontoSubTotal(),2))); /*(no incluye impuesto)*/
//		arrayTotalMonedaAdicional.getInformacionAdicionalTotalMonedaAdicional().add(totalMonedaAdicional);
//		
//		//Si es cortesia
//		if(isCortesia){
//			totalMonedaAdicional= new InformacionAdicionalTotalMonedaAdicional();
//			totalMonedaAdicional.setCodigo(new JAXBElement<String>(new QName(NAMESPACE,"codigo"), String.class, "1004")); /*Segun catalogo 14*/
//			totalMonedaAdicional.setNombre(new JAXBElement<String>(new QName(NAMESPACE,"nombre"), String.class, "TOTAL VALOR DE VENTA - OPERACIONES GRATUITAS"));
//			totalMonedaAdicional.setValor(new JAXBElement<String>(new QName(NAMESPACE,"valor"), String.class, Util.toNumberFormat(ventaPasaje.getTarifa(),2))); // 17/06/2019 cambios x giovanna
//			arrayTotalMonedaAdicional.getInformacionAdicionalTotalMonedaAdicional().add(totalMonedaAdicional);
//		}
//		
//		/*========================================================================*/
//        /*-->Elementos adicionales de la Factura y/o Boleta electronica. (Cat. 15)*/
//        /*========================================================================*/
//		ArrayOfInformacionAdicionalPropiedadAdicional arrayPropiedadAdicional= new ArrayOfInformacionAdicionalPropiedadAdicional();
//		if(!(isCortesia)){
//			InformacionAdicionalPropiedadAdicional propiedadAdicional= new InformacionAdicionalPropiedadAdicional();
//			propiedadAdicional.setCodigo(new JAXBElement<String>(new QName(NAMESPACE,"codigo"), String.class, "1000")); //Segun catalogo 15 (monto en letras)
//			propiedadAdicional.setNombre(new JAXBElement<String>(new QName(NAMESPACE,"nombre"), String.class, "MONTO EN LETRAS"));
//			propiedadAdicional.setValue(new JAXBElement<String>(new QName(NAMESPACE,"value"), String.class, getMontoLetras(venta.getMontoTotal())));
//			arrayPropiedadAdicional.getInformacionAdicionalPropiedadAdicional().add(propiedadAdicional);
//		}else{
//			InformacionAdicionalPropiedadAdicional propiedadAdicional= new InformacionAdicionalPropiedadAdicional();
//			propiedadAdicional.setCodigo(new JAXBElement<String>(new QName(NAMESPACE,"codigo"), String.class, "1002")); //Segun catalogo 15 (monto en letras)
//			propiedadAdicional.setNombre(new JAXBElement<String>(new QName(NAMESPACE,"nombre"), String.class, "TRANSFERENCIA GRATUITA"));
//			propiedadAdicional.setValue(new JAXBElement<String>(new QName(NAMESPACE,"value"), String.class, "TRANSFERENCIA GRATUITA DE UN BIEN Y/O SERVICIO PRESTADO GRATUITAMENTE"));
//			arrayPropiedadAdicional.getInformacionAdicionalPropiedadAdicional().add(propiedadAdicional);
//		}
//		
//		InformacionAdicional informacionAdicional= new InformacionAdicional();
//		informacionAdicional.setTotalesMonedaAdicional(new JAXBElement<ArrayOfInformacionAdicionalTotalMonedaAdicional>(new QName(NAMESPACE,"TotalesMonedaAdicional"), ArrayOfInformacionAdicionalTotalMonedaAdicional.class, arrayTotalMonedaAdicional));
//		informacionAdicional.setPropiedadesAdicionales(new JAXBElement<ArrayOfInformacionAdicionalPropiedadAdicional>(new QName(NAMESPACE,"PropiedadesAdicionales"), ArrayOfInformacionAdicionalPropiedadAdicional.class, arrayPropiedadAdicional));
//		
//		venta.setInformacionAdicional(new JAXBElement<InformacionAdicional>(new QName(NAMESPACE,"informacionAdicional"), InformacionAdicional.class,informacionAdicional));
//		
//		return venta;
//		
//	} catch (Exception e) {
//		e.printStackTrace();
//		return null;
//	}
//}
//
//private static String getMontoLetras(Double importe)throws Exception{
//	/*Monto en letras*/
//	String strImportePagado = Util.toNumberFormat(importe, 2);
//	int indice = strImportePagado.lastIndexOf(".");
//	ConvertirNumeroString num = new ConvertirNumeroString();
//	String strEnLetras = num.convertirLetras(importe.intValue()).toUpperCase()+" CON " + strImportePagado.substring(indice+1) + "/100 SOLES";
//	
//	return strEnLetras;
//}
//
///**
// * Completa el numero de digitos del correlativo a 8 digitos.
// * @param correlativo	: NÃºmere de correlativo.
// * @return	Correlativo formateado a 8 digitos.
// * @throws Exception
// */
//private static String autoCompletCorrelativo(String correlativo)throws Exception{
//	String _correlativo= "00000000" + correlativo;
//	_correlativo = _correlativo.substring(correlativo.toString().length(), _correlativo.length());
//
//	return _correlativo;
//}
//
///**
// * Obtiene la hora real del embarque del pasajero
// * @param ventaPasaje : Intancia de la clase VentaPasaje
// * @return
// */
//private static String getHoraRealEmbarque(VentaPasajeSispas ventaPasaje){
//	/*Obtiene la hora real de embarque del pasajero*/
//	String horaRealEmbarque=null;
//	if(ventaPasaje.getFechaPartida()==null)
//		horaRealEmbarque = "";
//	else{
//		if(ventaPasaje.getItinerario()!=null)
//			horaRealEmbarque = obtenerHoraEmbarque(ventaPasaje.getItinerario().getId(), ventaPasaje.getRuta().getId(), ventaPasaje.getAgenciaPartida().getId());
//		else
//			horaRealEmbarque = ventaPasaje.getHoraEmbarque();
//	}
//	String strHoraPartida = (horaRealEmbarque == null?ventaPasaje.getHoraPartida():horaRealEmbarque);
//	
//	return strHoraPartida;
//}
//
///**
// * Busca la hora de embarque segun el itinerario y agencia de partida.
// * @param idItinerario	: Identificador del Itienrario
// * @param idAgencia     : Identificador de la agencia de partida. 
// * @return
// */
//private static String obtenerHoraEmbarque(Long idItinerario, Integer idRuta, Integer idAgencia){
//	String result = null;
//	try{
//		ItinerarioAgenciaPartidaIDSispas itinerarioAgenciaPartidaID = new ItinerarioAgenciaPartidaIDSispas();
//		itinerarioAgenciaPartidaID.setIdItinerario(idItinerario);
//		itinerarioAgenciaPartidaID.setIdAgencia(idAgencia);
//		result = new VentasDaoImpl().obtenerHoraEmbarque(itinerarioAgenciaPartidaID, idRuta);
//	}catch(Exception ex){
//		ex.printStackTrace();
//	}
//	return result;
//}
//
//private static DetalleVenta createDetalleVenta(VentaPasajeSispas ventaPasaje, boolean isCortesia, boolean isMasDetallado) throws Exception {
//	String descripMovi="";
//	if(ventaPasaje.getTipoMovimiento().getId().intValue()==Constantes.ID_TIPMOV_EFECTIVO)		
//		descripMovi="VTA. PASAJE:";
//	else
//		descripMovi=ventaPasaje.getTipoMovimiento().getDenominacion().trim();
//	
//	String pasajero=ventaPasaje.getPasajero().toString().trim();
//	if(pasajero.length()>30)
//		pasajero=pasajero.substring(0, 30);
//	String tipoDocumento=ventaPasaje.getPasajero().getTipoDocumento().getNombreCorto().trim();
//	//String tipoDocumento=ventaPasaje.getPasajero().getTipoDocumento().getDenominacion().trim();
//	if(tipoDocumento.length()>10)
//		tipoDocumento=tipoDocumento.substring(0, 10);
//	
//	/*la Descripcion del Detalle*/
//	String descripcionPrincipal="";
//	if(ventaPasaje.getTipoMovimiento().getId().intValue()!=Constantes.ID_TIPMOV_GASTOS_ADMINISTRATIVOS){
//		String servicio=ventaPasaje.getServicio().getNombreCorto().trim();			
//		/*Obtiene la hora real del embarque*/
//		String strHoraPartida=getHoraRealEmbarque(ventaPasaje);		
//		
//		descripcionPrincipal= descripMovi+"\n"+
//				 "[PAX:"+pasajero+"] ["+tipoDocumento+":"+ventaPasaje.getPasajero().getNumeroDocumento().trim()+"]\n"+
//				 "[RUTA:"+ventaPasaje.getRuta().toString().trim()+"]\n"+
//				 "[SERV:"+servicio+"]\n"+
//				 "[ASIENTO:"+(ventaPasaje.getNumeroAsiento()!=null?ventaPasaje.getNumeroAsiento():"")+"]\n"+
//				 "[FECHA:"+(ventaPasaje.getFechaPartida()!=null?Constantes.FORMAT_DATE.format(ventaPasaje.getFechaPartida()):"")+"] [HORA:"+strHoraPartida+"]";
//			
//		if(isMasDetallado){
//			descripcionPrincipal += "\n";					    
//			// descripcion del embarque
//			if(ventaPasaje.getAgenciaPartida()!=null){
//
//				AgenciaSispas agPartida=ventaPasaje.getAgenciaPartida();
//				if(agPartida.getDireccion()==null)
//					agPartida = new VentasDaoImpl().buscarAgenciaById(agPartida.getId().longValue());
//				
//				descripcionPrincipal += "[EMBARQUE:"+agPartida.getDireccion()+" - "
//													+(agPartida.getUbigeo()!=null?agPartida.getUbigeo().getNombreUbigeo():"")+"]\n";
//			}else descripcionPrincipal += "[EMBARQUE:]\n";
//
//			if(ventaPasaje.getAgenciaLlegada()!=null){
//				
//				AgenciaSispas agLlegada=ventaPasaje.getAgenciaLlegada();
//				if(agLlegada.getDireccion()==null)
//					agLlegada = new VentasDaoImpl().buscarAgenciaById(agLlegada.getId().longValue());
//				
//				descripcionPrincipal += "[DESEMBARQUE:"+agLlegada.getDireccion()+"]\n";
//			}else descripcionPrincipal += "[DESEMBARQUE:]\n";
//		}
//		
//	}else{
//		descripcionPrincipal= descripMovi+(ventaPasaje.getObservaciones()!=null?" - "+ventaPasaje.getObservaciones():"");
//	}
//	
//	
//	DetalleVenta detalleVenta= new DetalleVenta();
//	
//	detalleVenta.setItem(1);
//	detalleVenta.setUnidadMedida(new JAXBElement<String>(new QName(NAMESPACE,"unidadMedida"), String.class, "NIU"));
//	detalleVenta.setDescripcion(new JAXBElement<String>(new QName(NAMESPACE,"descripcion"), String.class, descripcionPrincipal));
//	detalleVenta.setCantidad(1.0);
//	
//	if(!(isCortesia)){
//		detalleVenta.setTarifa(ventaPasaje.getImportePagado());
//		
//		if(ventaPasaje.getIgv()!=null && ventaPasaje.getIgv()>0.00){				
//			// Venta grabada
//			Double igv_x=Constantes.IGV/100; //(0.18)
//			Double igv_y=igv_x+1; //(1.18)
//			
//			detalleVenta.setValorUnitario(Double.valueOf(Util.toNumberFormat(ventaPasaje.getImportePagado() / igv_y,2))); //Precio o tarifa, pero sin igv
//			detalleVenta.setIgv(Double.valueOf(Util.toNumberFormat((ventaPasaje.getImportePagado() / igv_y) * igv_x,2))); //Igv del presio unitario
//			detalleVenta.setTotal(Double.valueOf(Util.toNumberFormat((ventaPasaje.getImportePagado()/ igv_y)*detalleVenta.getCantidad(), 2)));//total de la linea del detalle (Pero sin impuestos)
//			detalleVenta.setCodigoAfectacionIgv(new JAXBElement<String>(new QName(NAMESPACE,"codigoAfectacionIgv"), String.class, "10")); //-->Gravado - Operacion onerosa - Afectacion al igv (Cat. 7)
//			detalleVenta.setCodigoTipoPrecio(new JAXBElement<String>(new QName(NAMESPACE,"codigoTipoPrecio"), String.class, "01")); // Precio Unitario (incluye Igv) -Tipo de precio de venta unitario (Cat. 16)
//		}else{
//			// Venta Exonerada
//			detalleVenta.setValorUnitario(detalleVenta.getTarifa()); //Precio o tarifa, pero sin igv 
//			detalleVenta.setIgv(0.00); //No esta afento al IGV
//			detalleVenta.setTotal(detalleVenta.getTarifa()*detalleVenta.getCantidad());//total de la linea del detalle (Pero sin impuestos)
//			detalleVenta.setCodigoAfectacionIgv(new JAXBElement<String>(new QName(NAMESPACE,"codigoAfectacionIgv"), String.class, "20")); //Exonerado - Operacion Onerosa - Afectacion al igv (Cat. 7)
//			detalleVenta.setCodigoTipoPrecio(new JAXBElement<String>(new QName(NAMESPACE,"codigoTipoPrecio"), String.class, "01")); // Precio Unitario (incluye Igv) -Tipo de precio de venta unitario (Cat. 16)
//		}			
//	}else{
//		detalleVenta.setTarifa(ventaPasaje.getTarifa());
//		// Si es CORTESIA
//		detalleVenta.setValorUnitario(0.00); //Precio o tarifa, sin igv
//		detalleVenta.setIgv(0.00); //No esta afento al IGV
//		detalleVenta.setTotal(0.00);//total de la linea del detalle (Pero sin impuestos)
//		detalleVenta.setCodigoAfectacionIgv(new JAXBElement<String>(new QName(NAMESPACE,"codigoAfectacionIgv"), String.class, "20")); //Exonerado - Operacion Onerosa - Afectacion al igv (Cat. 7)
//		detalleVenta.setCodigoTipoPrecio(new JAXBElement<String>(new QName(NAMESPACE,"codigoTipoPrecio"), String.class, "02")); // Valor referencial unitario en operaciones no onerosas - Tipo de precio de venta unitario (Cat. 16)
//	}
//	
//	return detalleVenta;
//}
//
//}
