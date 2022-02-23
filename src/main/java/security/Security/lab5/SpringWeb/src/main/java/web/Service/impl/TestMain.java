package web.Service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.SecretKey;

import com.fasterxml.jackson.databind.ObjectMapper;

import web.database.AESHandler;
import web.database.GoogleKMS;

public class TestMain {

	public static void main(String[] args) {
		GoogleKMS kms = new GoogleKMS();
		AESHandler aes = new AESHandler(kms);
		String plain = "CHinCHeng Changhi";
		try {
			SecretKey key = aes.generateKey(256);
			byte[] key2 = kms.encryptB(key.getEncoded());
			Files.write(Path.of("C:/Users/alexs/eclipse-workspace/SpringWeb/src/main/webapp/WEB-INF/configs/notakey.conf"), Base64.getEncoder().encode(key2) , StandardOpenOption.WRITE);
		} catch (NoSuchAlgorithmException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] vec = aes.generateVector();
		String encodedKey = aes.encrypt(plain, vec);
		String decodedKey = aes.decrypt(encodedKey, vec);
		System.out.println(decodedKey);
		//byte[] encryptedKey = Base64.getEncoder().encode(kms.encryptB(key));
		//byte[] loolB = kms.encryptB("loooool");
		//try {
		//	Files.write(Path.of("C:/Users/alexs/eclipse-workspace/SpringWeb/src/main/webapp/WEB-INF/configs/notakey.conf"), encryptedKey , StandardOpenOption.WRITE);
		//} catch (IOException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		//}
		//String reverseloolB = kms.decryptB(loolB);
		//System.out.println("res=" + new String(reverseloolB));
		//String reverselool = kms.decryptB(Base64.getDecoder().decode(lool));
		//System.out.println(reverselool);
	}

}
