package NHSensor.NHSensorSim.sensor;

import NHSensor.NHSensorSim.core.Attachment;
import NHSensor.NHSensorSim.core.Time;
import NHSensor.NHSensorSim.exception.HasNoEnergyException;
import NHSensor.NHSensorSim.exception.HasNoVariableException;
import NHSensor.NHSensorSim.shape.Position;

public class Sensor {
	SensorBoard sensorBoard;
	private String name;
	private String physicsVariableName;
	private String description;
	private double sampleConsumedEnergy;

	public Sensor(String name, String physicsVariableName,
			double sampleConsumedEnergy, SensorBoard sensorBoard) {
		this.name = name;
		this.physicsVariableName = physicsVariableName;
		this.sampleConsumedEnergy = sampleConsumedEnergy;
		this.sensorBoard = sensorBoard;
	}

	public Object sample(Scene scene, Position pos, Time t)
			throws HasNoEnergyException, HasNoVariableException {
		sensorBoard.consumeEnergy(this.getSampleConsumedEnergy());
		return scene.get(this.getPhysicsVariableName(), pos, t);
	}

	/*
	 * this method is used to optimize the simulator performance.
	 */
	public Object sample(Attachment attachment, Scene scene, Position pos,
			Time t) throws HasNoEnergyException, HasNoVariableException {
		attachment.consumeEnergy(this.getSampleConsumedEnergy());
		return scene.get(this.getPhysicsVariableName(), pos, t);
	}

	/*
	 * this method is used to optimize the simulator performance.
	 */
	public Object sample(Attachment attachment, Scene scene, int nodeID, Time t)
			throws HasNoEnergyException, HasNoVariableException {
		attachment.consumeEnergy(this.getSampleConsumedEnergy());
		return scene.get(this.getPhysicsVariableName(), nodeID, t);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getSampleConsumedEnergy() {
		return sampleConsumedEnergy;
	}

	public void setSampleConsumedEnergy(double sampleConsumedEnergy) {
		this.sampleConsumedEnergy = sampleConsumedEnergy;
	}

	public SensorBoard getSensorBoard() {
		return sensorBoard;
	}

	public void setSensorBoard(SensorBoard sensorBoard) {
		this.sensorBoard = sensorBoard;
	}

	public String getPhysicsVariableName() {
		return physicsVariableName;
	}

	public void setPhysicsVariableName(String physicsVariableName) {
		this.physicsVariableName = physicsVariableName;
	}
}
