package NHSensor.NHSensorSim.shape;

import NHSensor.NHSensorSim.shapeTraverse.Direction;

public class BoundCircleInRect extends Shape {
	double beginBound;
	Circle endCircle;
	Rect rect;
	int boundType;

	public BoundCircleInRect(double beginBound, Circle endCircle, Rect rect,
			int boundType) {
		super();
		this.beginBound = beginBound;
		this.endCircle = endCircle;
		this.rect = rect;
		this.boundType = boundType;
	}

	public boolean contains(Position position) {
		if (this.satisfyBound(position) && this.endCircle.contains(position)
				&& this.rect.contains(position)
				&& this.getMBR().contains(position))
			return true;
		else
			return false;
	}

	public Position getCentre() {
		return this.getMBR().getCentre();
	}

	public LineSegment getBeginLineSegment() {
		switch (this.boundType) {
		case Direction.LEFT:
		case Direction.RIGHT:
			return new LineSegment(new Position(this.beginBound, this.rect
					.getMaxY()), new Position(this.beginBound, this.rect
					.getMinY()));
		case Direction.UP:
		case Direction.DOWN:
			return new LineSegment(new Position(this.rect.getMaxX(),
					this.beginBound), new Position(this.rect.getMinX(),
					this.beginBound));
		default:
			return null;
		}
	}

	public Rect getMBR() {
		double bound = ShapeUtil.circleIntersectRectMBREndBound(this.endCircle,
				this.boundType, this.rect);

		switch (this.boundType) {
		case Direction.LEFT:
			return new Rect(bound, this.beginBound, this.rect.getMinY(),
					this.rect.getMaxY());
		case Direction.RIGHT:
			return new Rect(this.beginBound, bound, this.rect.getMinY(),
					this.rect.getMaxY());
		case Direction.UP:
			return new Rect(this.rect.getMinX(), this.rect.getMaxX(),
					this.beginBound, bound);
		case Direction.DOWN:
			return new Rect(this.rect.getMinX(), this.rect.getMaxX(), bound,
					this.beginBound);
		default:
			return null;
		}
	}

	public Rect getMaxRectContained() {
		double bound = ShapeUtil.circleIntersectRectMBRBeginBound(
				this.endCircle, this.boundType, this.rect);

		switch (this.boundType) {
		case Direction.LEFT:
			return new Rect(bound, this.beginBound, this.rect.getMinY(),
					this.rect.getMaxY());
		case Direction.RIGHT:
			return new Rect(this.beginBound, bound, this.rect.getMinY(),
					this.rect.getMaxY());
		case Direction.UP:
			return new Rect(this.rect.getMinX(), this.rect.getMaxX(),
					this.beginBound, bound);
		case Direction.DOWN:
			return new Rect(this.rect.getMinX(), this.rect.getMaxX(), bound,
					this.beginBound);
		default:
			return null;
		}
	}

	protected boolean satisfyBound(Position position) {
		switch (this.boundType) {
		case Direction.LEFT:
			return position.getX() <= this.beginBound;
		case Direction.RIGHT:
			return position.getX() >= this.beginBound;
		case Direction.UP:
			return position.getY() >= this.beginBound;
		case Direction.DOWN:
			return position.getY() <= this.beginBound;
		default:
			return false;
		}
	}

	public double getBeginBound() {
		return beginBound;
	}

	public void setBeginBound(double beginBound) {
		this.beginBound = beginBound;
	}

	public Circle getEndCircle() {
		return endCircle;
	}

	public void setEndCircle(Circle endCircle) {
		this.endCircle = endCircle;
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

	public Arc getEndArc() {
		return ShapeUtil.circleIntersectRect(this.endCircle, this.boundType,
				this.rect);
	}

	public Object clone() {
		return new BoundCircleInRect(this.beginBound, (Circle) this.endCircle
				.clone(), (Rect) this.rect.clone(), this.boundType);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("BoundCircleInRect [");
		sb.append(this.beginBound + ", ");
		sb.append(this.getEndArc());
		sb.append("]");
		return sb.toString();
	}

}
