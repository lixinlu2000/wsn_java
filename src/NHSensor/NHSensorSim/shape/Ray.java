package NHSensor.NHSensorSim.shape;

public class Ray extends Shape {
	private Position pos;
	private double alpha;

	public Ray(Position pos, double alpha) {
		super();
		this.pos = pos;
		this.alpha = alpha;
	}

	public boolean contains(Position position) {
		// TODO Auto-generated method stub
		return false;
	}

	public Position getCentre() {
		// TODO Auto-generated method stub
		return null;
	}

	public Position getPos() {
		return pos;
	}

	public void setPos(Position pos) {
		this.pos = pos;
	}

	public double getAlpha() {
		return alpha;
	}

	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}

}
