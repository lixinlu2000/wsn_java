package NHSensor.NHSensorSim.shape;

import java.util.Arrays;
import java.util.Collections;
import java.util.Vector;

import NHSensor.NHSensorSim.exception.IntersectException;
import NHSensor.NHSensorSim.shapeTraverse.RelativeDistanceAlongDirectionComparatorForPositions;

public class SectorInRect extends Shape {
	Rect rect;
	Sector sector;
	protected int border;
	protected int endBorder;

	public SectorInRect(Sector sector, Rect rect) {
		super();
		this.sector = sector;
		this.rect = rect;
		this.initBorder();
	}

	protected void initBorder() {
		Position centre = sector.getCentre();
		double alpha1 = centre.bearing(this.rect.getLT());
		double alpha2 = centre.bearing(this.rect.getLB());
		double alpha3 = centre.bearing(this.rect.getRB());

		double beta1 = Radian.relativeTo(alpha2, alpha1);
		double beta2 = Radian.relativeTo(alpha3, alpha1);

		double beta3 = Radian.relativeTo(this.sector.getStartRadian(), alpha1);
		if (beta3 < beta1) {
			this.border = BorderType.LEFT;
		} else if (beta3 >= beta1 && beta3 < beta2) {
			this.border = BorderType.CENTRE;
		} else
			this.border = BorderType.RIGHT;

		double beta4 = Radian.relativeTo(this.sector.getStartRadian()
				+ this.sector.getSita(), alpha1);
		if (beta4 < beta1) {
			this.endBorder = BorderType.LEFT;
		} else if (beta4 >= beta1 && beta4 < beta2) {
			this.endBorder = BorderType.CENTRE;
		} else
			this.endBorder = BorderType.RIGHT;
	}
	
	public boolean isSameBorder() {
		return this.border == this.endBorder;
	}

	public boolean contains(Position position) {
		return this.sector.contains(position)
				&& this.getRect().contains(position);
	}
	
	public LineLineInSectorInRect caculateLineLineInSectorInRect(Position pos,
			int sectorDirection) {
		double minor = 0.001;
		Line beginLine = this.caculateInitialLine(sectorDirection);
		if(sectorDirection==SectorDirection.NEAR) {
			minor = -minor;
		}

		PolarPosition pp = new PolarPosition(this.getSector().getCentre(), 
				this.getSector().getCentre().distance(pos)+minor, this.getSector().getCentre().bearing(pos));
		
		Line endLine = new Line(pp.toPosition(), this.getSector().diagonalRadian()
				+ Math.PI / 2);
		return new LineLineInSectorInRect(beginLine, endLine, this, sectorDirection);
	}

	public Position getCentre() {
		double x = this.getBN().getX() + this.getBF().getX()
				+ this.getEN().getX() + this.getEF().getX();
		x /= 4;
		double y = this.getBN().getY() + this.getBF().getY()
				+ this.getEN().getY() + this.getEF().getY();
		y /= 4;
		return new Position(x, y);
	}

	public Rect getRect() {
		return rect;
	}

	public void setRect(Rect rect) {
		this.rect = rect;
	}

	public Sector getSector() {
		return sector;
	}

	public void setSector(Sector sector) {
		this.sector = sector;
	}

	/*
	 * line cur and line next compose of a polygon, any two point in it is less
	 * than the radius of communicaion
	 */
	public Line caculateNextLine(Line cur, double radius, int sectorDirection)
			throws IntersectException {
		Position[] ps = new Position[4];
		Position p1, p2;
		p1 = cur.intersect(this.getSector().getBeginLine());
		p2 = cur.intersect(this.getSector().getEndLine());
		
		if (p1.distance(p2)>= radius) {
			throw new IntersectException();
		}

		Circle cBegin = new Circle(p1, radius);
		Circle cEnd = new Circle(p2, radius);

		if (sectorDirection == SectorDirection.FAR) {
			ps[0] = this.getSector().getBeginFar(cBegin);
			ps[1] = this.getSector().getBeginFar(cEnd);
			ps[2] = this.getSector().getEndFar(cBegin);
			ps[3] = this.getSector().getEndFar(cEnd);
		} else {
			ps[0] = this.getSector().getBeginNear(cBegin);
			ps[1] = this.getSector().getBeginNear(cEnd);
			ps[2] = this.getSector().getEndNear(cBegin);
			ps[3] = this.getSector().getEndNear(cEnd);
		}

		Arrays.sort(ps,
				new RelativeDistanceAlongDirectionComparatorForPositions(this
						.getSector().getCentre(), this.getSector()
						.diagonalRadian()));
		
		Position nextP;
		if(sectorDirection == SectorDirection.FAR) {
			nextP = ps[0];
		}
		else nextP = ps[3];
		Line nextLine = new Line(nextP, this.getSector().diagonalRadian()
				+ Math.PI / 2);
		return nextLine;
	}

