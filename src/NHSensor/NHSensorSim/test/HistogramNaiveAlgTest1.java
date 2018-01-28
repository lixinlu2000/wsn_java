package NHSensor.NHSensorSim.test;

import org.apache.log4j.PropertyConfigurator;

import NHSensor.NHSensorSim.algorithm.HistogramNaiveAlg;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.Node;
import NHSensor.NHSensorSim.core.Param;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.core.Simulator;
import NHSensor.NHSensorSim.query.Attribute;
import NHSensor.NHSensorSim.query.HistogramInfo;
import NHSensor.NHSensorSim.query.HistogramQuery;
import NHSensor.NHSensorSim.query.ValueRange;
import NHSensor.NHSensorSim.sensor.SceneFactory;
import NHSensor.NHSensorSim.sensor.SensorBoardFactory;
import NHSensor.NHSensorSim.ui.Animator;

public class HistogramNaiveAlgTest1 {

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
		int sampleTimes = 5;
		Network network = Network.intelLabNetwork();
		Node sink = network.get2LRTNode();
		HistogramQuery histogramQuery = new HistogramQuery(sink, histogramInfo,
				sampleEpoch, sampleTimes);
		Param param = new Param();
		param.setRADIO_RANGE(6);
		SensorSim sensorSim = new SensorSim(histogramQuery, network,
				new Simulator(), param);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		sensorSim.addAlgorithm(HistogramNaiveAlg.NAME);
		HistogramNaiveAlg alg = (HistogramNaiveAlg) sensorSim
				.getAlgorithm(HistogramNaiveAlg.NAME);
		alg.setSceneAndSensorBoard(SceneFactory.createTestTemperaureScene(),
				SensorBoardFactory.createTestTemperatureSensorBoard());
		sensorSim.run();
		sensorSim.printStatistic();

		Animator animator = new Animator(alg);
		animator.start();
	}
}
