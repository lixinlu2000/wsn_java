package NHSensor.NHSensorSim.events;

import java.util.Vector;
import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.shape.Shape;

public class CollectNeighborLocationsEvent extends RadioEvent {
	NeighborAttachment node;
	Shape shapeForNeighbors = null;
	int queryLocationMessageSize = 4;
	int locationMessageSize = 2;
	Vector neighbors; //NeighborAttachment
	Vector neighborNodes = new Vector();

	protected void initNeighbors() {		
		if(this.shapeForNeighbors!=null) {
			neighbors = this.node.getNeighbors(this.shapeForNeighbors);
		}
		else {
			neighbors = this.node.getNeighbors();
		}
		
		NeighborAttachment na;
		for(int i=0;i<neighbors.size();i++) {
			na = (NeighborAttachment)neighbors.elementAt(i);
			neighborNodes.add(na.getNode());
		}
	}
	
	public double getRadioRange() {
		return node.getRadioRange();
	}
	
	public CollectNeighborLocationsEvent(NeighborAttachment node, Algorithm alg) {
		super(alg);
		this.node = node;
		this.setConsumeEnergy(false);
		this.initNeighbors();
	}

	public CollectNeighborLocationsEvent(NeighborAttachment node, Shape shapeForNeighbors, 
			Algorithm alg) {
		super(alg);
		this.node = node;
		this.shapeForNeighbors = shapeForNeighbors;
		this.setConsumeEnergy(false);
		this.initNeighbors();
	}
	
	public void handle() throws SensornetBaseException {
		BroadcastLocationEvent queryLocationEvent = new BroadcastLocationEvent(node, null, 
				new Message(this.queryLocationMessageSize,null), 
				this.getAlg());
		this.getAlg().getSimulator().handle(queryLocationEvent);
		
		BroadcastLocationEvent returnLocationEvent;
		
		NeighborAttachment na;
		for(int i=0;i<neighbors.size();i++) {
			na = (NeighborAttachment)neighbors.elementAt(i);
			returnLocationEvent = new BroadcastLocationEvent(na, this.node, new Message(this.locationMessageSize, null),
					this.getAlg());
			this.getAlg().getSimulator().handle(returnLocationEvent);
		}
	}

	public NeighborAttachment getNode() {
		return node;
	}

	public void setNode(NeighborAttachment node) {
		this.node = node;
	}

	public Shape getShapeForNeighbors() {
		return shapeForNeighbors;
	}

	public void setShapeForNeighbors(Shape shapeForNeighbors) {
		this.shapeForNeighbors = shapeForNeighbors;
	}
	
	public Vector getNeighbors() {
		return neighbors;
	}

	public Vector getNeighborNodes() {
		return neighborNodes;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
			
		sb.append("CollectNeighborLocationsEvent  ");
		sb.append("ID(" + this.getNode().getNode().getId() + ") ");
		sb.append(this.getNode().getNode().getPos());
		sb.append("-->");

		if (this.shapeForNeighbors != null) {
			sb.append(this.shapeForNeighbors.toString());
		} else {
			sb.append("all neighbors");
		}
		return sb.toString();
	}

}
