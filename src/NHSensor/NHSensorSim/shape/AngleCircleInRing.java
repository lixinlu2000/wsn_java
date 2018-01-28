package NHSensor.NHSensorSim.shape;

public class AngleCircleInRing extends Shape {
	double angle;
	Circle circle;
	Ring ring;

	public AngleCircleInRing(double angle, Circle circle, Ring ring) {
		super();
		this.angle = angle;
		this.circle = circle;
		this.ring = ring;
	}

	public boolean contains(Position position) {
		if (!this.ring.contains(position) || !this.circle.contains(position))
			return false;
		else {
			RingSector ringSector = ShapeUtil
					.angleCircleInRingMinRingSector(this);
			return ringSector.contains(position);
		}
	}

	/*
	 * mbr
	 */
	public RingSector getMinRingSector() {
		return ShapeUtil.angleCircleInRingMinRingSector(this);
	}

	public Position getCentre() {
		return this.getMinRingSector().getCentre();
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
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

	public Position getLeftDownPosition() {
		PolarPosition polarPosition = new PolarPosition(this.getRing()
				.getCircleCentre(), this.getRing().getLowRadius(), this
				.getAngle());
		return polarPosition.toPosition();
	}

	public Position getRightDownPosition() {
		PolarPosition polarPosition = new PolarPosition(this.getRing()
				.getCircleCentre(), this.getRing().getHighRadius(), this
				.getAngle());
		return polarPosition.toPosition();

	}

	public LineSegment getAngleLineSegment() {
		return new LineSegment(this.getLeftDownPosition(), this
				.getRightDownPosition());
	}

	public Arc getArc() {
		return ShapeUtil.circleIntersectRing(this.circle, this.ring);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("AngleCircleInRing [");
		sb.append("angle(");
		sb.append(Radian.getDegree(this.angle) + ", ");
		sb.append(this.getArc() + ", ");
		sb.append(this.ring);
		sb.append("]");
		return sb.toString();
	}

}
