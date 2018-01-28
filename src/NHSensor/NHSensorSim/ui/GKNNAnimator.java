package NHSensor.NHSensorSim.ui;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.algorithm.GKNNAlg;

public class GKNNAnimator extends Animator {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1472655879215570615L;

	public GKNNAnimator() {
		// TODO Auto-generated constructor stub
	}

	public GKNNAnimator(Algorithm algorithm) {
		super(algorithm);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	protected void initMV() {
		GKNNAlg gKNNAlg = (GKNNAlg) this.getAlgorithm();
		GKNNSensornetModel gKNNSensornetModel = new GKNNSensornetModel(gKNNAlg);

		this.sensornetModel = gKNNSensornetModel;
		gKNNSensornetModel.setAllRingSectors(gKNNAlg.getAllRingSectors());

		int sensornetCanvasWidth = (int) (this.algorithm.getNetwork()
				.getWidth());
		int sensornetCanvasHeight = (int) (this.algorithm.getNetwork()
				.getHeigth());
		this.sensornetCanvas = new GKNNSensornetCanvas(sensornetCanvasWidth,
				sensornetCanvasHeight, this.sensornetModel);

		this.sensornetModel.addSensornetView(sensornetCanvas);
	}

}
