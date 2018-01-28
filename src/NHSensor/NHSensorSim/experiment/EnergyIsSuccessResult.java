package NHSensor.NHSensorSim.experiment;

public class EnergyIsSuccessResult implements ExperimentResult {
	double consumedEnergy;
	boolean isSuccess;
	double packetFrameNum;
	double packetFrameNumNeed;
	double queryResultCorrectRate;
	int itineraryNodeSize;
	int queryNodeNum;
	double backupNodeNum;
	double communicationTime;
	double maintainLocationsFrameNum;
	double maintainLocationsFrameNumRate;

	public EnergyIsSuccessResult(double consumedEnergy, boolean isSuccess) {
		this.isSuccess = isSuccess;
		this.consumedEnergy = consumedEnergy;
	}

	public EnergyIsSuccessResult(double consumedEnergy, int queryNodeNum,
			double backupNodeNum, boolean isSuccess) {
		super();
		this.consumedEnergy = consumedEnergy;
		this.isSuccess = isSuccess;
		this.queryNodeNum = queryNodeNum;
		this.backupNodeNum = backupNodeNum;
	}

	public EnergyIsSuccessResult(double consumedEnergy, boolean isSuccess,
			double packetFrameNum, double packetFrameNumNeed,
			double queryResultCorrectRate, int itineraryNodeSize,
			int queryNodeNum) {
		super();
		this.consumedEnergy = consumedEnergy;
		this.isSuccess = isSuccess;
		this.packetFrameNum = packetFrameNum;
		this.packetFrameNumNeed = packetFrameNumNeed;
		this.queryResultCorrectRate = queryResultCorrectRate;
		this.itineraryNodeSize = itineraryNodeSize;
		this.queryNodeNum = queryNodeNum;
	}
	
	public EnergyIsSuccessResult(double consumedEnergy, boolean isSuccess,
			double packetFrameNum, double packetFrameNumNeed,
			double queryResultCorrectRate, int itineraryNodeSize,
			int queryNodeNum, double communicationTime,
			double maintainLocationsFrameNum) {
		super();
		this.consumedEnergy = consumedEnergy;
		this.isSuccess = isSuccess;
		this.packetFrameNum = packetFrameNum;
		this.packetFrameNumNeed = packetFrameNumNeed;
		this.queryResultCorrectRate = queryResultCorrectRate;
		this.itineraryNodeSize = itineraryNodeSize;
		this.queryNodeNum = queryNodeNum;
		this.communicationTime = communicationTime;
		this.maintainLocationsFrameNum = maintainLocationsFrameNum;
		this.maintainLocationsFrameNumRate = this.maintainLocationsFrameNum/this.packetFrameNum;
	}

	public EnergyIsSuccessResult(double consumedEnergy, boolean isSuccess,
			double packetFrameNum, double packetFrameNumNeed,
			double queryResultCorrectRate, int itineraryNodeSize) {
		super();
		this.consumedEnergy = consumedEnergy;
		this.isSuccess = isSuccess;
		this.packetFrameNum = packetFrameNum;
		this.packetFrameNumNeed = packetFrameNumNeed;
		this.queryResultCorrectRate = queryResultCorrectRate;
		this.itineraryNodeSize = itineraryNodeSize;
	}

	public EnergyIsSuccessResult(double consumedEnergy, boolean isSuccess,
			double packetFrameNum) {
		super();
		this.consumedEnergy = consumedEnergy;
		this.isSuccess = isSuccess;
		this.packetFrameNum = packetFrameNum;
	}

	public double getConsumedEnergy() {
		return consumedEnergy;
	}

	public void setConsumedEnergy(double consumedEnergy) {
		this.consumedEnergy = consumedEnergy;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public int getIsSuccess() {
		if (this.isSuccess)
			return 1;
		else
			return 0;
	}

	public double getPacketFrameNum() {
		return packetFrameNum;
	}

	public void setPacketFrameNum(double packetFrameNum) {
		this.packetFrameNum = packetFrameNum;
	}

	public double getPacketFrameNumNeed() {
		return packetFrameNumNeed;
	}

	public void setPacketFrameNumNeed(double packetFrameNumNeed) {
		this.packetFrameNumNeed = packetFrameNumNeed;
	}

	public double getQueryResultCorrectRate() {
		return queryResultCorrectRate;
	}

	public void setQueryResultCorrectRate(double queryResultCorrectRate) {
		this.queryResultCorrectRate = queryResultCorrectRate;
	}

	public int getItineraryNodeSize() {
		return itineraryNodeSize;
	}

	public void setItineraryNodeSize(int itineraryNodeSize) {
		this.itineraryNodeSize = itineraryNodeSize;
	}

	public int getQueryNodeNum() {
		return queryNodeNum;
	}

	public void setQueryNodeNum(int queryNodeNum) {
		this.queryNodeNum = queryNodeNum;
	}

	public double getBackupNodeNum() {
		return backupNodeNum;
	}

	public void setBackupNodeNum(double backupNodeNum) {
		this.backupNodeNum = backupNodeNum;
	}

	public double getCommunicationTime() {
		return communicationTime;
	}

	public void setCommunicationTime(double communicationTime) {
		this.communicationTime = communicationTime;
	}

	public double getMaintainLocationsFrameNum() {
		return maintainLocationsFrameNum;
	}

	public void setMaintainLocationsFrameNum(
			double maintainLocationsFrameNum) {
		this.maintainLocationsFrameNum = maintainLocationsFrameNum;
	}

	public double getMaintainLocationsFrameNumRate() {
		return maintainLocationsFrameNumRate;
	}
}
