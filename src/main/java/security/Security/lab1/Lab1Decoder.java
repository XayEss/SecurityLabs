package security.Security.lab1;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Lab1Decoder {
	private String decodedText;

	public void decipherTextFromBinary(String file) {
			byte[] arr = readFromFile(file);
			String text = new String(arr);
			text = text.replaceAll("\n", "");
			arr = text.getBytes();
			List<Integer> ints = new ArrayList<>();
			System.out.println(arr.length);
			String[] symbolsInBinary = byteArrayToSplitText(arr);
			System.out.println(Arrays.toString(symbolsInBinary));
			for (int i = 1; i < symbolsInBinary.length; i++) {
				ints.add(Integer.parseInt(symbolsInBinary[i], 2));
			}
			StringBuilder characters = new StringBuilder();
			for (Integer integer : ints) {
				characters.append((char) integer.intValue());
			}
			decodedText = decodeFromBase64(characters.toString());
	}

	public byte[] readFromFile(String file) {
		byte[] result = null;
		try (InputStream input = new DataInputStream(new FileInputStream(file))) {
			List<Byte> list = new ArrayList<>();
			byte b = 0;
			while ((b = (byte) input.read()) != -1) {
				list.add(b);
			}
			result = new byte[list.size()];
			for (int i = 0; i < result.length; i++) {
				result[i] = list.get(i).byteValue();
			}
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public String[] byteArrayToSplitText(byte[] arr) {
		String[] symbolsInBinary = new String[arr.length / 8];
		byte[] array = new byte[8];
		for (int i = 0; i < arr.length; i++) {
			array[i % 8] = arr[i];
			if (i % 8 == 0) {
				String string = (new String(array));
				symbolsInBinary[i / 8] = string;
				//array = new byte[8];
			}

		}
		return symbolsInBinary;
	}

	public static String decodeFromBase64(String text) {
		String decodedText = new String(Base64.getDecoder().decode(text));
		return decodedText;
	}

	public void printDecodedText() {
		System.out.println(decodedText);
	}
}
