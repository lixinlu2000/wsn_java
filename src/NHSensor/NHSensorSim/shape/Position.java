package NHSensor.NHSensorSim.shape;

import java.io.Serializable;
import java.text.DecimalFormat;

import NHSensor.NHSensorSim.shapeTraverse.Direction;

public class Position extends Shape implements Serializable{
	private double x;
	private double y;

	public Position(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Position() {
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	public Position move(double x, double y) {
		return new Position(this.getX()+x, this.getY()+y);
	}

	public Position getAddedPosition(Position pos) {
		return new Position(this.getX() + pos.getX(), this.getY() + pos.getY());
	}

	public static double distance(Position p1, Position p2) {
		double dx = p1.getX() - p2.getX();
		double dy = p1.getY() - p2.getY();
		double distance = Math.sqrt(dx * dx + dy * dy);
		return distance;
	}

	public static double xDistance(Position p1, Position p2) {
		double dx = p1.getX() - p2.getX();
		return Math.abs(dx);
	}

	public static double yDistance(Position p1, Position p2) {
		double dy = p1.getY() - p2.getY();
		return Math.abs(dy);
	}

	public double distance(Position p) {
		return Position.distance(this, p);
	}

	public double xDistance(Position p) {
		return Position.xDistance(this, p);
	}

	public double yDistance(Position p) {
		return Position.yDistance(this, p);
	}

	public boolean in(Rect rect) {
		return x <= rect.getMaxX() && x >= rect.getMinX()
				&& y <= rect.getMaxY() && y >= rect.getMinY();
	}

	public boolean equals(Position other) {
		if (this.getX() == other.getX() && this.getY() == other.getY())
			return true;
		else
			return false;
	}

	public double bearing(Position other) {
		double dx, dy;
		dx = other.getX() - this.getX();
		dy = other.getY() - this.getY();

		double sita = Math.atan2(dy, dx);
		if (sita < 0)
			sita += 2 * Math.PI;

		return sita;
	}

	public Object clone() {
		return new Position(this.getX(), this.getY());
	}

	public double getBound(int direction) {
		switch (direction) {
		case Direction.LEFT:
		case Direction.RIGHT:
			return this.getX();
		case Direction.DOWN:
		case Direction.UP:
			return this.getY();
		default:
			return 0;
		}
	}

	public double getBound(int direction, double movement) {
		switch (direction) {
		case Direction.LEFT:
			return this.getX() - movement;
		case Direction.RIGHT:
			return this.getX() + movement;
		case Direction.DOWN:
			return this.getY() - movement;
		case Direction.UP:
			return this.getY() + movement;
		default:
			return 0;
		}
	}

	public double distance(Position other, double radian) {
		double d1 = this.distance(other);
		double radian1 = this.bearing(other);
		double r1 = Radian.relativeTo(radian1, radian);
		return Math.abs(d1 * Math.cos(r1));
	}

	public String toString() {
		// xml style
		/*
		 * StringBuffer sb = new StringBuffer(); sb.append("<Position>");
		 * sb.append("<x>"+this.x+"</x>"); sb.append("<y>"+this.y+"</y>");
		 * sb.append("</Position>"); return sb.toString();
		 */

		// (x,y) style
		StringBuffer sb = new StringBuffer();
		DecimalFormat df = new DecimalFormat("#.##");
		sb.append("Pos(");
		sb.append(df.format(this.x) + ",");
		sb.append(df.format(this.y) + ")");
		return sb.toString();

	}

	public boolean contains(Position position) {
		return this.equals(position);
	}

	public Position getCentre() {
		return this;
	}
}
