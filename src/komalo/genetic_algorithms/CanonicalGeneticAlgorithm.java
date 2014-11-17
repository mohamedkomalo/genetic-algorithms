package komalo.genetic_algorithms;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import komalo.genetic_algorithms.curve_fitting.Main;

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
	
	private RandomGenerator<SolutionType> randomSolutionGenerator;

	public CanonicalGeneticAlgorithm(double populationNo, int iterationsNo,
			double mutationProbability, double crossOverProbability,
			FitnessFunction<SolutionType> fitnessFunction,
			ChromosomeCodec<SolutionType, GeneType> chromosomeCodec,
			GeneMutator<GeneType> chromosomeMutator, RandomGenerator<SolutionType> randomSolutionGenerator) {

		this.populationNo = populationNo;
		this.iterationsNo = iterationsNo;
		
		this.mutationProbability = mutationProbability;
		this.crossOverProbability = crossOverProbability;
		
		this.fitnessFunction = fitnessFunction;
		this.chromosomeCodec = chromosomeCodec;
		this.geneMutator = chromosomeMutator;
		this.randomSolutionGenerator = randomSolutionGenerator;
	}

	public void solve() {
		population.clear();

		generateRandomPopulation();

		for (int i = 0; i < iterationsNo; i++) {
			
			Main.log("Iteration No: " + i);
			
			ChromosomePair<GeneType> parents = selectParents();
			
			double crossOverTest = randomGenerator.nextDouble();
			
			ChromosomePair<GeneType> offSprings;
			if(crossOverTest <= crossOverProbability){
				offSprings = crossover(parents);
				mutateChromosomeInPlace(offSprings.chromosome1);
				mutateChromosomeInPlace(offSprings.chromosome2);
				replaceInPopulation(parents, offSprings);
			}
			else{
				mutateChromosomeInPlace(parents.chromosome1);
				mutateChromosomeInPlace(parents.chromosome2);
			}
	

		}
	}
	
	public SolutionInfo getBestSolution(){
		SolutionInfo solutionInfo = new SolutionInfo();
		
		for (Chromosome<GeneType> chromosome : population) {
				SolutionType decoded = chromosomeCodec.decode(chromosome);
				double fitness = fitnessFunction.evaluate(decoded);
				
				if(fitness > solutionInfo.fitness){
					solutionInfo.solution = decoded;
					solutionInfo.fitness = fitness;
				}
		}
		
		return solutionInfo;
	}

	private ChromosomePair<GeneType> selectParents() {
		List<Double> cummulativeFitness = new LinkedList<>();
		List<Chromosome<GeneType>> chromosomes = new LinkedList<>();

		double lastFitness = 0;

		for (Chromosome<GeneType> chromosome : population) {
			SolutionType decoded = chromosomeCodec.decode(chromosome);
			lastFitness += fitnessFunction.evaluate(decoded);

			cummulativeFitness.add(lastFitness);
			chromosomes.add(chromosome);
		}

		Chromosome<GeneType> parent1 = selectRandomParent(cummulativeFitness,
				chromosomes);

		Chromosome<GeneType> parent2 = parent1;

		int trials = 0;
		while (parent1 == parent2){
			parent2 = selectRandomParent(cummulativeFitness, chromosomes);
			
			if(++trials > 100)
				throw new RuntimeException("exceeded max ammount of tries to randomly select a parent");
		}

		return new ChromosomePair<>(parent1, parent2);
	}

	private Chromosome<GeneType> selectRandomParent(
			List<Double> cummulativeFitness,
			List<Chromosome<GeneType>> chromosomes) {

		double totalFitness = cummulativeFitness
				.get(cummulativeFitness.size() - 1);

		double randomNumber = randomGenerator.nextDouble() * totalFitness;
		
		Main.log("[Selection] chose a randomNumber: " + randomNumber + " totalFitness: " + totalFitness);
		
		for (int i = 1; i < cummulativeFitness.size(); i++) {
			
			Main.log("testing fitness: " + cummulativeFitness.get(i-1) +"    " + cummulativeFitness.get(i));
			
			if (randomNumber > cummulativeFitness.get(i)) 
				continue;
			
			return chromosomes.get(i - 1);
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
		for(int i=0; i<populationNo; i++){
			SolutionType randomSolution = randomSolutionGenerator.generateRandom();
			
			Chromosome<GeneType> encodedSolution = chromosomeCodec.encode(randomSolution);
			
			population.add(encodedSolution);
		}
	}

	public void mutateChromosomeInPlace(Chromosome<GeneType> chromosome) {
		for (int i = 0; i < chromosome.length(); i++) {
			double randomNumber = randomGenerator.nextDouble();

			if (randomNumber < mutationProbability) {
				GeneType newGene = geneMutator.mutuateGene(chromosome
						.getGeneAt(i), i, iterationsNo);

				chromosome.setGeneAt(i, newGene);
			}
		}
	}

	private static class ChromosomePair<GeneType> {
		Chromosome<GeneType> chromosome1;
		Chromosome<GeneType> chromosome2;

		public ChromosomePair(Chromosome<GeneType> chromosome1,
				Chromosome<GeneType> chromosome2) {
			
			this.chromosome1 = chromosome1;
			this.chromosome2 = chromosome2;
		}
	}
	
	public class SolutionInfo{
		SolutionType solution;
		double fitness;
		
		public SolutionType getSolution() {
			return solution;
		}
		public double getFitness() {
			return fitness;
		}
	}
}
