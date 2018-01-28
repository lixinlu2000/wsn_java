package NHSensor.NHSensorSim.sensor;

import NHSensor.NHSensorSim.core.*;
import NHSensor.NHSensorSim.shape.Position;

public class PhysicsVariable {
	private String physicsVariableName;
	private Function function;

	public String getPhysicsVariableName() {
		return physicsVariableName;
	}

	public void setPhysicsVariableName(String physicsVariableName) {
		this.physicsVariableName = physicsVariableName;
	}

	public PhysicsVariable(String physicsVariableName) {
		this.physicsVariableName = physicsVariableName;
	}

	public PhysicsVariable(String physicsVariableName, Function function) {
		this.physicsVariableName = physicsVariableName;
		this.function = function;
	}

	public Object getValue(Position position, Time time) {
		if (this.getFunction() instanceof PositionFunction) {
			PositionFunction pf = (PositionFunction) this.getFunction();
			return pf.getValue(position, time);
		}
		return null;
	}

	public Object getValue(int nodeID, Time time) {
		if (this.getFunction() instanceof NodeIDFunction) {
			NodeIDFunction nf = (NodeIDFunction) this.getFunction();
			return nf.getValue(nodeID, time);
		}
		return null;
	}

	public Function getFunction() {
		return function;
	}

	public void setFunction(Function function) {
		this.function = function;
	}
}
