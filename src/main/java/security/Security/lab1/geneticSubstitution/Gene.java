package security.Security.lab1.geneticSubstitution;

import java.util.Random;

import security.Security.lab1.EnglishTextAnalyzer;

public class Gene {
	
	private String key = "";
	private double fitness;
	private static final StringBuilder alphabet = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
	
	public Gene() {
		genKey();
	}
	
	
	
	public Gene(String key) {
		this.key = key;
	}



	public void genKey() {
		Random rand = new Random();
		char next;
		StringBuilder letters = new StringBuilder(alphabet);
		StringBuilder keyBuilder = new StringBuilder();
		for(int i = 0; i < 26; i++) {
			int index = rand.nextInt(letters.length());
			next = letters.charAt(index);
			keyBuilder.append(next);
			letters.deleteCharAt(index);
		}
		key = keyBuilder.toString();
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
		int secondPosition = -1;
		StringBuilder keyChanger = new StringBuilder(key);
		//System.out.println("bef: " + key);
		do {
			secondPosition = rand.nextInt(key.length());
		}while (firstPosition == secondPosition);
		char buffer = key.charAt(firstPosition);
		keyChanger.setCharAt(firstPosition, keyChanger.charAt(secondPosition));
		keyChanger.setCharAt(secondPosition, buffer);
		key = keyChanger.toString();
		//System.out.println("aft: " + key);
	}

	public String getKey() {
		return key;
	}

	public double getFitness() {
		return fitness;
	}
	
	public String decodeTextGene(String text) {
		StringBuilder resultText = new StringBuilder();
		for (int i = 0; i < text.length(); i++) {
			resultText.append(alphabet.charAt(key.indexOf(String.valueOf(text.charAt(i)))));
		}
		return resultText.toString();
	}

}
