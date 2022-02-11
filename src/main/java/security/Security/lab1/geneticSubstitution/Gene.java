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
		do {
			secondPosition = rand.nextInt(key.length());
		}while (firstPosition == secondPosition);
		char buffer = key.charAt(firstPosition);
		keyChanger.setCharAt(firstPosition, keyChanger.charAt(secondPosition));
		keyChanger.setCharAt(secondPosition, buffer);
		key = keyChanger.toString();
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



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Gene other = (Gene) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		return true;
	}
	
	

}
