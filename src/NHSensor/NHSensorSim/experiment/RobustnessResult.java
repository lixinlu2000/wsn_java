package NHSensor.NHSensorSim.experiment;

import java.util.Vector;

public class RobustnessResult implements ExperimentResult {
	boolean isSuccess;
	int itineraryNodeCount;
	Vector backupNodeCount;
	double successProbability;
	double averageBackupNodeCount;

	public RobustnessResult() {
	}

	public RobustnessResult(int itineraryNodeCount, Vector backupNodeCount,
			double successProbability, double averageBackupNodeCount,
			boolean isSuccess) {
		this.itineraryNodeCount = itineraryNodeCount;
		this.backupNodeCount = backupNodeCount;
		this.successProbability = successProbability;
		this.averageBackupNodeCount = averageBackupNodeCount;
		this.isSuccess = isSuccess;
	}

	public double getSuccessProbability() {
		return successProbability;
	}

	public void setSuccessProbability(double successProbability) {
		this.successProbability = successProbability;
	}

	public int getItineraryNodeCount() {
		return itineraryNodeCount;
	}

	public void setItineraryNodeCount(int itineraryNodeCount) {
		this.itineraryNodeCount = itineraryNodeCount;
	}

	public Vector getBackupNodeCount() {
		return backupNodeCount;
	}

	public void setBackupNodeCount(Vector backupNodeCount) {
		this.backupNodeCount = backupNodeCount;
	}

	public double getAverageBackupNodeCount() {
		return averageBackupNodeCount;
	}

	public void setAverageBackupNodeCount(double averageBackupNodeCount) {
		this.averageBackupNodeCount = averageBackupNodeCount;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public boolean isSuccess() {
		return true;
	}

	public int getIsSuccess() {
		if (this.isSuccess)
			return 1;
		else
			return 0;
	}

}
