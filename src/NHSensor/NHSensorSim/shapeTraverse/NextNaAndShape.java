package NHSensor.NHSensorSim.shapeTraverse;

import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.shape.Shape;

public class NextNaAndShape {
	NeighborAttachment nextNa;
	Shape nextShape;
	Shape shapeCalNextNa = null;

	public NextNaAndShape(NeighborAttachment nextNa, Shape nextShape) {
		super();
		this.nextNa = nextNa;
		this.nextShape = nextShape;
	}

	public NextNaAndShape(NeighborAttachment nextNa, Shape nextShape,
			Shape shapeCalNextNa) {
		super();
		this.nextNa = nextNa;
		this.nextShape = nextShape;
		this.shapeCalNextNa = shapeCalNextNa;
	}



	public NeighborAttachment getNextNa() {
		return nextNa;
	}

	public void setNextNa(NeighborAttachment nextNa) {
		this.nextNa = nextNa;
	}

	public Shape getNextShape() {
		return nextShape;
	}

	public void setNextShape(Shape nextShape) {
		this.nextShape = nextShape;
	}

	public Shape getShapeCalNextNa() {
		return shapeCalNextNa;
	}

	public void setShapeCalNextNa(Shape shapeCalNextNa) {
		this.shapeCalNextNa = shapeCalNextNa;
	}
}
