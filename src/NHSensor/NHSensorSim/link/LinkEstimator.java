package NHSensor.NHSensorSim.link;

import java.io.Serializable;

public abstract class LinkEstimator implements Serializable{
	public abstract double getLinkPRR(int nodeID1, int nodeID2);
}
