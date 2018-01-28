package NHSensor.NHSensorSim.ui;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.algorithm.GKNNAlg;
import NHSensor.NHSensorSim.analyser.EventTypeChooser;
import NHSensor.NHSensorSim.events.ItineraryNodeEvent;

public class GKNNItineraryAnimator extends Animator {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1472655879215570615L;

	public GKNNItineraryAnimator() {
		// TODO Auto-generated constructor stub
	}

	public GKNNItineraryAnimator(Algorithm algorithm) {
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
		GKNNSensornetModel gKNNSensornetModel = new GKNNSensornetModel(gKNNAlg,
				new EventTypeChooser(ItineraryNodeEvent.class));

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
