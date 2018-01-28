package NHSensor.NHSensorSim.shape;

public class CircleLineInSectorInRect extends Shape {
	Circle begin;
	Line end;
	SectorInRect sectorInRect;

	public CircleLineInSectorInRect(Circle begin, Line end,
			SectorInRect sectorInRect) {
		super();
		this.begin = begin;
		this.end = end;
		this.sectorInRect = sectorInRect;
	}

	public boolean contains(Position position) {
		// TODO Auto-generated method stub
		return false;
	}

	public Position getCentre() {
		// TODO Auto-generated method stub
		return null;
	}

	public Circle getBegin() {
		return begin;
	}

	public void setBegin(Circle begin) {
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
}
