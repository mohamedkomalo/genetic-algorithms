package komalo.genetic_algorithms.curve_fitting;

import java.util.Random;

import komalo.genetic_algorithms.RandomGenerator;

public class CurveFittingsSolutionGenerator implements RandomGenerator<Double[]> {

	private double lowerBound;
	private double upperBound;

	private int noOfCofficients;

	public CurveFittingsSolutionGenerator(double lowerBound, double upperBound,
			int noOfCofficients) {
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
		this.noOfCofficients = noOfCofficients;
	}

	@Override
	public Double[] generateRandom() {
		Double[] cofficients = new Double[noOfCofficients];
		
		for (int i = 0; i < noOfCofficients; i++) {
			cofficients[i] = new Random().nextDouble() * (upperBound - lowerBound) + lowerBound;
		}
		
		return cofficients;
	}

}
