package NHSensor.NHSensorSim.tools;

import NHSensor.NHSensorSim.experiment.ExperimentParam;
import NHSensor.NHSensorSim.util.ReflectUtil;

public class AllParam extends ExperimentParam {
	int networkID;
	int nodeNum;
	double queryRegionRate;
	double gridWidthRate;
	double radioRange;
	int queryMessageSize;
	int senseDataSize;
	double networkWidth = 100;
	double networkHeight = 100;
	double initialEnergy;

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

	public void setSenseDataSize(int senseDataSize) {
		this.senseDataSize = senseDataSize;
	}

	public int getSenseDataSize() {
		return senseDataSize;
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
