package NHSensor.NHSensorSim.core;

public class AttachmentEnergyRank implements Rank {

	public double getRank(Object object) {
		Attachment attachment = (Attachment) object;
		return attachment.getEnergy();
	}
}
