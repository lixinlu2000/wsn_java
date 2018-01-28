package NHSensor.NHSensorSim.core;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class EnergySource  implements Serializable{
	static Logger logger = Logger.getLogger(EnergySource.class);
	protected double energy = 1000;

	public EnergySource(double energy) {
		this.energy = energy;
	}

	public double getEnergy() {
		return energy;
	}

	public void setEnergy(double energy) {
		this.energy = energy;
	}

	public boolean consumeEnergy(double e) {
		if (this.energy >= e) {
			this.energy -= e;
			return true;
		} else {
			logger.error("energy is dry");
			return false;
		}
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("<EnergySouce>");
		sb.append("<energy>");
		sb.append(this.getEnergy());
		sb.append("</energy>");
		sb.append("</EnergySouce>");
		return sb.toString();
	}

}
