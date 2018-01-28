package cn.org1.user1;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.algorithm.algorithmDemoInstance.AlgorithmDemo;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.core.SensorSimWithNetwork;

public class ThirdPartyAlg1Demo extends AlgorithmDemo {

	public ThirdPartyAlg1Demo() {
	}

	public Algorithm getRunnedAlgorithmInstance() {
		SensorSim sensorSim = SensorSim.createSensorSim(1, 600, 400, 250, 0.6);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		sensorSim.addAlgorithm(ThirdPartyAlg1.class.getCanonicalName());
		ThirdPartyAlg1 alg = (ThirdPartyAlg1) sensorSim
				.getAlgorithm(ThirdPartyAlg1.class.getCanonicalName());
		alg.getParam().setANSWER_SIZE(30);
		alg.getParam().setRADIO_RANGE(50);

		sensorSim.run();
		return alg;
	}
	
	public Algorithm getRunnedAlgorithmInstance(Network network) {
		SensorSimWithNetwork sensorSim = SensorSimWithNetwork.createSensorSim(network, 0.6);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		sensorSim.addAlgorithm(ThirdPartyAlg1.class.getCanonicalName());
		ThirdPartyAlg1 alg = (ThirdPartyAlg1) sensorSim
				.getAlgorithm(ThirdPartyAlg1.class.getCanonicalName());
		alg.getParam().setANSWER_SIZE(30);
		alg.getParam().setRADIO_RANGE(50);

		sensorSim.run();
		return alg;
	}
}
