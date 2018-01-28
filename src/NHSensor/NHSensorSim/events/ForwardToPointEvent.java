package NHSensor.NHSensorSim.events;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.shape.Position;

public class ForwardToPointEvent extends ForwardToShapeEvent {

	public Position getDest() {
		return (Position) this.getShape();
	}

	public void setDest(Position dest) {
		this.shape = dest;
	}

	public ForwardToPointEvent(Message message, Algorithm alg) {
		super(message, alg);
		// TODO Auto-generated constructor stub
	}

	public void handle() throws SensornetBaseException {
		// TODO Auto-generated method stub

	}

}
