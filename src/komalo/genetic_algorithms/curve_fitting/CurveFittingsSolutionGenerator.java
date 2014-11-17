package komalo.genetic_algorithms.curve_fitting;

import java.util.Random;

import komalo.genetic_algorithms.Chromosome;
import komalo.genetic_algorithms.ChromosomeCodec;
import komalo.genetic_algorithms.RandomGenerator;

public class CurveFittingsSolutionGenerator implements ChromosomeCodec<Double[], Double>,
		RandomGenerator<Double[]> {

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

	@Override
	public Chromosome<Double> encode(Double[] coefficients) {
		Chromosome<Double> chromosome = new Chromosome<>(coefficients.length);

		for (int i = 0; i < coefficients.length; i++) {
			chromosome.setGeneAt(i, coefficients[i]);
		}

		return chromosome;
	}

	@Override
	public Double[] decode(Chromosome<Double> chromosome) {
		Double[] coefficients = new Double[chromosome.length()];

		for (int i = 0; i < chromosome.length(); i++) {
			coefficients[i] = chromosome.getGeneAt(i);
			;
		}

		return coefficients;
	}

}
