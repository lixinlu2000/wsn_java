package NHSensor.NHSensorSim.link;

public class FullConnectedLinkEstimator extends LinkEstimator {

	public FullConnectedLinkEstimator() {
	}

	public double getLinkPRR(int nodeID1, int nodeID2) {
		return 1;
	}

}
