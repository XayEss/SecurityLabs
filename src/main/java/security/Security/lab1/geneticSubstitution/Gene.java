package security.Security.lab1.geneticSubstitution;

import java.util.Random;

import security.Security.lab1.EnglishTextAnalyzer;

public class Gene {
	
	private String key = "";
	private double fitness;
	private StringBuilder alphabet;
	
	public Gene() {
		alphabet = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
		genKey();
	}
	
	
	
	public Gene(String key) {
		alphabet = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
		this.key = key;
	}



	public void genKey() {
		Random rand = new Random();
		char next;
		StringBuilder letters = new StringBuilder(alphabet.toString());
		for(int i = 0; i < 26; i++) {
			int index = rand.nextInt(letters.length());
			next = letters.charAt(index);
			key += next;
			letters.deleteCharAt(index);
		}
	}
	
	public String encodeTextGene(String text) {
		StringBuilder resultText = new StringBuilder();
		for (int i = 0; i < text.length(); i++) {
			resultText.append(key.charAt(alphabet.indexOf(String.valueOf(text.charAt(i)))));
		}
		return resultText.toString();

	}
	
	public double calculateFitness(String text) {
		return fitness = EnglishTextAnalyzer.isEnglishTextQuotient(encodeTextGene(text));
	}
	
	public void mutate() {
		Random rand = new Random();
		int firstPosition = rand.nextInt(key.length());
		int potentialSecond = -1;
		do {
		potentialSecond = rand.nextInt(key.length());
		}while (firstPosition == potentialSecond);
		int secondPosition = potentialSecond;
		char buffer = key.charAt(firstPosition);
		key = key.replace(key.charAt(firstPosition), key.charAt(secondPosition));
		key = key.replace(key.charAt(secondPosition), buffer);
	}

	public String getKey() {
		return key;
	}

	public double getFitness() {
		return fitness;
	}
	
	

}
