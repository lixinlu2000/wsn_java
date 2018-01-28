package NHSensor.NHSensorSim.shape;

import NHSensor.NHSensorSim.shapeTraverse.Direction;

public class CircleBoundInRect extends Shape {
	Circle beginCircle;
	double endBound;
	Rect rect;
	int boundType;

	public CircleBoundInRect(Circle beginCircle, double endBound, Rect rect,
			int boundType) {
		super();
		this.beginCircle = beginCircle;
		this.endBound = endBound;
		this.rect = rect;
		this.boundType = boundType;
	}

	public boolean contains(Position position) {
		if (!this.beginCircle.contains(position) && this.satisfyBound(position)
				&& this.rect.contains(position)
				&& this.getMBR().contains(position))
			return true;
		else
			return false;
	}

	public Arc getBeginArc() {
		return ShapeUtil.circleIntersectRect(beginCircle, boundType, rect);
	}

	public LineSegment getEndLineSegment() {
		switch (this.boundType) {
		case Direction.LEFT:
		case Direction.RIGHT:
			return new LineSegment(new Position(this.endBound, this.rect
					.getMaxY()), new Position(this.endBound, this.rect
					.getMinY()));
		case Direction.UP:
		case Direction.DOWN:
			return new LineSegment(new Position(this.rect.getMaxX(),
					this.endBound), new Position(this.rect.getMinX(),
					this.endBound));
		default:
			return null;
		}
	}

	public Rect getMBR() {
		double bound = ShapeUtil.circleIntersectRectMBRBeginBound(
				this.beginCircle, this.boundType, this.rect);

		switch (this.boundType) {
		case Direction.LEFT:
			return new Rect(this.endBound, bound, this.rect.getMinY(),
					this.rect.getMaxY());
		case Direction.RIGHT:
			return new Rect(bound, this.endBound, this.rect.getMinY(),
					this.rect.getMaxY());
		case Direction.UP:
			return new Rect(this.rect.getMinX(), this.rect.getMaxX(), bound,
					this.endBound);
		case Direction.DOWN:
			return new Rect(this.rect.getMinX(), this.rect.getMaxX(),
					this.endBound, bound);
		default:
			return null;
		}
	}

	public Rect getMaxRectContained() {
		double bound = ShapeUtil.circleIntersectRectMBREndBound(
				this.beginCircle, this.boundType, this.rect);

		switch (this.boundType) {
		case Direction.LEFT:
			return new Rect(this.endBound, bound, this.rect.getMinY(),
					this.rect.getMaxY());
		case Direction.RIGHT:
			return new Rect(bound, this.endBound, this.rect.getMinY(),
					this.rect.getMaxY());
		case Direction.UP:
			return new Rect(this.rect.getMinX(), this.rect.getMaxX(), bound,
					this.endBound);
		case Direction.DOWN:
			return new Rect(this.rect.getMinX(), this.rect.getMaxX(),
					this.endBound, bound);
		default:
			return null;
		}
	}

	public Position getCentre() {
		return this.getMBR().getCentre();
	}

	protected boolean satisfyBound(Position position) {
		switch (this.boundType) {
		case Direction.LEFT:
			return position.getX() >= this.endBound;
		case Direction.RIGHT:
			return position.getX() <= this.endBound;
		case Direction.UP:
			return position.getY() <= this.endBound;
		case Direction.DOWN:
			return position.getY() >= this.endBound;
		default:
			return false;
		}
	}

	public Circle getBeginCircle() {
		return beginCircle;
	}

	public void setBeginCircle(Circle beginCircle) {
		this.beginCircle = beginCircle;
	}

	public double getEndBound() {
		return endBound;
	}

	public void setEndBound(double endBound) {
		this.endBound = endBound;
	}

	public Rect getRect() {
		return rect;
	}

	public void setRect(Rect rect) {
		this.rect = rect;
	}

	public int getBoundType() {
		return boundType;
	}

	public void setBoundType(int boundType) {
		this.boundType = boundType;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("CircleBoundInRect [");
		sb.append(this.getBeginArc());
		sb.append(", ");
		sb.append(this.endBound);
		sb.append("]");
		return sb.toString();
	}

}
