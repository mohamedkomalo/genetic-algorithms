package komalo.genetic_algorithms;

public interface GeneMutator<GeneType> {
	
	GeneType mutuateGene(GeneType gene, int currentIterationNo, int iterationsNo);
}