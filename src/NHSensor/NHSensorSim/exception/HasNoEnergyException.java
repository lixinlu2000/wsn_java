package NHSensor.NHSensorSim.exception;

import NHSensor.NHSensorSim.core.Attachment;

public class HasNoEnergyException extends SensornetBaseException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5240145522753649169L;
	private Attachment attachment;

	public Attachment getAttachment() {
		return attachment;
	}

	public void setAttachment(Attachment attachment) {
		this.attachment = attachment;
	}

	public HasNoEnergyException() {
		// TODO Auto-generated constructor stub
	}

	public HasNoEnergyException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public HasNoEnergyException(Attachment attachment) {
		this.attachment = attachment;
	}

}
