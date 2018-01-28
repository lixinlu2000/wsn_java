package NHSensor.NHSensorSim.sensor;

import java.io.Serializable;
import java.util.Hashtable;

import NHSensor.NHSensorSim.core.Time;
import NHSensor.NHSensorSim.exception.HasNoVariableException;
import NHSensor.NHSensorSim.shape.Position;

public class Scene  implements Serializable{
	private String name;
	private String description;
	private Hashtable variables = new Hashtable();

	public Scene(String name) {
		this.name = name;
	}

	public Scene(String name, String description) {
		this.name = name;
		this.description = description;
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

	public Object get(String physicVariableName, Position position, Time t)
			throws HasNoVariableException {
		PhysicsVariable physicsVariable;
		physicsVariable = (PhysicsVariable) this.getVariables().get(
				physicVariableName);
		if (physicsVariable == null) {
			throw new HasNoVariableException(physicVariableName);
		}
		return physicsVariable.getValue(position, t);
	}

	public Object get(String physicVariableName, int nodeID, Time t)
			throws HasNoVariableException {
		PhysicsVariable physicsVariable;
		physicsVariable = (PhysicsVariable) this.getVariables().get(
				physicVariableName);
		if (physicsVariable == null) {
			throw new HasNoVariableException(physicVariableName);
		}
		return physicsVariable.getValue(nodeID, t);
	}

	public Hashtable getVariables() {
		return variables;
	}

	public void setVariables(Hashtable variables) {
		this.variables = variables;
	}

	public void addVariable(PhysicsVariable physicsVariable) {
		this.getVariables().put(physicsVariable.getPhysicsVariableName(),
				physicsVariable);
	}
}
