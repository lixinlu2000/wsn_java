package NHSensor.NHSensorSim.papers.RESA;

import NHSensor.NHSensorSim.experiment.ExperimentParam;
import NHSensor.NHSensorSim.link.LinkModel;
import NHSensor.NHSensorSim.util.ReflectUtil;

public class AllParam extends ExperimentParam {
	double pt = 30;
	double pld0 = 55;
	double d0 = 1;
	double n = 4;
	double sigma = 4;
	double pn = -105;

	double encodingRatio = 1;
	double frameLength = 50;
	double preambleLength;

	int networkID;
	int linkID;
	int nodeNum;
	double queryRegionRate;
	double gridWidthRate;
	int queryMessageSize;
	int answerMessageSize;
	double networkWidth = 100;
	double networkHeight = 100;
	int queryAndPatialAnswerSize;
	int resultSize;
	double radioRange = 30 * Math.sqrt(3);
	// double radioRange = 50;
	double nodeFailProbability;
	int k;

	public AllParam(int networkID, int nodeNum, double queryRegionRate,
			double gridWidthRate, int queryMessageSize, int answerMessageSize,
			int queryAndPatialAnswerSize, int resultSize, double radioRange,
			double nodeFailProbability, int k) {
		this.networkID = networkID;
		this.nodeNum = nodeNum;
		this.queryRegionRate = queryRegionRate;
		this.gridWidthRate = gridWidthRate;
		this.queryMessageSize = queryMessageSize;
		this.answerMessageSize = answerMessageSize;
		this.queryAndPatialAnswerSize = queryAndPatialAnswerSize;
		this.resultSize = resultSize;
		this.radioRange = radioRange;
		this.nodeFailProbability = nodeFailProbability;
		this.k = k;
	}

	public double calculateNodeDensity() {
		return this.nodeNum / this.networkWidth / this.networkHeight;
	}

	public String toString() {
		// StringBuffer sb = new StringBuffer();
		// sb.append("networkID("+this.networkID+") ");
		// sb.append("nodeNum("+this.nodeNum+") ");
		// sb.append("queryRegionRate("+this.queryRegionRate+") ");
		// sb.append("gridWidthRate("+this.gridWidthRate+") ");
		// sb.append("queryMessageSize("+this.queryMessageSize+") ");
		// sb.append("answerMessageSize("+this.answerMessageSize+") ");
		// sb.append("networkWidth("+this.networkWidth+") ");
		// sb.append("networkHeight("+this.networkHeight+") ");
		// sb.append("queryAndPatialAnswerSize("+this.queryAndPatialAnswerSize+
		// ") ");
		// sb.append("resultSize("+this.resultSize+") ");
		// sb.append("nodeFailProbability("+this.nodeFailProbability+") ");
		// sb.append("k("+this.k+")");
		// return sb.toString();
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

	public static AllParam fromString(String str) throws Exception {
		return (AllParam) ReflectUtil.stringToObject(str, AllParam.class);
	}

	public double getNodeFailProbability() {
		return nodeFailProbability;
	}

	public void setNodeFailProbability(double nodeFailProbability) {
		this.nodeFailProbability = nodeFailProbability;
	}

	public int getLinkID() {
		return linkID;
	}

	public void setLinkID(int linkID) {
		this.linkID = linkID;
	}

	public double getRadioRange() {
		return radioRange;
	}

	public void setRadioRange(double radioRange) {
		this.radioRange = radioRange;
	}

	public int getQueryAndPatialAnswerSize() {
		return queryAndPatialAnswerSize;
	}

	public void setQueryAndPatialAnswerSize(int queryAndPatialAnswerSize) {
		this.queryAndPatialAnswerSize = queryAndPatialAnswerSize;
	}

	public int getResultSize() {
		return resultSize;
	}

	public void setResultSize(int resultSize) {
		this.resultSize = resultSize;
	}

	public AllParam() {

	}

	public int getQueryMessageSize() {
		return queryMessageSize;
	}

	public void setQueryMessageSize(int queryMessageSize) {
		this.queryMessageSize = queryMessageSize;
	}

	public int getAnswerMessageSize() {
		return answerMessageSize;
	}

	public void setAnswerMessageSize(int answerMessageSize) {
		this.answerMessageSize = answerMessageSize;
	}

	public double getNetworkWidth() {
		return networkWidth;
	}

	public void setNetworkWidth(double networkWidth) {
		this.networkWidth = networkWidth;
	}

	public double getNetworkHeight() {
		return networkHeight;
	}

	public void setNetworkHeight(double networkHeight) {
		this.networkHeight = networkHeight;
	}

	public double getGridWidthRate() {
		return gridWidthRate;
	}

	public void setGridWidthRate(double gridWidthRate) {
		this.gridWidthRate = gridWidthRate;
	}

	public int getNetworkID() {
		return networkID;
	}

	public void setNetworkID(int networkID) {
		this.networkID = networkID;
	}

	public int getNodeNum() {
		return nodeNum;
	}

	public void setNodeNum(int nodeNum) {
		this.nodeNum = nodeNum;
	}

	public double getQueryRegionRate() {
		return queryRegionRate;
	}

	public void setQueryRegionRate(double queryRegionRate) {
		this.queryRegionRate = queryRegionRate;
	}

	public int getK() {
		return k;
	}

	public void setK(int k) {
		this.k = k;
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
}
