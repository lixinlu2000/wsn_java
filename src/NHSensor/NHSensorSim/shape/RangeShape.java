package NHSensor.NHSensorSim.shape;

public class RangeShape extends Shape {
	Shape startShape;
	Shape endShape;

	public RangeShape(Shape startShape, Shape endShape) {
		super();
		this.startShape = startShape;
		this.endShape = endShape;
	}

	public Shape getStartShape() {
		return startShape;
	}

	public void setStartShape(Shape startShape) {
		this.startShape = startShape;
	}

	public Shape getEndShape() {
		return endShape;
	}

	public void setEndShape(Shape endShape) {
		this.endShape = endShape;
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
