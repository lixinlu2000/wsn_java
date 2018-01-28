package NHSensor.NHSensorSim.core;

public class AlgBlackBox {
	private Attachment AttachmentWhichHasNoEnergy = null;

	public AlgBlackBox() {
	}

	public Attachment getAttachmentWhichHasNoEnergy() {
		return AttachmentWhichHasNoEnergy;
	}

	public void setAttachmentWhichHasNoEnergy(
			Attachment attachmentWhichHasNoEnergy) {
		AttachmentWhichHasNoEnergy = attachmentWhichHasNoEnergy;
	}

	public boolean isHasNoEnergy() {
		if (this.getAttachmentWhichHasNoEnergy() == null)
			return true;
		else
			return false;
	}
}
