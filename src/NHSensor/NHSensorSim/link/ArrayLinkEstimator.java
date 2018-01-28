package NHSensor.NHSensorSim.link;

public class ArrayLinkEstimator extends LinkEstimator {
	private double[][] linkPRRs;

	public ArrayLinkEstimator(double[][] linkPRRs) {
		super();
		this.linkPRRs = linkPRRs;
	}

	/*
	 * nodeID1 --> nodeID2 packet reception rate
	 * 
	 * @see NHSensor.NHSensorSim.link.LinkEstimator#getLinkPRR(int, int)
	 */
	public double getLinkPRR(int nodeID1, int nodeID2) {
		// if(nodeID1<linkPRRs.length&&nodeID2<linkPRRs.length)
		return linkPRRs[nodeID2][nodeID1];
	}

}
