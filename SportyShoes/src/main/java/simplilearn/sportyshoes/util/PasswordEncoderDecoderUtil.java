package simplilearn.sportyshoes.util;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class PasswordEncoderDecoderUtil {
	
	public static String decodePassword(String encodedPassword) {
		String decodedPass = "";
		byte[] base64decodedBytes = Base64.getDecoder().decode(encodedPassword);
		try {
			decodedPass = new String(base64decodedBytes, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return decodedPass;
	}


	public static String encodePassword(String password) {
		String encodedPassword = "";
		try {
			encodedPassword = Base64.getEncoder().encodeToString(
					password.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encodedPassword;
	}

}
