package NHSensor.NHSensorSim.sensor;

public class SensorBoardFactory {

	private SensorBoardFactory() {

	}

	public static SensorBoard createTestTemperatureSensorBoard() {
		SensorBoard sensorBoard = new SensorBoard("testTemperature");
		Sensor tempSensor = new Sensor("temperature", "temperature", 0,
				sensorBoard);
		sensorBoard.addSensor(tempSensor);
		return sensorBoard;
	}
}
