package security.Security.lab1.geneticSubstitution;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.stream.Collectors;

import security.Security.lab1.EnglishTextAnalyzer;

public class GeneticDecryptor {

	private int geneCount = 10;
	private int k = 5;
	private double p = 0.75;
	private int tournamentSize = 20;
	private double mutationChance = 0.20;
	private double crossoverChance = 1.65;
	private double elitism = 0.2;
	private int numberOfGenerations = 2;
	private Gene[] genes;
	private Gene[] bestGenes;
	private String encryptedText = "";
	private Random rand;

	public GeneticDecryptor() {
		rand = new Random();

	}

	public void decryptText(String text) {
		encryptedText = text;
		genes = new Gene[geneCount];
		for (int i = 0; i < geneCount; i++) {
			genes[i] = new Gene();
		}
		List<Double> fitArr = Arrays.stream(genes).map(g -> g.calculateFitness(encryptedText))
				.collect(Collectors.toList());
		double sum = 0;
		for (Double fit : fitArr) {
			sum += fit;
		}
		System.out.println(sum / fitArr.size());
		printResults();
		for (int g = 0; g < numberOfGenerations; g++) {
			calculateFitnessAndSort();
			saveBestGenes();
			//tournament();
			crossover();
			mutation();
			elitism();
		}
		//tournament();
		System.out.println("-------------------------------------");
		calculateFitnessAndSort();
		printResults();
		fitArr = Arrays.stream(genes).map(g -> g.calculateFitness(encryptedText)).limit(100)
				.collect(Collectors.toList());
		sum = 0;
		for (Double fit : fitArr) {
			sum += fit;
		}
		System.out.println(sum / fitArr.size());
	}
	
	public void calculateFitnessAndSort() {
		Arrays.stream(genes).forEach(g -> g.calculateFitness(encryptedText));
		Arrays.sort(genes, (g1, g2) -> Double.compare(g1.getFitness(), g2.getFitness()));
	}
	
	public void sortGenes(Gene[] unsortedGenes) {
		Arrays.sort(unsortedGenes, (g1, g2) -> Double.compare(g1.getFitness(), g2.getFitness()));
	}

	public Gene tournament() {	
		Gene[] contestOrder = new Gene[tournamentSize];
		int individualIndex;
		Gene chosenGene = null;
		for (int i = 0; i < contestOrder.length; i++) {
			do {
				individualIndex = rand.nextInt(genes.length);
			}while(Arrays.asList(contestOrder).contains(genes[individualIndex]));
		}
		sortGenes(contestOrder);
		double chance;
		double probability;
		for (int i = 0; i < genes.length; i++) {
			chance = rand.nextDouble();
			probability = p * Math.pow(1 - p, i);
			if(chance<=probability) {
				chosenGene = contestOrder[i];
				break;
			}
			
		}
		return chosenGene;
		// Arrays.stream(genes).map((g) -> g.getFitness()).forEach(System.out::println);
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

	public void crossover() { // make that if genes are odd the last one will be removed and size truncated
		Gene[] childrens = new Gene[genes.length];
		Gene firstChild;
		Gene secondChild;
		for (int i = 0; i < genes.length - 1; i += 2) {
			if (rand.nextDouble() < crossoverChance) {
				firstChild = crossGenes(genes[i], genes[i + 1]);
				secondChild = crossGenes(genes[i + 1], genes[i]);
			} else {
				firstChild = genes[i];
				secondChild = genes[i+1];
			}
			childrens[i] = firstChild;
			childrens[i + 1] = secondChild;

		}
		genes = childrens;
		// System.out.println(genes.length);
	}

	public Gene crossGenes(Gene parent1, Gene parent2) {
		int numberOfChromosomes = rand.nextInt(k) + 1;
		// System.out.println(numberOfChromosomes);
		String parent1Key = parent1.getKey();
		String parent2Key = parent2.getKey();
		StringBuilder childKey = new StringBuilder(" ".repeat(parent1Key.length()));
		int position;
		Queue<Character> missingChars = new LinkedList<>();
		for (int i = 0; i < numberOfChromosomes; i++) {
			do {
				position = rand.nextInt(parent1Key.length());
			} while (childKey.indexOf(String.valueOf(parent1Key.charAt(position))) != -1);
			// System.out.println(position);
			childKey.setCharAt(position, parent1Key.charAt(position));
		}
		char substitution;
		for (int i = 0; i < childKey.length(); i++) {
			substitution = parent2Key.charAt(i);
			String substitutionString = String.valueOf(substitution);
			if (childKey.indexOf(substitutionString) == -1) {
				if (childKey.charAt(i) == ' ') {
					childKey.setCharAt(i, substitution);
				} else {
					missingChars.add(substitution);
				}
			} else {

			}
		}
		for (int i = 0; i < childKey.length(); i++) {
			if (childKey.charAt(i) == ' ') {
				childKey.setCharAt(i, missingChars.poll());
			}
		}
		return new Gene(childKey.toString());
	}

	public Gene crossGenesv2(Gene parent1, Gene parent2) {
		String parent1Key = parent1.getKey();
		String parent2Key = parent2.getKey();
		StringBuilder childKey = new StringBuilder();
		for (int i = 0; i < parent1Key.length(); i++) {
			if (i < parent1Key.length() / 2) {
				childKey.append(parent1Key.charAt(i));
			} else {
				int index2 = i;
				while (childKey.indexOf(String.valueOf(parent2Key.charAt(index2))) != -1) {
					index2++;
					if (index2 == 26) {
						index2 = 0;
					}
				}
				childKey.append(parent2Key.charAt(index2));
			}
			// System.out.println(childKey.toString());
		}
		return new Gene(childKey.toString());
	}

	public void mutation() {
		// System.out.println(Arrays.toString(null));
		for (Gene gene : genes) {
			if (rand.nextDouble() < mutationChance) {
				gene.mutate();
			}
		}
	}
	
	public void saveBestGenes() {
		int amountOfElitism = (int)Math.floor(genes.length * elitism);
		bestGenes = Arrays.copyOf(genes, amountOfElitism);
	}
	
	public void elitism() {
		Gene[] newGenes = Arrays.copyOf(genes, genes.length + bestGenes.length);
		System.arraycopy(bestGenes, 0, newGenes, genes.length, bestGenes.length);
		genes = newGenes;
	}

	public void printResults() {
		String decoded = "";
		for (int i = 0; i < genes.length; i++) {
			decoded = genes[i].decodeTextGene(encryptedText);
			System.out.println("answer: " + decoded);
			System.out.println(genes[i].getFitness());
		}
	}

}
