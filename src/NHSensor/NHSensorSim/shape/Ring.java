package NHSensor.NHSensorSim.shape;

public class Ring extends Shape {
	Position circleCentre;
	double lowRadius;
	double highRadius;

	public Ring(Position circleCentre, double lowRadius, double highRadius) {
		this.circleCentre = circleCentre;
		this.lowRadius = lowRadius;
		this.highRadius = highRadius;
	}

	public double area() {
		double h2 = this.getHighRadius() * this.getHighRadius();
		double l2 = this.getLowRadius() * this.getLowRadius();
		return Math.PI * (h2 - l2);
	}

	public Circle getInnerCircle() {
		return new Circle(circleCentre, lowRadius);
	}

	public Circle getOuterCircle() {
		return new Circle(circleCentre, highRadius);
	}

	public Position getCircleCentre() {
		return circleCentre;
	}

	public Object clone() {
		Position c = (Position) this.circleCentre.clone();
		return new Ring(c, this.lowRadius, this.highRadius);
	}

	public void setCircleCentre(Position circleCentre) {
		this.circleCentre = circleCentre;
	}

	public double getHighRadius() {
		return highRadius;
	}

	public void setHighRadius(double highRadius) {
		this.highRadius = highRadius;
	}

	public double getLowRadius() {
		return lowRadius;
	}

	public void setLowRadius(double lowRadius) {
		this.lowRadius = lowRadius;
	}

	public boolean contains(Position pos) {
		double distance = pos.distance(this.getCircleCentre());
		if (distance >= this.getLowRadius() && distance <= this.getHighRadius())
			return true;
		else
			return false;
	}

	public Position getCentre() {
		return this.getCircleCentre();
	}

	public boolean equals(Ring ring) {
		if (this.circleCentre.equals(ring.getCircleCentre())
				&& this.lowRadius == ring.getLowRadius()
				&& this.highRadius == ring.getHighRadius())
			return true;
		else
			return false;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Ring(");
		sb.append(this.circleCentre);
		sb.append(" ");
		sb.append(this.lowRadius);
		sb.append(" ");
		sb.append(this.highRadius);
		return sb.toString();
	}
}
