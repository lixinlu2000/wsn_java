package NHSensor.NHSensorSim.analyser;

import NHSensor.NHSensorSim.algorithm.Algorithm;

public class AlgorithmEnergyAnalyser extends AlgorithmAnalyser {
	private double energy;

	public AlgorithmEnergyAnalyser(Algorithm algorithm) {
		super(algorithm);
	}

	public void analyse() {
		this.energy = this.algorithm.getStatistics().getConsumedEnergy();
	}

	public Object getResult() {
		return new Double(this.energy);
	}

}
