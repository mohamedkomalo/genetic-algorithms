package komalo.genetic_algorithms;

import java.util.ArrayList;
import java.util.List;

public class CanonicalGeneticAlgorithm<SolutionType, GeneType> {

	List<Chromosome<GeneType>> population = new ArrayList<>();

	ChromosomeCodec<SolutionType, GeneType> chromosomeCodec;

	ChromosomeMutator<GeneType> chromosomeMutator;

	private int iterationsNo;

	private final double mutationProbability;

	private final double crossOverProbability;

	private final double populationNo;

	public CanonicalGeneticAlgorithm(double populationNo, int iterationsNo,
			double mutationProbability, double crossOverProbability,
			ChromosomeCodec<SolutionType, GeneType> chromosomeCodec,
			ChromosomeMutator<GeneType> chromosomeMutator) {
		
		this.populationNo = populationNo;
		this.iterationsNo = iterationsNo;
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

			chromosomeMutator.mutuateChromosome(offSprings.chromosome1);

			chromosomeMutator.mutuateChromosome(offSprings.chromosome2);

			replaceInPopulation(parents, offSprings);
		}
	}

	private ChromosomePair<GeneType> selectParents() {
		return null;
	}

	private ChromosomePair<GeneType> crossover(ChromosomePair<GeneType> parents) {
		return null;
	}

	private void replaceInPopulation(ChromosomePair<GeneType> parents,
			ChromosomePair<GeneType> offSprings) {

	}
	
	private void generateRandomPopulation(){
		
	}

	private static class ChromosomePair<GeneType> {
		Chromosome<GeneType> chromosome1;
		Chromosome<GeneType> chromosome2;
	}
}
