package komalo.genetic_algorithms;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class CanonicalGeneticAlgorithm<SolutionType, GeneType> {

	private List<Chromosome<GeneType>> population = new ArrayList<>();

	private ChromosomeCodec<SolutionType, GeneType> chromosomeCodec;

	private GeneMutator<GeneType> geneMutator;

	private FitnessFunction<SolutionType> fitnessFunction;

	private int iterationsNo;

	private final double mutationProbability;

	private final double crossOverProbability;

	private final double populationNo;

	private Random randomGenerator = new Random();

	public CanonicalGeneticAlgorithm(double populationNo, int iterationsNo,
			double mutationProbability, double crossOverProbability,
			FitnessFunction<SolutionType> fitnessFunction,
			ChromosomeCodec<SolutionType, GeneType> chromosomeCodec,
			GeneMutator<GeneType> chromosomeMutator) {

		this.populationNo = populationNo;
		this.iterationsNo = iterationsNo;
		this.fitnessFunction = fitnessFunction;
		this.mutationProbability = mutationProbability;
		this.crossOverProbability = crossOverProbability;
		this.chromosomeCodec = chromosomeCodec;
		this.geneMutator = chromosomeMutator;
	}

	public void solve() {
		population.clear();

		generateRandomPopulation();

		for (int i = 0; i < iterationsNo; i++) {
			ChromosomePair<GeneType> parents = selectParents();

			ChromosomePair<GeneType> offSprings = crossover(parents);

			mutateChromosomeInPlace(offSprings.chromosome1);

			mutateChromosomeInPlace(offSprings.chromosome2);

			replaceInPopulation(parents, offSprings);
		}
	}

	private ChromosomePair<GeneType> selectParents() {
		List<Integer> cummulativeFitness = new LinkedList<>();
		List<Chromosome<GeneType>> chromosomes = new LinkedList<>();

		int lastFitness = 0;

		for (Chromosome<GeneType> chromosome : population) {
			SolutionType decoded = chromosomeCodec.decode(chromosome);
			int fitness = fitnessFunction.evaluate(decoded);

			cummulativeFitness.add(fitness + lastFitness);
			chromosomes.add(chromosome);
		}

		Chromosome<GeneType> parent1 = selectRandomParent(cummulativeFitness,
				chromosomes);

		Chromosome<GeneType> parent2 = parent1;

		while (parent1 == parent2)
			parent2 = selectRandomParent(cummulativeFitness, chromosomes);

		return new ChromosomePair<>(parent1, parent2);
	}

	private Chromosome<GeneType> selectRandomParent(
			List<Integer> cummulativeFitness,
			List<Chromosome<GeneType>> chromosomes) {

		int totalFitness = cummulativeFitness
				.get(cummulativeFitness.size() - 1);

		int randomNumber = randomGenerator.nextInt(totalFitness);

		for (int i = 0; i < cummulativeFitness.size() - 1; i++) {
			if (randomNumber < cummulativeFitness.get(i + 1)) {
				return chromosomes.get(i);
			}
		}

		throw new RuntimeException(
				"could not select a valid parent from the fitness list");
	}

	private ChromosomePair<GeneType> crossover(ChromosomePair<GeneType> parents) {
		int chromosomLength = parents.chromosome1.length();
		int randomCrossoverPoint = randomGenerator.nextInt(chromosomLength);

		Chromosome<GeneType> offSpring1 = new Chromosome<GeneType>(chromosomLength);
		for (int i = 0; i < randomCrossoverPoint; i++){
			offSpring1.setGeneAt(i, parents.chromosome1.getGeneAt(i));
		}

		for (int i = randomCrossoverPoint; i < chromosomLength; i++){
			offSpring1.setGeneAt(i, parents.chromosome2.getGeneAt(i));
		}

		Chromosome<GeneType> offSpring2 = new Chromosome<GeneType>(chromosomLength);
		for (int i = 0; i < randomCrossoverPoint; i++){
			offSpring2.setGeneAt(i, parents.chromosome2.getGeneAt(i));
		}

		for (int i = randomCrossoverPoint; i < chromosomLength; i++){
			offSpring2.setGeneAt(i, parents.chromosome1.getGeneAt(i));
		}
		
		return new ChromosomePair<>(offSpring1, offSpring2);
	}

	private void replaceInPopulation(ChromosomePair<GeneType> parents,
			ChromosomePair<GeneType> offSprings) {
		population.remove(parents.chromosome1);
		population.remove(parents.chromosome2);

		population.add(offSprings.chromosome1);
		population.add(offSprings.chromosome2);
	}

	private void generateRandomPopulation() {
		throw new RuntimeException("Not implemented");
	}

	public void mutateChromosomeInPlace(Chromosome<GeneType> chromosome) {
		for (int i = 0; i < chromosome.length(); i++) {
			double randomNumber = randomGenerator.nextDouble();

			if (randomNumber < mutationProbability) {
				GeneType newGene = geneMutator.mutuateGene(chromosome
						.getGeneAt(i));

				chromosome.setGeneAt(i, newGene);
			}
		}
	}

	private static class ChromosomePair<GeneType> {
		Chromosome<GeneType> chromosome1;
		Chromosome<GeneType> chromosome2;

		public ChromosomePair(Chromosome<GeneType> chromosome1,
				Chromosome<GeneType> chromosome2) {
			super();
			this.chromosome1 = chromosome1;
			this.chromosome2 = chromosome2;
		}
	}
}
