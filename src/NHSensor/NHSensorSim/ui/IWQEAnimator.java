package NHSensor.NHSensorSim.ui;

import javax.swing.SwingUtilities;

import org.apache.log4j.PropertyConfigurator;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.algorithm.IWQEAlg;

public class IWQEAnimator extends Animator {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1499673757914108914L;

	public IWQEAnimator() {
		// TODO Auto-generated constructor stub
	}

	public IWQEAnimator(Algorithm algorithm) {
		super(algorithm);
		// TODO Auto-generated constructor stub
	}

	protected void initMV() {
		IWQEAlg iWQEAlg = (IWQEAlg) this.getAlgorithm();
		IWQESensornetModel iWQESensornetModel = new IWQESensornetModel(iWQEAlg);

		this.sensornetModel = iWQESensornetModel;
		iWQESensornetModel.setSubQueryRegions(iWQEAlg.getSubQueryRegions());

		int sensornetCanvasWidth = (int) (this.algorithm.getNetwork()
				.getWidth());
		int sensornetCanvasHeight = (int) (this.algorithm.getNetwork()
				.getHeigth());
		this.sensornetCanvas = new IWQESensornetCanvas(sensornetCanvasWidth,
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
				Animator animator = new IWQEAnimator();
				animator.start();
			}
		});
	}

}
