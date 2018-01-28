package NHSensor.NHSensorSim.ui;

import javax.swing.SwingUtilities;

import org.apache.log4j.PropertyConfigurator;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.algorithm.DGSAAlg;
import NHSensor.NHSensorSim.algorithm.DGSANewAlg;

public class DGSANewAnimator extends Animator {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8415275996113463085L;

	public DGSANewAnimator() {
		// TODO Auto-generated constructor stub
	}

	public DGSANewAnimator(Algorithm algorithm) {
		super(algorithm);
		// TODO Auto-generated constructor stub
	}

	protected void initMV() {
		DGSANewAlg dGSAAlg = (DGSANewAlg) this.getAlgorithm();
		DGSASensornetModel dGSASensornetModel = new DGSASensornetModel(dGSAAlg);

		this.sensornetModel = dGSASensornetModel;
		dGSASensornetModel.setRects(dGSAAlg.getRects());
		dGSASensornetModel.setSubQueryRegions(dGSAAlg.getSubQueryRegions());

		int sensornetCanvasWidth = (int) (this.algorithm.getNetwork()
				.getWidth());
		int sensornetCanvasHeight = (int) (this.algorithm.getNetwork()
				.getHeigth());
		this.sensornetCanvas = new DGSASensornetCanvas(sensornetCanvasWidth,
				sensornetCanvasHeight, sensornetModel);

		sensornetModel.addSensornetView(sensornetCanvas);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PropertyConfigurator.configure("config/log4j.properties");

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Animator animator = new DGSANewAnimator();
				animator.start();
			}
		});
	}

}
