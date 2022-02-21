package security.Security.lab4;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class FileTextWorker {
	private static final String MD5 = "resources/The Hunger Games.txt";
	private static final String ARGON2I = "resources/Frankenstein";

	public String readFile(String path) {
		StringBuilder text = new StringBuilder();
		try (InputStream input = new FileInputStream(path)) {
			int i = -1;
			while ((i = input.read()) != -1) {
				text.append((char) i);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return text.toString();
	}

	public void writeLineToFile(String path, String message) {
		StringBuilder text = new StringBuilder();
		try (OutputStream output = new FileOutputStream(path)) {
			output.write((message + "\n").getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeTextAsLines(String path, List<String> passes) {
		StringBuilder text = new StringBuilder();
		try (OutputStream output = new FileOutputStream(path)) {
			for (String message : passes) {
				output.write((message + "\n").getBytes());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String readCertainLine() {

		return null;

	}

}
