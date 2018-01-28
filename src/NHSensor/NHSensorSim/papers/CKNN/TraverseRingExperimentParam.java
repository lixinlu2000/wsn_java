package NHSensor.NHSensorSim.papers.CKNN;

import NHSensor.NHSensorSim.experiment.SensornetExperimentParam;

public class TraverseRingExperimentParam extends SensornetExperimentParam {
	double nodeFailProbability;
	double ringLowRadius;
	double ringWidth;
	int nodeFailModelID;
	int failNodeNum;

	public TraverseRingExperimentParam() {

	}

	public TraverseRingExperimentParam(int networkID, int nodeNum,
			int queryMessageSize, int answerMessageSize, double networkWidth,
			double networkHeight, double radioRange,
			double nodeFailProbability, double ringLowRadius, double ringWidth,
			int nodeFailModelID, int failNodeNum) {
		this.networkID = networkID;
		this.nodeNum = nodeNum;
		this.queryMessageSize = queryMessageSize;
		this.answerMessageSize = answerMessageSize;
		this.nodeFailProbability = nodeFailProbability;
		this.ringLowRadius = ringLowRadius;
		this.ringWidth = ringWidth;
		this.nodeFailModelID = nodeFailModelID;
		this.failNodeNum = failNodeNum;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("networkID(" + this.networkID + ") ");
		sb.append("nodeNum(" + this.nodeNum + ") ");
		sb.append("queryMessageSize(" + this.queryMessageSize + ") ");
		sb.append("answerMessageSize(" + this.answerMessageSize + ") ");
		sb.append("networkWidth(" + this.networkWidth + ") ");
		sb.append("networkHeight(" + this.networkHeight + ") ");
		sb.append("nodeFailProbability(" + this.nodeFailProbability + ")");
		sb.append("ringLowRadius(" + this.ringLowRadius + ")");
		sb.append("ringWidth(" + this.ringWidth + ")");
		sb.append("failNodeNum(" + this.failNodeNum + ")");
		return sb.toString();
	}

	public double getNodeFailProbability() {
		return nodeFailProbability;
	}

	public void setNodeFailProbability(double nodeFailProbability) {
		this.nodeFailProbability = nodeFailProbability;
	}

	public double getRingLowRadius() {
		return ringLowRadius;
	}

	public void setRingLowRadius(double ringLowRadius) {
		this.ringLowRadius = ringLowRadius;
	}

	public double getRingWidth() {
		return ringWidth;
	}

	public void setRingWidth(double ringWidth) {
		this.ringWidth = ringWidth;
	}

	public double getRingHighRadius() {
		return this.ringLowRadius + this.ringWidth;
	}

	public int getNodeFailModelID() {
		return nodeFailModelID;
	}

	public void setNodeFailModelID(int nodeFailModelID) {
		this.nodeFailModelID = nodeFailModelID;
	}

	public int getFailNodeNum() {
		return failNodeNum;
	}

	public void setFailNodeNum(int failNodeNum) {
		this.failNodeNum = failNodeNum;
	}
}
