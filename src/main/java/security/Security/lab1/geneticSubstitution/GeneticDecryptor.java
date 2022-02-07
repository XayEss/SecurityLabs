package security.Security.lab1.geneticSubstitution;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

import security.Security.lab1.EnglishTextAnalyzer;

public class GeneticDecryptor {

	private int geneCount = 1000;
	private int k = 18;
	private Gene[] genes;
	private String encryptedText = "";

	public void decryptText(String text) {
		encryptedText = text;
		genes = new Gene[geneCount];
		for (int i = 0; i < geneCount; i++) {
			genes[i] = new Gene();
		}
		tournament();
	}

	public void tournament() {
		double p = 0.75;
		Random rand = new Random();
		Gene[] contestOrder = new Gene[genes.length];
		Arrays.stream(genes).forEach(g -> g.calculateFitness(encryptedText));
		Arrays.sort(genes, (g1, g2) -> (int) (g1.getFitness() - g2.getFitness()));
//		int contestIndex = 0;
//		double chance;
//		double probability;
//		for (int i = 0; i < genes.length; i++) {
//			chance = rand.nextDouble();
//			probability = p * Math.pow(1 - p, contestIndex);
//			System.out.println(chance + " " + probability);
//			if(chance<=probability) {
//				contestOrder[contestIndex++] = genes[i];
//				System.out.println("after: " + contestIndex);
//			}
//			
//		}
//		Arrays.stream(contestOrder).forEach(g -> System.out.println(g.getKey()));
	}
	
	public void crossover() {
		Gene[] childrens = new Gene[geneCount];
		for(int i = 0; i < genes.length - 1; i += 2) {
			if(i % 2 != 0) {
				childrens[i] = crossGenes(genes[i], genes[i+1]);
			} else {
				childrens[i + 1] = crossGenes(genes[i+1], genes[i]);
			}
		}
	}
	
	private Gene crossGenes(Gene parent1, Gene parent2) {
		Random rand = new Random();
		int numberOfChromosomes = rand.nextInt(k);
		StringBuilder childKey = new StringBuilder(parent2.getKey());
		String parent1Key = parent1.getKey();
		int position;
		for (int i = 0; i < numberOfChromosomes; i++) {
			position = rand.nextInt(parent1Key.length());
			childKey.setCharAt(position, parent1Key.charAt(position));
		}
		return new Gene(childKey.toString());
	}

}
