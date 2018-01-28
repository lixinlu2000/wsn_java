package NHSensor.NHSensorSim.test;

import org.apache.log4j.PropertyConfigurator;

import NHSensor.NHSensorSim.algorithm.IWQEAlg;
import NHSensor.NHSensorSim.core.ItineraryAlgParam;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.ui.IWQEAnimator;

public class IWQETest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("config/log4j.properties");

		SensorSim sensorSim = SensorSim.createSensorSim(1, 450, 450, 400, 0.36);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		sensorSim.addAlgorithm(IWQEAlg.NAME);
		IWQEAlg iwqe = (IWQEAlg) sensorSim.getAlgorithm(IWQEAlg.NAME);
		ItineraryAlgParam algParam = new ItineraryAlgParam(4);
		iwqe.getParam().setANSWER_SIZE(30);
		algParam.setAnswerSize(iwqe.getParam().getANSWER_SIZE());
		algParam.setQuerySize(iwqe.getParam().getQUERY_MESSAGE_SIZE());
		algParam.setQueryAndPatialAnswerSize(iwqe.getParam()
				.getQUERY_MESSAGE_SIZE()
				+ iwqe.getParam().getANSWER_SIZE());
		algParam.setResultSize(iwqe.getParam().getANSWER_SIZE());
		iwqe.setAlgParam(algParam);

		double subQueryRegionWidth = Math.sqrt(3) / 2
				* iwqe.getParam().getRADIO_RANGE();
		iwqe.initSubQueryRegionAndGridsByRegionWidth(subQueryRegionWidth);
		sensorSim.run();
		sensorSim.printStatistic();

		IWQEAnimator animator = new IWQEAnimator(iwqe);
		animator.start();
	}

}
