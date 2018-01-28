package NHSensor.NHSensorSim.experiment;

public class SensornetExperimentParam extends ExperimentParam {
	protected int networkID;
	protected int nodeNum;
	protected int queryMessageSize;
	protected int answerMessageSize;
	protected double networkWidth = 450;
	protected double networkHeight = 450;

	// TODO strange code
	//protected double nodeDensity;
	//protected double neighborNodeNum;

	public SensornetExperimentParam(int answerMessageSize,
			double networkHeight, int networkID, double networkWidth,
			int nodeNum, int queryMessageSize, double radioRange) {
		super();
		this.answerMessageSize = answerMessageSize;
		this.networkHeight = networkHeight;
		this.networkID = networkID;
		this.networkWidth = networkWidth;
		this.nodeNum = nodeNum;
		this.queryMessageSize = queryMessageSize;
	}

	// TODO strange code
	public double getNodeDensity() {
		return this.nodeNum / (this.networkWidth * this.networkHeight);
	}

	// TODO strange code
	public double getNeighborNodeNum() {
		double radioRange = 30 * Math.sqrt(3);
		return this.getNodeDensity() * Math.PI * radioRange
				* radioRange - 1;
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

	public SensornetExperimentParam() {
		// TODO Auto-generated constructor stub
	}

}
