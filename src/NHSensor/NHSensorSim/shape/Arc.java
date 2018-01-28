package NHSensor.NHSensorSim.shape;

import java.awt.geom.Arc2D;

public class Arc extends Shape {
	Position centre;
	double radius;
	double startRadian;
	double sita;
	int type = Arc2D.OPEN;

	public Arc(Position centre, double radius, double startRadian, double sita) {
		super();
		this.centre = centre;
		this.radius = radius;
		this.sita = sita;
		this.startRadian = startRadian;
	}

	public Arc(Circle circle, double startRadian, double sita) {
		this(circle.getCentre(), circle.getRadius(), startRadian, sita);
	}

	public Arc(Circle circle) {
		this(circle, 0, 2 * Math.PI);
	}

	public boolean contains(Position position) {
		Circle circle = this.getCircle();
		if (!circle.contains(position)) {
			return false;
		}

		double radian = circle.getCentre().bearing(position);
		if (Radian.relativeTo(radian, startRadian) <= this.sita)
			return true;
		else
			return false;
	}

	public Position getCentre() {
		return this.centre;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public double getStartRadian() {
		return startRadian;
	}

	public void setStartRadian(double startRadian) {
		this.startRadian = startRadian;
	}

	public double getEndRadian() {
		Radian r = new Radian(this.getStartRadian() + this.getSita());
		return r.getRadian();
	}

	public double getSita() {
		return sita;
	}

	public void setSita(double sita) {
		this.sita = sita;
	}

	public void setCentre(Position centre) {
		this.centre = centre;
	}

	public Circle getCircle() {
		return new Circle(this.getCentre(), this.getRadius());
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Arc (");
		sb.append("centre(");
		sb.append(this.centre + "), ");
		sb.append("radius(");
		sb.append(this.radius + "), ");
		sb.append("startDegree(");
		sb.append(Radian.getDegree(this.startRadian) + "), ");
		sb.append("sita(");
		sb.append(Radian.getDegree(this.sita) + ") ");
		sb.append(")");
		return sb.toString();

	}
}
