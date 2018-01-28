package NHSensor.NHSensorSim.shape;

import java.awt.geom.Path2D;

public class LineLineInSectorInRect extends Shape {
	Line begin;
	Line end;
	SectorInRect sectorInRect;
	int direction;

	public LineLineInSectorInRect(Line begin, Line end,
			SectorInRect sectorInRect, int direction) {
		super();
		this.begin = begin;
		this.end = end;
		this.sectorInRect = sectorInRect;
		this.direction = direction;
	}

	public boolean containsPosInSector(Position position) {
		if (this.direction == SectorDirection.FAR) {
			return !begin.lowerThan(position) && !end.higherThan(position);
		} else {
			return !begin.higherThan(position) && !end.lowerThan(position);
		}
	}

	public boolean containsOld(Position position) {
		if (!this.sectorInRect.contains(position))
			return false;
		else {
			if (this.direction == SectorDirection.FAR) {
				return !begin.lowerThan(position) && !end.higherThan(position);
			} else {
				return !begin.higherThan(position) && !end.lowerThan(position);
			}
		}
	}

	public boolean contains(Position position) {
		if (!this.sectorInRect.contains(position))
			return false;
		else {
//			if (this.direction == SectorDirection.FAR) {
//				return !begin.lowerThan(position) && !end.higherThan(position);
//			} else {
//				return !begin.higherThan(position) && !end.lowerThan(position);
//			}
			if (this.direction == SectorDirection.FAR) {
				if(end.isVerticalLine()) return !begin.lowerThan(position);
			} else {
				if(begin.isVerticalLine()) return !end.lowerThan(position);
			}

			Path2D.Double path = this.toPath2DDouble();
			return path.contains(position.getX(), position.getY());
		}
	}

	public Sector getSector() {
		return this.getSectorInRect().getSector();
	}

	public Position getCentre() {
		double x = this.getBB().getX() + this.getBE().getX()
				+ this.getEB().getX() + this.getEE().getX();
		x /= 4;
		double y = this.getBB().getY() + this.getBE().getY()
				+ this.getEB().getY() + this.getEE().getY();
		y /= 4;
		return new Position(x, y);
	}

	public Position[] getVertexs() {
		Position[] vertexs = new Position[4];
		vertexs[0] = this.getBB();
		vertexs[1] = this.getBE();
		vertexs[2] = this.getEE();
		vertexs[3] = this.getEB();
		return vertexs;
	}

	public Line getBegin() {
		return begin;
	}

	public void setBegin(Line begin) {
		this.begin = begin;
	}

	public Line getEnd() {
		return end;
	}

	public void setEnd(Line end) {
		this.end = end;
	}

	public SectorInRect getSectorInRect() {
		return sectorInRect;
	}

	public void setSectorInRect(SectorInRect sectorInRect) {
		this.sectorInRect = sectorInRect;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	/*
	 * Begin Begin position
	 */
	public Position getBB() {
		return this.getSector().getBeginLine().intersect(this.getBegin());
	}

	/*
	 * Begin End position
	 */
	public Position getBE() {
		return this.getSector().getBeginLine().intersect(this.getEnd());

	}

	/*
	 * End Begin position
	 */
	public Position getEB() {
		return this.getSector().getEndLine().intersect(this.getBegin());
	}

	/*
	 * End End position
	 */
	public Position getEE() {
		return this.getSector().getEndLine().intersect(this.getEnd());
	}
	
	public Path2D.Double toPath2DDouble() {
		Path2D.Double p = new Path2D.Double();
		Position[] ps = this.getVertexs();
		
		p.moveTo(ps[3].getX(), ps[3].getY());
		for(int i=0;i<ps.length;i++) {
			p.lineTo(ps[i].getX(), ps[i].getY());
		}
		return p;
	}

	public LineSegment[] getLineSegments() {
		LineSegment[] ls = new LineSegment[4];
		ls[0] = new LineSegment(this.getBB(), this.getEB());
		ls[1] = new LineSegment(this.getEB(), this.getEE());
		ls[2] = new LineSegment(this.getEE(), this.getBE());
		ls[3] = new LineSegment(this.getBE(), this.getBB());
		return ls;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("LineLineInSectorInRect(");
		sb.append(this.getBB());
		;
		sb.append(" " + this.getEB());
		sb.append(" " + this.getBE());
		sb.append(" " + this.getEE());
		sb.append(")");
		return sb.toString();
	}
}
