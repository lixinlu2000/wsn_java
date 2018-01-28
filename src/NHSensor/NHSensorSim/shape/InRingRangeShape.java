package NHSensor.NHSensorSim.shape;

public class InRingRangeShape extends RangeShape {
	Ring ring;

	public InRingRangeShape(Shape startShape, Shape endShape, Ring ring) {
		super(startShape, endShape);
		this.ring = ring;
	}

	public Ring getRing() {
		return ring;
	}

	public void setRing(Ring ring) {
		this.ring = ring;
	}

	public boolean contains(Position position) {
		// TODO Auto-generated method stub
		return false;
	}

	public Position getCentre() {
		// TODO Auto-generated method stub
		return null;
	}
}
