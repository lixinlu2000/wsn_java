package NHSensor.NHSensorSim.papers.EST;

import NHSensor.NHSensorSim.experiment.ExperimentParam;
import NHSensor.NHSensorSim.util.ReflectUtil;

public class AllParam extends ExperimentParam {
	int networkID;
	int nodeNum;
	double queryRegionRate;
	double gridWidthRate;// sub query region number
	int queryMessageSize;
	int answerMessageSize;
	// TODO allparam networkWidth networkHeight is not set
	double networkWidth = 450;
	double networkHeight = 450;
	int queryAndPatialAnswerSize;
	int resultSize;
	// TODO allparam networkWidth networkHeight is not set
	double radioRange = 30 * Math.sqrt(3);
	// double radioRange = 50;
	double nodeFailProbability;
	int k;
	int nodeFailModelID;
	int failNodeNum;

	public AllParam(int networkID, int nodeNum, double queryRegionRate,
			double gridWidthRate, int queryMessageSize, int answerMessageSize,
			int queryAndPatialAnswerSize, int resultSize, double radioRange,
			double nodeFailProbability, int k, int nodeFailModelID,
			int failNodeNum) {
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
		this.nodeFailModelID = nodeFailModelID;
		this.failNodeNum = failNodeNum;
	}

	public double getNodeFailProbability() {
		return nodeFailProbability;
	}

	public void setNodeFailProbability(double nodeFailProbability) {
		this.nodeFailProbability = nodeFailProbability;
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

	public double calculateNodeDensity() {
		return this.nodeNum / this.networkWidth / this.networkHeight;
	}

	// public String toString() {
	// StringBuffer sb = new StringBuffer();
	// sb.append("networkID("+this.networkID+") ");
	// sb.append("nodeNum("+this.nodeNum+") ");
	// sb.append("queryRegionRate("+this.queryRegionRate+") ");
	// sb.append("gridWidthRate("+this.gridWidthRate+") ");
	// sb.append("queryMessageSize("+this.queryMessageSize+") ");
	// sb.append("answerMessageSize("+this.answerMessageSize+") ");
	// sb.append("networkWidth("+this.networkWidth+") ");
	// sb.append("networkHeight("+this.networkHeight+") ");
	// sb.append("radioRange("+this.radioRange+") ");
	//sb.append("queryAndPatialAnswerSize("+this.queryAndPatialAnswerSize+") ");
	// sb.append("resultSize("+this.resultSize+")");
	// sb.append("nodeFailProbability("+this.nodeFailProbability+")");
	// sb.append("k("+this.k+")");
	// sb.append("nodeFailModelID("+this.nodeFailModelID+")");
	// sb.append("failNodeNum("+this.failNodeNum+")");
	// return sb.toString();
	//		
	// }

	public String toString() {
		try {
			return ReflectUtil.objectToString(this);
		} catch (Exception e) {
			e.printStackTrace();
			return e.toString();
		}
	}

	public static AllParam fromString(String str) throws Exception {
		return (AllParam) ReflectUtil.stringToObject(str, AllParam.class);
	}

	public int getK() {
		return k;
	}

	public void setK(int k) {
		this.k = k;
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
