package NHSensor.NHSensorSim.core;

import java.util.*;

import NHSensor.NHSensorSim.algorithm.Algorithm;

public class CompositeAttachment extends Attachment {
	Hashtable attachments = new Hashtable();

	public CompositeAttachment(Node node, Algorithm algorithm, double energy) {
		super(node, algorithm, energy);
		// TODO Auto-generated constructor stub
	}

	public Hashtable getAttachments() {
		return attachments;
	}

	public void setAttachments(Hashtable attachments) {
		this.attachments = attachments;
	}

	public void addAttachment(String name, Attachment attachment) {
		if (this.getAttachments().get(name) != null) {
			System.out.println("attachment exist");
			return;
		}

		attachment.setEnergySource(this.getEnergySource());
		this.getAttachments().put(name, attachment);
	}

	public Attachment getAttachment(String name) {
		return (Attachment) this.getAttachments().get(name);
	}

}
