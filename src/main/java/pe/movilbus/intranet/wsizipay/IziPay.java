package pe.movilbus.intranet.wsizipay;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import pe.movilbus.intranet.beans.JsonRest;
import pe.movilbus.intranet.beans.VentaPasajeros;
import pe.movilbus.intranet.dao.MensajeFlagResultIziPay;
import pe.movilbus.intranet.util.Constantes;
import pe.movilbus.intranet.wsniubiz.RequestApiWS;

public class IziPay {
	
	private static final Logger logger = LoggerFactory.getLogger(IziPay.class);
	
	public static MensajeFlagResultIziPay getTokenIziPay(String numOperacion){
		try{			
			String bodySession = " { \"RequestSource\": \"ECOMMERCE\", "+
								 " \"merchantCode\": \""+Constantes.MERCHANT_ID_IZIPAY+"\","+
								 " \"OrderNumber\": \""+Constantes.MERCHANT_ID_IZIPAY+"\","+
								 " \"PublicKey\": \""+Constantes.PUBLIC_KEY_IZIPAY+"\","+
								 " \"Amount\": \"0.00\""+
								 " } ";
						
			RequestApiWS rqSession = new RequestApiWS(Constantes.URL_TOKEN_IZIPAY, bodySession, numOperacion, "POST");
			JsonRest jsSession = consumoWsAPI(rqSession, "", 2);
			
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(jsSession);
			
			System.out.println(json);
			
			 if ("200".equals(jsSession.getRspCode())) {
	                JSONObject obj = new JSONObject(jsSession.getResponse().toString());
	                if ("00".equals(obj.getString("code"))) {
	                    String token = obj.getJSONObject("response").getString("token");
	                    
	                    return new MensajeFlagResultIziPay(
	                            true,
	                            token,
	                            Constantes.PUBLIC_KEY_IZIPAY,
	                            Constantes.MERCHANT_ID_IZIPAY,
	                            numOperacion,
	                            numOperacion,
	                            ""
	                    );
	                } else {
	                    return new MensajeFlagResultIziPay(false, "", "", "", "", "", "Error IziPay PagoLink: " + obj.getString("message"));
	                }
	            }
		}catch(Exception e){
			e.printStackTrace();
			//logger.error("Error en IziPay: ", e);
			
			return new MensajeFlagResultIziPay(false, "", "", "", "", "", "Excepción: " + e.getMessage());
		}
		
		return new MensajeFlagResultIziPay(false, "", "", "", "", "", "Error desconocido en IziPay PagoLink");
	}
	
