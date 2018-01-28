package NHSensor.NHSensorSim.test;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.PropertyConfigurator;

import com.sun.org.apache.bcel.internal.generic.NEW;

import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.routing.gpsr.GPSRAlg;
import NHSensor.NHSensorSim.ui.Animator;

public class NetworkTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("config/log4j.properties");

		try {
			System.out.println(new File(".").getCanonicalPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Network network = Network
				.createNetworkFromFile(new File(
						"./dataset/dataset/indoor-ceiling-array/2003_05_05/locations-lab.txt"));
		
		System.out.println("The number of node in network: " + network.getNodeNum());
		
		SensorSim sensorSim = new SensorSim(network);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		sensorSim.addAlgorithm(GPSRAlg.NAME);
		GPSRAlg gpsr = (GPSRAlg) sensorSim.getAlgorithm(GPSRAlg.NAME);
		gpsr.getParam().setANSWER_SIZE(30);

		sensorSim.run();
		sensorSim.printStatistic();
		Animator animator = new Animator(gpsr);
		animator.start();
	}

}
