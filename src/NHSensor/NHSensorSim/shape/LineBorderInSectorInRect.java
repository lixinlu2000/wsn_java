package NHSensor.NHSensorSim.shape;

/*
 * This class is used to represent the initial shape in a SectorInRect
 */
public class LineBorderInSectorInRect extends Shape {
	Line begin;
	SectorInRect sectorInRect;

	public LineBorderInSectorInRect(Line begin, SectorInRect sectorInRect) {
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

	public Line getBegin() {
		return begin;
	}

	public void setBegin(Line begin) {
		this.begin = begin;
	}

	public SectorInRect getSectorInRect() {
		return sectorInRect;
	}

	public void setSectorInRect(SectorInRect sectorInRect) {
		this.sectorInRect = sectorInRect;
	}
}
