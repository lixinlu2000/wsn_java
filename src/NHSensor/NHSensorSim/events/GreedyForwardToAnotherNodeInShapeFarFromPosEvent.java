package NHSensor.NHSensorSim.events;

import NHSensor.NHSensorSim.algorithm.Algorithm;
import NHSensor.NHSensorSim.core.Message;
import NHSensor.NHSensorSim.core.NeighborAttachment;
import NHSensor.NHSensorSim.exception.SensornetBaseException;
import NHSensor.NHSensorSim.shape.Position;
import NHSensor.NHSensorSim.shape.Shape;

public class GreedyForwardToAnotherNodeInShapeFarFromPosEvent extends ForwardToShapeEvent {
	Position pos;
	double radian;

	public GreedyForwardToAnotherNodeInShapeFarFromPosEvent(NeighborAttachment root, Position pos, double radian,
			Shape shape, Message message, Algorithm alg) {
		super(message, alg);
		this.root = root;
		this.shape = shape;
		this.pos = pos;
		this.radian = radian;
	}

	public void handle() throws SensornetBaseException {
		NeighborAttachment cur = this.getRoot();

		Position dest = this.getShape().getCentre();

		NeighborAttachment next = null;

		RadioEvent event;
		Message msg = this.getMessage();
		

		while (!this.getShape().contains(cur.getNode().getPos())) {
			next = cur.getNextNeighborAttachmentToDest(dest);
			if (cur != next) {
				if(this.getShape().contains(next.getNode().getPos())) {
					next = cur.getNextNeighborAttachmentInShapeFarFromPos(this.getShape(), pos, radian);
				}
				this.getRoute().add(next);
				event = new BroadcastEvent(cur, next, msg, this.getAlg());
				event.setParent(this);
				if (!this.isConsumeEnergy())
					event.setConsumeEnergy(false);
				this.getAlg().getSimulator().handle(event);
				
				cur = next;
			} else {
				// can not greedy
				break;
			}
		}

//		if(this.getShape().contains(cur.getNode().getPos())) {
			next = cur.getNextNeighborAttachmentInShapeFarFromPos(this.getShape(), pos, radian);			
			if (cur != next) {
				this.getRoute().add(next);
				event = new BroadcastEvent(cur, next, msg, this.getAlg());
				event.setParent(this);
				if (!this.isConsumeEnergy())
					event.setConsumeEnergy(false);
				this.getAlg().getSimulator().handle(event);				
				cur = next;
			}
//		}
		
		this.setLastNode(cur);
		
		if (this.getShape().contains(cur.getNode().getPos())) {
			this.setSuccess(true);
		} else {
			this.setSuccess(false);
		}
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("GreedyForwardToAnotherNodeInShapeFarFromPosEvent  ");
		sb.append(this.root.getNode().getPos());
		sb.append("-->");
		sb.append(this.shape.toString());
		return sb.toString();

	}

}
