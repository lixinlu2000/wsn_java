package NHSensor.NHSensorSim.ui;

import javax.swing.SwingUtilities;

import org.apache.log4j.PropertyConfigurator;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.algorithm.RSAAlg;

public class RSAAnimator extends Animator {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8415275996113463085L;

	public RSAAnimator() {
		// TODO Auto-generated constructor stub
	}

	public RSAAnimator(Algorithm algorithm) {
		super(algorithm);
		// TODO Auto-generated constructor stub
	}

	protected void initMV() {
		RSAAlg rSAAlg = (RSAAlg) this.getAlgorithm();
		DGSASensornetModel dGSASensornetModel = new DGSASensornetModel(rSAAlg);

		this.sensornetModel = dGSASensornetModel;
		dGSASensornetModel.setRects(rSAAlg.getRects());
		dGSASensornetModel.setSubQueryRegions(rSAAlg.getSubQueryRegions());

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
				Animator animator = new RSAAnimator();
				animator.start();
			}
		});
	}

}
