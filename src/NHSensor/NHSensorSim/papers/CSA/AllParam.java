package NHSensor.NHSensorSim.papers.CSA;

import NHSensor.NHSensorSim.experiment.ExperimentParam;
import NHSensor.NHSensorSim.util.ReflectUtil;

public class AllParam extends ExperimentParam {
	int networkID;
	int nodeNum;
	double queryRegionRate;
	double gridWidthRate;
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
	int holeNumber;
	int holeModelID;
	double maxHoleRadius;
	
	int nodeFailModelID;
	int failNodeNum;


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

	public static AllParam fromString(String str) throws Exception {
		return (AllParam) ReflectUtil.stringToObject(str, AllParam.class);
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


	public String toString() {
		try {
			return ReflectUtil.objectToString(this);
		} catch (Exception e) {
			e.printStackTrace();
			return e.toString();
		}
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
