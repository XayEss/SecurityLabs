package security.Security.lab1.geneticSubstitution;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.stream.Collectors;

import security.Security.lab1.EnglishTextAnalyzer;

public class GeneticDecryptor {

	private int geneCount = 1000;
	private int k = 6;
	private double mutationChance = 0.20;
	private int numberOfGenerations = 500;
	private Gene[] genes;
	private String encryptedText = "";
	private Random rand;

	public void decryptText(String text) {
		rand = new Random();
		encryptedText = text;
		genes = new Gene[geneCount];
		for (int i = 0; i < geneCount; i++) {
			genes[i] = new Gene();
		}
		for (int g = 0; g < numberOfGenerations; g++) {
			tournament();
			crossover();
			mutation();
		}
		tournament();
		printResults();
	}

	public void tournament() {
		double p = 0.75;
		Gene[] contestOrder = new Gene[genes.length];
		Arrays.stream(genes).forEach(g -> g.calculateFitness(encryptedText));
		Arrays.sort(genes, (g1, g2) -> Double.compare(g1.getFitness(), g2.getFitness()));
		//Arrays.stream(genes).map((g) -> g.getFitness()).forEach(System.out::println);
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
		//int g = 0;
		for (int i = 0; i < genes.length - 1; i+=2 ) {
			//if (i % 2 == 0) {
				childrens[i / 2] = crossGenes(genes[i], genes[i + 1]);
			//} else {
				childrens[i / 2 + 1] = crossGenes(genes[i + 1], genes[i]);
			//}
		}
		genes = childrens;
		System.out.println(genes.length);
	}

	public Gene crossGenes(Gene parent1, Gene parent2) {
		int numberOfChromosomes = rand.nextInt(k);
		String parent1Key = parent1.getKey();
		String parent2Key = parent2.getKey();
		StringBuilder childKey = new StringBuilder(" ".repeat(parent1Key.length()));
		int position;
		for (int i = 0; i < numberOfChromosomes; i++) {
			do {
			position = rand.nextInt(parent1Key.length());
			}while(childKey.indexOf(parent2Key.substring(position, position+1)) != -1);
			childKey.setCharAt(position, parent1Key.charAt(position));
		}
		char substitution;
		for(int i = 0; i < childKey.length(); i++) {
			substitution = parent2Key.charAt(i);
			String substitutionString = String.valueOf(substitution);
			if(childKey.charAt(i) == ' ' && childKey.indexOf(substitutionString) == -1) { // ==Refactor so that elifs conditions are in the beginning
				childKey.setCharAt(i, substitution);
			} else {
				int index = i;
				while(childKey.indexOf(substitutionString) != -1) {
					index++;
					if(index == 26) {
						index=0;
					}
					substitution = parent2Key.charAt(index);
					substitutionString = String.valueOf(substitution);
				}
				int insertIndex = i;
				while(childKey.charAt(insertIndex) != ' ') {
					insertIndex++;
					if(index == 26) {
						index = 0;
					}
				}
				childKey.setCharAt(insertIndex, substitution);
			}
		}
		return new Gene(childKey.toString());
	}

	public void mutation() {
		for (Gene gene : genes) {
			if (rand.nextDouble() < mutationChance) {
				gene.mutate();
			}
		}
	}
	
	public void printResults() {
		String decoded = "";
		for (int i = 0; i < 20; i++) {
			decoded = genes[i].decodeTextGene(encryptedText);
			System.out.println(decoded);
		}
	}

}
