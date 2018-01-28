package NHSensor.NHSensorSim.shape;

public class CircleCircleInRing extends Shape {
	private Circle circleBegin;
	private Circle circleEnd;
	Ring ring;

	public CircleCircleInRing(Circle circleBegin, Circle circleEnd, Ring ring) {
		super();
		this.circleBegin = circleBegin;
		this.circleEnd = circleEnd;
		this.ring = ring;
	}

	public boolean contains(Position position) {
		if (this.ring.contains(position)
				&& !this.circleBegin.contains(position)
				&& this.circleEnd.contains(position))
			return true;
		else
			return false;
	}

	public RingSector getMinRingSector() {
		return ShapeUtil.circleCircleInRingMinRingSector(this);
	}

	public Position getCentre() {
		return this.getMinRingSector().getCentre();
	}

	public Circle getCircleBegin() {
		return circleBegin;
	}

	public void setCircleBegin(Circle circleBegin) {
		this.circleBegin = circleBegin;
	}

	public Circle getCircleEnd() {
		return circleEnd;
	}

	public void setCircleEnd(Circle circleEnd) {
		this.circleEnd = circleEnd;
	}

	public Ring getRing() {
		return ring;
	}

	public void setRing(Ring ring) {
		this.ring = ring;
	}

	public Arc getArcBegin() {
		return ShapeUtil.circleIntersectRing(this.circleBegin, this.ring);
	}

	public Arc getArcEnd() {
		return ShapeUtil.circleIntersectRing(this.circleEnd, this.ring);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("CircleCircleInRing [");
		sb.append(this.getArcBegin() + ", ");
		sb.append(this.getArcEnd() + ", ");
		sb.append(this.ring);
		sb.append("]");
		return sb.toString();
	}

}
