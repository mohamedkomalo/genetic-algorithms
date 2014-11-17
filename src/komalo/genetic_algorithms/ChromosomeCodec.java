package komalo.genetic_algorithms;

public interface ChromosomeCodec<SolutionType, ChromosomeGeneType> {

	Chromosome<ChromosomeGeneType> encode(SolutionType value);

	SolutionType decode(Chromosome<ChromosomeGeneType> value);

}
