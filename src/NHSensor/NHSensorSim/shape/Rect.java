package NHSensor.NHSensorSim.shape;

import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.text.DecimalFormat;

import NHSensor.NHSensorSim.shapeTraverse.Direction;

public class Rect extends Shape implements Serializable{
	private Position p1;
	private Position p2;

	public Rect() {
		super();
	}

	public Rect(Position p1, Position p2) {
		this.p1 = p1;
		this.p2 = p2;
	}

	public Rect(double width, double heigth) {
		p1 = new Position(0, 0);
		p2 = new Position(width, heigth);

	}

	public Rect(double x1, double x2, double y1, double y2) {
		p1 = new Position(x1, y1);
		p2 = new Position(x2, y2);
	}

	public Position getP1() {
		return p1;
	}

	public void setP1(Position p1) {
		this.p1 = p1;
	}

	public Position getP2() {
		return p2;
	}

	public void setP2(Position p2) {
		this.p2 = p2;
	}

	public double getMinX() {
		return p1.getX() < p2.getX() ? p1.getX() : p2.getX();
	}

	public double getMaxX() {
		return p1.getX() > p2.getX() ? p1.getX() : p2.getX();
	}

	public double getMinY() {
		return p1.getY() < p2.getY() ? p1.getY() : p2.getY();
	}

	public double getMaxY() {
		return p1.getY() > p2.getY() ? p1.getY() : p2.getY();
	}

	public double getWidth() {
		return this.getMaxX() - this.getMinX();
	}

	public double getHeight() {
		return this.getMaxY() - this.getMinY();
	}

	public Position getCentre() {
		double destX = (p1.getX() + p2.getX()) / 2;
		double destY = (p1.getY() + p2.getY()) / 2;
		return new Position(destX, destY);
	}

	public Position getLB() {
		return new Position(this.getMinX(), this.getMinY());
	}

	public Position getLT() {
		return new Position(this.getMinX(), this.getMaxY());
	}

	public Position getRB() {
		return new Position(this.getMaxX(), this.getMinY());
	}

	public Position getRT() {
		return new Position(this.getMaxX(), this.getMaxY());
	}

	public Position getCB() {
		return new Position(this.getCentre().getX(), this.getMinY());
	}

	public Position getCT() {
		return new Position(this.getCentre().getX(), this.getMaxY());
	}

	public Rect neighborRect(int direction, double size, double limit) {
		switch (direction) {
		case Direction.LEFT:
			return this.leftNeighbour(size, limit);
		case Direction.RIGHT:
			return this.rightNeighbour(size, limit);
		case Direction.UP:
			return this.topNeighbour(size, limit);
		case Direction.DOWN:
			return this.downNeighbour(size, limit);
		default:
			return null;
		}
	}

	public Rect leftNeighbour(double size, double limit) {
		double minx = this.getMinX() - size;
		double maxx = this.getMinX();
		minx = minx > limit ? minx : limit;
		maxx = maxx > limit ? maxx : limit;
		return new Rect(minx, maxx, this.getMinY(), this.getMaxY());
	}

	public Rect rightNeighbour(double size, double limit) {
		double minx = this.getMaxX();
		double maxx = this.getMaxX() + size;
		minx = minx < limit ? minx : limit;
		maxx = maxx < limit ? maxx : limit;
		return new Rect(minx, maxx, this.getMinY(), this.getMaxY());
	}

	public Rect downNeighbour(double size, double limit) {
		double miny = this.getMinY() - size;
		double maxy = this.getMinY();
		miny = miny > limit ? miny : limit;
		maxy = maxy > limit ? maxy : limit;
		return new Rect(this.getMinX(), this.getMaxX(), miny, maxy);
	}

	public Rect topNeighbour(double size, double limit) {
		double miny = this.getMaxY();
		double maxy = this.getMaxY() + size;
		miny = miny < limit ? miny : limit;
		maxy = maxy < limit ? maxy : limit;
		return new Rect(this.getMinX(), this.getMaxX(), miny, maxy);
	}

