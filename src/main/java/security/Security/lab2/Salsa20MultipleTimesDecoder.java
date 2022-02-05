package security.Security.lab2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.plaf.synth.SynthOptionPaneUI;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import com.sun.tools.javac.util.List;

public class Salsa20MultipleTimesDecoder {

	private String codedLines = "280dc9e47f3352c307f6d894ee8d534313429a79c1d8a6021f8a8eabca919cfb685a0d468973625e757490daa981ea6b\n"
			+ "3a0a9cab782b4f8603eac28aadde1151005fd46a859df21d12c38eaa858596bf2548000e883d72117466c5c3a580f66b\n"
			+ "3a0adee4783a538403b9c29eaac958550242d3778ed9a61918959bf4ca849afa68450f5edc6e311a7f7ed1d7ec\n"
			+ "3a0adee461354e8c1cfcc39bef8d5e40525fdc6bc0dee359578290bcca849afa685a1e5c897362\n"
			+ "3a0adab0282b5c9719fcc38caac054541b449a62cf9df21d509690af858286f731091a4890786252\n"
			+ "390adeaa283358c318f0c08befc157061f59dd65dd9dee1c04c38fad839586ea3b0903489078\n"
			+ "390bcfac283a1d8111ebc8d8e8c2554d1b5e852dfed5e955008c8bb48ed094fe3a4d0b45883d731b7b609c\n"
			+ "3a0d9ba37a2e539750f8c39caade464313449a78c7d9e3075782deaf8f9180e66845074f9e31\n"
			+ "2c17cfe47c335c9750edc59daac9434313549a62cf9df51a1a868ab0839e95bf294f1a4c893d751b7b66d882\n"
			+ "3a0adee47d35598a03fac28eefdf54011610d962dcd3f2070ecfdebe989f9fbf3f41015a9e3d73116f60de\n"
			+ "200d9bb07a3a4b861cf5c88aaadf54520742d47e859df6000d9992bd99d086f72d09194097713d\n"
			+ "2f0cdfe4653a568603b9d88baadf50521a55c82dcbd8e707579796b79995d2f624451d098c7831167b64d5\n"
			+ "3a0adaaa283d519a50edc2d8e5d9594300439a79c1dcf2550086deb3849f85bf26461a09947b2e\n"
			+ "3a0aceb72838528d03fac49de4ce5406165fce6589d0e71e12c39db79d9180fb3b09014fdb68625e7b7edc82\n"
			+ "2f0cdfe47c33489050edc59daac350521b46df2dc1c8e3551885deaa8f839df33d5d074695\n"
			+ "27119bb76138568f19fcc9d8e58a54545247d379c19df21d12c38eb98695d2fc295a1a09947b310a727dc5c9a898a3\n"
			+ "2f0cdfe46d35498602e9df91f9c842061d569a6adbd8e701579397ac82d093f12c09034696787f0a\n"
			+ "390bcfac282f558a03b9df9dedcc43425244d268c0cfa61602918cbd848481bf3c5c1c47db7c660c63\n"
			+ "2f0cdfe464344e8650edc59daac3504b1710d56b89dce5011e8c90f6";

	private ArrayList<Integer> wordIndexes;
	private StringBuilder key;
	private String[] commonUsedWords = { "the ", /* " the ", */ " be ", " to ", "of", " and ", "that", "have", "for",
			"not", "with", "you" };
	private StringBuilder[] resultLines;
	private StringBuilder[] possibleLines;

