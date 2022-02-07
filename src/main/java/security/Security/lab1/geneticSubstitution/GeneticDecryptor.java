package security.Security.lab1.geneticSubstitution;

import java.util.Arrays;
import java.util.Comparator;

import security.Security.lab1.EnglishTextAnalyzer;

public class GeneticDecryptor {
	
	private int geneCount = 1000;
	private Gene[] genes;
	private String encryptedText = "";
	
	
	
	public void decryptText(String text) {
		encryptedText = text;
		for (int i = 0; i < geneCount; i++) {
			genes[i] = new Gene();
		}
	}
	
	public void tournament() {
		double p = 0.36;
		double[] fitness = new double[genes.length];
		Arrays.stream(genes).forEach(g -> g.calculateFitness(encryptedText));
		Arrays.sort(genes, (g1, g2) -> (int)(g1.getFitness() - g2.getFitness()));
		for(int i = 0; i < genes.length; i++) {
				fitness[i] = EnglishTextAnalyzer.isEnglishTextQuotient(genes[i].encodeTextGene(encryptedText)); 
				double probability = p * Math.pow(1-p, i);	
		}
		
		
	}

}
