package NHSensor.NHSensorSim.shape;

import NHSensor.NHSensorSim.shapeTraverse.Direction;

public class BoundBoundInRect extends Shape {
	double beginBound;
	double endBound;
	Rect rect;
	int boundType;

	public BoundBoundInRect(double beginBound, double endBound, Rect rect,
			int boundType) {
		super();
		this.beginBound = beginBound;
		this.endBound = endBound;
		this.rect = rect;
		this.boundType = boundType;
	}

	public boolean contains(Position position) {
		if (this.satisfyBound(position) && this.rect.contains(position))
			return true;
		else
			return false;
	}

	public Position getCentre() {
		return this.toRect().getCentre();
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

	public Rect toRect() {
		switch (this.boundType) {
		case Direction.LEFT:
			return new Rect(this.endBound, this.beginBound,
					this.rect.getMinY(), this.rect.getMaxY());
		case Direction.RIGHT:
			return new Rect(this.beginBound, this.endBound,
					this.rect.getMinY(), this.rect.getMaxY());
		case Direction.UP:
			return new Rect(this.rect.getMinX(), this.rect.getMaxX(),
					this.beginBound, this.endBound);
		case Direction.DOWN:
			return new Rect(this.rect.getMinX(), this.rect.getMaxX(),
					this.endBound, this.beginBound);
		default:
			return null;
		}
	}

	protected boolean satisfyBound(Position position) {
		switch (this.boundType) {
		case Direction.LEFT:
			return position.getX() <= this.beginBound
					&& position.getX() >= this.endBound;
		case Direction.RIGHT:
			return position.getX() >= this.beginBound
					&& position.getX() <= this.endBound;
		case Direction.UP:
			return position.getY() >= this.beginBound
					&& position.getY() <= this.endBound;
		case Direction.DOWN:
			return position.getY() <= this.beginBound
					&& position.getY() >= this.endBound;
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

	public Object clone() {
		return new BoundBoundInRect(this.beginBound, this.endBound,
				(Rect) this.rect.clone(), this.boundType);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("BoundBoundInRect [");
		sb.append(this.beginBound + ", ");
		sb.append(this.endBound + ", ");
		sb.append(this.rect);
		sb.append("]");
		return sb.toString();
	}

}
