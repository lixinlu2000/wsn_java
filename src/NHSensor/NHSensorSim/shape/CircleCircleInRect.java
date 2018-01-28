package NHSensor.NHSensorSim.shape;

import NHSensor.NHSensorSim.shapeTraverse.Direction;

public class CircleCircleInRect extends Shape {
	Circle beginCircle;
	Circle endCircle;
	Rect rect;
	int boundType;

	public CircleCircleInRect(Circle beginCircle, Circle endCircle, Rect rect,
			int boundType) {
		super();
		this.beginCircle = beginCircle;
		this.endCircle = endCircle;
		this.rect = rect;
		this.boundType = boundType;
	}

	public boolean contains(Position position) {
		/*
		 * old version
		 */
		// if(!this.beginCircle.contains(position)&&this.endCircle.contains(
		// position)&&
		// this.rect.contains(position)) {
		if (!this.beginCircle.contains(position)
				&& this.endCircle.contains(position)
				&& this.rect.contains(position)
				&& this.getMBR().contains(position)) {
			/*
			 * switch(this.boundType) { case Direction.LEFT:
			 * if(position.getX()>=this.endCircle.getCentre().getX())return
			 * false; case Direction.RIGHT:
			 * if(position.getX()<=this.endCircle.getCentre().getX())return
			 * false; case Direction.UP:
			 * if(position.getY()<=this.endCircle.getCentre().getY())return
			 * false; case Direction.DOWN:
			 * if(position.getY()>=this.endCircle.getCentre().getY())return
			 * false; }
			 */
			return true;
		} else
			return false;
	}

	public Position getCentre() {
		return this.getMBR().getCentre();
	}

	public Rect getMBR() {
		double boundBegin = ShapeUtil.circleIntersectRectMBRBeginBound(
				this.beginCircle, this.boundType, this.rect);
		double boundEnd = ShapeUtil.circleIntersectRectMBREndBound(
				this.endCircle, this.boundType, this.rect);

		switch (this.boundType) {
		case Direction.LEFT:
			return new Rect(boundEnd, boundBegin, this.rect.getMinY(),
					this.rect.getMaxY());
		case Direction.RIGHT:
			return new Rect(boundBegin, boundEnd, this.rect.getMinY(),
					this.rect.getMaxY());
		case Direction.UP:
			return new Rect(this.rect.getMinX(), this.rect.getMaxX(),
					boundBegin, boundEnd);
		case Direction.DOWN:
			return new Rect(this.rect.getMinX(), this.rect.getMaxX(), boundEnd,
					boundBegin);
		default:
			return null;
		}
	}

	public Rect getMaxRectContained() {
		double boundBegin = ShapeUtil.circleIntersectRectMBREndBound(
				this.beginCircle, this.boundType, this.rect);
		double boundEnd = ShapeUtil.circleIntersectRectMBRBeginBound(
				this.endCircle, this.boundType, this.rect);

		switch (this.boundType) {
		case Direction.LEFT:
			return new Rect(boundEnd, boundBegin, this.rect.getMinY(),
					this.rect.getMaxY());
		case Direction.RIGHT:
			return new Rect(boundBegin, boundEnd, this.rect.getMinY(),
					this.rect.getMaxY());
		case Direction.UP:
			return new Rect(this.rect.getMinX(), this.rect.getMaxX(),
					boundBegin, boundEnd);
		case Direction.DOWN:
			return new Rect(this.rect.getMinX(), this.rect.getMaxX(), boundEnd,
					boundBegin);
		default:
			return null;
		}
	}

	public Circle getBeginCircle() {
		return beginCircle;
	}

	public void setBeginCircle(Circle beginCircle) {
		this.beginCircle = beginCircle;
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

	public Arc getBeginArc() {
		return ShapeUtil.circleIntersectRect(this.beginCircle, this.boundType,
				this.rect);
	}

	public Arc getEndArc() {
		return ShapeUtil.circleIntersectRect(this.endCircle, this.boundType,
				this.rect);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("CircleCircleInRect [");
		sb.append(this.getBeginArc());
		sb.append(", ");
		sb.append(this.getEndArc());
		sb.append("]");
		return sb.toString();
	}
}
