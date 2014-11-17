package komalo.genetic_algorithms.curve_fitting;

import java.util.Random;

import komalo.genetic_algorithms.GeneMutator;

public class NonUniformDoubleGeneMutator implements GeneMutator<Double> {

	private double lowerBound;
	private double upperBound;

	public NonUniformDoubleGeneMutator(double lowerBound, double upperBound) {
		super();
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
	}

	@Override
	public Double mutuateGene(Double gene, int currentIterationNo, int iterationsNo) {
		double deltaLower = lowerBound - gene;
		double deltaUpper = upperBound - gene;
		
		double randomNumber = new Random().nextDouble();
		
		double y = randomNumber <= 0.5 ? deltaLower : deltaUpper;
		
		double dependencyFactor = 0.5;
		
		double mutationAmount = y * (1 - Math.pow(randomNumber, Math.pow(1 - currentIterationNo / iterationsNo, dependencyFactor)));
		
		return gene + mutationAmount;
	}

}
