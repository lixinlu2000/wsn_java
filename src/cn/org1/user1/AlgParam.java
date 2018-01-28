package cn.org1.user1;

import NHSensor.NHSensorSim.experiment.ExperimentParam;
import NHSensor.NHSensorSim.papers.E2STA.lifetime.AllParam;
import NHSensor.NHSensorSim.util.ReflectUtil;

public class AlgParam extends ExperimentParam {
	int networkID = 1;
	int nodeNum = 500;
	double queryRegionRate = 0.64;
	double gridWidthRate = 0.8;
	double radioRange = 50;
	int queryMessageSize = 50;
	int answerMessageSize = 40;
	double networkWidth = 600;
	double networkHeight = 400;
	double initialEnergy = 100000;

	public AlgParam() {

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

	public void setGridWidthRate(double gridWidthRate) {
		this.gridWidthRate = gridWidthRate;
	}

	public double getGridWidthRate() {
		return gridWidthRate;
	}

	public void setRadioRange(double radioRange) {
		this.radioRange = radioRange;
	}

	public double getRadioRange() {
		return radioRange;
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

	public void setInitialEnergy(double initialEnergy) {
		this.initialEnergy = initialEnergy;
	}

	public double getInitialEnergy() {
		return initialEnergy;
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
