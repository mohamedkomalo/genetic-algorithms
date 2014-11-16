package komalo.genetic_algorithms;

interface ChromosomeMutator<ChromosomeGeneType> {
	void mutuateChromosome(Chromosome<ChromosomeGeneType> chromosome);
}