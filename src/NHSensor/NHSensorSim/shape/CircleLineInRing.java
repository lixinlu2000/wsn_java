package NHSensor.NHSensorSim.shape;

public class CircleLineInRing extends Shape {
	Line line;
	Circle circle;
	Ring ring;

	public CircleLineInRing(Circle circle, Line line, Ring ring) {
		super();
		this.line = line;
		this.circle = circle;
		this.ring = ring;
	}

	public boolean contains(Position position) {
		return true;
	}

	public Position getCentre() {
		return null;
	}

	public Line getLine() {
		return line;
	}

	public void setLine(Line line) {
		this.line = line;
	}

	public Circle getCircle() {
		return circle;
	}

	public void setCircle(Circle circle) {
		this.circle = circle;
	}

	public Ring getRing() {
		return ring;
	}

	public void setRing(Ring ring) {
		this.ring = ring;
	}

	public Arc getArc() {
		return ShapeUtil.circleIntersectRing(this.circle, this.ring);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		return sb.toString();
	}

}
