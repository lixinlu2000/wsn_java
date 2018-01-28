package NHSensor.NHSensorSim.demo;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.log4j.PropertyConfigurator;
import org.pushingpixels.substance.api.SubstanceLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceMistSilverLookAndFeel;

import NHSensor.NHSensorSim.algorithm.ESAUseGBAFailureAwareFinalAlg;
import NHSensor.NHSensorSim.core.Network;
import NHSensor.NHSensorSim.core.SensorSim;
import NHSensor.NHSensorSim.failure.NodeFailureModelFactory;
import NHSensor.NHSensorSim.ui.Animator;

public class ESAUseGBAFailureAwareFinalTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("config/log4j.properties");
		try {
			UIManager.setLookAndFeel(new SubstanceMistSilverLookAndFeel());
			JFrame.setDefaultLookAndFeelDecorated(true);
			JDialog.setDefaultLookAndFeelDecorated(true);
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		SensorSim sensorSim = SensorSim.createSensorSim(6, 450, 450, 500, 0.36);
		sensorSim.getSimulator().addHandleAndTraceEventListener();

		sensorSim.addAlgorithm(ESAUseGBAFailureAwareFinalAlg.NAME);
		ESAUseGBAFailureAwareFinalAlg esa = (ESAUseGBAFailureAwareFinalAlg) sensorSim
				.getAlgorithm(ESAUseGBAFailureAwareFinalAlg.NAME);
		esa.getParam().setANSWER_SIZE(30);

		Network network = esa.getNetwork();
		network.setNodeFailureModel(NodeFailureModelFactory
				.createRandomNodeFailureModel(0, 10));

		double subQueryRegionWidth = Math.sqrt(3) / 2
				* esa.getParam().getRADIO_RANGE();
		esa.initSubQueryRegionAndGridsByRegionWidth(subQueryRegionWidth);

		sensorSim.run();
		sensorSim.printStatistic();
		Animator animator = new Animator(esa);
		animator.start();
	}

}
