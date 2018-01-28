package NHSensor.NHSensorSim.core;

public class Statistics {
	private double consumedEnergy = 0;
	private double maintainLocationsFrameNum = 0;
	// all packet frames including retransmission
	private double packetFrameNum = 0;
	// packet need to send not including retransmission
	private double packetFrameNumNeed = 0;
	private String algName;
	private boolean isSuccess = false;
	private QueryResultCorrectness queryResultCorrectness = new QueryResultCorrectness(
			0, 0, Double.MAX_VALUE);

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public Statistics(String algName) {
		this.algName = algName;
	}

	public String getAlgName() {
		return algName;
	}

	public void setAlgName(String algName) {
		this.algName = algName;
	}

	public double getConsumedEnergy() {
		return consumedEnergy;
	}

	public void setConsumedEnergy(double consumedEnergy) {
		this.consumedEnergy = consumedEnergy;
	}

	public void addConsumedEnergy(double energy) {
		this.consumedEnergy += energy;
	}
	
	public void addMaintainLocationsFrameNum(double maintainLocationsFrameNum) {
		this.maintainLocationsFrameNum += maintainLocationsFrameNum;
	}

	public double getPacketFrameNum() {
		return packetFrameNum;
	}

	public void setPacketFrameNum(double packetFrameNum) {
		this.packetFrameNum = packetFrameNum;
	}

	public void addPacketFrameNum(double newPacketFrameNum) {
		this.packetFrameNum += newPacketFrameNum;
	}

	public double getPacketFrameNumNeed() {
		return packetFrameNumNeed;
	}

	public void setPacketFrameNumNeed(double packetFrameNumNeed) {
		this.packetFrameNumNeed = packetFrameNumNeed;
	}

	public void addPacketFrameNumNeed(double newPacketFrameNum) {
		this.packetFrameNumNeed += newPacketFrameNum;
	}

	public void countPacketFrame(double newPacketFrameNum, double times) {
		this.addPacketFrameNum(newPacketFrameNum * times);
		this.addPacketFrameNumNeed(newPacketFrameNum);
	}

	public QueryResultCorrectness getQueryResultCorrectness() {
		return queryResultCorrectness;
	}

	public void setQueryResultCorrectness(
			QueryResultCorrectness queryResultCorrectness) {
		this.queryResultCorrectness = queryResultCorrectness;
	}

	public double getMaintainLocationsFrameNum() {
		return maintainLocationsFrameNum;
	}

	public void printResult() {
		System.out.println(this.algName + " consumed energy: "
				+ this.consumedEnergy);
		System.out.println(" Packet Frame Num: " + this.packetFrameNum);
		System.out
				.println(" Packet Frame Num Need: " + this.packetFrameNumNeed);
		if (this.getQueryResultCorrectness() != null) {
			System.out.println(this.getQueryResultCorrectness().toString());
		}
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(this.algName);
		sb.append(" consumed energy: " + this.consumedEnergy);
		sb.append(" PacketFrameNum: " + this.packetFrameNum);
		sb.append(" PacketFrameNumNeed: " + this.packetFrameNumNeed);
		sb.append(this.getQueryResultCorrectness().toString());
		return sb.toString();
	}
}
