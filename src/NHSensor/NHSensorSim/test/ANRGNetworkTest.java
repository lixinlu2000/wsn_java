package NHSensor.NHSensorSim.test;

import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.link.LinkEstimatorFactory;

public class ANRGNetworkTest {
	public static void main(String[] args) {
		Network network = Network.anrgNetwork1();
		network.setLinkEstimator(LinkEstimatorFactory
				.getArrayLinkEstimatorFromANRGFile1());
		System.out.println(network);
	}
}
