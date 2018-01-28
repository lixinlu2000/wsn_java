package NHSensor.NHSensorSim.shape;

public abstract class Shape {
	public abstract boolean contains(Position position);

	public abstract Position getCentre();

	public static double minDistance(Position position, Shape shape) {
		double minDist = Double.MAX_VALUE;
		if (shape.contains(position))
			return 0;
		if (shape instanceof Rect) {
			Rect rect = (Rect) shape;
			minDist = Math.min(position.distance(rect.getLB()), position
					.distance(rect.getRB()));
			minDist = Math.min(minDist, position.distance(rect.getLT()));
			minDist = Math.min(minDist, position.distance(rect.getRT()));
			return minDist;
		} else if (shape instanceof RingSector) {

		}
		return Double.MAX_VALUE;
	}
}