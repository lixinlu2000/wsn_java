package NHSensor.NHSensorSim.failure;

public class NodeFailureModelFactory {

	public static RandomNodeFailureModel createRandomNodeFailureModel(
			int randomNumber, int failNodeSize) {
		return new RandomNodeFailureModel(randomNumber, failNodeSize);
	}
}