	public void decode() {
		key = new StringBuilder();
		wordIndexes = new ArrayList<>();
		String[] lines = codedLines.split("\n");
		StringBuilder word = new StringBuilder();
		resultLines = new StringBuilder[codedLines.length()];
		possibleLines = new StringBuilder[codedLines.length()];

//		String linesXoredHex = byteXorHexLines(lines[0], lines[2]);
//		System.out.println("result:" + linesXoredHex);
//		String cribSearched = byteXorHexLines(lines[0],lines[1]);
//		cribSearched = byteXorHexLines(cribSearched,lines[2]);
//		System.out.println(convertHexToString(hexXorStrings(cribSearched,convertStringToHex("the "))));

//		for (int j = 0; j < 1 /* lines.length - 1*/; j++) {
//			resultLines[j] = "";
//			for (int k = 1; k < lines.length; k++) {
//				System.out.println(j + " " + k);
//				String s = (hexXorLines(lines[j], lines[k]));
//				for (String crib : commonUsedWords) {
//					int i = 0;
//					while (i < s.length() - crib.length() * 2) {
//						String result = convertHexToString(hexXorStringsIndex(s, convertStringToHex(crib), i));
//						if (compareRegex(result)) {
//							resultLines[j] += result;
//							possibleLines[k] += crib;
//							i += crib.length();
//						} else {
//							resultLines[j] += " ";
//							possibleLines[k] += " ";
//							i++;
//						}
//
//					}
		// System.out.println(resultLines[j]);
		// System.out.println(possibleLines[k]);
		// }
//			}
		// char[] cribs = new char[lines]
		// Array
		//String 
		Map<Integer, HashMap<Character, Integer>> wordIndexes = new HashMap<>();
		System.out.println("space check:" + convertHexToString(hexXorLines(lines[0], lines[1])));
		//String about = 
		String aboba = hexXorLines(lines[0], lines[2]);
		System.out.println(convertHexToString(aboba));
		StringBuilder result0 = new StringBuilder(" ".repeat(48));
		int[] ind = new int[result0.length()];
		for(int f = 0; f < lines.length; f++) {
			String line = convertHexToString(hexXorLines(lines[0], lines[f]));
			for(int n = 0; n < line.length(); n++) {
				wordIndexes.put(n, new HashMap<>());
				if(checkChar(line.charAt(n))) {
					if(wordIndexes.get(n).containsKey(line.charAt(n))){
						wordIndexes.get(n).put(line.charAt(n), wordIndexes.get(n).get(line.charAt(n))+1);
					} else {
						wordIndexes.get(n).put(line.charAt(n), 1);
					}
					result0.replace(n, n + 1, String.valueOf(Character.toLowerCase(line.charAt(n))));
				}
			}
		}
		for (int i = 0; i < ind.length; i++) {
			for (Entry<Character, Integer> map : wordIndexes.get(i).entrySet()) {
				System.out.println(map.getKey() + " " + map.getValue());
				
			}
		}
		System.out.println(result0.toString());
		resultLines[0] = new StringBuilder();
		possibleLines[1] = new StringBuilder();
		for (int j = 2; j < lines.length; j++) {
			resultLines[j] = new StringBuilder();
			possibleLines[j] = new StringBuilder();
			String crib = " the ";
			//String s = convertHexToString(hexXorLines(lines[0], lines[j]));
			String s = hexXorLines(lines[0], lines[j]);
			// for (String crib : commonUsedWords) {
			int i = 0;
			while (i < s.length() - crib.length() * 2) {
				String result = convertHexToString(hexXorStrings(s, convertStringToHex(i==0? crib.substring(1, crib.length()) : crib), i));
				if (compareRegex(result)) {
					resultLines[j].append(result);
					possibleLines[j].append(crib);
					//resultLines[j].insert(i, result);
					//possibleLines[j].insert(i, crib);
					i += crib.length() * 2;
				} else {
					if(i == s.length() - crib.length() * 2 - 2) {
						resultLines[j].append(" ".repeat(crib.length()*2));
						possibleLines[j].append(" ".repeat(crib.length()*2));
					}else {
					resultLines[j].append(" ");
					possibleLines[j].append(" ");
					}
					//resultLines[j].insert(i, " ");
					//possibleLines[j].insert(i, " ");
					i+=2;
				}
//				System.out.println(i);
//				System.out.println("line: " + resultLines[j]);

			}
			// }
			System.out.println("line: " + resultLines[j]);
			key.append(hexXorLines(lines[0], convertStringToHex(resultLines[j].toString())));
			System.out.println("key: " + key.toString());
			String t2xKey = convertHexToString(hexXorLines(lines[j], key.toString()));
			String t1xKey = convertHexToString(hexXorLines(lines[0], key.toString()));
			String[] t2xDivided = t2xKey.split("\s+");
			String[] t1xDivided = t1xKey.split("\s+");
			// for (String string : t2xDivided) {
			char[] chrs = t2xKey.toCharArray();
//			for (int c = 0; c < chrs.length && c < possibleLines[j].length(); c++) {
//				if (possibleLines[j].toString().charAt(c) != chrs[c]) {
//					// t2xKey = t2xKey.replace(chrs[c], ' ');
//					// t2xKey = t2xKey.replaceFirst(String.valueOf(chrs[c]), " ");
//					String replacement = String.valueOf(resultLines[j].charAt(c));
//					String reverseReplacement = String.valueOf(possibleLines[j].charAt(c));
//					resultLines[j].replace(c, c + 1, reverseReplacement);
//					possibleLines[j].replace(c, c + 1, replacement);
//				}
//			}
			System.out.println("reverse: " + convertHexToString(hexXorLines(lines[j], key.toString())));
			String ky1 = hexXorLines(lines[0], convertStringToHex(resultLines[j].toString()));
			key.replace(i, key.length(), ky1);
			System.out.println("possible: " + possibleLines[j]);
			System.out.println("reverse: " + convertHexToString(hexXorLines(lines[j], key.toString())));
			System.out.println("t2xkey: " + t2xKey);
			System.out.println("resultLines: " + resultLines[j]);
			for (int l = 1; l < lines.length; l++) {
				String lol = convertHexToString(hexXorLines(lines[l], ky1));
				System.out.println("ol: " + lol);
				System.out.println("lines to divide: " + resultLines[j] + "l: " + resultLines[j].length());
				System.out.println("lle: " + (lol));
				int start = -1;
				int end = 0;
				for (int c = 0; c < resultLines[j].length() && c < lol.length(); c++) {
					if (resultLines[j].charAt(c) != ' ' && start == -1) {
						start = c;
					} else if (resultLines[j].charAt(c) == ' ' && start != -1) {

						end = c + 1;
						if(compareRegex(lol.substring(start, end))) {
							 System.out.print(lol.substring(start, end) + "---" + resultLines[j].substring(start, end));

						 }else {
							 
							 System.out.print(lol.substring(start, end) + "---" + resultLines[j].substring(start, end));
							 //resultLines[j].replace(start, end, " ".repeat(end-start));
						 }	
						start = -1;
					}
					//System.out.println("c: " + c);
				}
				System.out.println("new Line " + resultLines[j].toString());
			}
			String newKey = hexXorLines(convertStringToHex(resultLines[j].toString()), lines[0]);
			System.out.println("line 1: " + convertHexToString(hexXorLines(newKey, lines[1])));
			
		}

//		

	}
	
