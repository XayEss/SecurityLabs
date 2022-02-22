package security.Security.lab4;

public class PasswordGeneratorMain {
	
	public static void main(String[] args) {
		PasswordGenerator generator = new PasswordGenerator();
		generator.generatePasswords(100000);
	}

}
