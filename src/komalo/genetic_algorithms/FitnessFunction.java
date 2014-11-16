package komalo.genetic_algorithms;

interface FitnessFunction<T> {
	int evaluate(T val);
}