	public boolean checkChar(char character) {
		if(String.valueOf(character).matches("[A-Z]{1}")) {
			return true;
		}
		return false;
		
	}

	public ArrayList<Integer> wordIndexes(String word) {
		ArrayList<Integer> indexes = new ArrayList<>();
		Pattern pattern = Pattern.compile("^\s?[A-Za-z,]+\s?$");
		Matcher matcher = pattern.matcher(word);

		while (!matcher.hitEnd()) {
			matcher.find();
			indexes.add(matcher.start());
			indexes.add(matcher.end());
		}
		System.out.println(Arrays.toString(indexes.toArray()));
		return indexes;
	}

	public boolean compareRegex(String line) {
		Pattern pattern = Pattern.compile("^\s?[A-Za-z]+['\s]?[a-z]*[,]?\s?$");
		// Pattern pattern2 =
		// Pattern.compile("^([A-Z]?[a-z]+-?[a-z]*)|(\\d+[-.,]?\\d*)[.,]?$");
		Matcher matcher = pattern.matcher(line);
		boolean found = matcher.find();
		if (found) {
			wordIndexes.add(matcher.start());
			wordIndexes.add(matcher.end());
		}
		return found;
	}

	public String hexXorStrings(String s1, String crib, int start) {
		String word = "";
		for (int i = start, j = 0; i < start + crib.length(); i++, j++) {
			word += Integer.toHexString((Integer.parseInt(String.valueOf(s1.charAt(i)), 16)
					^ Integer.parseInt(String.valueOf(crib.charAt(j)), 16)));
		}
		return word;
	}

