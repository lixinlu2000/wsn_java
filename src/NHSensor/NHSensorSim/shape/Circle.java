package NHSensor.NHSensorSim.shape;

import java.io.Serializable;

public class Circle extends Shape implements Serializable{
	Position centre;
	double radius;

	public Circle() {
		super();
	}

	public Circle(Position centre, double radius) {
		this.centre = centre;
		this.radius = radius;
	}

	public boolean contains(Position position) {
		if (position.distance(this.getCentre()) <= this.getRadius())
			return true;
		else
			return false;
	}

	public boolean containsRect(Rect rect) {
		if (this.contains(rect.getLB()) && this.contains(rect.getLT())
				&& this.contains(rect.getRB()) && this.contains(rect.getRT()))
			return true;
		else
			return false;
	}

	public boolean containsRing(Ring ring) {
		double centreDistance = ring.getCircleCentre().distance(
				this.getCentre());
		if (this.getRadius() >= centreDistance + ring.getHighRadius())
			return true;
		else
			return false;
	}

	public boolean containsRingSector(RingSector ringSector) {
		Position[] vertexs = ringSector.getVertexs();
		for (int i = 0; i < vertexs.length; i++) {
			if (!this.contains(vertexs[i]))
				return false;
		}
		return true;
	}

	public boolean containsLineLineInSectorInRect(
			LineLineInSectorInRect lineLineInSectorInRect) {
		Position[] vertexs = lineLineInSectorInRect.getVertexs();
		for (int i = 0; i < vertexs.length; i++) {
			if (!this.contains(vertexs[i]))
				return false;
		}
		return true;
	}

	public boolean contains(Shape shape) {
		if (shape instanceof Position) {
			return this.contains((Position) shape);
		} else if (shape instanceof Rect) {
			return this.containsRect((Rect) shape);
		} else if (shape instanceof RingSector) {
			return this.containsRingSector((RingSector) shape);
		} else if (shape instanceof Ring) {
			return this.containsRing((Ring) shape);
		} else if (shape instanceof CircleBoundInRect) {
			return this.containsRect(((CircleBoundInRect) shape).getMBR());
		} else if (shape instanceof CircleCircleInRect) {
			return this.containsRect(((CircleCircleInRect) shape).getMBR());
		} else if (shape instanceof BoundCircleInRect) {
			return this.containsRect(((BoundCircleInRect) shape).getMBR());
		} else if (shape instanceof BoundBoundInRect) {
			return this.containsRect(((BoundBoundInRect) shape).getRect());
		} else if (shape instanceof LineLineInSectorInRect) {
			return this
					.containsLineLineInSectorInRect(((LineLineInSectorInRect) shape));
		} else {
			return false;
		}
	}

	public Position getCentre() {
		return centre;
	}

	public void setCentre(Position centre) {
		this.centre = centre;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public Rect minBox() {
		double x1 = this.getCentre().getX() - this.getRadius();
		double x2 = this.getCentre().getX() + this.getRadius();
		double y1 = this.getCentre().getY() - this.getRadius();
		double y2 = this.getCentre().getY() + this.getRadius();
		return new Rect(x1, x2, y1, y2);
	}

	public Object clone() {
		return new Circle((Position) this.centre.clone(), this.radius);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Circle(");
		sb.append(this.centre);
		sb.append(" ");
		sb.append(this.radius);
		sb.append(")");
		return sb.toString();
	}

}
