package komalo.genetic_algorithms.curve_fitting;

public class Point {
	private double X;
	private double Y;
	
	public Point(){
		
	}
	
	public Point(double x, double y) {
		super();
		X = x;
		Y = y;
	}
	public double getX() {
		return X;
	}
	public void setX(double x) {
		X = x;
	}
	public double getY() {
		return Y;
	}
	public void setY(double y) {
		Y = y;
	}

}
