package komalo.genetic_algorithms;

public interface GeneMutator<GeneType> {
	GeneType mutuateGene(GeneType chromosome);
}