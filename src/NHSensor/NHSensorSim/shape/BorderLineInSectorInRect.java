package NHSensor.NHSensorSim.shape;

/*
 * This class is used to represent the initial shape in a SectorInRect
 */
public class BorderLineInSectorInRect extends Shape {
	Line end;
	SectorInRect sectorInRect;
	int direction;

	public BorderLineInSectorInRect(Line end, SectorInRect sectorInRect, int direction) {
		super();
		this.end = end;
		this.sectorInRect = sectorInRect;
		this.direction = direction;
	}

	public boolean contains(Position position) {
		if (!this.sectorInRect.contains(position))
			return false;
		else {
			if (this.direction == SectorDirection.FAR) {
				return !end.higherThan(position);
			} else {
				return !end.lowerThan(position);
			}
		}
	}

	public Position getCentre() {
		// TODO Auto-generated method stub
		return null;
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
}
