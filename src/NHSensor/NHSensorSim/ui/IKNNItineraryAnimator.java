package NHSensor.NHSensorSim.ui;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.algorithm.IKNNAlg;
import NHSensor.NHSensorSim.analyser.EventTypeChooser;
import NHSensor.NHSensorSim.events.ItineraryNodeEvent;

public class IKNNItineraryAnimator extends Animator {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1472655879215570615L;

	public IKNNItineraryAnimator() {
		// TODO Auto-generated constructor stub
	}

	public IKNNItineraryAnimator(Algorithm algorithm) {
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
		IKNNAlg alg = (IKNNAlg) this.getAlgorithm();
		SensornetModel sensornetModel = new SensornetModel(alg,
				new EventTypeChooser(ItineraryNodeEvent.class));

		this.sensornetModel = sensornetModel;

		int sensornetCanvasWidth = (int) (this.algorithm.getNetwork()
				.getWidth());
		int sensornetCanvasHeight = (int) (this.algorithm.getNetwork()
				.getHeigth());
		this.sensornetCanvas = new SensornetCanvas(sensornetCanvasWidth,
				sensornetCanvasHeight, this.sensornetModel);

		this.sensornetModel.addSensornetView(sensornetCanvas);
	}

}
