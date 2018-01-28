package NHSensor.NHSensorSim.shape;

public class LineCircleInSectorInRect extends Shape {
	Line begin;
	Circle end;
	SectorInRect sectorInRect;
	int direction;

	public LineCircleInSectorInRect(Line begin, Circle end,
			SectorInRect sectorInRect, int direction) {
		super();
		this.begin = begin;
		this.end = end;
		this.sectorInRect = sectorInRect;
		this.direction = direction;
	}

	public boolean contains(Position position) {
		if (!this.sectorInRect.contains(position)
				|| !this.end.contains(position))
			return false;
		else {
			if (this.direction == SectorDirection.FAR) {
				return !begin.lowerThan(position);
			} else {
				return !begin.higherThan(position);
			}
		}
	}

	public Position getCentre() {
		// TODO Auto-generated method stub
		return null;
	}

	public Line getBegin() {
		return begin;
	}

	public void setBegin(Line begin) {
		this.begin = begin;
	}

	public Circle getEnd() {
		return end;
	}

	public void setEnd(Circle end) {
		this.end = end;
	}

	public SectorInRect getSectorInRect() {
		return sectorInRect;
	}

	public void setSectorInRect(SectorInRect sectorInRect) {
		this.sectorInRect = sectorInRect;
	}
}
