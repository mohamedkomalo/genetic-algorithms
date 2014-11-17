package komalo.genetic_algorithms.curve_fitting;

import komalo.genetic_algorithms.Chromosome;
import komalo.genetic_algorithms.ChromosomeCodec;

public class DoubleArrToDoubleGeneChromosomeCodec implements ChromosomeCodec<Double[], Double> {

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
