package NHSensor.NHSensorSim.ui;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.algorithm.GSAAlg;

public class GSAAnimator extends Animator {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2996600838344021782L;

	public GSAAnimator() {
		// TODO Auto-generated constructor stub
	}

	public GSAAnimator(Algorithm algorithm) {
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
		GSAAlg gSAAlg = (GSAAlg) this.getAlgorithm();
		GSASensornetModel gSASensornetModel = new GSASensornetModel(gSAAlg);

		this.sensornetModel = gSASensornetModel;
		gSASensornetModel.setGrids(gSAAlg.getGrids());
		gSASensornetModel.setXnum(gSAAlg.getXGridNum());
		gSASensornetModel.setYnum(gSAAlg.getYGridNum());

		int sensornetCanvasWidth = (int) (this.algorithm.getNetwork()
				.getWidth());
		int sensornetCanvasHeight = (int) (this.algorithm.getNetwork()
				.getHeigth());
		this.sensornetCanvas = new GSASensornetCanvas(sensornetCanvasWidth,
				sensornetCanvasHeight, sensornetModel);

		sensornetModel.addSensornetView(sensornetCanvas);
	}

}
