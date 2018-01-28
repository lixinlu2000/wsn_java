package NHSensor.NHSensorSim.shape;

public class CircleBorderInSectorInRect extends Shape {
	Circle begin;
	SectorInRect sectorInRect;

	public CircleBorderInSectorInRect(Circle begin, SectorInRect sectorInRect) {
		super();
		this.begin = begin;
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

	public SectorInRect getSectorInRect() {
		return sectorInRect;
	}

	public void setSectorInRect(SectorInRect sectorInRect) {
		this.sectorInRect = sectorInRect;
	}
}