	public String xorHexNew(String a, String b) {
		// TODO: Validation
		char[] chars = new char[a.length()];
		for (int i = 0; i < chars.length; i++) {
			chars[i] = convertCharToHex((char) (Integer.parseInt(String.valueOf(a.charAt(i)), 16)
					^ Integer.parseInt(String.valueOf(b.charAt(i)), 16)));
		}
		return new String(chars);
	}

	public String xorHex(String a, String b) {
		// TODO: Validation
		char[] chars = new char[a.length()];
		for (int i = 0; i < chars.length; i++) {
			chars[i] = toHex(fromHex(a.charAt(i)) ^ fromHex(b.charAt(i)));
		}
		return new String(chars);
	}

	private static int fromHex(char c) {
		if (c >= '0' && c <= '9') {
			return c - '0';
		}
		if (c >= 'A' && c <= 'F') {
			return c - 'A' + 10;
		}
		if (c >= 'a' && c <= 'f') {
			return c - 'a' + 10;
		}
		throw new IllegalArgumentException();
	}

	private char toHex(int nybble) {
		if (nybble < 0 || nybble > 15) {
			throw new IllegalArgumentException();
		}
		return "0123456789ABCDEF".charAt(nybble);
	}

	public String hexXorStrings(String s1, String s2) {
		// System.out.println(s1 + " " + s2);
		String word = "";
		for (int i = 0; i < s1.length(); i++) {
			word += Integer.toHexString((Integer.parseInt(String.valueOf(s1.charAt(i)), 16)
					^ Integer.parseInt(String.valueOf(s2.charAt(i % s2.length())), 16)));
		}
		return word;
	}

	public String hexXorStringsIgnoreSpaces(String s1, String s2) {
		// System.out.println(s1 + " " + s2);
		String s1Hex = convertStringToHex(s1);
		String s2Hex = convertStringToHex(s2);
		String word = "";
		for (int i = 0; i < s1Hex.length() && i < s2Hex.length(); i++) {
			if (s2.charAt(i) == ' ') {
				word += convertStringToHex(" ");
				continue;
			}
			word += Integer.toHexString((Integer.parseInt(String.valueOf(s1.charAt(i)), 16)
					^ Integer.parseInt(String.valueOf(s2.charAt(i % s2.length())), 16)));
		}
		return word;
	}

	public String hexXorLines(String s1, String s2) {
		// System.out.println(s1 + " " + s2);
		String word = "";
		for (int i = 0; i < s1.length() && i < s2.length(); i++) {
			word += Integer.toHexString((Integer.parseInt(String.valueOf(s1.charAt(i)), 16)
					^ Integer.parseInt(String.valueOf(s2.charAt(i)), 16)));
		}
		return word;
	}

	public String xorStrings(String s1, String s2) {
		// System.out.println(s1 + " " + s2);
		String word = "";
		for (int i = 0; i < s1.length(); i++) {
			word += (char) (s1.charAt(i) ^ s2.charAt(i % s2.length()));
		}
		return word;
	}

	public String xorStringsInts(String s1, String s2) {
		// System.out.println(s1 + " " + s2);
		String word = "";
		long i1 = Integer.parseInt(s1, 16);
		long i2 = Integer.parseInt(s2, 16);
		long result = i1 ^ i2;
//		for (int i = 0; i < s1.length(); i++) {
//			word += (char) (s1.charAt(i) ^ s2.charAt(i % s2.length()));
//		}
		return Integer.toHexString((int) result);
	}

