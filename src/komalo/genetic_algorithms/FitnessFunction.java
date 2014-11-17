package komalo.genetic_algorithms;

public interface FitnessFunction<T> {
	double evaluate(T solution);
}
