package komalo.genetic_algorithms.curve_fitting;

import java.util.Random;

import komalo.genetic_algorithms.RandomGenerator;

public class CurveFittingsSolutionGenerator implements RandomGenerator<Double[]> {

	private double lowerBound;
	private double upperBound;

	private int polynomialDegree;

	public CurveFittingsSolutionGenerator(double lowerBound, double upperBound,
			int noOfCofficients) {
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
		this.polynomialDegree = noOfCofficients;
	}

	@Override
	public Double[] generateRandom() {
		Double[] cofficients = new Double[polynomialDegree + 1];
		
		for (int i = 0; i <= polynomialDegree; i++) {
			cofficients[i] = new Random().nextDouble() * (upperBound - lowerBound) + lowerBound;
		}
		
		return cofficients;
	}

}
