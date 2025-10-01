package pe.movilbus.intranet.wsexterno;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

import pe.movilbus.intranet.util.Constantes;

public class Sha2 {
	

	public static String getStringSHA(String cadena) {

		MessageDigest md = null;
	
	        String result = "";
	        try
	        {
	            md= MessageDigest.getInstance("SHA-512");
	            md.update(cadena.getBytes());
	            byte[] mb = md.digest(); 
	            result = String.valueOf(Hex.encodeHex(mb));
	            return result;
	        }
	        catch(NoSuchAlgorithmException e)
	        {
	            
	        }
	        return "";
	}
	
//	public static String encriptarDatos(String datos) {
//		try {
//			//Clave SHA2 obtenida del V-Payment
//			String claveSHA2 = Constantes.CLAVE_SHA2_WALLET;			
//			String cadena = datos + claveSHA2;			
//			String cadenaSHA2 = Sha2.getStringSHA(cadena);			
//			return cadenaSHA2;			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}		
//		return "";
//	}
	
	//prueba VPOS
	public static String encriptarDatosPassarela(String datos) {
		try {
			//Clave SHA2 obtenida del V-Payment
			//String claveSHA2 = Constantes.CLAVE_SHA2_VPOS;		//@elujan --
			//String cadena = datos + claveSHA2;					//@elujan --		
			String cadena = datos;	
			
			String cadenaSHA2 = Sha2.getStringSHA(cadena);			
			return cadenaSHA2;			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return "";
	}
	
}
