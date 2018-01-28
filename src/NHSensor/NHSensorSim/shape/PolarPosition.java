package NHSensor.NHSensorSim.shape;

public class PolarPosition {
	private double radius;
	private double radian;
	private Position centre;

	public PolarPosition(Position centre, double radius, double radian) {
		this.centre = centre;
		this.radius = radius;
		this.radian = radian;
	}

	public PolarPosition(double radius, double radian) {
		this.centre = null;
		this.radius = radius;
		this.radian = radian;
	}

	public double getRadian() {
		return radian;
	}

	public void setRadian(double radian) {
		this.radian = radian;
	}

	public double getRadius() {
		return radius;
	}

	public void setCentre(Position centre) {
		this.centre = centre;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public Position toPosition() {
		Radian r = new Radian(this.radian);
		double relativeX, relativeY;

		if (!r.isVertical()) {
			relativeX = this.getRadius() * Math.cos(this.getRadian());
			relativeY = this.getRadius() * Math.sin(this.getRadian());
		} else {
			relativeX = 0;
			relativeY = this.getRadius() * Math.sin(this.getRadian());
		}
		if (this.centre == null) {
			return new Position(relativeX, relativeY);
		} else {
			return new Position(this.centre.getX() + relativeX, this.centre
					.getY()
					+ relativeY);
		}
	}
}
