package security.Security.lab1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.plaf.synth.SynthOptionPaneUI;

public class NGramTextAnalyzer {
	private static Map<String, Double> nGramFrequencies;

	public static void insertNGramFrequencies() {
		nGramFrequencies = new HashMap<>();
		try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/bigrams.txt"))) {
			String line;
			String[] split;
			while ((line = reader.readLine()) != null) {
				split = line.split(" ");
				nGramFrequencies.put(split[0], Double.valueOf(split[1]));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		for (Map.Entry<String, Double>  entry: nGramFrequencies.entrySet()) {
//			System.out.println(entry.getKey() + " " + entry.getValue());
//		}
	}

	public static double analyzeText(String text) {
		if(nGramFrequencies == null) {
			insertNGramFrequencies();
			System.out.println("lol null");
		}
		text = text.toLowerCase();
		Map<String, Double> nGrams = new HashMap<>();
		int firstPivot = 0;
		int secondPivot = 0;
		int step = 2;
		secondPivot += step;
		int numberOfnGrams = 0;
		String ngram;
		while (secondPivot != text.length()) {
			ngram = text.substring(firstPivot, secondPivot);
			if (nGramFrequencies.containsKey(ngram) && nGrams.putIfAbsent(ngram, 1d) != null) {
				nGrams.put(ngram, nGrams.get(ngram) + 1);
				
			}
			//System.out.println();
			numberOfnGrams += 1;
			secondPivot += 1;
			firstPivot += 1;
		}
		for (Map.Entry<String, Double>  entry : nGrams.entrySet()) {
			entry.setValue((entry.getValue()/numberOfnGrams) * 100);
		}
		return calculateDistance(nGrams);
	}
	
	private static double calculateDistance(Map<String, Double> nGrams) {
		double distance = 0;
		for (Map.Entry<String, Double>  entry : nGrams.entrySet()) {
				distance += Math.abs(nGramFrequencies.get(entry.getKey()) - entry.getValue());
		}
		return distance;
	}
}
