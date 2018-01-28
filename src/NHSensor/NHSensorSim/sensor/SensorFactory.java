package NHSensor.NHSensorSim.sensor;

public class SensorFactory {

	private SensorFactory() {

	}

	public static SensorBoard createTestSensorBoard() {
		SensorBoard sensorBoard = new SensorBoard("Test");
		Sensor tempSensor = new Sensor("temp", "temp", 1, sensorBoard);
		sensorBoard.addSensor(tempSensor);
		return sensorBoard;
	}
}
