package security.Security.lab4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class PasswordGenerator {
	
	List<String> generatedPasswords;
	
	private char[] symbolList = new char[] {'@', '&', '!', '$', '%', '*', '^', '-', '+', '='};
	private String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	
	public void generatePasswords(int amount) {
		generatedPasswords = new ArrayList<>();
		for(int i = 0; i < amount; i++) {
			String pass = generatePassword();
			//System.out.println(pass);
			generatedPasswords.add(pass);
		}
		writeMD5Passwords();
		//writeArgon2iPasswords();
	}

	public String generatePassword() {
		Random rand = new Random();
		int generationMethod = rand.nextInt(101);
		String result = "";
		Path path;
		int passwordNum = 0;
		if (generationMethod < 10) {
			try {
				path = Paths.get("src/main/resources/top100.txt");
				List<String> lines = Files.readAllLines(path);
				passwordNum = rand.nextInt(lines.size());
				result = "1: " + Files.readAllLines(path).get(passwordNum);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (generationMethod > 15) {
			try (Stream<String> lines = Files.lines(Paths.get("src/main/resources/top-1000000.txt"))) {
				passwordNum = rand.nextInt(900000);
				result = "2: " + lines.skip(passwordNum).findFirst().get();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			int length = rand.nextInt(9) + 8;
			StringBuilder password = new StringBuilder();
			int symbolChance = 50;
			int choose;
			char character;
			for(int i = 0; i < length; i++) {
				choose = rand.nextInt(101);
				if(choose < symbolChance) {
					character = symbolList[rand.nextInt(symbolList.length)];
					password.append(character);
					symbolChance -= symbolChance > 5 ? 5 : 0;
				} else {
					//character = (char)(rand.nextInt(26) + 'a');
					character = characters.charAt(rand.nextInt(characters.length()));
					password.append(character);
					symbolChance += symbolChance < 95 ? 5 : 0;
				}
			}
			result = "3: " + password.toString();
		}
		return result;
	}
	
	public void writeMD5Passwords() {
		List<String> hashedPasses = new ArrayList<>();
		for (String password : generatedPasswords) {
			hashedPasses.add(PasswordEncryptor.encryptMD5(password));
		}
		writePasswords("src/main/resources/md5-passwords.csv", hashedPasses);
		
	}
	
	public void writeArgon2iPasswords() {
		List<String> hashedPasses = new ArrayList<>();
		for (String password : generatedPasswords) {
			hashedPasses.add(PasswordEncryptor.encryptArgon2i(password));
		}
		writePasswords("src/main/resources/argon2i-passwords.csv", hashedPasses);
		
	}
	
	public void writePasswords(String path, List<String> hashedPasses) {
		FileTextWorker worker = new FileTextWorker();
		worker.writeTextAsLines(path, hashedPasses);
		
	}

}
