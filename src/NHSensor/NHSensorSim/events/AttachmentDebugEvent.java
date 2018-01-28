package NHSensor.NHSensorSim.events;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Attachment;
import NHSensor.NHSensorSim.core.Node;
import NHSensor.NHSensorSim.exception.SensornetBaseException;

public class AttachmentDebugEvent extends DebugEvent {
	Attachment attachment;

	public AttachmentDebugEvent(Attachment attachment, Algorithm alg) {
		super(alg);
		this.attachment = attachment;
	}

	public AttachmentDebugEvent(Algorithm alg, int tag) {
		super(alg, tag);
	}

	public void handle() throws SensornetBaseException {
		// TODO Auto-generated method stub

	}

	public Attachment getAttachment() {
		return attachment;
	}

	public void setAttachment(Attachment attachment) {
		this.attachment = attachment;
	}

	public String toString() {
		return "AttachmentDebugEvent Attachment: "
				+ this.attachment.getNode().getPos();
	}

	public Node getNode() {
		return this.attachment.getNode();
	}
}
