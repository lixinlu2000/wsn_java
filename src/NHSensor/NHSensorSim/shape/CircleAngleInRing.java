package NHSensor.NHSensorSim.shape;

public class CircleAngleInRing extends Shape {
	double angle;
	Circle circle;
	Ring ring;

	public CircleAngleInRing(Circle circle, double angle, Ring ring) {
		super();
		this.angle = angle;
		this.circle = circle;
		this.ring = ring;
	}

	public boolean contains(Position position) {
		if (!this.ring.contains(position) || this.circle.contains(position))
			return false;
		else {
			RingSector ringSector = ShapeUtil
					.circleAngleInRingMinRingSector(this);
			return ringSector.contains(position);
		}
	}

	public RingSector getMinRingSector() {
		return ShapeUtil.circleAngleInRingMinRingSector(this);
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

	public Position getLeftUpPosition() {
		PolarPosition polarPosition = new PolarPosition(this.getRing()
				.getCircleCentre(), this.getRing().getHighRadius(), this
				.getAngle());
		return polarPosition.toPosition();

	}

	public LineSegment getAngleLineSegment() {
		return new LineSegment(this.getLeftDownPosition(), this
				.getLeftUpPosition());
	}

	public Arc getArc() {
		return ShapeUtil.circleIntersectRing(this.circle, this.ring);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("CircleAngleInRing [");
		sb.append(this.getArc() + ", ");
		sb.append("angle(");
		sb.append(Radian.getDegree(this.angle) + ", ");
		sb.append(this.ring);
		sb.append("]");
		return sb.toString();
	}

}
