package NHSensor.NHSensorSim.analyser;

import java.util.Vector;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.events.ItineraryNodeEvent;
import NHSensor.NHSensorSim.experiment.RobustnessResult;

/*
 * NOTE ItineraryNodeEvent in algorithm should be in order!
 */
public class ItineraryNodeEventAnalyser extends AlgorithmAnalyser {
	private Vector itineraryNodeEvents;
	double failProbability;
	RobustnessResult robustnessResult;

	public ItineraryNodeEventAnalyser(Algorithm algorithm,
			double failProbability) {
		super(algorithm);
		EventsDatabase eventsDatabase = new EventsDatabase(this.algorithm
				.getSimulator().getAllEvents());
		this.itineraryNodeEvents = eventsDatabase
				.select(ItineraryNodeEvent.class);
		this.failProbability = failProbability;
	}

	public double calSuccessProbability(int nodeNum) {
		double fp = Math.pow(this.failProbability, nodeNum);
		return 1 - fp;
	}

	public void analyse() {
		double tmpSuccessProbability = 1;
		ItineraryNodeEvent ine;
		int nodeNum;
		double sumBackupNodeCount = 0;
		double averageBackupNodeCount = 0;
		Vector backupNodeCount = new Vector();

		for (int i = 0; i < this.itineraryNodeEvents.size(); i++) {
			ine = (ItineraryNodeEvent) this.itineraryNodeEvents.elementAt(i);
			nodeNum = ine.getNodeCount();
			backupNodeCount.add(new Integer(nodeNum - 1));
			sumBackupNodeCount += (nodeNum - 1);
			tmpSuccessProbability *= calSuccessProbability(nodeNum);
		}

		this.robustnessResult = new RobustnessResult();
		this.robustnessResult.setItineraryNodeCount(this.itineraryNodeEvents
				.size());
		this.robustnessResult.setSuccessProbability(tmpSuccessProbability);
		if (this.itineraryNodeEvents.size() != 0) {
			averageBackupNodeCount = sumBackupNodeCount
					/ this.itineraryNodeEvents.size();
		}
		this.robustnessResult.setAverageBackupNodeCount(averageBackupNodeCount);
		this.robustnessResult.setBackupNodeCount(backupNodeCount);
		this.robustnessResult.setSuccess(this.getAlgorithm().getStatistics()
				.isSuccess());
	}

	public double getFailProbability() {
		return failProbability;
	}

	public void setFailProbability(double failProbability) {
		this.failProbability = failProbability;
	}

	public Object getResult() {
		return this.robustnessResult;
	}
}
