package NHSensor.NHSensorSim.core;

import org.apache.log4j.PropertyConfigurator;

import NHSensor.NHSensorSim.algorithm.EcstaAlg;
import NHSensor.NHSensorSim.algorithm.SWinFloodAlg;

/**
 * 
 */

/**
 * @author Liang Liu
 * 
 * 
 */
public class Main {

	/**
	 * 
	 */
	public Main() {
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("config/log4j.properties");

		SensorSim sensorSim = SensorSim.createSensorSim("Random2");
		sensorSim.getSimulator().addHandleEventListener();

		sensorSim.addAlgorithm(SWinFloodAlg.NAME);
		sensorSim.addAlgorithm(EcstaAlg.NAME);
		sensorSim.addAlgorithm(TAGAlg.NAME);
		// sensorSim.addAlgorithm(GPSRAlg.NAME);
		// GreedyTAGAlg gtag =
		// (GreedyTAGAlg)sensorSim.addAlgorithm(GreedyTAGAlg.NAME);
		// gtag.setRadioRange(4*Math.sqrt(2));
		// GStarAlg gsa = (GStarAlg)sensorSim.addAlgorithm(GStarAlg.NAME);
		// Position subRoot = new Position(10,10);
		// GStarAlgParam gsap = new
		// GStarAlgParam(100,100,subRoot,Math.sqrt(100*100+200*200));
		// gsa.setAlgParam(gsap);
		System.out.println("running..............");
		sensorSim.run();

		sensorSim.printStatistic();

		System.out.println(sensorSim.getQuery());
		// System.out.println(sensorSim.getNetwork());

		// System.out.println(sensorSim.getAnswers());
	}

}