	public static String getLink(String token, String numOperacion, String montoTotal, String descripcion, VentaPasajeros pasajero, String formatoHoraCompleto){
	    try{
	    	String tipo_documento = "DNI";
	    	
	    	if(pasajero.getVentaIda().getPasajero().getIdTipoDocumento() == 1){
	    		tipo_documento = "DNI";
	    	}else if(pasajero.getVentaIda().getPasajero().getIdTipoDocumento() == 6){
	    		tipo_documento = "PASAPORTE";
	    	}else if(pasajero.getVentaIda().getPasajero().getIdTipoDocumento() == 7){
	    		tipo_documento = "CEDULA IDENTIDAD";
	    	}else if(pasajero.getVentaIda().getPasajero().getIdTipoDocumento() == 8){
	    		tipo_documento = "CARNET EXTRANJERIA";
	    	}
	    	
	        JSONObject json = new JSONObject();
	        json.put("merchantCode", Constantes.MERCHANT_ID_IZIPAY);
	        json.put("productDescription", descripcion);
	        json.put("amount", montoTotal);
	        json.put("currency", "PEN");
	        //json.put("expirationDate", generarExpirationDateUnaHoraRedondeada());
	        json.put("expirationDate", formatoHoraCompleto);
	        json.put("wayOfUse", "INDIVIDUAL");
	        json.put("email_Notification", pasajero.getVentaIda().getEmailContacto() != null && !pasajero.getVentaIda().getEmailContacto().isEmpty() ? pasajero.getVentaIda().getEmailContacto() : "correo@demo.com");
	        //json.put("email_Notification", null);
	        json.put("payMethod", "CARD,QR,YAPE_CODE,PAGO_PUSH");
	        json.put("referenceCode", numOperacion);
	        json.put("languageUsed", "ESP");
	        json.put("urL_Terms_and_Conditions", "https://www.izipay.pe/pdf/terminos-y-condiciones-formulario-pago");
	        json.put("urlIpn", Constantes.URL_WEBHOOK_IZIPAY);

	        // Billing
	        JSONObject billing = new JSONObject();
	        billing.put("firstName", pasajero.getVentaIda().getPasajero().getNombre() != null && !pasajero.getVentaIda().getPasajero().getNombre().isEmpty() ? pasajero.getVentaIda().getPasajero().getNombre() : "Nombre");
	        billing.put("lastName", (pasajero.getVentaIda().getPasajero().getApePaterno() + " " + pasajero.getVentaIda().getPasajero().getApeMaterno()).trim().isEmpty() ? "Apellido" : (pasajero.getVentaIda().getPasajero().getApePaterno() + " " + pasajero.getVentaIda().getPasajero().getApeMaterno()));
	        billing.put("email", pasajero.getVentaIda().getEmailContacto() != null && !pasajero.getVentaIda().getEmailContacto().isEmpty() ? pasajero.getVentaIda().getEmailContacto() : "correo@demo.com");
	        billing.put("phoneNumber", pasajero.getVentaIda().getTelefonoOpcional() != null && !pasajero.getVentaIda().getTelefonoOpcional().isEmpty() ? pasajero.getVentaIda().getTelefonoOpcional() : "999999999");
	        billing.put("street", "Av. Nicolás Arriola 740, Lima, Perú");
	        billing.put("postalCode", "15000");
	        billing.put("city", "Lima");
	        billing.put("state", "Lima");
	        billing.put("country", "PE");
	        billing.put("documentType", tipo_documento);
	        billing.put("document", pasajero.getVentaIda().getPasajero().getNumDocumento());
	        json.put("billing", billing);

	        // Shipping
	        JSONObject shipping = new JSONObject();
	        shipping.put("firstName", pasajero.getVentaIda().getPasajero().getNombre() != null && !pasajero.getVentaIda().getPasajero().getNombre().isEmpty() ? pasajero.getVentaIda().getPasajero().getNombre() : "Nombre");
	        shipping.put("lastName", (pasajero.getVentaIda().getPasajero().getApePaterno() + " " + pasajero.getVentaIda().getPasajero().getApeMaterno()).trim().isEmpty() ? "Apellido" : (pasajero.getVentaIda().getPasajero().getApePaterno() + " " + pasajero.getVentaIda().getPasajero().getApeMaterno()));
	        shipping.put("email", pasajero.getVentaIda().getEmailContacto() != null && !pasajero.getVentaIda().getEmailContacto().isEmpty() ? pasajero.getVentaIda().getEmailContacto() : "correo@demo.com");
	        shipping.put("phoneNumber", pasajero.getVentaIda().getTelefonoOpcional() != null && !pasajero.getVentaIda().getTelefonoOpcional().isEmpty() ? pasajero.getVentaIda().getTelefonoOpcional() : "999999999");
	        shipping.put("street", "Av. Nicolás Arriola 740, Lima, Perú");
	        shipping.put("postalCode", "15000");
	        shipping.put("city", "Lima");
	        shipping.put("state", "Lima");
	        shipping.put("country", "PE");
	        shipping.put("documentType", tipo_documento);
	        shipping.put("document", pasajero.getVentaIda().getPasajero().getNumDocumento());
	        json.put("shipping", shipping);

	        // Custom Fields
	        JSONArray custom = new JSONArray();
	        JSONObject field = new JSONObject();
	        field.put("name", "field1");
	        field.put("value", numOperacion);
	        custom.put(field);
	        json.put("customFields", custom);

	        String bodyPagoLink = json.toString();

	        RequestApiWS rqSession = new RequestApiWS(Constantes.URL_CREACION_LINK_IZIPAY, bodyPagoLink, numOperacion, "POST");
	        JsonRest jsSession = consumoWsAPI(rqSession, token, 1);

	        if ("200".equals(jsSession.getRspCode())) {
	            JSONObject obj = new JSONObject(jsSession.getResponse().toString());
	            if ("00".equals(obj.getString("code"))) {
	                JSONObject data = obj.getJSONObject("response");
	                String linkPago = data.getString("urL_PaymentLink");
	                
	                return linkPago;
	            } else {
	                return "";
	            }
	        }
	    }catch(Exception e){
	        e.printStackTrace();
	    }

	    return "";
	}
	
	/*public static String generarExpirationDateUnDia() {
	    LocalDateTime fecha = LocalDateTime.now()
	            .plusDays(1)                // 1 día después del día actual
	            .withHour(23)               // hora fija 23
	            .withMinute(59)             // minuto 59
	            .withSecond(0)              // segundo 00
	            .withNano(0);               // milisegundos .000

	    DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
	    return fecha.format(f);
	}*/
	
	public static String generarExpirationDateUnaHoraRedondeada() {
	    LocalDateTime ahora = LocalDateTime.now().plusHours(1);
	    int minutos = ahora.getMinute();

	    if (minutos == 0) {
	        // se queda tal cual
	    } else if (minutos <= 30) {
	        ahora = ahora.withMinute(30);
	    } else {
	        ahora = ahora.plusHours(1).withMinute(0);
	    }

	    ahora = ahora.withSecond(0).withNano(0);

	    DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
	    
	    return ahora.format(f);
	}
	
	public static JsonRest consumoWsAPI(RequestApiWS request, String token, int val){
		try{
			//segurar ssl
			//System.setProperty("javax.net.debug", "ssl,handshake");
			//java.security.Security.setProperty("jdk.tls.client.protocols", "TLSv1.2");
			System.setProperty("https.protocols", "TLSv1.2");
			
            // https
            URL endpoint = new URL(request.getUrl());
            HttpURLConnection postConnection = (HttpURLConnection) endpoint.openConnection();
            if(val == 1){
            	postConnection.setRequestProperty("Authorization", "Bearer "+token);
            	postConnection.setRequestProperty("transactionId", request.getAutorizacion());
            }else if(val == 2){
            	postConnection.setRequestProperty("transactionId", request.getAutorizacion());
            }
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
        	io.printStackTrace();
        	
        	logger.error("Error al consumir IZIPAY PAGOLINK: " + io.getMessage(), io);
        	
            return null;
        }
	}
}