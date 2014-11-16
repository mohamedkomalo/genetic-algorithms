package komalo.genetic_algorithms;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.management.RuntimeErrorException;

public class CanonicalGeneticAlgorithm<SolutionType, GeneType> {

	List<Chromosome<GeneType>> population = new ArrayList<>();

	ChromosomeCodec<SolutionType, GeneType> chromosomeCodec;

	ChromosomeMutator<GeneType> chromosomeMutator;
	
	FitnessFunction<SolutionType> fitnessFunction;

	private int iterationsNo;

	private final double mutationProbability;

	private final double crossOverProbability;

	private final double populationNo;

	private Random randomGenerator = new Random();

	public CanonicalGeneticAlgorithm(double populationNo, int iterationsNo,
			double mutationProbability, double crossOverProbability,
			FitnessFunction<SolutionType> fitnessFunction, ChromosomeCodec<SolutionType, GeneType> chromosomeCodec,
			ChromosomeMutator<GeneType> chromosomeMutator) {
		
		this.populationNo = populationNo;
		this.iterationsNo = iterationsNo;
		this.fitnessFunction = fitnessFunction;
		this.mutationProbability = mutationProbability;
		this.crossOverProbability = crossOverProbability;
		this.chromosomeCodec = chromosomeCodec;
		this.chromosomeMutator = chromosomeMutator;
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
		
		for(Chromosome<GeneType> chromosome : population){
			SolutionType decoded = chromosomeCodec.decode(chromosome);
			int fitness = fitnessFunction.evaluate(decoded);
			
			cummulativeFitness.add(fitness + lastFitness);
			chromosomes.add(chromosome);
		}
		
		Chromosome<GeneType> parent1 = selectRandomParent(cummulativeFitness, chromosomes);
		
		Chromosome<GeneType> parent2 = parent1;
		
		while(parent1 == parent2)
			parent2 = selectRandomParent(cummulativeFitness, chromosomes);
		
		return new ChromosomePair<>(parent1, parent2);
	}

	private Chromosome<GeneType> selectRandomParent(List<Integer> cummulativeFitness,
			List<Chromosome<GeneType>> chromosomes) {
		
		int totalFitness = cummulativeFitness.get(cummulativeFitness.size() - 1);
		
		int randomNumber = randomGenerator.nextInt(totalFitness);
		
		for(int i=0; i < cummulativeFitness.size() - 1; i++){
			if(randomNumber < cummulativeFitness.get(i+1)){
				return chromosomes.get(i);
			}
		}
		
		throw new RuntimeException("could not select a valid parent from the fitness list");
	}

	private ChromosomePair<GeneType> crossover(ChromosomePair<GeneType> parents) {
		return null;
	}

	private void replaceInPopulation(ChromosomePair<GeneType> parents,
			ChromosomePair<GeneType> offSprings) {

	}
	
	private void generateRandomPopulation(){
		
	}
	
	public void mutateChromosomeInPlace(Chromosome<GeneType> chromosome){
		
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
