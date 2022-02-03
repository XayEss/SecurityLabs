package security.Security.lab1;

import java.awt.font.FontRenderContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Part3 {

	private String encoded = "G0IFOFVMLRAPI1QJbEQDbFEYOFEPJxAfI10JbEMFIUAAKRAfOVIfOFkYOUQFI15ML1kcJFUeYhA4IxAeKVQZL1VMOFgJbFMDIUAAKUgFOElMI1ZMOFgFPxADIlVMO1VMO1kAIBAZP1VMI14ANRAZPEAJPlMNP1VMIFUYOFUePxxMP19MOFgJbFsJNUMcLVMJbFkfbF8CIElMfgZNbGQDbFcJOBAYJFkfbF8CKRAeJVcEOBANOUQDIVEYJVMNIFwVbEkDORAbJVwAbEAeI1INLlwVbF4JKVRMOF9MOUMJbEMDIVVMP18eOBADKhALKV4JOFkPbFEAK18eJUQEIRBEO1gFL1hMO18eJ1UIbEQEKRAOKUMYbFwNP0RMNVUNPhlAbEMFIUUALUQJKBANIl4JLVwFIldMI0JMK0INKFkJIkRMKFUfL1UCOB5MH1UeJV8ZP1wVYBAbPlkYKRAFOBAeJVcEOBACI0dAbEkDORAbJVwAbF4JKVRMJURMOF9MKFUPJUAEKUJMOFgJbF4JNERMI14JbFEfbEcJIFxCbHIJLUJMJV5MIVkCKBxMOFgJPlVLPxACIxAfPFEPKUNCbDoEOEQcPwpDY1QDL0NCK18DK1wJYlMDIR8II1MZIVUCOB8IYwEkFQcoIB1ZJUQ1CAMvE1cHOVUuOkYuCkA4eHMJL3c8JWJffHIfDWIAGEA9Y1UIJURTOUMccUMELUIFIlc=";
	private List<byte[]> possibleKeys;

	public String part3Decoder() {
		String decText = Lab1Decoder.decodeFromBase64(encoded);
		char[] symbols = decText.toCharArray();
		// System.out.println(symbols);
		int length = shiftArray(symbols);
		keyLengthXorDecoder(length, Arrays.copyOfRange(symbols, 0, symbols.length / 1));
		return null;
	}

	public int shiftArray(char[] array) {
		List<Integer> pekedCoincedenceIndex = new ArrayList<>();
		int keyLength;
		char[] shifted = new char[array.length];
		for (int i = 1; i < 34; i++) {
			int coincedenceCounter = 0;
			for (int j = 0; j < shifted.length; j++) {
				int shiftIndex = j + i;
				if (shiftIndex > shifted.length - 1) {
					shiftIndex = (j + i) - shifted.length;
				}
				shifted[j] = array[shiftIndex];
				// System.out.print(shifted[j]);
			}
			// System.out.println("default " + Arrays.toString(array).replaceAll("[,]",
			// ""));
			// System.out.println("shifted: " + i + ": " +
			// Arrays.toString(shifted).replaceAll("[,]", ""));
			coincedenceCounter = countCoincedence(array, shifted);
			double coincedence = (double) coincedenceCounter / array.length;
			// System.out.println(coincedence);
			// System.out.println(1/26d);
			// System.out.println();
			if (coincedence > 1 / 26d) {
				pekedCoincedenceIndex.add(i);
			}
		}
		keyLength = findKeyLength(pekedCoincedenceIndex);
		// System.out.println(Arrays.toString(pekedCoincedenceIndex.toArray()));
		System.out.println(keyLength);
		return keyLength;

	}

	public int countCoincedence(char[] arr, char[] shiftArr) {
		int coincedenceCounter = 0;
		for (int i = 0; i < shiftArr.length; i++) {
			if (arr[i] == shiftArr[i]) {
				coincedenceCounter++;
			}
		}
		return coincedenceCounter;

	}

	public int findKeyLength(List<Integer> ints) {
		int step = ints.get(1) - ints.get(0);
		for (Integer integer : ints) {
			if (integer % step != 0) {
				step = 0;
			}
		}
		return step;
	}

	public void keyLengthXorDecoder(int keyLength, char[] symbols) { // заполнить байтами длину ключа и проверить все
																		// варианты иксорами
		possibleKeys = new ArrayList<>();
		List<String> possibleResults = new ArrayList<>();
		byte[] bytes = new byte[keyLength];
		StringBuilder text = new StringBuilder("");
		double nearestQuotient = 0.8;
		String result = "";
		for (int j = 0; j < 256; j++) {
			bytes[0] = (byte) j;
			for (int k = 0; k < 256; k++) {
				bytes[1] = (byte) k;
				for (int l = 0; l < 256; l++) {
					bytes[2] = (byte) l;
					for (int m = 0; m < symbols.length / 4; m++) {
						text.append((char) (symbols[m] ^ bytes[m % keyLength]));
						// System.out.println("m: " + m + "n: " + m%3);
					}
					// if(EnglishTextAnalyzer.isEnglishText(text.toString())) {
					// if (text.toString().contains("Write a code")) {
					double quotient = EnglishTextAnalyzer.isEnglishTextQuotient(text.toString());
					if (quotient <= nearestQuotient) {
						nearestQuotient = quotient;
						result = text.toString();
						possibleResults.add(result);
						possibleKeys.add(new byte[] { bytes[0], bytes[1], bytes[2] });
						System.out.println(text.toString());
						System.out.println(quotient);

					}
					// }
					text.delete(0, text.length());
				}
			}
		}
		System.out.println(result);
		System.out.println(Arrays.deepToString(possibleResults.toArray()));
		for (int i = 0; i < possibleKeys.size(); i++) {
			for (int m = 0; m < symbols.length; m++) {
				char a = (char) (symbols[m] ^ possibleKeys.get(i)[m % keyLength]);
				if (a > 19 && a < 126) {
					text.append(a);
				} else {
					text.delete(0, text.length());
					break;
				}
				// System.out.println("m: " + m + "n: " + m%3);
			}
			if(text.toString().contains("the") || text.toString().contains("an") || text.toString().contains("to"))
			System.out.println(text.toString());
			text.delete(0, text.length());
		}
		// System.out.println(Arrays.deepToString(possibleKeys));

		// }
	}

	public byte generateByte() {
		for (int i = 0; i < 256; i++) {

		}
		return 0;
	}
}
