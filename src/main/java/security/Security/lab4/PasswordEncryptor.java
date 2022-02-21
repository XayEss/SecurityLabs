package security.Security.lab4;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class PasswordEncryptor {

	public static String encryptMD5(String password) {
		String hashedPass = null;
		MessageDigest md = null;
		if (password != null && !password.isEmpty()) {
			try {
				md = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			md.update(password.getBytes(StandardCharsets.UTF_8));
			// md.digest();
			byte[] digest = md.digest();
			String hash = new BigInteger(1, digest).toString(16);
			hashedPass = hash;// String.format("%064x", new BigInteger(1, md.digest()));
		}
		System.out.println(hashedPass);
		return hashedPass;
	}

	public static String encryptArgon2i(String password) {
		String hashedPass = null;
		Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2i);
		char[] passArr = password.toCharArray();
		try {
		hashedPass = argon2.hash(22, 65536, 1, passArr);
		System.out.println("Argon: " + hashedPass);
		}finally {
			argon2.wipeArray(passArr);
		}
		hashedPass = hashedPass.substring(hashedPass.indexOf("p="));
		return hashedPass;
	}
}
