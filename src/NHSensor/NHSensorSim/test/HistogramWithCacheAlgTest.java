package NHSensor.NHSensorSim.test;

import org.apache.log4j.PropertyConfigurator;

import NHSensor.NHSensorSim.algorithm.HistogramWithCacheAlg;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.query.Attribute;
import NHSensor.NHSensorSim.query.HistogramInfo;
import NHSensor.NHSensorSim.query.ValueRange;
import NHSensor.NHSensorSim.sensor.SceneFactory;
import NHSensor.NHSensorSim.sensor.SensorBoardFactory;
import NHSensor.NHSensorSim.ui.Animator;

public class HistogramWithCacheAlgTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("config/log4j.properties");

		HistogramInfo histogramInfo = new HistogramInfo(new Attribute(
				"temperature"));
		histogramInfo.addValueRange(new ValueRange(0, 10, true, false));
		histogramInfo.addValueRange(new ValueRange(10, 20, true, false));
		histogramInfo.addValueRange(new ValueRange(30, 40, true, false));
		histogramInfo.addValueRange(new ValueRange(40, 50, true, true));

		int sampleEpoch = 1;
		int sampleTimes = 2;
		double radioRange = 50;
		SensorSim sensorSim = SensorSim.createHistogramSensorSim(0, 450, 450,
				200, histogramInfo, sampleEpoch, sampleTimes, radioRange);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		sensorSim.addAlgorithm(HistogramWithCacheAlg.NAME);
		HistogramWithCacheAlg alg = (HistogramWithCacheAlg) sensorSim
				.getAlgorithm(HistogramWithCacheAlg.NAME);
		alg.setSceneAndSensorBoard(SceneFactory.createTestTemperaureScene(),
				SensorBoardFactory.createTestTemperatureSensorBoard());
		sensorSim.run();
		sensorSim.printStatistic();

		Animator animator = new Animator(alg);
		animator.start();
	}
}
