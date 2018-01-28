package NHSensor.NHSensorSim.exception;

import NHSensor.NHSensorSim.core.Attachment;

public class NodeFailureErrorException extends SensornetBaseException {
	/**
	 * 
	 */
	private Attachment attachment;

	public Attachment getAttachment() {
		return attachment;
	}

	public void setAttachment(Attachment attachment) {
		this.attachment = attachment;
	}

	public NodeFailureErrorException() {
		// TODO Auto-generated constructor stub
	}

	public NodeFailureErrorException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public NodeFailureErrorException(Attachment attachment) {
		this.attachment = attachment;
	}

}
