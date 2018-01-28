package NHSensor.NHSensorSim.experiment;

public class NI_NN_QRR_GW_Param extends ExperimentParam {
	int networkID;
	int nodeNum;
	double queryRegionRate;
	double gridWidthRate;

	public NI_NN_QRR_GW_Param(int networkID, int nodeNum,
			double queryRegionRate, double gridWidthRate) {
		this.networkID = networkID;
		this.nodeNum = nodeNum;
		this.queryRegionRate = queryRegionRate;
		this.gridWidthRate = gridWidthRate;
	}

	public NI_NN_QRR_GW_Param() {
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

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("(");
		sb.append(this.getNetworkID() + ",");
		sb.append(this.getNodeNum() + ",");
		sb.append(this.getQueryRegionRate() + ",");
		sb.append(this.getGridWidthRate());
		sb.append(")");
		return sb.toString();
	}
}
