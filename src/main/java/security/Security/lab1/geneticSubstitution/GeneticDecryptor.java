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

	private int geneCount = 1000;
	private int k = 5;
	private double p = 0.75;
	private int tournamentSize = 20;	
	private double mutationChance = 0.2;
	private double crossoverChance = 0.65;
	private double elitism = 0.15;
	private int numberOfGenerations = 400;
	private Gene[] genes;
	private List<Gene> aviableGenes;
	private Gene[] bestGenes;
	private String encryptedText = "";
	private Random rand;

	public GeneticDecryptor() {
		rand = new Random();
	}

	public void generateGenes(int amount, String seed) {
		// seed = "ETAOINSHRDLUCMFWYPVBGKJQXZ";
		genes = new Gene[geneCount];
		for (int i = 0; i < amount; i++) {
			genes[i] = new Gene(seed);
			genes[i].mutate();
			genes[i].mutate();
			// genes[i].mutate();
		}
	}
	
	public void generateGenes() {
		genes = new Gene[geneCount];
		for (int i = 0; i < geneCount; i++) {
			genes[i] = new Gene();
		}
	}

	public void decryptText(String text) {
		encryptedText = text;
		generateGenes();
		List<Double> fitArr = Arrays.stream(genes).map(g -> g.calculateFitness(encryptedText))
				.collect(Collectors.toList());
		double sum = 0;
		for (Double fit : fitArr) {
			sum += fit;
		}
		System.out.println(sum / fitArr.size());
		// calculateFitnessAndSort();
		// printResults();
		for (int g = 0; g < numberOfGenerations; g++) {
			System.out.println("cal fit");
			calculateFitnessAndSort();
			System.out.println("save best");
			saveBestGenes();
			System.out.println("crossover");
			crossover();
			System.out.println("mutate");
			mutation();
			System.out.println("elitism");
			calculateFitnessAndSort();
			elitism();
			System.out.println("end of generation: " + g + " genes size: " + genes.length);
		}
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
		sortGenes(genes);
	}

	public void sortGenes(Gene[] unsortedGenes) {
		Arrays.sort(unsortedGenes, (g1, g2) -> Double.compare(g1.getFitness(), g2.getFitness()));
	}

	public Gene tournament() {
		Gene[] contestOrder = new Gene[tournamentSize];												
		int individualIndex;
		Gene potentialGene;
		Gene chosenGene = null;
		// long start = System.nanoTime();
		aviableGenes = new LinkedList<>(Arrays.asList(genes));
		for (int i = 0; i < contestOrder.length; i++) {

			individualIndex = rand.nextInt(aviableGenes.size());
			potentialGene = aviableGenes.get(individualIndex);
			aviableGenes.remove(potentialGene);
			contestOrder[i] = potentialGene;
		}
		// usedGenes.addAll(Arrays.asList(contestOrder));
		// System.out.println(Arrays.toString(contestOrder));
		sortGenes(contestOrder);
		double chance;
		double probability;
		for (int i = 0; i < contestOrder.length; i++) {
			chance = rand.nextDouble();
			probability = p * Math.pow(1 - p, i);
			if (chance <= probability) {
				chosenGene = contestOrder[i];
				break;
			} else if (i == contestOrder.length - 1) {
				chosenGene = contestOrder[contestOrder.length / 2];
			}

		}
		return chosenGene;

	}

	public void crossover() {
		int size = genes.length;
		Gene[] childrens = new Gene[size % 2 == 0 ? size : size - 1];
		Gene firstChild;
		Gene secondChild;
		Gene firstParent;
		Gene secondParent;
		double isCrossover = rand.nextDouble();
		if (isCrossover < crossoverChance) {
			for (int i = 0; i < childrens.length; i += 2) {
				List<Gene> childrenList = Arrays.asList(childrens);
				do {
					do {
						firstParent = tournament();
						secondParent = tournament();
					} while (firstParent.checkParent(secondParent));
					firstChild = crossGenesPBX(firstParent, secondParent);
					secondChild = crossGenesPBX(secondParent, firstParent);
				} while (childrenList.contains(secondParent) || childrenList.contains(firstParent));
				childrens[i] = firstChild;
				childrens[i + 1] = secondChild;
				aviableGenes.clear();
			}
			genes = childrens;
		}
		// usedGenes.clear();
		// System.out.println(genes.length);
	}

	public Gene crossGenes(Gene parent1, Gene parent2) { // refactor this method
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

	public Gene crossGenesPBX(Gene parent1, Gene parent2) { 
		int numberOfChromosomes = rand.nextInt(k) + 1;
		// System.out.println(numberOfChromosomes);
		StringBuilder parent1Key = new StringBuilder(parent1.getKey());
		StringBuilder parent2Key = new StringBuilder(parent2.getKey());
		StringBuilder childKey = new StringBuilder(" ".repeat(parent1Key.length()));
		int position;
		char next;
		for (int i = 0; i < numberOfChromosomes; i++) {
			do {
				position = rand.nextInt(parent1Key.length());
			} while (childKey.indexOf(String.valueOf(parent1Key.charAt(position))) != -1);
			next = parent1Key.charAt(position);
			parent2Key.deleteCharAt(parent2Key.indexOf(String.valueOf(next)));
			childKey.setCharAt(position, next);
		}
		int charIndex = 0;
		// System.out.println("child: " + childKey.toString());
		// System.out.println("parent2: " + parent2Key.toString());
		for (int i = 0; i < childKey.length(); i++) {
			if (childKey.charAt(i) == ' ') {
				childKey.setCharAt(i, parent2Key.charAt(charIndex++));
			}
		}
		return new Gene(childKey.toString(), parent1, parent2);
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
		int amountOfElitism = (int) Math.floor(genes.length * elitism);
		bestGenes = Arrays.copyOf(genes, amountOfElitism);
	}

	public void elitism() {
//		Gene[] newGenes = Arrays.copyOf(genes, genes.length + bestGenes.length);
//		System.arraycopy(bestGenes, 0, newGenes, genes.length, bestGenes.length);
		Gene[] newGenes = Arrays.copyOf(bestGenes, genes.length);
		System.arraycopy(genes, 0, newGenes, bestGenes.length, genes.length - bestGenes.length);
		genes = newGenes;
		newGenes = null;
	}

	public double calculateAverage() {
		List<Double> fitArr = Arrays.stream(genes).map(g -> g.calculateFitness(encryptedText))
				.collect(Collectors.toList());
		double sum = 0;
		for (Double fit : fitArr) {
			sum += fit;
		}
		return sum / fitArr.size();
	}

	public void printResults() {
		String decoded = "";
		for (int i = 0; i < 100; i++) {
			decoded = genes[i].decodeTextGene(encryptedText);
			System.out.println("answer: " + decoded);
			System.out.println("key: " + genes[i].getKey());
			System.out.println(genes[i].calculateFitness(encryptedText));
		}
	}

}
