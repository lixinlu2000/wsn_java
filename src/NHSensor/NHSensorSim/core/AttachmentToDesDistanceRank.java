package NHSensor.NHSensorSim.core;

import NHSensor.NHSensorSim.shape.Position;

public class AttachmentToDesDistanceRank implements Rank {
	private Position destinationPos;

	public AttachmentToDesDistanceRank(Position destinationPos) {
		this.destinationPos = destinationPos;
	}

	public double getRank(Object object) {
		Attachment attachment = (Attachment) object;
		return 1 / this.destinationPos.distance(attachment.getNode().getPos());
	}

}
