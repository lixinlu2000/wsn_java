package NHSensor.NHSensorSim.shape;

public class LineCircleInRing extends Shape {
	Line line;
	Circle circle;
	Ring ring;
	int direction;

	public LineCircleInRing(Line line, Circle circle, Ring ring, int direction) {
		super();
		this.line = line;
		this.circle = circle;
		this.ring = ring;
		this.direction = direction;
	}

	public boolean contains(Position position) {
		if (!this.ring.contains(position) && !this.circle.contains(position))
			return false;
		else {
			return this.getMBRLineLineInRing().contains(position);
		}
	}

	public LineLineInRing getMBRLineLineInRing() {
		return ShapeUtil.lineCircleInRingMinLineLineInRing(this);
	}

	public LineLineInRing getMaxContainedLineLineInRing() {
		Position p1, p2;
		Circle circle1 = this.getCircle();
		p1 = ShapeUtil.circleIntersectCirclePosition(circle1, this.getRing()
				.getInnerCircle(), this.direction);
		p2 = ShapeUtil.circleIntersectCirclePosition(circle1, this.getRing()
				.getOuterCircle(), this.direction);
		Line endLine = new Line(p1, p2);
		return new LineLineInRing(this.getLine(), endLine, this.getRing(),
				this.direction);
	}

	public Position getCentre() {
		return this.getMBRLineLineInRing().getCentre();
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

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public String getDirectionString() {
		if (this.direction == RingDirection.ANTICLOCKWISE) {
			return "ANTICLOCKWISE";
		} else
			return "CLOCKWISE";
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("LineCircleInRing [");
		sb.append(this.getLine() + ", ");
		sb.append(this.getCircle() + ", ");
		sb.append(this.ring + ", ");
		sb.append(this.getDirectionString());
		sb.append("]");
		return sb.toString();
	}

}
