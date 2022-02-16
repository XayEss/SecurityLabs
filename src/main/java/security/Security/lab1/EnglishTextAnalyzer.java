package security.Security.lab1;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collector;

public class EnglishTextAnalyzer {
	private static Map<Character, Double> letterFrequency;
	private static final double eProbability = 12.813865;
	private static final double tProbability = 9.1357551;
	private static final double aProbability = 8.2389258;
	private static final double oProbability = 7.5731132;
	private static final double iProbability = 6.1476691;
	private static final double nProbability = 6.8084376;
	private static final double sProbability = 6.3827211;
	private static final double hProbability = 6.1476691;
	private static final double rProbability = 6.0397268;
	private static final double dProbability = 4.2904556;
	private static final double lProbability = 4.0604477;
	private static final double uProbability = 2.7822893;
	private static final double maxThreshold = 1.8;
	private static final double minThreshold = 0.5;

	private static final String lettersByFrequency = "etaoinshrdlucmfwypvbgkjqxz";
	// private static final double[] letterFrequencies = new double[] {12.813865,
	// 9.1357551, 8.2389258, 7.5731132, 6.1476691, 6.8084376, 6.3827211, 6.1476691,
	// 6.0397268, 4.2904556, 4.0604477, 2.7822893, 2.8, 2.5, 2.2, 2.4, 2, 1.9, 0.98,
	// 1.5, 2, 0.77, 0.15, 0.095, 0.15, 0.074 };
	private static final double[] letterFrequencies = new double[] { 12.813865, 9.1357551, 8.2389258, 7.5731132,
			6.1476691, 6.8084376, 6.3827211, 6.1476691, 6.0397268, 4.2904556, 4.0604477, 2.7822893, 2.8065007,
			2.4271893, 2.2476217, 2.3807842, 1.9913847, 1.9459884, 0.9866131, 1.5051398, 2.0327458, 0.7787989,
			0.1543474, 0.0958366, 0.1513210, 0.0746517 };

	{
		addFrequencies();
	}

	public static boolean isEnglishText(String text) {
		boolean isEnglish = false;
		double length = text.length();
		long letterEAmount = text.chars().map(Character::toLowerCase).filter(ch -> ch == 'e').count();
		long letterTAmount = text.chars().map(Character::toLowerCase).filter(ch -> ch == 't').count();
		long letterAAmount = text.chars().map(Character::toLowerCase).filter(ch -> ch == 'a').count();
		long letterOAmount = text.chars().map(Character::toLowerCase).filter(ch -> ch == 'o').count();
		double e = letterEAmount / length * 100;
		double t = letterTAmount / length * 100;
		double a = letterAAmount / length * 100;
		double o = letterOAmount / length * 100;
		if (isInThreshold(e, eProbability, minThreshold) || isInThreshold(t, tProbability, minThreshold)
				|| isInThreshold(a, aProbability, minThreshold) || isInThreshold(o, oProbability, minThreshold)) {
			isEnglish = true;
		} else {
			int amountPassed = 0;
			if (isInThreshold(e, eProbability, maxThreshold)) {
				amountPassed++;
			}
			if (isInThreshold(t, tProbability, maxThreshold)) {
				amountPassed++;
			}
			if (isInThreshold(o, oProbability, maxThreshold)) {
				amountPassed++;
			}
			if (isInThreshold(a, aProbability, maxThreshold)) {
				amountPassed++;
			}
			if (amountPassed > 3) {
				isEnglish = true;
			}
		}

		// System.out.println(e + " " + t + " " + a + " " + o + " ");
		return isEnglish;
	}

	public static void addFrequencies() {
		letterFrequency = new HashMap<>();
		for (int i = 0; i < letterFrequencies.length; i++) {
			letterFrequency.put(lettersByFrequency.charAt(i), letterFrequencies[i]);
		}
	}

	public static boolean isEnglishTextRating(String text) {
		boolean isEnglish = false;
		double rating = 0;
		double length = text.length();
		long letterEAmount = text.chars().map(Character::toLowerCase).filter(ch -> ch == 'e').count();
		long letterTAmount = text.chars().map(Character::toLowerCase).filter(ch -> ch == 't').count();
		long letterAAmount = text.chars().map(Character::toLowerCase).filter(ch -> ch == 'a').count();
		long letterOAmount = text.chars().map(Character::toLowerCase).filter(ch -> ch == 'o').count();
		double e = letterEAmount / length * 100;
		double t = letterTAmount / length * 100;
		double a = letterAAmount / length * 100;
		double o = letterOAmount / length * 100;
		if (isInThreshold(e, eProbability, minThreshold) || isInThreshold(t, tProbability, minThreshold)
				|| isInThreshold(a, aProbability, minThreshold) || isInThreshold(o, oProbability, minThreshold)) {
			rating += 40;
		} else {
			if (isInThreshold(e, eProbability, maxThreshold)) {
				rating += 12;
			}
			if (isInThreshold(t, tProbability, maxThreshold)) {
				rating += 12;
			}
			if (isInThreshold(o, oProbability, maxThreshold)) {
				rating += 12;
			}
			if (isInThreshold(a, aProbability, maxThreshold)) {
				rating += 12;
			}
		}
		if (e >= t && t >= a && a >= o) {
			rating += 35;
		}
		if (text.contains("the") || text.contains("The")) {
			rating += 15;
		}
		if (text.contains("an") || text.contains("An")) {
			rating += 15;
		}
		if (rating > 50) {
			isEnglish = true;
		}
//		System.out.println(letterEAmount + " " + letterTAmount + " " + letterAAmount + " " + letterOAmount + " ");
//		System.out.println(e + " " + t + " " + a + " " + o + " ");
//		System.out.println(rating);
		return isEnglish;
	}