	public String byteXorHexLines(String s1, String s2) {
		StringBuilder word = new StringBuilder();
		System.out.println(s1 + " " + s2);
		String[] textPairs = groupUpHex(s1);
		String[] secondTextPairs = groupUpHex(s2);
		int i = 0;
		byte[] a = new byte[textPairs.length];
		byte[] b = new byte[secondTextPairs.length];
		while (i < textPairs.length && i < secondTextPairs.length) {
			// System.out.println(i + " " + i%secondTextPairs.length);
			a[i] = (byte) Integer.parseInt(textPairs[i], 16);
			b[i] = (byte) Integer.parseInt(secondTextPairs[i], 16);
			i++;
		}

		return byteXor(a, b);
	}

	public String byteXor(byte[] arr, byte[] arr2) {
		// byte[] result = new byte[arr.length<arr2.length ? arr.length : arr2.length];
		int[] result = new int[arr.length < arr2.length ? arr.length : arr2.length];
		String word = "";
		int i = 0;
		for (int j = 0; j < result.length; j++) {
			result[i] = arr[i] ^ arr2[i % arr2.length];
			word += Integer.toHexString(result[i]);
			i++;
		}
		return word;
	}

	public String xorHexLines(String s1, String s2) {
		StringBuilder word = new StringBuilder();
		System.out.println(s1 + " " + s2);
		String[] textPairs = groupUpHex(s1);
		String[] crib = groupUpHex(s2);
		int i = 0;
		while (i < textPairs.length && i < crib.length) {
			System.out.println(i + " " + i % crib.length);
			word.append((char) (Integer.parseInt(textPairs[i], 16) ^ Integer.parseInt(crib[i % crib.length], 16)));
			i++;
		}
		return word.toString();
	}

	public String xorHexStrings(String s1, String s2) {
		String word = "";
		System.out.println(s1 + " " + s2);
		String[] textPairs = groupUpHex(s1);
		String[] crib = groupUpHex(s2);
		for (int i = 0; i < textPairs.length; i++) {
			System.out.println(i + " " + i % crib.length);
			word += (char) (Integer.parseInt(textPairs[i], 16) ^ Integer.parseInt(crib[i % crib.length], 16));
		}
		return word;
	}

	public String[] groupUpHex(String hex) {
		String[] pairs = new String[hex.length()];
		for (int i = 0; i < hex.length(); i++) {
			pairs[i] = hex.substring(i, i + 1);
		}
		System.out.println(Arrays.toString(pairs));
		return pairs;
	}

	public static String convertStringToHex(String str) {

		// display in uppercase
		// char[] chars = Hex.encodeHex(str.getBytes(StandardCharsets.UTF_8), false);

		// display in lowercase, default
		char[] chars = Hex.encodeHex(str.getBytes());

		return String.valueOf(chars);
	}

	public static char convertCharToHex(char str) {

		// display in uppercase
		// char[] chars = Hex.encodeHex(str.getBytes(StandardCharsets.UTF_8), false);

		// display in lowercase, default
		char[] chars = Hex.encodeHex(new byte[] { (byte) str });

		return chars[0];
	}

	public static String convertHexToString(String hex) {

		String result = "";
		try {
			byte[] bytes = Hex.decodeHex(hex);
			result = new String(bytes);
		} catch (DecoderException e) {
			throw new IllegalArgumentException("Invalid Hex format!");
		}
		return result;
	}

//	public static String convertStringToHex(String str) {
//
//		StringBuffer hex = new StringBuffer();
//
//		// loop chars one by one
//		for (char temp : str.toCharArray()) {
//
//			// convert char to int, for char `a` decimal 97
//			int decimal = (int) temp;
//
//			// convert int to hex, for decimal 97 hex 61
//			hex.append(Integer.toHexString(decimal));
//		}
//
//		return hex.toString();
//
//	}

	// Hex -> Decimal -> Char
//	public static String convertHexToString(String hex) {
//
//		StringBuilder result = new StringBuilder();
//
//		// split into two chars per loop, hex, 0A, 0B, 0C...
//		for (int i = 0; i < hex.length() - 1; i += 2) {
//
//			String tempInHex = hex.substring(i, (i + 2));
//
//			// convert hex to decimal
//			int decimal = Integer.parseInt(tempInHex, 16);
//
//			// convert the decimal to char
//			result.append((char) decimal);
//
//		}
//
//		return result.toString();
//
//	}

}
