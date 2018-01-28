package NHSensor.NHSensorSim.core;

public class GlobalConstants {
	private boolean debug = false;
	private boolean onDemandCollectNeighborLocations = false;
	public static String EXPERIMENT_RESULT_FILE_PATH = "./result";

	private static GlobalConstants instance = new GlobalConstants();

	private GlobalConstants() {

	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public static GlobalConstants getInstance() {
		return GlobalConstants.instance;
	}

	public boolean isOnDemandCollectNeighborLocations() {
		return onDemandCollectNeighborLocations;
	}

	public void setOnDemandCollectNeighborLocations(
			boolean onDemandCollectNeighborLocations) {
		this.onDemandCollectNeighborLocations = onDemandCollectNeighborLocations;
	}
}