	public static double isEnglishTextQuotient(String text) {
		double length = text.length();
		double[] frequencies = new double[] { eProbability, tProbability, aProbability, oProbability, iProbability,
				nProbability, sProbability, hProbability, rProbability, dProbability, lProbability, uProbability };
		// Map<Character, Double> letterFrequencies = new HashMap<>() {'e' :
		// eProbability};
		long letterEAmount = text.chars().map(Character::toLowerCase).filter(ch -> ch == 'e').count();
		long letterTAmount = text.chars().map(Character::toLowerCase).filter(ch -> ch == 't').count();
		long letterAAmount = text.chars().map(Character::toLowerCase).filter(ch -> ch == 'a').count();
		long letterOAmount = text.chars().map(Character::toLowerCase).filter(ch -> ch == 'o').count();
		long letterIAmount = text.chars().map(Character::toLowerCase).filter(ch -> ch == 'i').count();
		long letterNAmount = text.chars().map(Character::toLowerCase).filter(ch -> ch == 'n').count();
		long letterSAmount = text.chars().map(Character::toLowerCase).filter(ch -> ch == 's').count();
		long letterHAmount = text.chars().map(Character::toLowerCase).filter(ch -> ch == 'h').count();
		long letterRAmount = text.chars().map(Character::toLowerCase).filter(ch -> ch == 'r').count();
		long letterDAmount = text.chars().map(Character::toLowerCase).filter(ch -> ch == 'd').count();
		long letterLAmount = text.chars().map(Character::toLowerCase).filter(ch -> ch == 'l').count();
		long letterUAmount = text.chars().map(Character::toLowerCase).filter(ch -> ch == 'u').count();
		double e = letterEAmount / length * 100;
		double t = letterTAmount / length * 100;
		double a = letterAAmount / length * 100;
		double o = letterOAmount / length * 100;
		double i = letterIAmount / length * 100;
		double n = letterNAmount / length * 100;
		double s = letterSAmount / length * 100;
		double h = letterHAmount / length * 100;
		double r = letterRAmount / length * 100;
		double d = letterDAmount / length * 100;
		double l = letterLAmount / length * 100;
		double u = letterUAmount / length * 100;
		return fittingQuotient(new double[] { e, t, a, o, i, n, s, h, r, d, l, u }, frequencies);
	}

	public static double isEnglishTextQuotientV2(String text) {
		if (letterFrequency == null || letterFrequency.isEmpty()) {
			addFrequencies();
		}
		Map<Character, Double> charToFreq = new HashMap<>();
		char[] characters = new char[lettersByFrequency.length()];
		double[] frequencies = new double[characters.length];
		double[] frequenciesNominal = new double[characters.length];
		text = text.toLowerCase();
		for (char c : lettersByFrequency.toCharArray()) {
			charToFreq.putIfAbsent(c, (double) text.chars().filter(ch -> ch == c).count());
		}
		int in = 0;
		for (Map.Entry<Character, Double> entry : charToFreq.entrySet()) {
			entry.setValue((entry.getValue() / text.length()) * 100);
		}
		return fittingQuotient(charToFreq);
	}

	public static double fittingQuotient(Map<Character, Double> charToFrequency) {
		double quotient = 0;
		for (Map.Entry<Character, Double> entry : charToFrequency.entrySet()) {
			quotient += Math.abs(letterFrequency.get(entry.getKey()) - entry.getValue());
		}
		return quotient / 26;

	}

	public static double fittingQuotient(double[] frequency, double[] nominalFrequency) {
		double quotient = 0;
		for (int i = 0; i < frequency.length; i++) {
			// quotient += nominalFrequency[i] - frequency[i];
			quotient += Math.abs(nominalFrequency[i] - frequency[i]);
		}
		return quotient / 26;

	}

	public static void createDictionary() {
		letterFrequency.put('e', 12.813865);
		letterFrequency.put('t', 9.1357551);
		letterFrequency.put('a', 8.2389258);
		letterFrequency.put('o', 7.5731132);
		letterFrequency.put('i', 6.1476691);
		letterFrequency.put('n', 6.8084376);
		letterFrequency.put('s', 6.3827211);
		letterFrequency.put('r', 6.0397268);

	}

	public static boolean isInThreshold(double occurance, double probability, double threshold) {
		boolean inThreshold = false;
		if (occurance > probability - threshold && occurance < probability + threshold) {
			inThreshold = true;
		}
		return inThreshold;
	}

}
