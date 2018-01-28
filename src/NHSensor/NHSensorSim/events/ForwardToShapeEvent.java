package NHSensor.NHSensorSim.events;

import java.util.Vector;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.shape.Shape;

public abstract class ForwardToShapeEvent extends TransferEvent {
	protected NeighborAttachment root;
	protected Shape shape;
	protected NeighborAttachment lastNode;
	protected Vector route = new Vector();

	public ForwardToShapeEvent(Message message, Algorithm alg) {
		super(message, alg);
	}

	public NeighborAttachment getRoot() {
		return root;
	}

	public void setRoot(NeighborAttachment root) {
		this.root = root;
	}

	public NeighborAttachment getLastNode() {
		return lastNode;
	}

	public Shape getShape() {
		return shape;
	}

	public void setShape(Shape shape) {
		this.shape = shape;
	}

	public void setLastNode(NeighborAttachment lastNode) {
		this.lastNode = lastNode;
	}

	public Vector getRoute() {
		return route;
	}

	public int getRouteSize() {
		return this.route.size();
	}
	
	public int getHopCount() {
		return this.route.size();
	}

}
