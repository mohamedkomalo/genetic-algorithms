package komalo.genetic_algorithms;

interface ChromosomeCodec<T, ChromosomeGeneType> {

	Chromosome<ChromosomeGeneType> encode(T value);

	T decode(Chromosome<ChromosomeGeneType> value);

}
