package NHSensor.NHSensorSim.shape;

public class LineLineInRing extends Shape {
	Line begin;
	Line end;
	Ring ring;
	int direction;
	Position[] ps = new Position[4];

	public LineLineInRing(Line begin, Line end, Ring ring, int direction) {
		super();
		this.begin = begin;
		this.end = end;
		this.ring = ring;
		this.direction = direction;

		Circle innerCircle = this.ring.getInnerCircle();
		Circle outerCircle = this.ring.getOuterCircle();

		ps[0] = this.begin.intersectNear(innerCircle);
		ps[1] = this.begin.intersectNear(outerCircle);
		ps[2] = this.end.intersectNear(innerCircle);
		ps[3] = this.end.intersectNear(outerCircle);
	}

	public boolean contains(Position position) {
		if (!this.ring.contains(position))
			return false;
		else {
			Sector sector = new Sector(this.begin, this.end);
			return sector.contains(position);
		}
	}

	public Position getCentre() {
		double x = 0, y = 0;
		for (int i = 0; i < 4; i++) {
			x += ps[0].getX();
			y += ps[0].getY();
		}
		x = x / 4;
		y = y / 4;
		return new Position(x, y);
	}

	public Line getBegin() {
		return begin;
	}

	public LineSegment getBeginLineSegment() {
		return new LineSegment(this.getBL(), this.getBH());
	}

	public Line getEnd() {
		return end;
	}

	public LineSegment getEndLineSegment() {
		return new LineSegment(this.getEL(), this.getEH());
	}

	public Ring getRing() {
		return ring;
	}

	public void setRing(Ring ring) {
		this.ring = ring;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public Position getBL() {
		return this.ps[0];
	}

	public Position getBH() {
		return this.ps[1];
	}

	public Position getEL() {
		return this.ps[2];
	}

	public Position getEH() {
		return this.ps[3];
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("LineLineInRing(");
		sb.append(this.getBL());
		;
		sb.append(" " + this.getBH());
		sb.append(" " + this.getEL());
		sb.append(" " + this.getEH());
		sb.append(")");
		return sb.toString();
	}
}
