package NHSensor.NHSensorSim.core;

import NHSensor.NHSensorSim.shape.Shape;

public class AttachmentInShapeCondition implements Condition {
	private Shape shape;

	public AttachmentInShapeCondition(Shape shape) {
		this.shape = shape;
	}

	public boolean isTrue(Object attachment) {
		Attachment a = (Attachment) attachment;
		return this.getShape().contains(a.getNode().getPos());
	}

	public Shape getShape() {
		return shape;
	}

	public void setShape(Shape shape) {
		this.shape = shape;
	}

}
