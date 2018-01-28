package NHSensor.NHSensorSim.papers.RISC;

import NHSensor.NHSensorSim.experiment.SensornetExperimentParam;

public class TraverseRingExperimentParam extends SensornetExperimentParam {
	double nodeFailProbability;
	double sita;
	double ringLowRadius;
	double ringWidth;

	// derived parameter
	double ringSectorArea;
	double ringSectorNodeNum;

	public TraverseRingExperimentParam() {

	}

	public TraverseRingExperimentParam(int networkID, int nodeNum,
			int queryMessageSize, int answerMessageSize, double networkWidth,
			double networkHeight, double radioRange,
			double nodeFailProbability, double sita, double ringLowRadius,
			double ringWidth) {
		this.networkID = networkID;
		this.nodeNum = nodeNum;
		this.queryMessageSize = queryMessageSize;
		this.answerMessageSize = answerMessageSize;
		this.nodeFailProbability = nodeFailProbability;
		this.sita = sita;
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
		sb.append("sita(" + this.sita + ")");
		return sb.toString();
	}

	public double getNodeFailProbability() {
		return nodeFailProbability;
	}

	public void setNodeFailProbability(double nodeFailProbability) {
		this.nodeFailProbability = nodeFailProbability;
	}

	public double getSita() {
		return sita;
	}

	public void setSita(double sita) {
		this.sita = sita;
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

	public double getRingSectorArea() {
		double highRadius = this.getRingLowRadius() + this.getRingWidth();
		this.ringSectorArea = this.getSita()
				/ 2
				* (highRadius * highRadius - this.ringLowRadius
						* this.ringLowRadius);
		return ringSectorArea;
	}

	public double getRingSectorNodeNum() {
		double rsa = this.getRingSectorArea();
		double ringSectorNodeNum = this.getNodeDensity() * rsa;
		return ringSectorNodeNum;
	}

}