	public double area() {
		return this.getWidth() * this.getHeight();
	}

	public boolean contains(Position position) {
		if (position.in(this))
			return true;
		else
			return false;
	}

	/*
	 * for CollectDataInRectViaNodeExEvent
	 */
	public Rect add(double size, int direction) {
		switch (direction) {
		case Direction.LEFT:
			return new Rect(this.getMinX() - size, this.getMaxX(), this
					.getMinY(), this.getMaxY());
		case Direction.RIGHT:
			return new Rect(this.getMinX(), this.getMaxX() + size, this
					.getMinY(), this.getMaxY());
		case Direction.UP:
			return new Rect(this.getMinX(), this.getMaxX(), this.getMinY(),
					this.getMaxY() + size);
		case Direction.DOWN:
			return new Rect(this.getMinX(), this.getMaxX(), this.getMinY()
					- size, this.getMaxY());
		default:
			return null;
		}
	}

	/*
	 * for CollectDataInRectViaNodeExEvent
	 */
	public Rect add(Rect rect, int direction) {
		double size = 0;
		switch (direction) {
		case Direction.LEFT:
		case Direction.RIGHT:
			size = rect.getWidth();
			break;
		case Direction.UP:
		case Direction.DOWN:
			size = rect.getHeight();
			break;
		default:
		}
		return this.add(size, direction);
	}
	
	public static Rect combineMBR(Rect rect1, Rect rect2) {
		double x1 = Math.min(rect1.getMinX(), rect2.getMinX());
		double x2 = Math.max(rect1.getMaxX(), rect2.getMaxX());
		double y1 = Math.min(rect1.getMinY(), rect2.getMinY());
		double y2 = Math.max(rect1.getMaxY(), rect2.getMaxY());
		return new Rect(x1,x2,y1,y2);
	}

	public boolean equals(Object obj) {
		if (obj instanceof Rect) {
			Rect rect1 = (Rect) obj;
			return rect1.getMinX() == this.getMinX()
					&& rect1.getMaxX() == this.getMaxX()
					&& rect1.getMinY() == this.getMinY()
					&& rect1.getMaxY() == this.getMaxY();
		} else
			return false;
	}

	public double getBeginBound(int direction) {
		switch (direction) {
		case Direction.LEFT:
			return this.getMaxX();
		case Direction.RIGHT:
			return this.getMinX();
		case Direction.UP:
			return this.getMinY();
		case Direction.DOWN:
			return this.getMaxY();
		default:
			return 0;
		}
	}

	public double getEndBound(int direction) {
		switch (direction) {
		case Direction.LEFT:
			return this.getMinX();
		case Direction.RIGHT:
			return this.getMaxX();
		case Direction.UP:
			return this.getMaxY();
		case Direction.DOWN:
			return this.getMinY();
		default:
			return 0;
		}

	}

	public Object clone() {
		return new Rect(this.getMinX(), this.getMaxX(), this.getMinY(), this
				.getMaxY());
	}
	
	public boolean intersects(Rect rect) {
		return this.toRectangle().intersects(rect.toRectangle());
	}
	
	public Rectangle2D.Double toRectangle() {
		return new Rectangle2D.Double(this.getMinX(), this.getMaxY(), this.getWidth(), this.getHeight());
	}

	public String toString() {
		/* xml style */
		/*
		 * StringBuffer sb = new StringBuffer(); sb.append("<Rectangle>");
		 * sb.append(this.p1); sb.append(this.p2); sb.append("</Rectangle>");
		 * return sb.toString();
		 */

		// [x1,x2,y1,y2] style
		StringBuffer sb = new StringBuffer();
		DecimalFormat df = new DecimalFormat("#.##");
		sb.append("Rect[");
		sb.append(df.format(this.getMinX()) + ",");
		sb.append(df.format(this.getMaxX()) + ",");
		sb.append(df.format(this.getMinY()) + ",");
		sb.append(df.format(this.getMaxY()) + "]");
		return sb.toString();
	}

}
