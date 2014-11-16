package komalo.genetic_algorithms;

interface ChromosomeCodec<SolutionType, ChromosomeGeneType> {

	Chromosome<ChromosomeGeneType> encode(SolutionType value);

	SolutionType decode(Chromosome<ChromosomeGeneType> value);

}
