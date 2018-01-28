package NHSensor.NHSensorSim.sensor;

import java.util.Hashtable;

import NHSensor.NHSensorSim.core.Attachment;
import NHSensor.NHSensorSim.core.Time;
import NHSensor.NHSensorSim.exception.HasNoEnergyException;
import NHSensor.NHSensorSim.exception.HasNoVariableException;
import NHSensor.NHSensorSim.shape.Position;

public class SensorBoard {
	private Attachment attachment;
	private String name = "Default";
	private String description = "Default Sensor Board";
	private Hashtable sensors = new Hashtable();

	public SensorBoard() {

	}

	public SensorBoard(String name) {
		this.name = name;
	}

	public SensorBoard(String name, String description) {
		this.name = name;
		this.description = description;
	}

	protected Object sample(Scene scene, String sensorName, Position pos, Time t)
			throws HasNoEnergyException {
		Sensor sensor = this.getSensor(sensorName);
		Object object = null;
		try {
			object = sensor.sample(scene, pos, t);
		} catch (HasNoVariableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return object;
	}

	protected Object sample(Scene scene, String sensorName)
			throws HasNoEnergyException {
		return this.sample(scene, sensorName, this.getAttachment().getNode()
				.getPos(), Time.getCurrentTime());
	}

	public Object sample(String sensorName) throws HasNoEnergyException {
		return this.sample(this.getAttachment().getScene(), sensorName, this
				.getAttachment().getNode().getPos(), Time.getCurrentTime());
	}

	public Object sample(Attachment attachment, Scene scene, String sensorName,
			Position pos, Time t) throws HasNoEnergyException {
		Sensor sensor = this.getSensor(sensorName);
		Object object = null;
		try {
			object = sensor.sample(attachment, scene, pos, t);
		} catch (HasNoVariableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return object;
	}

	public Object sample(Attachment attachment, Scene scene, String sensorName,
			int nodeID, Time t) throws HasNoEnergyException {
		Sensor sensor = this.getSensor(sensorName);
		Object object = null;
		try {
			object = sensor.sample(attachment, scene, nodeID, t);
		} catch (HasNoVariableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return object;
	}

	public void consumeEnergy(double energy) throws HasNoEnergyException {
		this.getAttachment().consumeEnergy(energy);
	}

	public Attachment getAttachment() {
		return attachment;
	}

	public void setAttachment(Attachment attachment) {
		this.attachment = attachment;
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

	public Hashtable getSensors() {
		return sensors;
	}

	public void setSensors(Hashtable sensors) {
		this.sensors = sensors;
	}

	public Sensor getSensor(String name) {
		return (Sensor) this.getSensors().get(name);
	}

	public void addSensor(Sensor sensor) {
		this.getSensors().put(sensor.getName(), sensor);
	}
}