	/*
	 * just for bypassing void region
	 */
	public Line caculateNextLineEx(Line cur, double radius, int sectorDirection) {
		Vector ps = new Vector();
		Circle cBegin = new Circle(cur.intersect(this.getSector()
				.getBeginLine()), radius);
		Circle cEnd = new Circle(cur.intersect(this.getSector().getEndLine()),
				radius);

		try {
			if (sectorDirection == SectorDirection.FAR) {
				ps.add(this.getSector().getBeginFar(cBegin));
				ps.add(this.getSector().getEndFar(cEnd));
				try {
					ps.add(this.getSector().getBeginFar(cEnd));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
				try {
					ps.add(this.getSector().getEndFar(cBegin));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
			} else {
				ps.add(this.getSector().getBeginNear(cBegin));
				ps.add(this.getSector().getEndNear(cEnd));
				try {
					ps.add(this.getSector().getBeginNear(cEnd));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
				try {
					ps.add(this.getSector().getEndNear(cBegin));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
			}
		} catch (IntersectException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
		
		Collections.sort(ps,
				new RelativeDistanceAlongDirectionComparatorForPositions(this
						.getSector().getCentre(), this.getSector()
						.diagonalRadian()));
		
		Position pos;
		if(sectorDirection == SectorDirection.FAR) {
			pos = (Position)ps.firstElement();
		}
		else pos = (Position)ps.lastElement();
		Line nextLine = new Line(pos, this.getSector().diagonalRadian()
				+ Math.PI / 2);
		return nextLine;
	}

	/*
	 * any two point in LineLineInSectorInRect returned is less than the radius
	 * of communicaion
	 */
	public LineLineInSectorInRect caculateNextShape(Line cur, double radius,
			int sectorDirection) {
		Line nextLine;
		try {
			nextLine = this.caculateNextLine(cur, radius, sectorDirection);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			nextLine = this.caculateNextLineEx(cur, radius, sectorDirection);
		}
		return new LineLineInSectorInRect(cur, nextLine, this, sectorDirection);
	}
	
	public LineLineInSectorInRect caculateInitialShapeWithBorder(double radius, int sectorDirection) {
		Position pos = null;
		Line line = null;
		
		if(this.border==BorderType.LEFT) {
			pos = this.getRect().getLB();
		}
		else if(this.border==BorderType.CENTRE) {
			pos = this.getRect().getRB();
		}
		line = new Line(pos, this.getSector().diagonalRadian()
				+ Math.PI / 2);
		
		Line nextLine;
		try {
			nextLine = this.caculateNextLine(line, radius, sectorDirection);
		} catch (Exception e) {
			nextLine = this.caculateNextLineEx(line, radius, sectorDirection);
		}
		return new LineLineInSectorInRect(line, nextLine, this, sectorDirection);
	}

	protected Line nextLine(Line cur, int direction, double moveDistance) {
		double k = cur.getK();
		double delta = moveDistance * Math.sqrt(1 + k * k);
		double b;

		int sign1 = 1, sign2 = 1;
		if (direction == SectorDirection.NEAR)
			sign1 = -1;
		if (k < 0)
			sign2 = -1;

		if (sign1 * sign2 == 1) {
			b = cur.getB() + delta;
		} else {
			b = cur.getB() - delta;
		}

		return new Line(new Position(0, b), cur.getAlpha());
	}

	public Line caculateNearLine() {
		return new Line(this.getRect().getLT(), new Radian(0));
	}

	/*
	 * caculateFarLine for begin line of the sector
	 */
	public Line caculateFarLine() {
		if (this.border == BorderType.LEFT) {
			return new Line(this.getRect().getLT(), new Radian(1.5 * Math.PI));
		} else if (this.border == BorderType.RIGHT) {
			return new Line(this.getRect().getRT(), new Radian(1.5 * Math.PI));
		} else {// this.border==BorderType.CENTRE
			return new Line(this.getRect().getLB(), new Radian(0));
		}
	}

	/*
	 * caculateFarLine for end line of the sector
	 */
	public Line caculateFarLineForEnd() {
		if (this.endBorder == BorderType.LEFT) {
			return new Line(this.getRect().getLT(), new Radian(1.5 * Math.PI));
		} else if (this.endBorder == BorderType.RIGHT) {
			return new Line(this.getRect().getRT(), new Radian(1.5 * Math.PI));
		} else {// this.endBorder==BorderType.CENTRE
			return new Line(this.getRect().getLB(), new Radian(0));
		}
	}

	public Line caculateInitialLine(int direction) {
		if (direction == SectorDirection.FAR) {
			return this.caculateNearLine();
		} else
			return this.caculateFarLine();
	}
	
	public LineLineInSectorInRect getInitialShape(int direction, double radioRange) throws IntersectException {
		Line initialLine = this.caculateInitialLine(
				direction);

		return this.caculateNextShape(initialLine, radioRange,
				direction);
	}
	
	public LineLineInSectorInRect nextShape(LineLineInSectorInRect curShape, int direction, double radioRange) {
		return this.caculateNextShape(curShape.getEnd(), radioRange,
						direction);
	}

	/*
	 * Begin Near position
	 */
	public Position getBN() {
		return this.getSector().getBeginLine().intersect(
				this.caculateNearLine());
	}

	/*
	 * Begin Far position
	 */
	public Position getBF() {
		return this.getSector().getBeginLine()
				.intersect(this.caculateFarLine());

	}

	/*
	 * End Near position
	 */
	public Position getEN() {
		return this.getSector().getEndLine().intersect(this.caculateNearLine());
	}

	/*
	 * End Far position
	 */
	public Position getEF() {
		return this.getSector().getEndLine().intersect(
				this.caculateFarLineForEnd());
	}

	public LineSegment getBeginLineSegment() {
		return new LineSegment(this.sector.getCentre(), this.getBF());
	}

	/*
	 * return the radian between begin line and border
	 */
	public double getBeginLineBorderAlpha() {
		double alpha1 = this.getBF().bearing(this.getSector().getCentre());

		if (this.border == BorderType.LEFT) {
			return Radian.relativeTo(alpha1, -0.5 * Math.PI);
		} else if (this.border == BorderType.RIGHT) {
			return Radian.relativeTo(alpha1, 0.5 * Math.PI);
		} else {// this.border==BorderType.CENTRE
			return Radian.relativeTo(alpha1, 0);
		}
	}
	
	public boolean isEnd(LineLineInSectorInRect curShape, int direction) {
		Line endLine = curShape.getEnd();

		Position p1, p2, p3, p4, p5, p6;
		p1 = this.getBF();
		p2 = this.getEF();
		p3 = this.getBN();
		p4 = this.getEN();
		p5 = endLine.intersect(this.getSector().getBeginLine());
		p6 = endLine.intersect(this.getSector().getEndLine());

		if (direction == SectorDirection.FAR) {
			if (p5.distance(p3) >= p1.distance(p3)
					&& p6.distance(p4) >= p2.distance(p4))
				return true;
			else
				return false;
		} else {
			if (p5.distance(p1) >= p3.distance(p1)
					&& p6.distance(p2) >= p4.distance(p2))
				return true;
			else
				return false;
		}
	}

	public LineSegment getEndLineSegment() {
		return new LineSegment(this.sector.getCentre(), this.getEF());
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("SectorInRect(");
		sb.append(this.sector);
		sb.append(this.rect);
		sb.append(")");
		return sb.toString();
	}

}
