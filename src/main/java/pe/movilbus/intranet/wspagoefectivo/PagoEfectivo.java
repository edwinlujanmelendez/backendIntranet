package pe.movilbus.intranet.wspagoefectivo;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.codec.binary.Hex;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lombok.extern.slf4j.Slf4j;
import pe.movilbus.intranet.beans.JsonRest;
import pe.movilbus.intranet.util.Constantes;
import pe.movilbus.intranet.wsniubiz.RequestApiWS;

@Slf4j
public class PagoEfectivo {
	
	
	public static JsonRest ObtenerCip(RequestPagoEfectivo requestPE){
		try{ 
		  // Generador de Autorizacion - Pago Efectivo
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
			df.setTimeZone(TimeZone.getTimeZone("America/Lima"));
			String dateformatoutc = df.format(new Date());
			String bodyAutorizacion="{ \"accessKey\": \""+Constantes.ACCESSKEY_PAGOEFECTIVO+"\", "+
					" \"idService\": "+Constantes.IDSERVICIO_PAGOEFECTIVO+","+
					" \"dateRequest\": \""+dateformatoutc+"\", "+
					" \"hashString\": \""+getStringSHA256(Constantes.IDSERVICIO_PAGOEFECTIVO+"."+
											Constantes.ACCESSKEY_PAGOEFECTIVO+"."+
											Constantes.SECRETKEY_PAGOEFECTIVO+"."+dateformatoutc)+"\" "+					
				   "}";
			RequestApiWS rqBase = new RequestApiWS(Constantes.URL_BASE_PAGOEFECTIVO+"v1/authorizations",
													bodyAutorizacion,"","POST");
			JsonRest jsBase = consumoWsAPI(rqBase);
			if(jsBase.getRspCode().equals("201")) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date());
				cal.set(Calendar.HOUR, cal.get(Calendar.HOUR)+Constantes.HORAS_PAGO_EFECTIVO);
				// el body para generar el CIP
				String bodyCIP="{ \"currency\": \"PEN\", "+
								" \"amount\": "+requestPE.getMontoTotal()+","+
								" \"transactionCode\": \""+requestPE.getNroOperacion()+"\", "+
								" \"dateExpiry\": \""+df.format(cal.getTime())+"\", "+
								" \"paymentConcept\": \"Movil Bus Web\", "+
								" \"additionalData\": \"Movil Bus Web*TransporteInterprovincial\", "+
								" \"adminEmail\": \""+Constantes.EMAIL_RESPUESTA_ADMIN+"\", "+
								" \"userEmail\": \""+requestPE.getEmailcontacto()+"\", "+
								" \"userName\": \""+requestPE.getNombreUsuario()+"\", "+
								" \"userLastName\": \""+requestPE.getApellidoUsuario()+"\", "+
								" \"userUbigeo\": \"150115\", "+
								" \"userCountry\": \"Peru\", "+
								" \"userDocumentType\": \""+requestPE.getTipoDocumento()+"\", "+
								" \"userDocumentNumber\": \""+requestPE.getNroDocumento()+"\", "+
								" \"userPhone\": \""+requestPE.getNroTelefono()+"\", "+
								" \"userCodeCountry\": \""+requestPE.getCodePaisPhone()+"\", "+
								" \"serviceId\": "+Constantes.IDSERVICIO_PAGOEFECTIVO+
								  " }";
				JsonObject resultado = new JsonParser().parse(jsBase.getResponse()).getAsJsonObject();
				RequestApiWS rqCIP = new RequestApiWS(Constantes.URL_BASE_PAGOEFECTIVO+"v1/cips",
															bodyCIP,"Bearer "+resultado.get("data").getAsJsonObject().get("token").getAsString(),"POST");
				JsonRest jsCIP = consumoWsAPI(rqCIP);
				if(jsCIP.getRspCode().equals("201"))							
					return jsCIP;
				else return null;
				
				
				
				
			}else {
				//log.error("Error-GenerandoAutorizacion-jsBase::"+jsBase.getResponse());
				System.out.println("Error-GenerandoAutorizacion-jsBase::"+jsBase.getResponse());
				return null;
			}
			
		 }catch(Exception io){				
			//log.error("ObtenerCip-General::"+io.getMessage());
			System.out.println("ObtenerCip-General::"+io.getMessage());
            return null;
         }
		
	}

	public static JsonRest consumoWsAPI(RequestApiWS request){
		
		try{      
			
			 //segurar ssl
			System.setProperty("https.protocols", "TLSv1.2");
			
            // https 
            URL endpoint = new URL(request.getUrl());
            HttpURLConnection postConnection = (HttpURLConnection) endpoint.openConnection();
            postConnection.setRequestProperty("Content-Type","application/json");
            if(!request.getAutorizacion().isEmpty()) {
            	postConnection.setRequestProperty("Accept-Lenguage","es-PE");            	
            	postConnection.setRequestProperty("Origin","web");
            	postConnection.setRequestProperty("Authorization",request.getAutorizacion());                
            }
            postConnection.setRequestMethod(request.getTipometodo());                        
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
			//log.error("consumoWsAPI-General::"+io.getMessage());
			System.out.println("consumoWsAPI-General::"+io.getMessage());
            return null;
        }
	}
	
	public static String getStringSHA256(String cadena) {

	        String result = "";
	        try
	        {
	        	MessageDigest md= MessageDigest.getInstance("SHA-256");
	            md.update(cadena.getBytes());
	            byte[] mb = md.digest(); 
	            result = String.valueOf(Hex.encodeHex(mb));
	            return result;
	        }
	        catch(NoSuchAlgorithmException e)
	        {
	        	//log.error("Error-getStringSHA256-General::"+e.getMessage());
	        	System.out.println("Error-getStringSHA256-General::"+e.getMessage());
	        }
	        return result;
	}

}
