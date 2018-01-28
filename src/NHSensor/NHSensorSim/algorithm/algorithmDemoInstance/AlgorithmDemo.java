package NHSensor.NHSensorSim.algorithm.algorithmDemoInstance;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Network;

public abstract class AlgorithmDemo {
	public abstract Algorithm getRunnedAlgorithmInstance();
	public abstract Algorithm getRunnedAlgorithmInstance(Network network);
}
