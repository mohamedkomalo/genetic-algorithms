package komalo.genetic_algorithms.curve_fitting;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import komalo.genetic_algorithms.CanonicalGeneticAlgorithm;
import komalo.genetic_algorithms.ChromosomeCodec;
import komalo.genetic_algorithms.FitnessFunction;
import komalo.genetic_algorithms.GeneMutator;
import komalo.genetic_algorithms.RandomGenerator;

public class Main {

	public static void main(String[] args) throws Exception {
		int noOfTestCases, noOfPoints, polynomialDegree = 0;
		Set<Point> points = new HashSet<>();

		Scanner scanner = new Scanner(new File("input.txt"));

		noOfTestCases = scanner.nextInt();

		while (noOfTestCases-- > 0) {
			noOfPoints = scanner.nextInt();
			polynomialDegree = scanner.nextInt();

			while (noOfPoints-- > 0) {
				Point newPoint = new Point();
				newPoint.setX(scanner.nextDouble());
				newPoint.setY(scanner.nextDouble());

				points.add(newPoint);
			}

			processInput(polynomialDegree, points);

			points.clear();
		}

		scanner.close();
	}

	/**
	 * @param polynomialDegree
	 * @param points
	 */
	private static void processInput(int polynomialDegree, Set<Point> points) {
		int lowerBound = -100;
		int upperBound = 100;

		int populationNo = 50;
		int iterationsNo = 100;
		
		double mutationProbability = 0.0001;
		double crossOverProbability = 0.4;

		FitnessFunction<Double[]> fitnessFunction;
		GeneMutator<Double> geneMutator;
		ChromosomeCodec<Double[], Double> chromosomeCodec;
		RandomGenerator<Double[]> randomGenerator;
		
		fitnessFunction = new CurveFittingFitnessFunction(polynomialDegree, points);
		
		geneMutator = new NonUniformDoubleGeneMutator(lowerBound, upperBound);
		
		chromosomeCodec = new DoubleArrToDoubleGeneChromosomeCodec();
		
		randomGenerator = new CurveFittingsSolutionGenerator(lowerBound, upperBound, polynomialDegree);

		CanonicalGeneticAlgorithm<Double[], Double> algorithm = new CanonicalGeneticAlgorithm<>(
				populationNo, iterationsNo, mutationProbability, crossOverProbability,
				fitnessFunction, chromosomeCodec, geneMutator, randomGenerator);

		algorithm.solve();

		CanonicalGeneticAlgorithm<Double[], Double>.SolutionInfo info = algorithm.getBestSolution();
		
		Main.output("Coefficients: " + Arrays.toString(info.getSolution()) + " Error: " + 1.0 / info.getFitness());
	}

	public static void output(String x) {
		System.out.println(x);
	}
	
	public static void log(String x) {
		//System.out.println(x);
	}
}
