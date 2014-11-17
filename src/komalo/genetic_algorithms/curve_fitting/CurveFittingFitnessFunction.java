package komalo.genetic_algorithms.curve_fitting;

import java.awt.Point;
import java.util.Set;

import komalo.genetic_algorithms.FitnessFunction;

public class CurveFittingFitnessFunction implements FitnessFunction<Double[]> {

	private int polynomialDegree;
	
	private Set<Point> points;
	
	public CurveFittingFitnessFunction(int polynomialDegree, Set<Point> points) {
		this.polynomialDegree = polynomialDegree;
		this.points = points;
	}

	@Override
	public double evaluate(Double[] coefficients) {
		if(polynomialDegree + 1 != coefficients.length)
			throw new RuntimeException("Solution arr doesn't match the polynomial degree");
		
		double meanSquareError = 0;
		
		for(Point p : points){
			double actualY = 0;
			for(int degree=0; degree <= polynomialDegree; degree++){
				actualY += coefficients[degree] * Math.pow(p.x, degree);
			}
			meanSquareError += (actualY - p.y) * (actualY - p.y);
		}
		
		meanSquareError /= points.size();
		
		return meanSquareError;
	}
}
