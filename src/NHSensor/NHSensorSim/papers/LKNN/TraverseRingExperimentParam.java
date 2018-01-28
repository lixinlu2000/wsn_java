package NHSensor.NHSensorSim.papers.LKNN;

import NHSensor.NHSensorSim.experiment.SensornetExperimentParam;
import NHSensor.NHSensorSim.link.LinkModel;
import NHSensor.NHSensorSim.util.ReflectUtil;

public class TraverseRingExperimentParam extends SensornetExperimentParam {
	double ringLowRadius;
	double ringWidth;

	double pt = 0;
	double pld0 = 55;
	double d0 = 1;
	double n = 4;
	double sigma = 4;
	double pn = -105;

	double encodingRatio = 1;
	double frameLength = 50;
	double preambleLength;

	int linkID;
	double radioRange = 30 * Math.sqrt(3);
	int holeNumber;
	int holeModelID;
	double maxHoleRadius;

	public double getRadioRange() {
		return radioRange;
	}

	public void setRadioRange(double radioRange) {
		this.radioRange = radioRange;
	}

	public TraverseRingExperimentParam() {

	}

	public String toString() {
		try {
			return ReflectUtil.objectToString(this);
		} catch (Exception e) {
			e.printStackTrace();
			return e.toString();
		}
	}

	public LinkModel createLinkModel() {
		return new LinkModel(d0, n, pld0, pt, pn, sigma, encodingRatio,
				frameLength);
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

	public double getPt() {
		return pt;
	}

	public void setPt(double pt) {
		this.pt = pt;
	}

	public double getPld0() {
		return pld0;
	}

	public void setPld0(double pld0) {
		this.pld0 = pld0;
	}

	public double getD0() {
		return d0;
	}

	public void setD0(double d0) {
		this.d0 = d0;
	}

	public double getN() {
		return n;
	}

	public void setN(double n) {
		this.n = n;
	}

	public double getSigma() {
		return sigma;
	}

	public void setSigma(double sigma) {
		this.sigma = sigma;
	}

	public double getPn() {
		return pn;
	}

	public void setPn(double pn) {
		this.pn = pn;
	}

	public double getEncodingRatio() {
		return encodingRatio;
	}

	public void setEncodingRatio(double encodingRatio) {
		this.encodingRatio = encodingRatio;
	}

	public double getFrameLength() {
		return frameLength;
	}

	public void setFrameLength(double frameLength) {
		this.frameLength = frameLength;
	}

	public double getPreambleLength() {
		return preambleLength;
	}

	public void setPreambleLength(double preambleLength) {
		this.preambleLength = preambleLength;
	}

	public int getLinkID() {
		return linkID;
	}

	public void setLinkID(int linkID) {
		this.linkID = linkID;
	}
	
	public int getHoleNumber() {
		return holeNumber;
	}

	public void setHoleNumber(int holeNumber) {
		this.holeNumber = holeNumber;
	}

	public int getHoleModelID() {
		return holeModelID;
	}

	public void setHoleModelID(int holeModelID) {
		this.holeModelID = holeModelID;
	}

	public double getMaxHoleRadius() {
		return maxHoleRadius;
	}

	public void setMaxHoleRadius(double maxHoleRadius) {
		this.maxHoleRadius = maxHoleRadius;
	}

	public static TraverseRingExperimentParam fromString(String str) throws Exception {
		return (TraverseRingExperimentParam) ReflectUtil.stringToObject(str, TraverseRingExperimentParam.class);
	}
}
