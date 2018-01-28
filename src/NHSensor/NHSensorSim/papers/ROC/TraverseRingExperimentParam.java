package NHSensor.NHSensorSim.papers.ROC;

import NHSensor.NHSensorSim.experiment.SensornetExperimentParam;

public class TraverseRingExperimentParam extends SensornetExperimentParam {
	double nodeFailProbability;
	double ringLowRadius;
	double ringWidth;

	public TraverseRingExperimentParam() {

	}

	public TraverseRingExperimentParam(int networkID, int nodeNum,
			int queryMessageSize, int answerMessageSize, double networkWidth,
			double networkHeight, double radioRange,
			double nodeFailProbability, double ringLowRadius, double ringWidth) {
		this.networkID = networkID;
		this.nodeNum = nodeNum;
		this.queryMessageSize = queryMessageSize;
		this.answerMessageSize = answerMessageSize;
		this.nodeFailProbability = nodeFailProbability;
		this.ringLowRadius = ringLowRadius;
		this.ringWidth = ringWidth;
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
}
