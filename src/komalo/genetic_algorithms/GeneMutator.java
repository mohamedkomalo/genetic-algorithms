package komalo.genetic_algorithms;

interface GeneMutator<GeneType> {
	GeneType mutuateGene(GeneType chromosome);
}