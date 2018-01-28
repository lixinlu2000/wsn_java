package NHSensor.NHSensorSim.papers.HSA;

import NHSensor.NHSensorSim.experiment.ExperimentParam;
import NHSensor.NHSensorSim.util.ReflectUtil;

public class AllParam extends ExperimentParam {
	int networkID;
	int nodeNum;
	double queryRegionRate;
	int queryMessageSize;
	int answerMessageSize;
	double networkWidth = 100;
	double networkHeight = 100;
	int subAreaNum;
	double datasetMax = 10;
	int datasetID;
	double delta;
	double initialEnergy = 1500;
	double radioRange = 10;

	public AllParam() {

	}

	public void setNetworkID(int networkID) {
		this.networkID = networkID;
	}

	public int getNetworkID() {
		return networkID;
	}

	public void setNodeNum(int nodeNum) {
		this.nodeNum = nodeNum;
	}

	public int getNodeNum() {
		return nodeNum;
	}

	public void setQueryRegionRate(double queryRegionRate) {
		this.queryRegionRate = queryRegionRate;
	}

	public double getQueryRegionRate() {
		return queryRegionRate;
	}

	public void setQueryMessageSize(int queryMessageSize) {
		this.queryMessageSize = queryMessageSize;
	}

	public int getQueryMessageSize() {
		return queryMessageSize;
	}

	public void setAnswerMessageSize(int answerMessageSize) {
		this.answerMessageSize = answerMessageSize;
	}

	public int getAnswerMessageSize() {
		return answerMessageSize;
	}

	public void setNetworkWidth(double networkWidth) {
		this.networkWidth = networkWidth;
	}

	public double getNetworkWidth() {
		return networkWidth;
	}

	public void setNetworkHeight(double networkHeight) {
		this.networkHeight = networkHeight;
	}

	public double getNetworkHeight() {
		return networkHeight;
	}

	public void setSubAreaNum(int subAreaNum) {
		this.subAreaNum = subAreaNum;
	}

	public int getSubAreaNum() {
		return subAreaNum;
	}

	public void setDatasetMax(double datasetMax) {
		this.datasetMax = datasetMax;
	}

	public double getDatasetMax() {
		return datasetMax;
	}

	public void setDatasetID(int datasetID) {
		this.datasetID = datasetID;
	}

	public int getDatasetID() {
		return datasetID;
	}

	public void setDelta(double delta) {
		this.delta = delta;
	}

	public double getDelta() {
		return delta;
	}

	public void setInitialEnergy(double initialEnergy) {
		this.initialEnergy = initialEnergy;
	}

	public double getInitialEnergy() {
		return initialEnergy;
	}

	public void setRadioRange(double radioRange) {
		this.radioRange = radioRange;
	}

	public double getRadioRange() {
		return radioRange;
	}

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
}
