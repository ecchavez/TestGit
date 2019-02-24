/**
 * 
 */
package mx.com.grupogigante.gestionvivienda.utils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * @author Ing. Efren Alberto Zavala Barrera
 *
 */
public class EncriptaMD5 {
	String md5val = "";
	public EncriptaMD5() {
		//super();
		
	}	
	public String methashMD5(String p_strg){
		MessageDigest algorithm = null;
		
		try
        {
            algorithm = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException nsae)
        {
            
        }
        
       // for (String arg : p_strg)
       //{
            byte[] defaultBytes = p_strg.getBytes();
            algorithm.reset();
            algorithm.update(defaultBytes);
            byte messageDigest[] = algorithm.digest();
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < messageDigest.length; i++)
            {
                String hex = Integer.toHexString(0xFF & messageDigest[i]);
                if (hex.length() == 1)
                {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            md5val = hexString.toString();
       // }
        return md5val;
	}
	
	
}
